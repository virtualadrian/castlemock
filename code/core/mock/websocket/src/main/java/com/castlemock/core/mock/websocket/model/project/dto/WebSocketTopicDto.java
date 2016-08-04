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

package com.castlemock.core.mock.websocket.model.project.dto;

import com.castlemock.core.mock.websocket.model.project.domain.WebSocketResourceStatus;
import org.dozer.Mapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public class WebSocketTopicDto {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("resources")
    private List<WebSocketResourceDto> resources = new LinkedList<WebSocketResourceDto>();

    private Map<WebSocketResourceStatus, Integer> statusCount = new HashMap<WebSocketResourceStatus, Integer>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WebSocketResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<WebSocketResourceDto> resources) {
        this.resources = resources;
    }

    public Map<WebSocketResourceStatus, Integer> getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(Map<WebSocketResourceStatus, Integer> statusCount) {
        this.statusCount = statusCount;
    }
}
