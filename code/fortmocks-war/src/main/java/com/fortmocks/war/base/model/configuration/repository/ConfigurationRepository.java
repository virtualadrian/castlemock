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

package com.fortmocks.war.base.model.configuration.repository;

import com.fortmocks.core.base.model.Repository;
import com.fortmocks.core.base.model.configuration.ConfigurationGroup;

/**
 * The configuration file repository provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving configuration groups for the file system. Each
 * configuration group is stored as a separate file.
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.base.model.configuration.ConfigurationGroup
 * @see com.fortmocks.core.base.model.Repository
 * @see com.fortmocks.war.base.model.configuration.repository.ConfigurationRepositoryImpl
 */
public interface ConfigurationRepository extends Repository<ConfigurationGroup, Long> {
}
