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

package com.castlemock.web.mock.websocket.stomp.web.mvc.controller.application;

import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompResourceStatus;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompResourceDto;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.ReadWebSocketStompApplicationInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.ReadWebSocketStompResourceInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.UpdateWebSocketStompResourcesStatusInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.output.ReadWebSocketStompApplicationOutput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.output.ReadWebSocketStompResourceOutput;
import com.castlemock.web.mock.websocket.stomp.web.mvc.command.resource.DeleteWebSocketStompResourcesCommand;
import com.castlemock.web.mock.websocket.stomp.web.mvc.command.resource.UpdateWebSocketStompResourcesEndpointCommand;
import com.castlemock.web.mock.websocket.stomp.web.mvc.command.resource.WebSocketStompResourceModifierCommand;
import com.castlemock.web.mock.websocket.stomp.web.mvc.controller.AbstractWebSocketStompViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * The WebSocket Stomp resource controller provides functionality to retrieve a specific {@link WebSocketStompResourceDto}.
 * The controller is also responsible for executing actions on {@link WebSocketStompResourceDto} related to
 * the WebSocket Stomp resource.
 * @author Karl Dahlgren
 * @since 1.5
 */
@Controller
@RequestMapping("/web/wss/project")
public class WebSocketStompApplicationController extends AbstractWebSocketStompViewController {

    private static final String PAGE = "mock/websocket/stomp/application/webSocketStompApplication";
    private static final String WEBSOCKET_STOMP_RESOURCE_MODIFIER_COMMAND = "webSocketStompResourceModifierCommand";
    private static final Logger LOGGER = Logger.getLogger(WebSocketStompApplicationController.class);
    private static final String DELETE_WEBSOCKET_STOMP_RESOURCES = "delete";
    private static final String DELETE_WEBSOCKET_STOMP_RESOURCES_COMMAND = "deleteWebSocketStompResourcesCommand";
    private static final String DELETE_WEBSOCKET_STOMP_RESOURCES_PAGE = "mock/websocket/stomp/resource/deleteWebSocketStompResources";
    private static final String UPDATE_STATUS = "update";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String UPDATE_WEBSOCKET_STOMP_RESOURCES_ENDPOINT_PAGE = "mock/websocket/stomp/resource/updateWebSocketStompResourcesEndpoint";
    private static final String UPDATE_WEBSOCKET_STOMP_RESOURCES_ENDPOINT_COMMAND = "updateWebSocketStompResourcesEndpointCommand";

    /**
     * Retrieves a specific project with a project id
     * @param webSocketStompProjectId The id of the project that the application belongs to
     * @param webSocketStompApplicationId The id of the application that should be retrieved
     * @return Application that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketStompProjectId}/application/{webSocketStompApplicationId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String webSocketStompProjectId, @PathVariable final String webSocketStompApplicationId) {
        final ReadWebSocketStompApplicationOutput output = serviceProcessor.process(new ReadWebSocketStompApplicationInput(webSocketStompProjectId, webSocketStompApplicationId));

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_STOMP_PROJECT_ID, webSocketStompProjectId);
        model.addObject(WEBSOCKET_STOMP_APPLICATION, output.getWebSocketStompApplication());
        model.addObject(WEBSOCKET_STOMP_RESOURCE_STATUSES, getWebSocketStompResourceStatuses());
        model.addObject(WEBSOCKET_STOMP_RESOURCE_MODIFIER_COMMAND, new WebSocketStompResourceModifierCommand());
        return model;
    }

    /**
     * The method is responsible for executing a specific action for {@link com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompResource}.
     * The following actions is supported:
     * <ul>
     *  <li>Update status: Updates a method status</li>
     *  <li>Delete WebSocket Stomp resource: Deletes one or more WebSocket Stomp resources ({@link com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompResource})</li>
     *  <li>Update endpoint: Change the endpoint for certain WebSocket Stomp methods</li>
     * </ul>
     * @param webSocketStompProjectId The id of the project responsible for the WebSocket Stomp application
     * @param webSocketStompApplicationId The id of the application that the action should be invoked upon
     * @param action The requested action
     * @param webSocketStompResourceModifierCommand The command object contains meta data required for certain actions
     * @return Either a model related to the action or redirects the user to the WebSocket Stomp application page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketStompProjectId}/application/{webSocketStompApplicationId}", method = RequestMethod.POST)
    public ModelAndView applicationFunctionality(@PathVariable final String webSocketStompProjectId, @PathVariable final String webSocketStompApplicationId, @RequestParam final String action, @ModelAttribute final WebSocketStompResourceModifierCommand webSocketStompResourceModifierCommand) {
        LOGGER.debug("Requested WebSocket Stomp project action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final WebSocketStompResourceStatus webSocketStompResourceStatus = WebSocketStompResourceStatus.valueOf(webSocketStompResourceModifierCommand.getWebSocketStompResourceStatus());
            for(String webSocketStompResourceId : webSocketStompResourceModifierCommand.getWebSocketStompResourceIds()){
                serviceProcessor.process(new UpdateWebSocketStompResourcesStatusInput(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId, webSocketStompResourceStatus));
            }
        } else if(DELETE_WEBSOCKET_STOMP_RESOURCES.equalsIgnoreCase(action)) {
            final List<WebSocketStompResourceDto> webSocketStompResources = new ArrayList<WebSocketStompResourceDto>();
            for(String webSocketStompResourceId : webSocketStompResourceModifierCommand.getWebSocketStompResourceIds()){
                ReadWebSocketStompResourceOutput output = serviceProcessor.process(new ReadWebSocketStompResourceInput(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId));
                webSocketStompResources.add(output.getWebSocketStompResource());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_WEBSOCKET_STOMP_RESOURCES_PAGE);
            model.addObject(WEBSOCKET_STOMP_PROJECT_ID, webSocketStompProjectId);
            model.addObject(WEBSOCKET_STOMP_RESOURCE_ID, webSocketStompApplicationId);
            model.addObject(WEBSOCKET_STOMP_RESOURCES, webSocketStompResources);
            model.addObject(DELETE_WEBSOCKET_STOMP_RESOURCES_COMMAND, new DeleteWebSocketStompResourcesCommand());
            return model;
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<WebSocketStompResourceDto> webSocketStompResourceDtos = new ArrayList<WebSocketStompResourceDto>();
            for(String webSocketStompResourceId : webSocketStompResourceModifierCommand.getWebSocketStompResourceIds()){
                final ReadWebSocketStompResourceOutput output = serviceProcessor.process(new ReadWebSocketStompResourceInput(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId));
                webSocketStompResourceDtos.add(output.getWebSocketStompResource());
            }
            final ModelAndView model = createPartialModelAndView(UPDATE_WEBSOCKET_STOMP_RESOURCES_ENDPOINT_PAGE);
            model.addObject(WEBSOCKET_STOMP_PROJECT_ID, webSocketStompProjectId);
            model.addObject(WEBSOCKET_STOMP_RESOURCE_ID, webSocketStompApplicationId);
            model.addObject(WEBSOCKET_STOMP_RESOURCES, webSocketStompResourceDtos);
            model.addObject(UPDATE_WEBSOCKET_STOMP_RESOURCES_ENDPOINT_COMMAND, new UpdateWebSocketStompResourcesEndpointCommand());
            return model;
        }
        return redirect("/wss/project/" + webSocketStompProjectId + "/application/" + webSocketStompApplicationId);
    }
}

