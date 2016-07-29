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

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.websocket.stomp.model.event.domain.WebSocketStompResponse;
import com.castlemock.core.mock.websocket.stomp.model.event.dto.WebSocketStompResponseDto;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompApplication;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompMockResponse;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompProject;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompResource;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompApplicationDto;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompMockResponseDto;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompProjectDto;
import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompResourceDto;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving WebSocket stomp project from the file system. Each WebSocket stomp project is stored as
 * a separate file. The class also contains the directory and the filename extension for the WebSocket stomp project.
 * @author Karl Dahlgren
 * @since 1.5
 * @see WebSocketStompProjectRepository
 * @see RepositoryImpl
 * @see WebSocketStompProject
 */
@Repository
public class WebSocketStompProjectRepositoryImpl extends RepositoryImpl<WebSocketStompProject, WebSocketStompProjectDto, String> implements WebSocketStompProjectRepository {

    @Value(value = "${websocket.stomp.project.file.directory}")
    private String webSocketStompProjectFileDirectory;
    @Value(value = "${websocket.stomp.project.file.extension}")
    private String webSocketStompProjectFileExtension;

    @Autowired
    private MessageSource messageSource;

    private static final String WEBSOCKET_STOMP = "WebSocketStomp";
    private static final String PROJECT = "project";
    private static final String APPLICATION = "application";
    private static final String RESOURCE = "resource";
    private static final String RESPONSE = "response";
    private static final String COMMA = ", ";
    private static final String WEBSOCKET_STOMP_TYPE = "WebSocket Stomp";

