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

package com.castlemock.web.mock.websocket.model.project.repository;

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.mock.websocket.model.project.domain.WebSocketApplication;
import com.castlemock.core.mock.websocket.model.project.domain.WebSocketMockResponse;
import com.castlemock.core.mock.websocket.model.project.domain.WebSocketResource;
import com.castlemock.core.mock.websocket.model.project.domain.WebSocketProject;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketApplicationDto;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketMockResponseDto;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketProjectDto;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketResourceDto;

/**
 * The WebSocket project file repository provides the functionality to interact with the file system.
 * The repository is responsible for loading and WebSocket project to the file system. Each
 * WebSocket project is stored as a separate file.
 * @author Karl Dahlgren
 * @since 1.5
 * @see WebSocketProject
 * @see Repository
 */
public interface WebSocketProjectRepository extends Repository<WebSocketProject, WebSocketProjectDto, String> {



    /*
     * FIND OPERATIONS
     */

    /**
     * The method find an port with project id and port id
     * @param webSocketProjectId The id of the project which the port belongs to
     * @param webSocketApplicationId The id of the port that will be retrieved
     * @return Returns an port that matches the search criteria. Returns null if no port matches.
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP port was found
     * @see WebSocketProject
     * @see WebSocketProjectDto
     * @see WebSocketApplication
     * @see WebSocketApplicationDto
     */
    WebSocketApplicationDto findWebSocketApplication(String webSocketProjectId, String webSocketApplicationId);

    /**
     * The method finds a operation that matching the search criteria and returns the result
     * @param webSocketProjectId The id of the project which the operation belongs to
     * @param webSocketApplicationId The id of the port which the operation belongs to
     * @param webSocketResourceId The id of the operation that will be retrieved
     * @return Returns an operation that matches the search criteria. Returns null if no operation matches.
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see WebSocketProject
     * @see WebSocketProjectDto
     * @see WebSocketApplication
     * @see WebSocketApplicationDto
     * @see WebSocketResource
     * @see WebSocketResourceDto
     */
    WebSocketResourceDto findWebSocketResource(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId);



    /**
     * The method provides the functionality to retrieve a mocked response with project id, port id,
     * operation id and mock response id
     * @param webSocketProjectId The id of the project that the mocked response belongs to
     * @param webSocketApplicationId The id of the port that the mocked response belongs to
     * @param webSocketResourceId The id of the operation that the mocked response belongs to
     * @param webSocketMockResponseId The id of the mocked response that will be retrieved
     * @return Mocked response that match the provided parameters
     * @see WebSocketProject
     * @see WebSocketProjectDto
     * @see WebSocketApplication
     * @see WebSocketApplicationDto
     * @see WebSocketResource
     * @see WebSocketResourceDto
     * @see WebSocketMockResponse
     * @see WebSocketMockResponseDto
     */
    WebSocketMockResponseDto findWebSocketMockResponse(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId, String webSocketMockResponseId);

    WebSocketProjectDto findWebSocketProject(String name);

    /*
     * SAVE OPERATIONS
     */

    /**
     * The method adds a new {@link WebSocketApplication} to a {@link WebSocketProject} and then saves
     * the {@link WebSocketProject} to the file system.
     * @param webSocketProjectId The id of the {@link WebSocketProject}
     * @param webSocketApplicationDto The dto instance of {@link WebSocketApplication} that will be added to the {@link WebSocketProject}
     * @return The saved {@link WebSocketApplicationDto}
     * @see WebSocketProject
     * @see WebSocketProjectDto
     * @see WebSocketApplication
     * @see WebSocketApplicationDto
     */
    WebSocketApplicationDto saveWebSocketApplication(String webSocketProjectId, WebSocketApplicationDto webSocketApplicationDto);

    /**
     * The method adds a new {@link WebSocketResource} to a {@link WebSocketApplication} and then saves
     * the {@link WebSocketProject} to the file system.
     * @param webSocketProjectId The id of the {@link WebSocketProject}
     * @param webSocketApplicationId The id of the {@link WebSocketApplication}
     * @param webSocketResource The dto instance of {@link WebSocketResource} that will be added to the {@link WebSocketApplication}
     * @return The saved {@link WebSocketResourceDto}
     * @see WebSocketProject
     * @see WebSocketProjectDto
     * @see WebSocketApplication
     * @see WebSocketApplicationDto
     * @see WebSocketResource
     * @see WebSocketResourceDto
     */
    WebSocketResourceDto saveWebSocketResource(String webSocketProjectId, String webSocketApplicationId, WebSocketResourceDto webSocketResource);


