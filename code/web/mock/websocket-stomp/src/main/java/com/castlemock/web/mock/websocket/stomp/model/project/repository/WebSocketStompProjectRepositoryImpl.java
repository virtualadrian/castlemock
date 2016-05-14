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

import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompApplication;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompMockResponse;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompProject;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompResource;
import com.castlemock.web.basis.model.RepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving WebSocket stomp project from the file system. Each WebSocket stomp project is stored as
 * a separate file. The class also contains the directory and the filename extension for the WebSocket stomp project.
 * @author Karl Dahlgren
 * @since 1.5
 * @see WebSocketStompProjectProjectRepository
 * @see RepositoryImpl
 * @see WebSocketStompProject
 */
@Repository
public class WebSocketStompProjectRepositoryImpl extends RepositoryImpl<WebSocketStompProject, String> implements WebSocketStompProjectProjectRepository {

    @Value(value = "${websocket.stomp.project.file.directory}")
    private String webSocketStompProjectFileDirectory;
    @Value(value = "${websocket.stomp.project.file.extension}")
    private String webSocketStompProjectFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return webSocketStompProjectFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return webSocketStompProjectFileExtension;
    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful.
     *
     * The method is responsible to validate the imported types and make certain that all the collections are
     * initialized.
     * @see #initialize
     * @see WebSocketStompProject
     * @since 1.5
     */
    @Override
    protected void postInitiate() {

    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param webSocketStompProject The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     * @see WebSocketStompProject
     */
    @Override
    protected void checkType(WebSocketStompProject webSocketStompProject) {

    }


    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param webSocketStompProject The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public WebSocketStompProject save(final WebSocketStompProject webSocketStompProject) {
        for(WebSocketStompApplication webSocketStompApplication : webSocketStompProject.getApplications()){
            if(webSocketStompApplication.getId() == null){
                String webSocketStompApplicationId = generateId();
                webSocketStompApplication.setId(webSocketStompApplicationId);
            }
            for(WebSocketStompResource webSocketStompResource : webSocketStompApplication.getResources()){
                if(webSocketStompResource.getId() == null){
                    String webSocketStompResourceId = generateId();
                    webSocketStompResource.setId(webSocketStompResourceId);
                }
                for(WebSocketStompMockResponse webSocketStompMockResponse : webSocketStompResource.getMockResponses()){
                    if(webSocketStompMockResponse.getId() == null){
                        String webSocketStompMockResponseId = generateId();
                        webSocketStompMockResponse.setId(webSocketStompMockResponseId);
                    }
                }
            }
        }
        return super.save(webSocketStompProject);
    }

}
