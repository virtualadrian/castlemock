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
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketApplicationDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public class DeleteWebSocketApplicationsInput implements Input{

    @NotNull
    private String webSocketProjectId;
    @NotNull
    private List<WebSocketApplicationDto> webSocketApplications;

    public DeleteWebSocketApplicationsInput(String webSocketProjectId, List<WebSocketApplicationDto> webSocketApplications) {
        this.webSocketProjectId = webSocketProjectId;
        this.webSocketApplications = webSocketApplications;
    }

    public String getWebSocketProjectId() {
        return webSocketProjectId;
    }

    public void setWebSocketProjectId(String webSocketProjectId) {
        this.webSocketProjectId = webSocketProjectId;
    }

    public List<WebSocketApplicationDto> getWebSocketApplications() {
        return webSocketApplications;
    }

    public void setWebSocketApplications(List<WebSocketApplicationDto> webSocketApplications) {
        this.webSocketApplications = webSocketApplications;
    }
}