    /**
     * The method adds a new {@link WebSocketMockResponse} to a {@link WebSocketResource} and then saves
     * the {@link WebSocketProject} to the file system.
     * @param webSocketProjectId The id of the {@link WebSocketProject}
     * @param webSocketApplicationId The id of the {@link WebSocketApplication}
     * @param webSocketResourceId The id of the {@link WebSocketResource}
     * @param webSocketMockResponseDto The dto instance of {@link WebSocketMockResponse} that will be added to the {@link WebSocketResource}
     * @return The saved {@link WebSocketMockResponseDto}
     * @see WebSocketProject
     * @see WebSocketProjectDto
     * @see WebSocketApplication
     * @see WebSocketApplicationDto
     * @see WebSocketResource
     * @see WebSocketResourceDto
     * @see WebSocketMockResponse
     * @see WebSocketMockResponseDto
     */
    WebSocketMockResponseDto saveWebSocketMockResponse(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId, WebSocketMockResponseDto webSocketMockResponseDto);

    /*
     * UPDATE OPERATIONS
     */

    /**
     * The method updates an already existing {@link WebSocketApplication}
     * @param webSocketProjectId The id of the {@link WebSocketProject}
     * @param webSocketApplicationId The id of the {@link WebSocketApplication} that will be updated
     * @param webSocketApplicationDto The updated {@link WebSocketApplicationDto}
     * @return The updated version of the {@link WebSocketApplicationDto}
     * @see WebSocketProject
     * @see WebSocketProjectDto
     * @see WebSocketApplication
     * @see WebSocketApplicationDto
     */
    WebSocketApplicationDto updateWebSocketApplication(String webSocketProjectId, String webSocketApplicationId, WebSocketApplicationDto webSocketApplicationDto);

    /**
     * The method updates an already existing {@link WebSocketResource}
     * @param webSocketProjectId The id of the {@link WebSocketProject}
     * @param webSocketApplicationId The id of the {@link WebSocketApplication}
     * @param webSocketResourceId The id of the {@link WebSocketResource} that will be updated
     * @param webSocketResourceDto The updated {@link WebSocketResourceDto}
     * @return The updated version of the {@link WebSocketResourceDto}
     * @see WebSocketProject
     * @see WebSocketProjectDto
     * @see WebSocketApplication
     * @see WebSocketApplicationDto
     * @see WebSocketResource
     * @see WebSocketResourceDto
     */
    WebSocketResourceDto updateWebSocketResource(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId, WebSocketResourceDto webSocketResourceDto);


    /**
     * The method updates an already existing {@link WebSocketResource}
     * @param webSocketProjectId The id of the {@link WebSocketProject}
     * @param webSocketApplicationId The id of the {@link WebSocketApplication}
     * @param webSocketResourceId The id of the {@link WebSocketResource}
     * @param webSocketMockResponseId The id of the {@link WebSocketMockResponse} that will be updated
     * @param webSocketMockResponseDto The updated {@link WebSocketMockResponseDto)
     * @return The updated version of the {@link WebSocketMockResponseDto}
     * @see WebSocketProject
     * @see WebSocketProjectDto
     * @see WebSocketApplication
     * @see WebSocketApplicationDto
     * @see WebSocketResource
     * @see WebSocketResourceDto
     * @see WebSocketMockResponse
     * @see WebSocketMockResponseDto
     */
    WebSocketMockResponseDto updateWebSocketMockResponse(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId, String webSocketMockResponseId, WebSocketMockResponseDto webSocketMockResponseDto);

    /*
     * DELETE OPERATIONS
     */

    /**
     * The method deletes a {@link WebSocketApplication}
     * @param webSocketProjectId The id of the {@link WebSocketProject}
     * @param webSocketApplicationId The id of the {@link WebSocketApplication}
     * @return The deleted {@link WebSocketApplicationDto}
     * @see WebSocketProject
     * @see WebSocketProjectDto
     * @see WebSocketApplication
     * @see WebSocketApplicationDto
     */
    WebSocketApplicationDto deleteWebSocketApplication(String webSocketProjectId, String webSocketApplicationId);

    /**
     * The method deletes a {@link WebSocketResource}
     * @param webSocketProjectId The id of the {@link WebSocketProject}
     * @param webSocketApplicationId The id of the {@link WebSocketApplication}
     * @param webSocketResourceId The id of the {@link WebSocketResource}
     * @return The deleted {@link WebSocketResourceDto}
     * @see WebSocketProject
     * @see WebSocketProjectDto
     * @see WebSocketApplication
     * @see WebSocketApplicationDto
     * @see WebSocketResource
     * @see WebSocketResourceDto
     */
    WebSocketResourceDto deleteWebSocketResource(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId);


    /**
     * The method deletes a {@link WebSocketMockResponse}
     * @param webSocketProjectId The id of the {@link WebSocketProject}
     * @param webSocketApplicationId The id of the {@link WebSocketApplication}
     * @param webSocketResourceId The id of the {@link WebSocketResource}
     * @param webSocketMockResponseId The id of the {@link WebSocketMockResponse}
     * @return The deleted {@link WebSocketMockResponseDto}
     * @see WebSocketProject
     * @see WebSocketProjectDto
     * @see WebSocketApplication
     * @see WebSocketApplicationDto
     * @see WebSocketResource
     * @see WebSocketResourceDto
     * @see WebSocketMockResponse
     * @see WebSocketMockResponseDto
     */
    WebSocketMockResponseDto deleteWebSocketMockResponse(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId, String webSocketMockResponseId);

}
