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

package com.castlemock.web.mock.websocket.stomp.model.project.service;

import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompApplication;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompProject;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompResource;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompResourceStatus;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompProjectDto;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompResourceDto;
import com.castlemock.web.basis.model.AbstractService;
import com.google.common.base.Preconditions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public abstract class AbstractWebSocketStompProjectService extends AbstractService<WebSocketStompProject, WebSocketStompProjectDto, String> {

    protected static final String SLASH = "/";
    protected static final String START_BRACKET = "{";
    protected static final String END_BRACKET = "}";

    /**
     * Count the operation statuses
     * @param webSocketStompResources The list of resources, which status will be counted
     * @return The result of the status count
     */
    protected Map<WebSocketStompResourceStatus, Integer> getWebSocketStompResourceStatusCount(final List<WebSocketStompResourceDto> webSocketStompResources){
        Preconditions.checkNotNull(webSocketStompResources, "The resource list cannot be null");
        final Map<WebSocketStompResourceStatus, Integer> statuses = new HashMap<WebSocketStompResourceStatus, Integer>();

        for(WebSocketStompResourceStatus webSocketStompResourceStatus : WebSocketStompResourceStatus.values()){
            statuses.put(webSocketStompResourceStatus, 0);
        }
        for(WebSocketStompResourceDto webSocketStompResourceDto : webSocketStompResources){
            WebSocketStompResourceStatus webSocketStompResourceStatus = webSocketStompResourceDto.getStatus();
            statuses.put(webSocketStompResourceStatus, statuses.get(webSocketStompResourceStatus)+1);
        }
        return statuses;
    }

    /**
     * The save method saves a project to the database
     * @param project Project that will be saved to the database
     * @return The saved project
     */
    @Override
    public WebSocketStompProjectDto save(final WebSocketStompProjectDto project){
        Preconditions.checkNotNull(project, "Project cannot be null");
        Preconditions.checkArgument(!project.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final WebSocketStompProjectDto projectInDatebase = findWebSocketStompProject(project.getName());
        Preconditions.checkArgument(projectInDatebase == null, "Project name is already taken");
        project.setUpdated(new Date());
        project.setCreated(new Date());
        return super.save(project);
    }


    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see WebSocketStompProjectDto
     */
    public WebSocketStompProjectDto findWebSocketStompProject(final String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");
        for(WebSocketStompProject webSocketStompProject : findAllTypes()){
            if(webSocketStompProject.getName().equalsIgnoreCase(name)) {
                return toDto(webSocketStompProject);
            }
        }
        return null;
    }

    protected WebSocketStompApplication findWebSocketStompApplicationType(final String webSocketStompProjectId, final String webSocketStompApplicationId) {
        Preconditions.checkNotNull(webSocketStompProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(webSocketStompApplicationId, "Application id cannot be null");
        final WebSocketStompProject webSocketStompProject = findType(webSocketStompProjectId);

        if(webSocketStompProject == null){
            throw new IllegalArgumentException("Unable to find a WebSocket Stomp project with id " + webSocketStompProjectId);
        }

        for(WebSocketStompApplication webSocketStompApplication : webSocketStompProject.getApplications()){
            if(webSocketStompApplication.getId().equals(webSocketStompApplicationId)){
                return webSocketStompApplication;
            }
        }
        throw new IllegalArgumentException("Unable to find a WebSocket Stomp application with id " + webSocketStompApplicationId);
    }

    protected WebSocketStompResource findWebSocketStompResourceType(final String webSocketStompProjectId, final String webSocketStompApplicationId, final String webSocketStompResourceId){
        Preconditions.checkNotNull(webSocketStompResourceId, "Resource id cannot be null");
        final WebSocketStompApplication webSocketStompApplication = findWebSocketStompApplicationType(webSocketStompProjectId, webSocketStompApplicationId);
        for(WebSocketStompResource webSocketStompResource : webSocketStompApplication.getResources()){
            if(webSocketStompResource.getId().equals(webSocketStompResourceId)){
                return webSocketStompResource;
            }
        }
        throw new IllegalArgumentException("Unable to find a WebSocket Stomp resource with id " + webSocketStompResourceId);
    }

    /**
     * Find a WebSocket Stomp resource with a project id, application id and a set of resource parts
     * @param webSocketStompProjectId The id of the project that the resource belongs to
     * @param webSocketStompApplicationId The id of the application that the resource belongs to
     * @param otherWebSocketStompResourceUriParts The set of resources that will be used to identify the WebSocket Stomp resource
     * @return A WebSocket Stomp resource that matches the search criteria. Null otherwise
     */
    protected WebSocketStompResource findWebSocketStompResourceType(final String webSocketStompProjectId, final String webSocketStompApplicationId, final String[] otherWebSocketStompResourceUriParts) {
        final WebSocketStompApplication webSocketStompApplication = findWebSocketStompApplicationType(webSocketStompProjectId, webSocketStompApplicationId);

        for(WebSocketStompResource webSocketStompResource : webSocketStompApplication.getResources()){
            final String[] webSocketStompResourceUriParts = webSocketStompResource.getUri().split(SLASH);

            if(compareWebSocketStompResourceUri(webSocketStompResourceUriParts, otherWebSocketStompResourceUriParts)){
                return webSocketStompResource;
            }
        }

        return null;
    }

    /**
     * The method provides the functionality to compare two sets of WebSocket Stomp resource URI parts.
     * @param webSocketStompResourceUriParts THe first set of resource URI parts
     * @param otherWebSocketStompResourceUriParts The second set of resource URI parts
     * @return True if the provided URIs are matching. False otherwise
     */
    protected boolean compareWebSocketStompResourceUri(final String[] webSocketStompResourceUriParts, final String[] otherWebSocketStompResourceUriParts){
        if(webSocketStompResourceUriParts.length != otherWebSocketStompResourceUriParts.length){
            return false;
        }

        for(int index = 0; index < webSocketStompResourceUriParts.length; index++){
            final String webSocketStompResourceUriPart = webSocketStompResourceUriParts[index];
            final String otherWebSocketStompResourceUriPart = otherWebSocketStompResourceUriParts[index];

            if(webSocketStompResourceUriPart.startsWith(START_BRACKET) && webSocketStompResourceUriPart.endsWith(END_BRACKET)){
                continue;
            }

            if(!webSocketStompResourceUriPart.equalsIgnoreCase(otherWebSocketStompResourceUriPart)){
                return false;
            }
        }
        return true;
    }
}
