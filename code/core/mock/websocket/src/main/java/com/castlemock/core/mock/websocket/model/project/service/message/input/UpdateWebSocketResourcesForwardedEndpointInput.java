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

package com.castlemock.core.mock.websocket.model.project.service.message.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketResourceDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public class UpdateWebSocketResourcesForwardedEndpointInput implements Input {

    @NotNull
    private String webSocketProjectId;
    @NotNull
    private String webSocketApplicationId;
    @NotNull
    private List<WebSocketResourceDto> webSocketResources;
    @NotNull
    private String forwardedEndpoint;

    public UpdateWebSocketResourcesForwardedEndpointInput(String webSocketProjectId, String webSocketApplicationId, List<WebSocketResourceDto> webSocketResources, String forwardedEndpoint) {
        this.webSocketProjectId = webSocketProjectId;
        this.webSocketApplicationId = webSocketApplicationId;
        this.webSocketResources = webSocketResources;
        this.forwardedEndpoint = forwardedEndpoint;
    }

    public String getWebSocketProjectId() {
        return webSocketProjectId;
    }

    public void setWebSocketProjectId(String webSocketProjectId) {
        this.webSocketProjectId = webSocketProjectId;
    }

    public String getWebSocketApplicationId() {
        return webSocketApplicationId;
    }

    public void setWebSocketApplicationId(String webSocketApplicationId) {
        this.webSocketApplicationId = webSocketApplicationId;
    }

    public List<WebSocketResourceDto> getWebSocketResources() {
        return webSocketResources;
    }

    public void setWebSocketResources(List<WebSocketResourceDto> webSocketResources) {
        this.webSocketResources = webSocketResources;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }
}