    protected static final String SLASH = "/";
    protected static final String START_BRACKET = "{";
    protected static final String END_BRACKET = "}";

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
    public WebSocketStompProjectDto save(final WebSocketStompProjectDto webSocketStompProject) {
        for(WebSocketStompApplicationDto webSocketStompApplication : webSocketStompProject.getApplications()){
            if(webSocketStompApplication.getId() == null){
                String webSocketStompApplicationId = generateId();
                webSocketStompApplication.setId(webSocketStompApplicationId);
            }
            for(WebSocketStompResourceDto webSocketStompResource : webSocketStompApplication.getResources()){
                if(webSocketStompResource.getId() == null){
                    String webSocketStompResourceId = generateId();
                    webSocketStompResource.setId(webSocketStompResourceId);
                }
                for(WebSocketStompMockResponseDto webSocketStompMockResponse : webSocketStompResource.getMockResponses()){
                    if(webSocketStompMockResponse.getId() == null){
                        String webSocketStompMockResponseId = generateId();
                        webSocketStompMockResponse.setId(webSocketStompMockResponseId);
                    }
                }
            }
        }
        return super.save(webSocketStompProject);
    }

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        final String query = searchQuery.getQuery();
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        for(WebSocketStompProject webSocketStompProject : collection.values()){
            List<SearchResult> webSocketStompProjectSearchResult = searchWebSocketStompProject(webSocketStompProject, query);
            searchResults.addAll(webSocketStompProjectSearchResult);
        }
        return searchResults;
    }


    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see WebSocketStompProjectDto
     */
    @Override
    public WebSocketStompProjectDto findWebSocketStompProject(final String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");
        for(WebSocketStompProject webSocketStompProject : collection.values()){
            if(webSocketStompProject.getName().equalsIgnoreCase(name)) {
                return mapper.map(webSocketStompProject, WebSocketStompProjectDto.class);
            }
        }
        return null;
    }

    public WebSocketStompApplication findWebSocketStompApplicationType(final String webSocketStompProjectId, final String webSocketStompApplicationId) {
        Preconditions.checkNotNull(webSocketStompProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(webSocketStompApplicationId, "Application id cannot be null");
        final WebSocketStompProject webSocketStompProject = collection.get(webSocketStompProjectId);

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

    public WebSocketStompResource findWebSocketStompResourceType(final String webSocketStompProjectId, final String webSocketStompApplicationId, final String webSocketStompResourceId){
        Preconditions.checkNotNull(webSocketStompResourceId, "Resource id cannot be null");
        final WebSocketStompApplication webSocketStompApplication = findWebSocketStompApplicationType(webSocketStompProjectId, webSocketStompApplicationId);
        for(WebSocketStompResource webSocketStompResource : webSocketStompApplication.getResources()){
            if(webSocketStompResource.getId().equals(webSocketStompResourceId)){
                return webSocketStompResource;
            }
        }
        throw new IllegalArgumentException("Unable to find a WebSocket Stomp resource with id " + webSocketStompResourceId);
    }

    public WebSocketStompMockResponse findWebSocketStompMockResponseType(final String webSocketStompProjectId, final String webSocketStompApplicationId, final String webSocketStompResourceId, final String webSocketStompMockResponseId){
        Preconditions.checkNotNull(webSocketStompResourceId, "Resource id cannot be null");
        final WebSocketStompResource webSocketStompResource = findWebSocketStompResourceType(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId);
        for(WebSocketStompMockResponse webSocketStompMockResponse : webSocketStompResource.getMockResponses()){
            if(webSocketStompMockResponse.getId().equals(webSocketStompMockResponseId)){
                return webSocketStompMockResponse;
            }
        }
        throw new IllegalArgumentException("Unable to find a WebSocket Stomp mock response with id " + webSocketStompMockResponseId);
    }




    /**
     * Find a WebSocket Stomp resource with a project id, application id and a set of resource parts
     * @param webSocketStompProjectId The id of the project that the resource belongs to
     * @param webSocketStompApplicationId The id of the application that the resource belongs to
     * @param otherWebSocketStompResourceUriParts The set of resources that will be used to identify the WebSocket Stomp resource
     * @return A WebSocket Stomp resource that matches the search criteria. Null otherwise
     */
    public WebSocketStompResource findWebSocketStompResourceType(final String webSocketStompProjectId, final String webSocketStompApplicationId, final String[] otherWebSocketStompResourceUriParts) {
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

    @Override
    public WebSocketStompApplicationDto findWebSocketStompApplication(String webSocketStompProjectId, String webSocketStompApplicationId) {
        Preconditions.checkNotNull(webSocketStompApplicationId, "Application id cannot be null");
        final WebSocketStompApplication webSocketStompApplication = findWebSocketStompApplicationType(webSocketStompProjectId, webSocketStompApplicationId);
        return mapper.map(webSocketStompApplication, WebSocketStompApplicationDto.class);
    }

    @Override
    public WebSocketStompResourceDto findWebSocketStompResource(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId) {
        Preconditions.checkNotNull(webSocketStompResourceId, "Resource id cannot be null");
        final WebSocketStompResource webSocketStompResource = findWebSocketStompResourceType(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId);
        return mapper.map(webSocketStompResource, WebSocketStompResourceDto.class);
    }

    @Override
    public WebSocketStompMockResponseDto findWebSocketStompMockResponse(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId, String webSocketStompMockResponseId) {
        Preconditions.checkNotNull(webSocketStompResourceId, "Mock response id cannot be null");
        final WebSocketStompMockResponse webSocketStompMockResponse = findWebSocketStompMockResponseType(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId, webSocketStompMockResponseId);
        return mapper.map(webSocketStompMockResponse, WebSocketStompMockResponseDto.class);
    }

    @Override
    public WebSocketStompApplicationDto saveWebSocketStompApplication(String webSocketStompProjectId, WebSocketStompApplicationDto webSocketStompApplicationDto) {
        WebSocketStompProject webSocketStompProject = collection.get(webSocketStompProjectId);
        WebSocketStompApplication webSocketStompApplication = mapper.map(webSocketStompApplicationDto, WebSocketStompApplication.class);
        webSocketStompProject.getApplications().add(webSocketStompApplication);
        save(webSocketStompProjectId);
        return mapper.map(webSocketStompApplication, WebSocketStompApplicationDto.class);
    }

    @Override
    public WebSocketStompResourceDto saveWebSocketStompResource(String webSocketStompProjectId, String webSocketStompApplicationId, WebSocketStompResourceDto webSocketStompResourceDto) {
        WebSocketStompApplication webSocketStompApplication = findWebSocketStompApplicationType(webSocketStompProjectId, webSocketStompApplicationId);
        WebSocketStompResource webSocketStompResource = mapper.map(webSocketStompResourceDto, WebSocketStompResource.class);
        webSocketStompApplication.getResources().add(webSocketStompResource);
        save(webSocketStompProjectId);
        return mapper.map(webSocketStompResource, WebSocketStompResourceDto.class);
    }

    @Override
    public WebSocketStompMockResponseDto saveWebSocketStompMockResponse(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId, WebSocketStompMockResponseDto webSocketStompMockResponseDto) {
        WebSocketStompResource webSocketStompResource = findWebSocketStompResourceType(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId);
        WebSocketStompMockResponse webSocketStompMockResponse = mapper.map(webSocketStompMockResponseDto, WebSocketStompMockResponse.class);
        webSocketStompResource.getMockResponses().add(webSocketStompMockResponse);
        save(webSocketStompProjectId);
        return mapper.map(webSocketStompMockResponse, WebSocketStompMockResponseDto.class);
    }

    @Override
    public WebSocketStompApplicationDto updateWebSocketStompApplication(String webSocketStompProjectId, String webSocketStompApplicationId, WebSocketStompApplicationDto updatedWebSocketStompApplicationDto) {
        WebSocketStompApplication webSocketStompApplication = findWebSocketStompApplicationType(webSocketStompProjectId, webSocketStompApplicationId);
        webSocketStompApplication.setName(updatedWebSocketStompApplicationDto.getName());
        return updatedWebSocketStompApplicationDto;
    }

    @Override
    public WebSocketStompResourceDto updateWebSocketStompResource(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId, WebSocketStompResourceDto webSocketStompResourceDto) {
        WebSocketStompResource webSocketStompResource = findWebSocketStompResourceType(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId);
        webSocketStompResource.setName(webSocketStompResourceDto.getName());
        webSocketStompResource.setUri(webSocketStompResourceDto.getUri());
        webSocketStompResource.setStatus(webSocketStompResourceDto.getStatus());
        return webSocketStompResourceDto;
    }

    @Override
    public WebSocketStompMockResponseDto updateWebSocketStompMockResponse(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId, String webSocketStompMockResponseId, WebSocketStompMockResponseDto webSocketStompMockResponseDto) {
        WebSocketStompMockResponse webSocketStompMockResponse = findWebSocketStompMockResponseType(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId, webSocketStompMockResponseId);
        webSocketStompMockResponse.setName(webSocketStompMockResponseDto.getName());
        webSocketStompMockResponse.setBody(webSocketStompMockResponseDto.getBody());
        webSocketStompMockResponse.setHttpStatusCode(webSocketStompMockResponseDto.getHttpStatusCode());
        webSocketStompMockResponse.setStatus(webSocketStompMockResponseDto.getStatus());
        return webSocketStompMockResponseDto;
    }

    @Override
    public WebSocketStompApplicationDto deleteWebSocketStompApplication(String webSocketStompProjectId, String webSocketStompApplicationId) {
        WebSocketStompProject webSocketStompProject = collection.get(webSocketStompProjectId);
        Iterator<WebSocketStompApplication> webSocketStompApplicationIterator = webSocketStompProject.getApplications().iterator();
        WebSocketStompApplication deletedWebSocketStompApplication = null;
        while(webSocketStompApplicationIterator.hasNext()){
            deletedWebSocketStompApplication = webSocketStompApplicationIterator.next();
            if(webSocketStompApplicationId.equals(deletedWebSocketStompApplication.getId())){
                webSocketStompApplicationIterator.remove();
                break;
            }
        }

        if(deletedWebSocketStompApplication != null){
            save(webSocketStompProjectId);
        }
        return deletedWebSocketStompApplication != null ? mapper.map(deletedWebSocketStompApplication, WebSocketStompApplicationDto.class) : null;
    }

    @Override
    public WebSocketStompResourceDto deleteWebSocketStompResource(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId) {
        WebSocketStompApplication webSocketStompApplication = findWebSocketStompApplicationType(webSocketStompProjectId, webSocketStompApplicationId);
        Iterator<WebSocketStompResource> webSocketStompResourceIterator = webSocketStompApplication.getResources().iterator();
        WebSocketStompResource deletedWebSocketStompResource = null;
        while(webSocketStompResourceIterator.hasNext()){
            deletedWebSocketStompResource = webSocketStompResourceIterator.next();
            if(webSocketStompApplicationId.equals(deletedWebSocketStompResource.getId())){
                webSocketStompResourceIterator.remove();
                break;
            }
        }

        if(deletedWebSocketStompResource != null){
            save(webSocketStompProjectId);
        }
        return deletedWebSocketStompResource != null ? mapper.map(deletedWebSocketStompResource, WebSocketStompResourceDto.class) : null;
    }

    @Override
    public WebSocketStompMockResponseDto deleteWebSocketStompMockResponse(String webSocketStompProjectId, String webSocketStompApplicationId, String webSocketStompResourceId, String webSocketStompMockResponseId) {
        WebSocketStompResource webSocketStompResource = findWebSocketStompResourceType(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId);
        Iterator<WebSocketStompMockResponse> webSocketStompMockResponseIterator = webSocketStompResource.getMockResponses().iterator();
        WebSocketStompMockResponse deletedWebSocketStompMockResponse = null;
        while(webSocketStompMockResponseIterator.hasNext()){
            deletedWebSocketStompMockResponse = webSocketStompMockResponseIterator.next();
            if(webSocketStompApplicationId.equals(deletedWebSocketStompMockResponse.getId())){
                webSocketStompMockResponseIterator.remove();
                break;
            }
        }

        if(deletedWebSocketStompMockResponse != null){
            save(webSocketStompProjectId);
        }
        return deletedWebSocketStompMockResponse != null ? mapper.map(deletedWebSocketStompMockResponse, WebSocketStompMockResponseDto.class) : null;
    }


    /**
     * Search through a WebSocket Stomp project and all its resources
     * @param webSocketStompProject The WebSocket Stomp project which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchWebSocketStompProject(final WebSocketStompProject webSocketStompProject, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(webSocketStompProject.getName(), query)){
            final String projectType = messageSource.getMessage("general.type.project", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(webSocketStompProject.getName());
            searchResult.setLink(WEBSOCKET_STOMP + SLASH + PROJECT + SLASH + webSocketStompProject.getId());
            searchResult.setDescription(WEBSOCKET_STOMP_TYPE + COMMA + projectType);
            searchResults.add(searchResult);
        }


        for(WebSocketStompApplication webSocketStompApplication : webSocketStompProject.getApplications()){
            List<SearchResult> webSocketStompOperationSearchResult = searchWebSocketStompApplication(webSocketStompProject, webSocketStompApplication, query);
            searchResults.addAll(webSocketStompOperationSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a WebSocket Stomp application and all its resources
     * @param webSocketStompProject The WebSocket Stomp project which will be searched
     * @param webSocketStompApplication The WebSocket Stomp application which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchWebSocketStompApplication(final WebSocketStompProject webSocketStompProject, final WebSocketStompApplication webSocketStompApplication, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(webSocketStompApplication.getName(), query)){
            final String portType = messageSource.getMessage("webSocketStomp.type.port", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(webSocketStompApplication.getName());
            searchResult.setLink(WEBSOCKET_STOMP + SLASH + PROJECT + SLASH + webSocketStompProject.getId() + SLASH + APPLICATION + SLASH + webSocketStompApplication.getId());
            searchResult.setDescription(WEBSOCKET_STOMP_TYPE + COMMA + portType);
            searchResults.add(searchResult);
        }

        for(WebSocketStompResource webSocketStompResource : webSocketStompApplication.getResources()){
            List<SearchResult> webSocketStompOperationSearchResult = searchWebSocketStompResource(webSocketStompProject, webSocketStompApplication, webSocketStompResource, query);
            searchResults.addAll(webSocketStompOperationSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a WebSocket Stomp resource and all its resources
     * @param webSocketStompProject The WebSocket Stomp project which will be searched
     * @param webSocketStompApplication The WebSocket Stomp application which will be searched
     * @param webSocketStompResource The WebSocket Stomp resource which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchWebSocketStompResource(final WebSocketStompProject webSocketStompProject, final WebSocketStompApplication webSocketStompApplication, final WebSocketStompResource webSocketStompResource, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(webSocketStompResource.getName(), query)){
            final String operationType = messageSource.getMessage("webSocketStomp.type.operation", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(webSocketStompResource.getName());
            searchResult.setLink(WEBSOCKET_STOMP + SLASH + PROJECT + SLASH + webSocketStompProject.getId() + SLASH + APPLICATION + SLASH + webSocketStompApplication.getId() + SLASH + RESOURCE + SLASH + webSocketStompResource.getId());
            searchResult.setDescription(WEBSOCKET_STOMP_TYPE + COMMA + operationType);
            searchResults.add(searchResult);
        }

        for(WebSocketStompMockResponse webSocketStompMockResponse : webSocketStompResource.getMockResponses()){
            SearchResult webSocketStompMockResponseSearchResult = searchWebSocketStompMockResponse(webSocketStompProject, webSocketStompApplication, webSocketStompResource, webSocketStompMockResponse, query);
            if(webSocketStompMockResponseSearchResult != null){
                searchResults.add(webSocketStompMockResponseSearchResult);
            }
        }
        return searchResults;
    }

    /**
     * Search through a WebSocket Stomp response
     * @param webSocketStompProject The WebSocket Stomp project which will be searched
     * @param webSocketStompApplication The WebSocket Stomp application which will be searched
     * @param webSocketStompResource The WebSocket Stomp resource which will be searched
     * @param webSocketStompMockResponse The WebSocket Stomp response that will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private SearchResult searchWebSocketStompMockResponse(final WebSocketStompProject webSocketStompProject, final WebSocketStompApplication webSocketStompApplication, final WebSocketStompResource webSocketStompResource, final WebSocketStompMockResponse webSocketStompMockResponse, final String query){
        SearchResult searchResult = null;

        if(SearchValidator.validate(webSocketStompMockResponse.getName(), query)){
            final String mockResponseType = messageSource.getMessage("webSocketStomp.type.mockresponse", null , LocaleContextHolder.getLocale());
            searchResult = new SearchResult();
            searchResult.setTitle(webSocketStompMockResponse.getName());
            searchResult.setLink(WEBSOCKET_STOMP + SLASH + PROJECT + SLASH + webSocketStompProject.getId() + SLASH + APPLICATION + SLASH + webSocketStompApplication.getId() + SLASH + RESOURCE + SLASH + webSocketStompResource.getId() + SLASH + RESPONSE + SLASH + webSocketStompMockResponse.getId());
            searchResult.setDescription(WEBSOCKET_STOMP_TYPE + COMMA + mockResponseType);
        }

        return searchResult;
    }
}
