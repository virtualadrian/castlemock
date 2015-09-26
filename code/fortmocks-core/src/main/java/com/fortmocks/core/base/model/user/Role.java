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

package com.fortmocks.core.base.model.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * The Role enum contains the various role values a user can have
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.base.model.user.User
 */
@XmlType
@XmlEnum(String.class)
public enum Role {
    @XmlEnumValue("READER")
    READER,
    @XmlEnumValue("MODIFIER")
    MODIFIER,
    @XmlEnumValue("ADMIN")
    ADMIN

}
