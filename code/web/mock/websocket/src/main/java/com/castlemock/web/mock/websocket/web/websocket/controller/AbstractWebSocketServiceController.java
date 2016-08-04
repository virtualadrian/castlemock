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

import com.castlemock.core.basis.model.http.dto.HttpHeaderDto;
import com.castlemock.core.mock.websocket.model.event.dto.WebSocketRequestDto;
import com.castlemock.web.basis.web.mvc.controller.AbstractController;
import com.castlemock.web.mock.websocket.model.WebSocketException;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public abstract class AbstractWebSocketServiceController extends AbstractController {

    @Autowired
    private SimpMessagingTemplate template;
    private static final String TOPIC = "topic";
    private static final String URI_HEADER_1 = "lookupDestination";
    private static final String URI_HEADER_2 = "simpDestination";
    private static final Logger LOGGER = Logger.getLogger(AbstractWebSocketServiceController.class);


    protected void process(String projectId, String topicId, Message message){
        try{
            Preconditions.checkNotNull(projectId, "The project id cannot be null");
            Preconditions.checkNotNull(topicId, "The topic id cannot be null");
            Preconditions.checkNotNull(message, "The message cannot be null");

            final WebSocketRequestDto request = prepareRequest(projectId, topicId, message);

            this.template.convertAndSend("/topic/greetings", "Hello!");
        } catch (Exception exception){
            LOGGER.debug("WebSocket service exception: " + exception.getMessage(), exception);
            throw new WebSocketException(exception.getMessage());
        }
    }

    /**
     * The method prepares an request
     * @param projectId The id of the project that the incoming request belongs to
     * @param topicId The id of the topic that the incoming request belongs to
     * @param message The incoming message
     * @return A new created project
     */
    protected WebSocketRequestDto prepareRequest(final String projectId, final String topicId, final Message message) {
        final WebSocketRequestDto request = new WebSocketRequestDto();

        String incomingRequestUri = new String();
        if(message.getHeaders().containsKey(URI_HEADER_1)){
            incomingRequestUri = message.getHeaders().get(URI_HEADER_1).toString();
        } else if(message.getHeaders().containsKey(URI_HEADER_2)){
            incomingRequestUri = message.getHeaders().get(URI_HEADER_2).toString();
        }

        final String resourceUri = incomingRequestUri.replace(SLASH + PROJECT + SLASH + projectId + SLASH + TOPIC + SLASH + topicId, EMPTY);

        List<HttpHeaderDto> httpHeaders = new ArrayList<>();

        for(Map.Entry<String, Object> entry : message.getHeaders().entrySet()){
            HttpHeaderDto httpHeader = new HttpHeaderDto();
            httpHeader.setName(entry.getKey());
            httpHeader.setValue(entry.getValue().toString());
            httpHeaders.add(httpHeader);
        }

        request.setUri(resourceUri);
        request.setHttpHeaders(httpHeaders);
        return request;
    }


}
