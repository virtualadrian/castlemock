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

package com.castlemock.web.mock.websocket.web.mvc.controller.topic;

import com.castlemock.core.mock.websocket.model.project.domain.WebSocketResource;
import com.castlemock.core.mock.websocket.model.project.domain.WebSocketResourceStatus;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketResourceDto;
import com.castlemock.core.mock.websocket.model.project.service.message.input.ReadWebSocketTopicInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.ReadWebSocketResourceInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.UpdateWebSocketResourcesStatusInput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketTopicOutput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketResourceOutput;
import com.castlemock.web.mock.websocket.web.mvc.command.resource.DeleteWebSocketResourcesCommand;
import com.castlemock.web.mock.websocket.web.mvc.command.resource.UpdateWebSocketResourcesEndpointCommand;
import com.castlemock.web.mock.websocket.web.mvc.command.resource.WebSocketResourceModifierCommand;
import com.castlemock.web.mock.websocket.web.mvc.controller.AbstractWebSocketViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * The WebSocket resource controller provides functionality to retrieve a specific {@link WebSocketResourceDto}.
 * The controller is also responsible for executing actions on {@link WebSocketResourceDto} related to
 * the WebSocket resource.
 * @author Karl Dahlgren
 * @since 1.5
 */
@Controller
@RequestMapping("/web/wss/project")
public class WebSocketTopicController extends AbstractWebSocketViewController {

    private static final String PAGE = "mock/websocket/topic/webSocketTopic";
    private static final String WEBSOCKET_RESOURCE_MODIFIER_COMMAND = "webSocketResourceModifierCommand";
    private static final Logger LOGGER = Logger.getLogger(WebSocketTopicController.class);
    private static final String DELETE_WEBSOCKET_RESOURCES = "delete";
    private static final String DELETE_WEBSOCKET_RESOURCES_COMMAND = "deleteWebSocketResourcesCommand";
    private static final String DELETE_WEBSOCKET_RESOURCES_PAGE = "mock/websocket/resource/deleteWebSocketResources";
    private static final String UPDATE_STATUS = "update";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String UPDATE_WEBSOCKET_RESOURCES_ENDPOINT_PAGE = "mock/websocket/resource/updateWebSocketResourcesEndpoint";
    private static final String UPDATE_WEBSOCKET_RESOURCES_ENDPOINT_COMMAND = "updateWebSocketResourcesEndpointCommand";

    /**
     * Retrieves a specific project with a project id
     * @param webSocketProjectId The id of the project that the topic belongs to
     * @param webSocketTopicId The id of the topic that should be retrieved
     * @return Topic that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/topic/{webSocketTopicId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketTopicId) {
        final ReadWebSocketTopicOutput output = serviceProcessor.process(new ReadWebSocketTopicInput(webSocketProjectId, webSocketTopicId));

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_PROJECT_ID, webSocketProjectId);
        model.addObject(WEBSOCKET_TOPIC, output.getWebSocketTopic());
        model.addObject(WEBSOCKET_RESOURCE_STATUSES, getWebSocketResourceStatuses());
        model.addObject(WEBSOCKET_RESOURCE_MODIFIER_COMMAND, new WebSocketResourceModifierCommand());
        return model;
    }

    /**
     * The method is responsible for executing a specific action for {@link WebSocketResource}.
     * The following actions is supported:
     * <ul>
     *  <li>Update status: Updates a method status</li>
     *  <li>Delete WebSocket resource: Deletes one or more WebSocket resources ({@link WebSocketResource})</li>
     *  <li>Update endpoint: Change the endpoint for certain WebSocket methods</li>
     * </ul>
     * @param webSocketProjectId The id of the project responsible for the WebSocket topic
     * @param webSocketTopicId The id of the topic that the action should be invoked upon
     * @param action The requested action
     * @param webSocketResourceModifierCommand The command object contains meta data required for certain actions
     * @return Either a model related to the action or redirects the user to the WebSocket topic page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/topic/{webSocketTopicId}", method = RequestMethod.POST)
    public ModelAndView topicFunctionality(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketTopicId, @RequestParam final String action, @ModelAttribute final WebSocketResourceModifierCommand webSocketResourceModifierCommand) {
        LOGGER.debug("Requested WebSocket project action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final WebSocketResourceStatus webSocketResourceStatus = WebSocketResourceStatus.valueOf(webSocketResourceModifierCommand.getWebSocketResourceStatus());
            for(String webSocketResourceId : webSocketResourceModifierCommand.getWebSocketResourceIds()){
                serviceProcessor.process(new UpdateWebSocketResourcesStatusInput(webSocketProjectId, webSocketTopicId, webSocketResourceId, webSocketResourceStatus));
            }
        } else if(DELETE_WEBSOCKET_RESOURCES.equalsIgnoreCase(action)) {
            final List<WebSocketResourceDto> webSocketResources = new ArrayList<WebSocketResourceDto>();
            for(String webSocketResourceId : webSocketResourceModifierCommand.getWebSocketResourceIds()){
                ReadWebSocketResourceOutput output = serviceProcessor.process(new ReadWebSocketResourceInput(webSocketProjectId, webSocketTopicId, webSocketResourceId));
                webSocketResources.add(output.getWebSocketResource());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_WEBSOCKET_RESOURCES_PAGE);
            model.addObject(WEBSOCKET_PROJECT_ID, webSocketProjectId);
            model.addObject(WEBSOCKET_RESOURCE_ID, webSocketTopicId);
            model.addObject(WEBSOCKET_RESOURCES, webSocketResources);
            model.addObject(DELETE_WEBSOCKET_RESOURCES_COMMAND, new DeleteWebSocketResourcesCommand());
            return model;
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<WebSocketResourceDto> webSocketResourceDtos = new ArrayList<WebSocketResourceDto>();
            for(String webSocketResourceId : webSocketResourceModifierCommand.getWebSocketResourceIds()){
                final ReadWebSocketResourceOutput output = serviceProcessor.process(new ReadWebSocketResourceInput(webSocketProjectId, webSocketTopicId, webSocketResourceId));
                webSocketResourceDtos.add(output.getWebSocketResource());
            }
            final ModelAndView model = createPartialModelAndView(UPDATE_WEBSOCKET_RESOURCES_ENDPOINT_PAGE);
            model.addObject(WEBSOCKET_PROJECT_ID, webSocketProjectId);
            model.addObject(WEBSOCKET_RESOURCE_ID, webSocketTopicId);
            model.addObject(WEBSOCKET_RESOURCES, webSocketResourceDtos);
            model.addObject(UPDATE_WEBSOCKET_RESOURCES_ENDPOINT_COMMAND, new UpdateWebSocketResourcesEndpointCommand());
            return model;
        }
        return redirect("/wss/project/" + webSocketProjectId + "/topic/" + webSocketTopicId);
    }
}

