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

package com.castlemock.web.mock.websocket.model.project.service;

import com.castlemock.core.mock.websocket.model.project.domain.WebSocketProject;
import com.castlemock.core.mock.websocket.model.project.domain.WebSocketResourceStatus;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketProjectDto;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketResourceDto;
import com.castlemock.web.basis.model.AbstractService;
import com.castlemock.web.mock.websocket.model.project.repository.WebSocketProjectRepository;
import com.google.common.base.Preconditions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public abstract class AbstractWebSocketProjectService extends AbstractService<WebSocketProject, WebSocketProjectDto, String, WebSocketProjectRepository> {

    /**
     * Count the operation statuses
     * @param webSocketResources The list of resources, which status will be counted
     * @return The result of the status count
     */
    protected Map<WebSocketResourceStatus, Integer> getWebSocketResourceStatusCount(final List<WebSocketResourceDto> webSocketResources){
        Preconditions.checkNotNull(webSocketResources, "The resource list cannot be null");
        final Map<WebSocketResourceStatus, Integer> statuses = new HashMap<WebSocketResourceStatus, Integer>();

        for(WebSocketResourceStatus webSocketResourceStatus : WebSocketResourceStatus.values()){
            statuses.put(webSocketResourceStatus, 0);
        }
        for(WebSocketResourceDto webSocketResourceDto : webSocketResources){
            WebSocketResourceStatus webSocketResourceStatus = webSocketResourceDto.getStatus();
            statuses.put(webSocketResourceStatus, statuses.get(webSocketResourceStatus)+1);
        }
        return statuses;
    }

    /**
     * The save method saves a project to the database
     * @param project Project that will be saved to the database
     * @return The saved project
     */
    @Override
    public WebSocketProjectDto save(final WebSocketProjectDto project){
        Preconditions.checkNotNull(project, "Project cannot be null");
        Preconditions.checkArgument(!project.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final WebSocketProjectDto projectInDatebase = repository.findWebSocketProject(project.getName());
        Preconditions.checkArgument(projectInDatebase == null, "Project name is already taken");
        project.setUpdated(new Date());
        project.setCreated(new Date());
        return super.save(project);
    }




}
