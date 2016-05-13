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

package com.castlemock.web.mock.websocket.stomp.model.event.repository;

import com.castlemock.core.mock.websocket.stomp.model.event.domain.WebSocketStompEvent;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving WebSocket stomp events from the file system. Each WebSocket stomp event is stored as
 * a separate file. The class also contains the directory and the filename extension for the WebSocket stomp event.
 * @author Karl Dahlgren
 * @since 1.5
 * @see WebSocketStompEventRepositoryImpl
 * @see RepositoryImpl
 * @see WebSocketStompEvent
 */
@Repository
public class WebSocketStompEventRepositoryImpl extends RepositoryImpl<WebSocketStompEvent, String> implements WebSocketStompEventRepository {

    @Value(value = "${websocket.stomp.event.file.directory}")
    private String webSocketStompEventFileDirectory;
    @Value(value = "${websocket.stomp.event.file.extension}")
    private String webSocketStompEventFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return webSocketStompEventFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return webSocketStompEventFileExtension;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param webSocketStompEvent The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     * @see WebSocketStompEvent
     */
    @Override
    protected void checkType(final WebSocketStompEvent webSocketStompEvent) {
        Preconditions.checkNotNull(webSocketStompEvent, "Event cannot be null");
        Preconditions.checkNotNull(webSocketStompEvent.getId(), "Event id cannot be null");
        Preconditions.checkNotNull(webSocketStompEvent.getEndDate(), "Event end date cannot be null");
        Preconditions.checkNotNull(webSocketStompEvent.getStartDate(), "Event start date cannot be null");
    }

}
