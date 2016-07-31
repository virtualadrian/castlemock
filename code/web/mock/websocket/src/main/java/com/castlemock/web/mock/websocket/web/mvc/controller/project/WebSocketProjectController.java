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

package com.castlemock.web.mock.websocket.web.mvc.controller.project;

import com.castlemock.core.mock.websocket.model.project.domain.WebSocketResourceStatus;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketApplicationDto;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketProjectDto;
import com.castlemock.core.mock.websocket.model.project.service.message.input.ReadWebSocketApplicationInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.ReadWebSocketProjectInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.UpdateWebSocketApplicationsStatusInput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketApplicationOutput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketProjectOutput;
import com.castlemock.web.mock.websocket.web.mvc.command.application.DeleteWebSocketApplicationsCommand;
import com.castlemock.web.mock.websocket.web.mvc.command.application.UpdateWebSocketApplicationsEndpointCommand;
import com.castlemock.web.mock.websocket.web.mvc.command.application.WebSocketApplicationModifierCommand;
import com.castlemock.web.mock.websocket.web.mvc.controller.AbstractWebSocketViewController;
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
public class WebSocketProjectController extends AbstractWebSocketViewController {

    private static final String PAGE = "mock/websocket/project/webSocketProject";
    private static final String DELETE_WEBSOCKET_APPLICATIONS_PAGE = "mock/websocket/application/deleteWebSocketApplications";
    private static final String UPDATE_WEBSOCKET_APPLICATIONS_ENDPOINT_PAGE = "mock/websocket/application/updateWebSocketApplicationsEndpoint";
    private static final String UPDATE_STATUS = "update";
    private static final String DELETE_WEBSOCKET_STOPM_APPLICATION = "delete";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String DELETE_WEBSOCKET_APPLICATIONS_COMMAND = "deleteWebSocketApplicationsCommand";
    private static final String WEBSOCKET_APPLICATION_MODIFIER_COMMAND = "webSocketApplicationModifierCommand";
    private static final String UPDATE_WEBSOCKET_APPLICATIONS_ENDPOINT_COMMAND = "updateWebSocketApplicationsEndpointCommand";
    private static final Logger LOGGER = Logger.getLogger(WebSocketProjectController.class);

    /**
     * Retrieves a specific project with a project id
     * @param projectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ModelAndView getProject(@PathVariable final String projectId) {
        final ReadWebSocketProjectOutput readWebSocketProjectOutput = serviceProcessor.process(new ReadWebSocketProjectInput(projectId));
        final WebSocketProjectDto project = readWebSocketProjectOutput.getWebSocketProject();
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_PROJECT, project);
        model.addObject(WEBSOCKET_RESOURCE_STATUSES, getWebSocketResourceStatuses());
        model.addObject(WEBSOCKET_APPLICATION_MODIFIER_COMMAND, new WebSocketApplicationModifierCommand());
        model.addObject(DEMO_MODE, demoMode);
        return model;
    }

    /**
     * The method projectFunctionality provides multiple functionalities: Update applications status
     * and delete applications. Both of the functionalities involves a set of applications that belongs
     * to a specific project
     * @param projectId The id of the project that the applications belong to
     * @param action The name of the action that should be executed (delete or update).
     * @param webSocketApplicationModifierCommand The command object that contains the list of applications that get affected by the executed action.
     * @return Redirects the user back to the main page for the project with the provided id.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.POST)
    public ModelAndView projectFunctionality(@PathVariable final String projectId, @RequestParam final String action, @ModelAttribute final WebSocketApplicationModifierCommand webSocketApplicationModifierCommand) {
        LOGGER.debug("Requested WebSocket project action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final WebSocketResourceStatus webSocketResourceStatus = WebSocketResourceStatus.valueOf(webSocketApplicationModifierCommand.getWebSocketResourceStatus());
            for(String webSocketApplicationId : webSocketApplicationModifierCommand.getWebSocketApplicationIds()){
                serviceProcessor.process(new UpdateWebSocketApplicationsStatusInput(projectId, webSocketApplicationId, webSocketResourceStatus));
            }
        } else if(DELETE_WEBSOCKET_STOPM_APPLICATION.equalsIgnoreCase(action)) {
            final List<WebSocketApplicationDto> webSocketApplicationDtos = new ArrayList<WebSocketApplicationDto>();
            for(String webSocketApplicationId : webSocketApplicationModifierCommand.getWebSocketApplicationIds()){
                final ReadWebSocketApplicationOutput output = serviceProcessor.process(new ReadWebSocketApplicationInput(projectId, webSocketApplicationId));
                webSocketApplicationDtos.add(output.getWebSocketApplication());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_WEBSOCKET_APPLICATIONS_PAGE);
            model.addObject(WEBSOCKET_PROJECT_ID, projectId);
            model.addObject(WEBSOCKET_APPLICATIONS, webSocketApplicationDtos);
            model.addObject(DELETE_WEBSOCKET_APPLICATIONS_COMMAND, new DeleteWebSocketApplicationsCommand());
            return model;
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<WebSocketApplicationDto> webSocketApplicationDtos = new ArrayList<WebSocketApplicationDto>();
            for(String webSocketApplicationId : webSocketApplicationModifierCommand.getWebSocketApplicationIds()){
                final ReadWebSocketApplicationOutput output = serviceProcessor.process(new ReadWebSocketApplicationInput(projectId, webSocketApplicationId));
                webSocketApplicationDtos.add(output.getWebSocketApplication());
            }
            final ModelAndView model = createPartialModelAndView(UPDATE_WEBSOCKET_APPLICATIONS_ENDPOINT_PAGE);
            model.addObject(WEBSOCKET_PROJECT_ID, projectId);
            model.addObject(WEBSOCKET_APPLICATIONS, webSocketApplicationDtos);
            model.addObject(UPDATE_WEBSOCKET_APPLICATIONS_ENDPOINT_COMMAND, new UpdateWebSocketApplicationsEndpointCommand());
            return model;
        }
        return redirect("/wss/project/" + projectId);
    }


}

