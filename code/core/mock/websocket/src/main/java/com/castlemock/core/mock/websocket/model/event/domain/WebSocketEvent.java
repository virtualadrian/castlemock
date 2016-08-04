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

package com.castlemock.core.mock.websocket.model.event.domain;

import com.castlemock.core.basis.model.event.domain.Event;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
@XmlRootElement
public class WebSocketEvent extends Event {

    private WebSocketRequest request;
    private WebSocketResponse response;
    private String projectId;
    private String topicId;
    private String resourceId;

    @XmlElement
    public WebSocketRequest getRequest() {
        return request;
    }

    public void setRequest(WebSocketRequest request) {
        this.request = request;
    }

    @XmlElement
    public WebSocketResponse getResponse() {
        return response;
    }

    public void setResponse(WebSocketResponse response) {
        this.response = response;
    }

    @XmlElement
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @XmlElement
    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @XmlElement
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
