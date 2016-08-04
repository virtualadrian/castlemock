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

import com.castlemock.core.mock.websocket.model.project.dto.WebSocketTopicDto;
import com.castlemock.core.mock.websocket.model.project.service.message.input.ReadWebSocketTopicInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.UpdateWebSocketTopicInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.UpdateWebSocketTopicsForwardedEndpointInput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketTopicOutput;
import com.castlemock.web.mock.websocket.web.mvc.command.topic.UpdateWebSocketTopicsEndpointCommand;
import com.castlemock.web.mock.websocket.web.mvc.controller.AbstractWebSocketViewController;
import com.google.common.base.Preconditions;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The project controller provides functionality to retrieve a specific project
 * @author Karl Dahlgren
 * @since 1.5
 */
@Controller
@RequestMapping("/web/wss/project")
public class UpdateWebSocketTopicController extends AbstractWebSocketViewController {

    private static final String PAGE = "mock/websocket/topic/updateWebSocketTopic";


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/topic/{topicId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String projectId, @PathVariable final String topicId) {
        final ReadWebSocketTopicOutput output = serviceProcessor.process(new ReadWebSocketTopicInput(projectId, topicId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_TOPIC, output.getWebSocketTopic());
        model.addObject(WEBSOCKET_PROJECT_ID, projectId);
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/topic/{topicId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String projectId, @PathVariable final String topicId,  @ModelAttribute final WebSocketTopicDto webSocketTopicDto) {
        serviceProcessor.process(new UpdateWebSocketTopicInput(projectId, topicId, webSocketTopicDto));
        return redirect("/wss/project/" + projectId + "/topic/" + topicId);
    }

    /**
     * The method provides the functionality to update the endpoint address for multiple
     * topics at once
     * @param projectId The id of the project that the ports belongs to
     * @param updateWebSocketTopicsEndpointCommand The command object contains both the topic that
     *                                          will be updated and the new forwarded address
     * @return Redirects the user to the project page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/topic/update/confirm", method = RequestMethod.POST)
    public ModelAndView updateEndpoint(@PathVariable final String projectId, @ModelAttribute final UpdateWebSocketTopicsEndpointCommand updateWebSocketTopicsEndpointCommand) {
        Preconditions.checkNotNull(updateWebSocketTopicsEndpointCommand, "The update topic endpoint command cannot be null");
        serviceProcessor.process(new UpdateWebSocketTopicsForwardedEndpointInput(projectId, updateWebSocketTopicsEndpointCommand.getWebSocketTopics(), updateWebSocketTopicsEndpointCommand.getForwardedEndpoint()));
        return redirect("/wss/project/" + projectId);
    }
}

