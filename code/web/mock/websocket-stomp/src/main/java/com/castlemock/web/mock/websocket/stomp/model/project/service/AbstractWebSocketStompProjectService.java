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

import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompProject;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompResourceStatus;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompProjectDto;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompResourceDto;
import com.castlemock.web.basis.model.AbstractService;
import com.castlemock.web.mock.websocket.stomp.model.project.repository.WebSocketStompProjectRepository;
import com.google.common.base.Preconditions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public abstract class AbstractWebSocketStompProjectService extends AbstractService<WebSocketStompProject, WebSocketStompProjectDto, String, WebSocketStompProjectRepository> {

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
        final WebSocketStompProjectDto projectInDatebase = repository.findWebSocketStompProject(project.getName());
        Preconditions.checkArgument(projectInDatebase == null, "Project name is already taken");
        project.setUpdated(new Date());
        project.setCreated(new Date());
        return super.save(project);
    }




}
