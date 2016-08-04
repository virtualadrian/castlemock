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

import com.castlemock.core.mock.websocket.model.project.service.message.input.DeleteWebSocketTopicInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.DeleteWebSocketTopicsInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.ReadWebSocketTopicInput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketTopicOutput;
import com.castlemock.web.mock.websocket.web.mvc.command.topic.DeleteWebSocketTopicsCommand;
import com.castlemock.web.mock.websocket.web.mvc.controller.AbstractWebSocketViewController;
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
public class DeleteWebSocketTopicController extends AbstractWebSocketViewController {

    private static final String PAGE = "mock/websocket/topic/deleteWebSocketTopic";


    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/topic/{webSocketTopicId}/delete", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketTopicId) {
        final ReadWebSocketTopicOutput ouput = serviceProcessor.process(new ReadWebSocketTopicInput(webSocketProjectId, webSocketTopicId));
        ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_PROJECT_ID, webSocketProjectId);
        model.addObject(WEBSOCKET_TOPIC, ouput.getWebSocketTopic());
        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/topic/{webSocketTopicId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketTopicId) {
        serviceProcessor.process(new DeleteWebSocketTopicInput(webSocketProjectId, webSocketTopicId));
        return redirect("/wss/project/" + webSocketProjectId);
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/topic/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleProjects(@PathVariable final String webSocketProjectId, @ModelAttribute final DeleteWebSocketTopicsCommand deleteWebSocketTopicsCommand) {
        serviceProcessor.process(new DeleteWebSocketTopicsInput(webSocketProjectId, deleteWebSocketTopicsCommand.getWebSocketTopics()));
        return redirect("/wss/project/" + webSocketProjectId);
    }


}

