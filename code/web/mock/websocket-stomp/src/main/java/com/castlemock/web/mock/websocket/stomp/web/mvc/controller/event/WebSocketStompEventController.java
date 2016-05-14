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

package com.castlemock.web.mock.websocket.stomp.web.mvc.controller.event;

import com.castlemock.core.mock.websocket.stomp.model.event.service.message.input.ReadWebSocketStompEventInput;
import com.castlemock.core.mock.websocket.stomp.model.event.service.message.output.ReadWebSocketStompEventOutput;
import com.castlemock.web.mock.websocket.stomp.web.mvc.controller.AbstractWebSocketStompViewController;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The controller WebSocketStompEventController provides functionality to retrieve a specific WebSocket stomp event
 * @author Karl Dahlgren
 * @since 1.5
 */
@Controller
@Scope("request")
@RequestMapping("/web")
public class WebSocketStompEventController extends AbstractWebSocketStompViewController {

    private static final String PAGE = "mock/websocket/stomp/event/websocketStompEvent";

    /**
     * The method provides the functionality to retrieve a specific WEBSOCKET_STOMP event
     * @param eventId The id of the WEBSOCKET_STOMP event that should be retrieved
     * @return A view that displays the retrieved WEBSOCKET_STOMP event
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "wss/event/{eventId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String eventId) {
        final ReadWebSocketStompEventOutput output = serviceProcessor.process(new ReadWebSocketStompEventInput(eventId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(EVENT, output.getWebSocketStompEvent());
        return model;
    }

}