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

import com.castlemock.core.mock.websocket.model.project.dto.WebSocketBroadcasterDto;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketResourceDto;
import com.castlemock.core.mock.websocket.model.project.service.message.input.*;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketBroadcasterOutput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketResourceOutput;
import com.castlemock.web.mock.websocket.web.mvc.command.resource.UpdateWebSocketResourcesEndpointCommand;
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
public class UpdateWebSocketBroadcasterController extends AbstractWebSocketViewController {

    private static final String PAGE = "mock/websocket/broadcaster/updateWebSocketBroadcaster";

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/topic/{webSocketTopicId}/broadcaster/{webSocketBroadcasterId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketTopicId, @PathVariable final String webSocketBroadcasterId) {
        final ReadWebSocketBroadcasterOutput output = serviceProcessor.process(new ReadWebSocketBroadcasterInput(webSocketProjectId, webSocketTopicId, webSocketBroadcasterId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_BROADCASTER, output.getWebSocketBroadcaster());
        model.addObject(WEBSOCKET_PROJECT_ID, webSocketProjectId);
        model.addObject(WEBSOCKET_TOPIC_ID, webSocketTopicId);
        model.addObject(WEBSOCKET_BROADCASTER_ID, webSocketBroadcasterId);
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/topic/{webSocketTopicId}/broadcaster/{webSocketBroadcasterId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketTopicId, @PathVariable final String webSocketBroadcasterId,  @ModelAttribute final WebSocketBroadcasterDto webSocketBroadcasterDto) {
        serviceProcessor.process(new UpdateWebSocketBroadcasterInput(webSocketProjectId, webSocketTopicId, webSocketBroadcasterId, webSocketBroadcasterDto));
        return redirect("/wss/project/" + webSocketProjectId + "/topic/" + webSocketTopicId + "/broadcaster/" + webSocketBroadcasterId);
    }

}

