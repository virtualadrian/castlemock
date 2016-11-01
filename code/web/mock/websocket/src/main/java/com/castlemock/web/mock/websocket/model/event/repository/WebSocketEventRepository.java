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

package com.castlemock.web.mock.websocket.model.event.repository;


import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.mock.websocket.model.event.domain.WebSocketEvent;
import com.castlemock.core.mock.websocket.model.event.dto.WebSocketEventDto;

/**
 * The WebSocket event file repository provides the functionality to interact with the file system.
 * The repository is responsible for loading and WebSocket events to the file system. Each
 * WebSocket event is stored as a separate file.
 * @author Karl Dahlgren
 * @since 1.5
 * @see WebSocketEvent
 * @see Repository
 */
public interface WebSocketEventRepository extends Repository<WebSocketEvent, WebSocketEventDto, String> {

    /**
     * The service finds the oldest event
     * @return The oldest event
     */
    WebSocketEventDto getOldestEvent();

    /**
     * The method clears and deletes all logs.
     * @since 1.7
     */
    void clearAll();

}
