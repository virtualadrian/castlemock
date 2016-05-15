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

package com.castlemock.core.mock.websocket.stomp.model.project.dto;

import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompResourceStatus;
import org.dozer.Mapping;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public class WebSocketStompResourceDto {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("uri")
    private String uri;

    @Mapping("mockResponses")
    private List<WebSocketStompMockResponseDto> mockResponses = new LinkedList<WebSocketStompMockResponseDto>();

    @Mapping("status")
    private WebSocketStompResourceStatus status;

    private String invokeAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<WebSocketStompMockResponseDto> getMockResponses() {
        return mockResponses;
    }

    public void setMockResponses(List<WebSocketStompMockResponseDto> mockResponses) {
        this.mockResponses = mockResponses;
    }

    public WebSocketStompResourceStatus getStatus() {
        return status;
    }

    public void setStatus(WebSocketStompResourceStatus status) {
        this.status = status;
    }

    public String getInvokeAddress() {
        return invokeAddress;
    }

    public void setInvokeAddress(String invokeAddress) {
        this.invokeAddress = invokeAddress;
    }
}
