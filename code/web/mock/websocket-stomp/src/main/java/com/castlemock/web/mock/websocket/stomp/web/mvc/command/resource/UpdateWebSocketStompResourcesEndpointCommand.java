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

package com.castlemock.web.mock.websocket.stomp.web.mvc.command.resource;




import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompResourceDto;

import java.util.List;

/**
 * The UpdateWebSocketStompResourcesEndpointCommand is a command class and is used to identify which resource
 * will be updated with a new endpoint
 * @author Karl Dahlgren
 * @since 1.5
 */
public class UpdateWebSocketStompResourcesEndpointCommand {

    private List<WebSocketStompResourceDto> webSocketStompResources;
    private String forwardedEndpoint;

    public List<WebSocketStompResourceDto> getWebSocketStompResources() {
        return webSocketStompResources;
    }

    public void setWebSocketStompResources(List<WebSocketStompResourceDto> webSocketStompResources) {
        this.webSocketStompResources = webSocketStompResources;
    }

    /**
     * Returns the forwarded endpoint
     * @return The forwarded endpoint
     */
    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    /**
     * Sets a new value for the forwarded endpoint
     * @param forwardedEndpoint The new value for forwarded endpoint
     */
    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }
}

