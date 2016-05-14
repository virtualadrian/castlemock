/*
 * Copyright 2016 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.web.mock.websocket.stomp.web.mvc.controller.project;

import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompResourceStatus;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompApplicationDto;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompProjectDto;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.ReadWebSocketStompProjectInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.output.ReadWebSocketStompProjectOutput;
import com.castlemock.web.mock.websocket.stomp.web.mvc.command.application.DeleteWebSocketStompApplicationsCommand;
import com.castlemock.web.mock.websocket.stomp.web.mvc.command.application.UpdateWebSocketStompApplicationsEndpointCommand;
import com.castlemock.web.mock.websocket.stomp.web.mvc.command.application.WebSocketStompApplicationModifierCommand;
import com.castlemock.web.mock.websocket.stomp.web.mvc.controller.AbstractWebSocketStompViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * The project controller provides functionality to retrieve a specific project
 * @author Karl Dahlgren
 * @since 1.5
 */
@Controller
@RequestMapping("/web/wss/project")
public class WebSocketStompProjectController extends AbstractWebSocketStompViewController {

    private static final String PAGE = "mock/websocket/stomp/project/webSocketStompProject";
    private static final String DELETE_WEBSOCKET_STOMP_APPLICATIONS_PAGE = "mock/websocket/stomp/application/deleteWebSocketStompApplications";
    private static final String UPDATE_WEBSOCKET_STOMP_APPLICATIONS_ENDPOINT_PAGE = "mock/websocket/stomp/application/updateWebSocketStompApplicationEndpoint";
    private static final String UPDATE_STATUS = "update";
    private static final String DELETE_WEBSOCKET_STOPM_APPLICATION = "delete";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String DELETE_WEBSOCKET_STOMP_PORTS_COMMAND = "deleteWebSocketStompApplicationsCommand";
    private static final String WEBSOCKET_STOMP_APPLICATION_MODIFIER_COMMAND = "webSocketStompApplicationModifierCommand";
    private static final String UPDATE_WEBSOCKET_STOMP_PORTS_ENDPOINT_COMMAND = "updateWebSocketStompApplicationsEndpointCommand";
    private static final Logger LOGGER = Logger.getLogger(WebSocketStompProjectController.class);

    /**
     * Retrieves a specific project with a project id
     * @param projectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ModelAndView getProject(@PathVariable final String projectId) {
        final ReadWebSocketStompProjectOutput readWebSocketStompProjectOutput = serviceProcessor.process(new ReadWebSocketStompProjectInput(projectId));
        final WebSocketStompProjectDto project = readWebSocketStompProjectOutput.getWebSocketStompProject();
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_STOMP_PROJECT, project);
        model.addObject(WEBSOCKET_STOMP_RESOURCE_STATUSES, getWebSocketStompResourceStatuses());
        model.addObject(WEBSOCKET_STOMP_APPLICATION_MODIFIER_COMMAND, new WebSocketStompApplicationModifierCommand());
        model.addObject(DEMO_MODE, demoMode);
        return model;
    }

    /**
     * The method projectFunctionality provides multiple functionalities: Update ports' status
     * and delete ports. Both of the functionalities involves a set of ports that belongs
     * to a specific project
     * @param projectId The id of the project that the ports belong to
     * @param action The name of the action that should be executed (delete or update).
     * @param webSocketStompApplicationModifierCommand The command object that contains the list of ports that get affected by the executed action.
     * @return Redirects the user back to the main page for the project with the provided id.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.POST)
    public ModelAndView projectFunctionality(@PathVariable final String projectId, @RequestParam final String action, @ModelAttribute final WebSocketStompApplicationModifierCommand webSocketStompApplicationModifierCommand) {
        LOGGER.debug("Requested WebSocket Stomp project action requested: " + action);
        /*
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final WebSocketStompResourceStatus webSocketStompResourceStatus = WebSocketStompResourceStatus.valueOf(webSocketStompApplicationModifierCommand.getWebSocketStompApplicationStatus());
            for(String websocketStompApplicationId : webSocketStompApplicationModifierCommand.getWebSocketStompApplicationIds()){
                serviceProcessor.process(new UpdateWebSocketStompApplicationsStatusInput(projectId, websocketStompApplicationId, webSocketStompResourceStatus));
            }
        } else if(DELETE_WEBSOCKET_STOPM_APPLICATION.equalsIgnoreCase(action)) {
            final List<WebSocketStompApplicationDto> webSocketStompApplicationDtos = new ArrayList<WebSocketStompApplicationDto>();
            for(String websocketStompApplicationId : webSocketStompApplicationModifierCommand.getWebSocketStompApplicationIds()){
                final ReadWebSocketStompApplicationOutput output = serviceProcessor.process(new ReadWebSocketStompApplicationInput(projectId, websocketStompApplicationId));
                webSocketStompApplicationDtos.add(output.getWebSocketStompApplication());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_WEBSOCKET_STOMP_PORTS_PAGE);
            model.addObject(WEBSOCKET_STOMP_PROJECT_ID, projectId);
            model.addObject(WEBSOCKET_STOMP_RESOURCES, webSocketStompApplicationDtos);
            model.addObject(DELETE_WEBSOCKET_STOMP_PORTS_COMMAND, new DeleteWebSocketStompApplicationsCommand());
            return model;
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<WebSocketStompApplicationDto> webSocketStompApplicationDtos = new ArrayList<WebSocketStompApplicationDto>();
            for(String websocketStompApplicationId : webSocketStompApplicationModifierCommand.getWebSocketStompApplicationIds()){
                final ReadWebSocketStompApplicationOutput output = serviceProcessor.process(new ReadWebSocketStompApplicationInput(projectId, websocketStompApplicationId));
                webSocketStompApplicationDtos.add(output.getWebSocketStompApplication());
            }
            final ModelAndView model = createPartialModelAndView(UPDATE_WEBSOCKET_STOMP_PORTS_ENDPOINT_PAGE);
            model.addObject(WEBSOCKET_STOMP_PROJECT_ID, projectId);
            model.addObject(WEBSOCKET_STOMP_RESOURCES, webSocketStompApplicationDtos);
            model.addObject(UPDATE_WEBSOCKET_STOMP_PORTS_ENDPOINT_COMMAND, new UpdateWebSocketStompApplicationsEndpointCommand());
            return model;
        }
        */

        return redirect("/websocket-stomp/project/" + projectId);
    }


}

