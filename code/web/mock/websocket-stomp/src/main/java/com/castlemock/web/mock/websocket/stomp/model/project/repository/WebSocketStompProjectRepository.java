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

package com.castlemock.web.mock.websocket.stomp.model.project.repository;

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.*;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompProject;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompApplicationDto;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompMockResponseDto;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompProjectDto;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompResourceDto;

/**
 * The WebSocket stomp project file repository provides the functionality to interact with the file system.
 * The repository is responsible for loading and WebSocket stomp project to the file system. Each
 * WebSocket stomp project is stored as a separate file.
 * @author Karl Dahlgren
 * @since 1.5
 * @see WebSocketStompProject
 * @see Repository
 */
public interface WebSocketStompProjectRepository extends Repository<WebSocketStompProject, WebSocketStompProjectDto, String> {



    /*
     * FIND OPERATIONS
     */

    /**
     * The method find an port with project id and port id
     * @param webSocketStompProjectId The id of the project which the port belongs to
     * @param webSocketStompApplicationId The id of the port that will be retrieved
     * @return Returns an port that matches the search criteria. Returns null if no port matches.
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP port was found
     * @see WebSocketStompProject
     * @see WebSocketStompProjectDto
     * @see WebSocketStompApplication
     * @see WebSocketStompApplicationDto
     */
    WebSocketStompApplicationDto findWebSocketStompApplication(String webSocketStompProjectId, String webSocketStompApplicationId);

    /**
     * The method finds a operation that matching the search criteria and returns the result
     * @param webSocketStompProjectId The id of the project which the operation belongs to
     * @param webSocketStompApplicationId The id of the port which the operation belongs to
     * @param webSocketStompResourceId The id of the operation that will be retrieved
     * @return Returns an operation that matches the search criteria. Returns null if no operation matches.
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see WebSocketStompProject
     * @see WebSocketStompProjectDto
     * @see WebSocketStompApplication
     * @see WebSocketStompApplicationDto
     * @see WebSocketStompResource
     * @see WebSocketStompResourceDto
     */
    WebSocketStompResourceDto findWebSocketStompResource(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId);



    /**
     * The method provides the functionality to retrieve a mocked response with project id, port id,
     * operation id and mock response id
     * @param webSocketStompProjectId The id of the project that the mocked response belongs to
     * @param webSocketStompApplicationId The id of the port that the mocked response belongs to
     * @param webSocketStompResourceId The id of the operation that the mocked response belongs to
     * @param webSocketStompMockResponseId The id of the mocked response that will be retrieved
     * @return Mocked response that match the provided parameters
     * @see WebSocketStompProject
     * @see WebSocketStompProjectDto
     * @see WebSocketStompApplication
     * @see WebSocketStompApplicationDto
     * @see WebSocketStompResource
     * @see WebSocketStompResourceDto
     * @see WebSocketStompMockResponse
     * @see WebSocketStompMockResponseDto
     */
    WebSocketStompMockResponseDto findWebSocketStompMockResponse(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId, String webSocketStompMockResponseId);

    WebSocketStompProjectDto findWebSocketStompProject(String name);

    /*
     * SAVE OPERATIONS
     */

    /**
     * The method adds a new {@link WebSocketStompApplication} to a {@link WebSocketStompProject} and then saves
     * the {@link WebSocketStompProject} to the file system.
     * @param webSocketStompProjectId The id of the {@link WebSocketStompProject}
     * @param webSocketStompApplicationDto The dto instance of {@link WebSocketStompApplication} that will be added to the {@link WebSocketStompProject}
     * @return The saved {@link WebSocketStompApplicationDto}
     * @see WebSocketStompProject
     * @see WebSocketStompProjectDto
     * @see WebSocketStompApplication
     * @see WebSocketStompApplicationDto
     */
    WebSocketStompApplicationDto saveWebSocketStompApplication(String webSocketStompProjectId, WebSocketStompApplicationDto webSocketStompApplicationDto);

    /**
     * The method adds a new {@link WebSocketStompResource} to a {@link WebSocketStompApplication} and then saves
     * the {@link WebSocketStompProject} to the file system.
     * @param webSocketStompProjectId The id of the {@link WebSocketStompProject}
     * @param webSocketStompApplicationId The id of the {@link WebSocketStompApplication}
     * @param webSocketStompResource The dto instance of {@link WebSocketStompResource} that will be added to the {@link WebSocketStompApplication}
     * @return The saved {@link WebSocketStompResourceDto}
     * @see WebSocketStompProject
     * @see WebSocketStompProjectDto
     * @see WebSocketStompApplication
     * @see WebSocketStompApplicationDto
     * @see WebSocketStompResource
     * @see WebSocketStompResourceDto
     */
    WebSocketStompResourceDto saveWebSocketStompResource(String webSocketStompProjectId, String webSocketStompApplicationId, WebSocketStompResourceDto webSocketStompResource);


    /**
     * The method adds a new {@link WebSocketStompMockResponse} to a {@link WebSocketStompResource} and then saves
     * the {@link WebSocketStompProject} to the file system.
     * @param webSocketStompProjectId The id of the {@link WebSocketStompProject}
     * @param webSocketStompApplicationId The id of the {@link WebSocketStompApplication}
     * @param webSocketStompResourceId The id of the {@link WebSocketStompResource}
     * @param webSocketStompMockResponseDto The dto instance of {@link WebSocketStompMockResponse} that will be added to the {@link WebSocketStompResource}
     * @return The saved {@link WebSocketStompMockResponseDto}
     * @see WebSocketStompProject
     * @see WebSocketStompProjectDto
     * @see WebSocketStompApplication
     * @see WebSocketStompApplicationDto
     * @see WebSocketStompResource
     * @see WebSocketStompResourceDto
     * @see WebSocketStompMockResponse
     * @see WebSocketStompMockResponseDto
     */
    WebSocketStompMockResponseDto saveWebSocketStompMockResponse(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId, WebSocketStompMockResponseDto webSocketStompMockResponseDto);

