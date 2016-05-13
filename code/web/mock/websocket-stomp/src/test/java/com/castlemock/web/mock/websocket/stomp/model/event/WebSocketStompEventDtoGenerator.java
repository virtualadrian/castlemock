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

package com.castlemock.web.mock.websocket.stomp.model.event;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.websocket.stomp.model.event.domain.WebSocketStompEvent;
import com.castlemock.core.mock.websocket.stomp.model.event.domain.WebSocketStompRequest;
import com.castlemock.core.mock.websocket.stomp.model.event.domain.WebSocketStompResponse;
import com.castlemock.core.mock.websocket.stomp.model.event.dto.WebSocketStompEventDto;
import com.castlemock.core.mock.websocket.stomp.model.event.dto.WebSocketStompRequestDto;
import com.castlemock.core.mock.websocket.stomp.model.event.dto.WebSocketStompResponseDto;

import java.util.Date;


/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public class WebSocketStompEventDtoGenerator {

    public static WebSocketStompEventDto generateWebSocketStompEventDto(){
        final WebSocketStompEventDto websocketStompEventDto = new WebSocketStompEventDto();
        websocketStompEventDto.setId("WEBSOCKET STOMP EVENT");
        websocketStompEventDto.setStartDate(new Date());
        websocketStompEventDto.setEndDate(new Date());
        websocketStompEventDto.setApplicationId("Application id");
        websocketStompEventDto.setResourceId("Resource id");
        websocketStompEventDto.setProjectId("Project id");

        WebSocketStompRequestDto webSocketStompRequestDto = new WebSocketStompRequestDto();
        webSocketStompRequestDto.setBody("WebSocket Stomp request body");
        webSocketStompRequestDto.setContentType("application/json");
        webSocketStompRequestDto.setHttpMethod(HttpMethod.POST);

        WebSocketStompResponseDto webSocketStompResponseDto = new WebSocketStompResponseDto();
        webSocketStompResponseDto.setHttpStatusCode(200);
        webSocketStompResponseDto.setBody("WebSocket stomp response body");
        webSocketStompResponseDto.setMockResponseName("MockResponseName");

        websocketStompEventDto.setRequest(webSocketStompRequestDto);
        websocketStompEventDto.setResponse(webSocketStompResponseDto);
        return websocketStompEventDto;
    }

    public static WebSocketStompEvent generateWebSocketStompEvent(){
        final WebSocketStompEvent webSocketStompEvent = new WebSocketStompEvent();
        webSocketStompEvent.setId("WEBSOCKET STOMP EVENT");
        webSocketStompEvent.setStartDate(new Date());
        webSocketStompEvent.setEndDate(new Date());
        webSocketStompEvent.setApplicationId("Application id");
        webSocketStompEvent.setResourceId("Resource id");
        webSocketStompEvent.setProjectId("Project id");

        WebSocketStompRequest webSocketStompRequest = new WebSocketStompRequest();
        webSocketStompRequest.setBody("WebSocket Stomp request body");
        webSocketStompRequest.setContentType("application/json");
        webSocketStompRequest.setHttpMethod(HttpMethod.POST);

        WebSocketStompResponse webSocketStompResponse = new WebSocketStompResponse();
        webSocketStompResponse.setHttpStatusCode(200);
        webSocketStompResponse.setBody("WebSocket Stomp response body");
        webSocketStompResponse.setMockResponseName("MockResponseName");

        webSocketStompEvent.setRequest(webSocketStompRequest);
        webSocketStompEvent.setResponse(webSocketStompResponse);
        return webSocketStompEvent;
    }
}
