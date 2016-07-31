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

package com.castlemock.web.mock.websocket.model.event;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.websocket.model.event.domain.WebSocketEvent;
import com.castlemock.core.mock.websocket.model.event.domain.WebSocketRequest;
import com.castlemock.core.mock.websocket.model.event.domain.WebSocketResponse;
import com.castlemock.core.mock.websocket.model.event.dto.WebSocketEventDto;
import com.castlemock.core.mock.websocket.model.event.dto.WebSocketRequestDto;
import com.castlemock.core.mock.websocket.model.event.dto.WebSocketResponseDto;

import java.util.Date;


/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public class WebSocketEventDtoGenerator {

    public static WebSocketEventDto generateWebSocketEventDto(){
        final WebSocketEventDto webSocketEventDto = new WebSocketEventDto();
        webSocketEventDto.setId("WEBSOCKET EVENT");
        webSocketEventDto.setStartDate(new Date());
        webSocketEventDto.setEndDate(new Date());
        webSocketEventDto.setApplicationId("Application id");
        webSocketEventDto.setResourceId("Resource id");
        webSocketEventDto.setProjectId("Project id");

        WebSocketRequestDto webSocketRequestDto = new WebSocketRequestDto();
        webSocketRequestDto.setBody("WebSocket request body");
        webSocketRequestDto.setContentType("application/json");
        webSocketRequestDto.setHttpMethod(HttpMethod.POST);

        WebSocketResponseDto webSocketResponseDto = new WebSocketResponseDto();
        webSocketResponseDto.setHttpStatusCode(200);
        webSocketResponseDto.setBody("WebSocket response body");
        webSocketResponseDto.setMockResponseName("MockResponseName");

        webSocketEventDto.setRequest(webSocketRequestDto);
        webSocketEventDto.setResponse(webSocketResponseDto);
        return webSocketEventDto;
    }

    public static WebSocketEvent generateWebSocketEvent(){
        final WebSocketEvent webSocketEvent = new WebSocketEvent();
        webSocketEvent.setId("WEBSOCKET EVENT");
        webSocketEvent.setStartDate(new Date());
        webSocketEvent.setEndDate(new Date());
        webSocketEvent.setApplicationId("Application id");
        webSocketEvent.setResourceId("Resource id");
        webSocketEvent.setProjectId("Project id");

        WebSocketRequest webSocketRequest = new WebSocketRequest();
        webSocketRequest.setBody("WebSocket request body");
        webSocketRequest.setContentType("application/json");
        webSocketRequest.setHttpMethod(HttpMethod.POST);

        WebSocketResponse webSocketResponse = new WebSocketResponse();
        webSocketResponse.setHttpStatusCode(200);
        webSocketResponse.setBody("WebSocket response body");
        webSocketResponse.setMockResponseName("MockResponseName");

        webSocketEvent.setRequest(webSocketRequest);
        webSocketEvent.setResponse(webSocketResponse);
        return webSocketEvent;
    }
}
