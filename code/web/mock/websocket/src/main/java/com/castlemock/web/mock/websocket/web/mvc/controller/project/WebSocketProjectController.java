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
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketTopicDto;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketProjectDto;
import com.castlemock.core.mock.websocket.model.project.service.message.input.ReadWebSocketTopicInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.ReadWebSocketProjectInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.UpdateWebSocketTopicsStatusInput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketTopicOutput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketProjectOutput;
import com.castlemock.web.mock.websocket.web.mvc.command.topic.DeleteWebSocketTopicsCommand;
import com.castlemock.web.mock.websocket.web.mvc.command.topic.UpdateWebSocketTopicsEndpointCommand;
import com.castlemock.web.mock.websocket.web.mvc.command.topic.WebSocketTopicModifierCommand;
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
    private static final String DELETE_WEBSOCKET_TOPICS_PAGE = "mock/websocket/topic/deleteWebSocketTopics";
    private static final String UPDATE_WEBSOCKET_TOPICS_ENDPOINT_PAGE = "mock/websocket/topic/updateWebSocketTopicsEndpoint";
    private static final String UPDATE_STATUS = "update";
    private static final String DELETE_WEBSOCKET_STOPM_TOPIC = "delete";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String DELETE_WEBSOCKET_TOPICS_COMMAND = "deleteWebSocketTopicsCommand";
    private static final String WEBSOCKET_TOPIC_MODIFIER_COMMAND = "webSocketTopicModifierCommand";
    private static final String UPDATE_WEBSOCKET_TOPICS_ENDPOINT_COMMAND = "updateWebSocketTopicsEndpointCommand";
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
        model.addObject(WEBSOCKET_TOPIC_MODIFIER_COMMAND, new WebSocketTopicModifierCommand());
        model.addObject(DEMO_MODE, demoMode);
        return model;
    }

    /**
     * The method projectFunctionality provides multiple functionalities: Update topics status
     * and delete topics. Both of the functionalities involves a set of topics that belongs
     * to a specific project
     * @param projectId The id of the project that the topics belong to
     * @param action The name of the action that should be executed (delete or update).
     * @param webSocketTopicModifierCommand The command object that contains the list of topics that get affected by the executed action.
     * @return Redirects the user back to the main page for the project with the provided id.
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.POST)
    public ModelAndView projectFunctionality(@PathVariable final String projectId, @RequestParam final String action, @ModelAttribute final WebSocketTopicModifierCommand webSocketTopicModifierCommand) {
        LOGGER.debug("Requested WebSocket project action requested: " + action);
        if(UPDATE_STATUS.equalsIgnoreCase(action)){
            final WebSocketResourceStatus webSocketResourceStatus = WebSocketResourceStatus.valueOf(webSocketTopicModifierCommand.getWebSocketResourceStatus());
            for(String webSocketTopicId : webSocketTopicModifierCommand.getWebSocketTopicIds()){
                serviceProcessor.process(new UpdateWebSocketTopicsStatusInput(projectId, webSocketTopicId, webSocketResourceStatus));
            }
        } else if(DELETE_WEBSOCKET_STOPM_TOPIC.equalsIgnoreCase(action)) {
            final List<WebSocketTopicDto> webSocketTopicDtos = new ArrayList<WebSocketTopicDto>();
            for(String webSocketTopicId : webSocketTopicModifierCommand.getWebSocketTopicIds()){
                final ReadWebSocketTopicOutput output = serviceProcessor.process(new ReadWebSocketTopicInput(projectId, webSocketTopicId));
                webSocketTopicDtos.add(output.getWebSocketTopic());
            }
            final ModelAndView model = createPartialModelAndView(DELETE_WEBSOCKET_TOPICS_PAGE);
            model.addObject(WEBSOCKET_PROJECT_ID, projectId);
            model.addObject(WEBSOCKET_TOPICS, webSocketTopicDtos);
            model.addObject(DELETE_WEBSOCKET_TOPICS_COMMAND, new DeleteWebSocketTopicsCommand());
            return model;
        } else if(UPDATE_ENDPOINTS.equalsIgnoreCase(action)){
            final List<WebSocketTopicDto> webSocketTopicDtos = new ArrayList<WebSocketTopicDto>();
            for(String webSocketTopicId : webSocketTopicModifierCommand.getWebSocketTopicIds()){
                final ReadWebSocketTopicOutput output = serviceProcessor.process(new ReadWebSocketTopicInput(projectId, webSocketTopicId));
                webSocketTopicDtos.add(output.getWebSocketTopic());
            }
            final ModelAndView model = createPartialModelAndView(UPDATE_WEBSOCKET_TOPICS_ENDPOINT_PAGE);
            model.addObject(WEBSOCKET_PROJECT_ID, projectId);
            model.addObject(WEBSOCKET_TOPICS, webSocketTopicDtos);
            model.addObject(UPDATE_WEBSOCKET_TOPICS_ENDPOINT_COMMAND, new UpdateWebSocketTopicsEndpointCommand());
            return model;
        }
        return redirect("/wss/project/" + projectId);
    }


}

