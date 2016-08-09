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

package com.castlemock.web.mock.websocket.web.mvc.controller.broadcaster;

import com.castlemock.core.mock.websocket.model.project.service.message.input.*;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketBroadcasterOutput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketResourceOutput;
import com.castlemock.web.mock.websocket.web.mvc.command.broadcaster.DeleteWebSocketBroadcastersCommand;
import com.castlemock.web.mock.websocket.web.mvc.command.resource.DeleteWebSocketResourcesCommand;
import com.castlemock.web.mock.websocket.web.mvc.controller.AbstractWebSocketViewController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**e
 * The Delete WebSocket resource controller provides functionality to delete a specific WebSocket resource.
 * @author Karl Dahlgren
 * @since 1.5
 */
@Controller
@RequestMapping("/web/wss/project")
public class DeleteWebSocketBroadcasterController extends AbstractWebSocketViewController {

    private static final String PAGE = "mock/websocket/broadcaster/deleteWebSocketBroadcaster";
    /**
     * Retrieves a specific WebSocket broadcaster with a project id, topic id and a broadcaster id
     * @param webSocketProjectId The id of the project that the WebSocket broadcaster belongs to
     * @param webSocketTopicId The id of the topic that the WebSocket broadcaster belongs to
     * @param webSocketBroadcasterId The id of the WebSocket broadcaster that should be retrieve
     * @return WebSocket broadcaster that matches the provided broadcaster id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/topic/{webSocketTopicId}/broadcaster/{webSocketBroadcasterId}/delete", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketTopicId, @PathVariable final String webSocketBroadcasterId) {
        final ReadWebSocketBroadcasterOutput output = serviceProcessor.process(new ReadWebSocketBroadcasterInput(webSocketProjectId, webSocketTopicId, webSocketBroadcasterId));

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_PROJECT_ID, webSocketProjectId);
        model.addObject(WEBSOCKET_TOPIC_ID, webSocketTopicId);
        model.addObject(WEBSOCKET_BROADCASTER, output.getWebSocketBroadcaster());
        return model;
    }

    /**
     * The method provides the functionality to delete a WebSocket broadcaster
     * @param webSocketProjectId The id of the project that the WebSocket broadcaster belongs to
     * @param webSocketTopicId The id of the topic that the WebSocket broadcaster belongs to
     * @param webSocketBroadcasterId The id of the WebSocket broadcaster that should be deleted
     * @return Redirects the user to the WebSocket topic page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/topic/{webSocketTopicId}/broadcaster/{webSocketBroadcasterId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketTopicId, @PathVariable final String webSocketBroadcasterId) {
        serviceProcessor.process(new DeleteWebSocketBroadcasterInput(webSocketProjectId, webSocketTopicId, webSocketBroadcasterId));
        return redirect("/wss/project/" + webSocketProjectId + "/topic/" + webSocketTopicId);
    }

    /**
     * The method provides the functionality to delete a list WebSocket broadcasters
     * @param webSocketProjectId The id of the project that the WebSocket broadcasters belongs to
     * @param webSocketTopicId The id of the topic that the WebSocket broadcasters belongs to
     * @param deleteWebSocketBroadcastersCommand The command object contains the WebSocket broadcaster that should be deleted
     * @return Redirects the user to the WebSocket topic page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/topic/{webSocketTopicId}/broadcaster/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleProjects(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketTopicId, @ModelAttribute final DeleteWebSocketBroadcastersCommand deleteWebSocketBroadcastersCommand) {
        serviceProcessor.process(new DeleteWebSocketBroadcastersInput(webSocketProjectId, webSocketTopicId, deleteWebSocketBroadcastersCommand.getWebSocketBroadcasters()));
        return redirect("/wss/project/" + webSocketProjectId + "/topic/" + webSocketTopicId);
    }



}