    /*
     * UPDATE OPERATIONS
     */

    /**
     * The method updates an already existing {@link WebSocketStompApplication}
     * @param webSocketStompProjectId The id of the {@link WebSocketStompProject}
     * @param webSocketStompApplicationId The id of the {@link WebSocketStompApplication} that will be updated
     * @param webSocketStompApplicationDto The updated {@link WebSocketStompApplicationDto}
     * @return The updated version of the {@link WebSocketStompApplicationDto}
     * @see WebSocketStompProject
     * @see WebSocketStompProjectDto
     * @see WebSocketStompApplication
     * @see WebSocketStompApplicationDto
     */
    WebSocketStompApplicationDto updateWebSocketStompApplication(String webSocketStompProjectId, String webSocketStompApplicationId, WebSocketStompApplicationDto webSocketStompApplicationDto);

    /**
     * The method updates an already existing {@link WebSocketStompResource}
     * @param webSocketStompProjectId The id of the {@link WebSocketStompProject}
     * @param webSocketStompApplicationId The id of the {@link WebSocketStompApplication}
     * @param webSocketStompResourceId The id of the {@link WebSocketStompResource} that will be updated
     * @param webSocketStompResourceDto The updated {@link WebSocketStompResourceDto}
     * @return The updated version of the {@link WebSocketStompResourceDto}
     * @see WebSocketStompProject
     * @see WebSocketStompProjectDto
     * @see WebSocketStompApplication
     * @see WebSocketStompApplicationDto
     * @see WebSocketStompResource
     * @see WebSocketStompResourceDto
     */
    WebSocketStompResourceDto updateWebSocketStompResource(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId, WebSocketStompResourceDto webSocketStompResourceDto);


    /**
     * The method updates an already existing {@link WebSocketStompResource}
     * @param webSocketStompProjectId The id of the {@link WebSocketStompProject}
     * @param webSocketStompApplicationId The id of the {@link WebSocketStompApplication}
     * @param webSocketStompResourceId The id of the {@link WebSocketStompResource}
     * @param webSocketStompMockResponseId The id of the {@link WebSocketStompMockResponse} that will be updated
     * @param webSocketStompMockResponseDto The updated {@link WebSocketStompMockResponseDto)
     * @return The updated version of the {@link WebSocketStompMockResponseDto}
     * @see WebSocketStompProject
     * @see WebSocketStompProjectDto
     * @see WebSocketStompApplication
     * @see WebSocketStompApplicationDto
     * @see WebSocketStompResource
     * @see WebSocketStompResourceDto
     * @see WebSocketStompMockResponse
     * @see WebSocketStompMockResponseDto
     */
    WebSocketStompMockResponseDto updateWebSocketStompMockResponse(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId, String webSocketStompMockResponseId, WebSocketStompMockResponseDto webSocketStompMockResponseDto);

    /*
     * DELETE OPERATIONS
     */

    /**
     * The method deletes a {@link WebSocketStompApplication}
     * @param webSocketStompProjectId The id of the {@link WebSocketStompProject}
     * @param webSocketStompApplicationId The id of the {@link WebSocketStompApplication}
     * @return The deleted {@link WebSocketStompApplicationDto}
     * @see WebSocketStompProject
     * @see WebSocketStompProjectDto
     * @see WebSocketStompApplication
     * @see WebSocketStompApplicationDto
     */
    WebSocketStompApplicationDto deleteWebSocketStompApplication(String webSocketStompProjectId, String webSocketStompApplicationId);

    /**
     * The method deletes a {@link WebSocketStompResource}
     * @param webSocketStompProjectId The id of the {@link WebSocketStompProject}
     * @param webSocketStompApplicationId The id of the {@link WebSocketStompApplication}
     * @param webSocketStompResourceId The id of the {@link WebSocketStompResource}
     * @return The deleted {@link WebSocketStompResourceDto}
     * @see WebSocketStompProject
     * @see WebSocketStompProjectDto
     * @see WebSocketStompApplication
     * @see WebSocketStompApplicationDto
     * @see WebSocketStompResource
     * @see WebSocketStompResourceDto
     */
    WebSocketStompResourceDto deleteWebSocketStompResource(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId);


    /**
     * The method deletes a {@link WebSocketStompMockResponse}
     * @param webSocketStompProjectId The id of the {@link WebSocketStompProject}
     * @param webSocketStompApplicationId The id of the {@link WebSocketStompApplication}
     * @param webSocketStompResourceId The id of the {@link WebSocketStompResource}
     * @param webSocketStompMockResponseId The id of the {@link WebSocketStompMockResponse}
     * @return The deleted {@link WebSocketStompMockResponseDto}
     * @see WebSocketStompProject
     * @see WebSocketStompProjectDto
     * @see WebSocketStompApplication
     * @see WebSocketStompApplicationDto
     * @see WebSocketStompResource
     * @see WebSocketStompResourceDto
     * @see WebSocketStompMockResponse
     * @see WebSocketStompMockResponseDto
     */
    WebSocketStompMockResponseDto deleteWebSocketStompMockResponse(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId, String webSocketStompMockResponseId);

}
