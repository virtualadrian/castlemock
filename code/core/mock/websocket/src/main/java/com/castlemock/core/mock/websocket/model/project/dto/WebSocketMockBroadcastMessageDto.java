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

package com.castlemock.core.mock.websocket.model.project.dto;

import com.castlemock.core.mock.websocket.model.project.domain.WebSocketMockBroadcastMessageStatus;
import org.dozer.Mapping;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public class WebSocketMockBroadcastMessageDto {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("body")
    private String body;

    @Mapping("status")
    private WebSocketMockBroadcastMessageStatus status;

    @Mapping("httpStatusCode")
    private Integer httpStatusCode;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public WebSocketMockBroadcastMessageStatus getStatus() {
        return status;
    }

    public void setStatus(WebSocketMockBroadcastMessageStatus status) {
        this.status = status;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }


}
