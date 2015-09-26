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

package com.fortmocks.war.mock.soap.model;

import com.fortmocks.core.base.model.TypeIdentifier;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapTypeIdentifier implements TypeIdentifier {

    private static final String SOAP_TYPE = "SOAP";
    private static final String SOAP_TYPE_URL = "soap";

    @Override
    public String getType(){
        return SOAP_TYPE;
    }

    @Override
    public String getTypeUrl(){
        return SOAP_TYPE_URL;
    }

}
