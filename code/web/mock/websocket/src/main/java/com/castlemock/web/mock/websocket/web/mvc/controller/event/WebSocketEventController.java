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

package com.castlemock.web.mock.websocket.web.mvc.controller.event;

import com.castlemock.core.mock.websocket.model.event.service.message.input.ReadWebSocketEventInput;
import com.castlemock.core.mock.websocket.model.event.service.message.output.ReadWebSocketEventOutput;
import com.castlemock.web.mock.websocket.web.mvc.controller.AbstractWebSocketViewController;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The controller WebSocketEventController provides functionality to retrieve a specific WebSocket event
 * @author Karl Dahlgren
 * @since 1.5
 */
@Controller
@Scope("request")
@RequestMapping("/web")
public class WebSocketEventController extends AbstractWebSocketViewController {

    private static final String PAGE = "mock/websocket/event/webSocketEvent";

    /**
     * The method provides the functionality to retrieve a specific WEBSOCKET event
     * @param eventId The id of the WEBSOCKET event that should be retrieved
     * @return A view that displays the retrieved WEBSOCKET event
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "wss/event/{eventId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String eventId) {
        final ReadWebSocketEventOutput output = serviceProcessor.process(new ReadWebSocketEventInput(eventId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(EVENT, output.getWebSocketEvent());
        return model;
    }

}