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

package com.fortmocks.core.base.model.project;

import com.fortmocks.core.base.model.Saveable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * The Project class is an abstract class which all the various project types inherit from.
 * The project class is one of the most central classes in Fort Mocks and is used to organize
 * all information regarding a specific type of projects.
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.base.model.project.dto.ProjectDto
 */
@XmlRootElement
public abstract class Project implements Saveable<Long> {

    private Long id;
    private String name;
    private String description;
    private Date updated;
    private Date created;
    private DomainNameStrategy domainNameStrategy;

    @XmlElement
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @XmlElement
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @XmlElement
    public DomainNameStrategy getDomainNameStrategy() {
        return domainNameStrategy;
    }

    public void setDomainNameStrategy(DomainNameStrategy domainNameStrategy) {
        this.domainNameStrategy = domainNameStrategy;
    }

}
