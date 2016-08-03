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

package com.castlemock.web.mock.websocket.web.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
@CrossOrigin
@Controller
public class WebSocketServiceController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/project/{projectId}/topic/{topicId}")
    public void greeting(@DestinationVariable String projectId, @DestinationVariable String topicId) throws Exception {
        Thread.sleep(3000); // simulated delay


        Map<String, List<String>> nativeHeaders = new HashMap<>();
        nativeHeaders.put("hello", Collections.singletonList("world"));

        Map<String,Object> headers = new HashMap<>();
        headers.put(NativeMessageHeaderAccessor.NATIVE_HEADERS, nativeHeaders);
        this.template.convertAndSend("/topic/greetings", new Greeting("Hello!"));
    }

}
