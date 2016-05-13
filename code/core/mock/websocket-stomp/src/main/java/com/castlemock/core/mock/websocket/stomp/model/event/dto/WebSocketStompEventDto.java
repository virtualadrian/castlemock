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

package com.castlemock.core.mock.websocket.stomp.model.event.dto;

import com.castlemock.core.basis.model.event.dto.EventDto;
import org.dozer.Mapping;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public class WebSocketStompEventDto extends EventDto {

    @Mapping("request")
    private WebSocketStompRequestDto request;

    @Mapping("response")
    private WebSocketStompResponseDto response;

    @Mapping("projectId")
    private String projectId;

    @Mapping("applicationId")
    private String applicationId;

    @Mapping("resourceId")
    private String resourceId;

    /**
     * Default constructor for the WebSocket Stomp event DTO
     */
    public WebSocketStompEventDto() {
    }

    /**
     * Default constructor for the WebSocket Stomp event DTO
     */
    public WebSocketStompEventDto(final EventDto eventDto) {
        super(eventDto);
    }

    public WebSocketStompRequestDto getRequest() {
        return request;
    }

    public void setRequest(WebSocketStompRequestDto request) {
        this.request = request;
    }

    public WebSocketStompResponseDto getResponse() {
        return response;
    }

    public void setResponse(WebSocketStompResponseDto response) {
        this.response = response;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
