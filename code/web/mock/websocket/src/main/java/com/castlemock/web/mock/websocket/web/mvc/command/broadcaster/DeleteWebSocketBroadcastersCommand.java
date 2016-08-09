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

package com.castlemock.web.mock.websocket.web.mvc.command.broadcaster;


import com.castlemock.core.mock.websocket.model.project.dto.WebSocketBroadcasterDto;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketResourceDto;

import java.util.List;

/**
 * The DeleteWebSocketBroadcastersCommand is a command class and is used to carry information on
 * which broadcasters should be deleted from the database
 * @author Karl Dahlgren
 * @since 1.5
 */
public class DeleteWebSocketBroadcastersCommand {

    private List<WebSocketBroadcasterDto> webSocketBroadcasters;

    public List<WebSocketBroadcasterDto> getWebSocketBroadcasters() {
        return webSocketBroadcasters;
    }

    public void setWebSocketBroadcasters(List<WebSocketBroadcasterDto> webSocketBroadcasters) {
        this.webSocketBroadcasters = webSocketBroadcasters;
    }
}

