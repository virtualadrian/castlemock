/*
 * Copyright 2015 Karl Dahlgren
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

package com.fortmocks.war.base.web.mvc.command.project;

import com.fortmocks.core.base.model.project.dto.ProjectDto;

/**
 * The CreateProjectCommand is a command object and is used when creating a new project.
 * The class contains both the instance of the project that will be created. It also contains
 * the project type of the instance.
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.base.model.project.Project
 * @see com.fortmocks.core.base.model.project.dto.ProjectDto
 * @see com.fortmocks.war.base.web.mvc.controller.project.CreateProjectController
 */
public class CreateProjectCommand {

    private ProjectDto project = new ProjectDto();
    private String projectType;

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}

