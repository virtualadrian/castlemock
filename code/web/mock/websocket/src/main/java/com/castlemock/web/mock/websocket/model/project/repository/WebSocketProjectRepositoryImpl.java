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

package com.castlemock.web.mock.websocket.model.project.repository;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.websocket.model.project.domain.WebSocketApplication;
import com.castlemock.core.mock.websocket.model.project.domain.WebSocketMockResponse;
import com.castlemock.core.mock.websocket.model.project.domain.WebSocketProject;
import com.castlemock.core.mock.websocket.model.project.domain.WebSocketResource;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketApplicationDto;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketMockResponseDto;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketProjectDto;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketResourceDto;
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
 * The repository is responsible for loading and saving WebSocket project from the file system. Each WebSocket project is stored as
 * a separate file. The class also contains the directory and the filename extension for the WebSocket project.
 * @author Karl Dahlgren
 * @since 1.5
 * @see WebSocketProjectRepository
 * @see RepositoryImpl
 * @see WebSocketProject
 */
@Repository
public class WebSocketProjectRepositoryImpl extends RepositoryImpl<WebSocketProject, WebSocketProjectDto, String> implements WebSocketProjectRepository {

    @Value(value = "${websocket.project.file.directory}")
    private String webSocketProjectFileDirectory;
    @Value(value = "${websocket.project.file.extension}")
    private String webSocketProjectFileExtension;

    @Autowired
    private MessageSource messageSource;

    private static final String WEBSOCKET = "WebSocket";
    private static final String PROJECT = "project";
    private static final String APPLICATION = "application";
    private static final String RESOURCE = "resource";
    private static final String RESPONSE = "response";
    private static final String COMMA = ", ";
    private static final String WEBSOCKET_TYPE = "WebSocket";

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
        return webSocketProjectFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return webSocketProjectFileExtension;
    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful.
     *
     * The method is responsible to validate the imported types and make certain that all the collections are
     * initialized.
     * @see #initialize
     * @see WebSocketProject
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
     * @param webSocketProject The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     * @see WebSocketProject
     */
    @Override
    protected void checkType(WebSocketProject webSocketProject) {

    }


    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param webSocketProject The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public WebSocketProjectDto save(final WebSocketProjectDto webSocketProject) {
        for(WebSocketApplicationDto webSocketApplication : webSocketProject.getApplications()){
            if(webSocketApplication.getId() == null){
                String webSocketApplicationId = generateId();
                webSocketApplication.setId(webSocketApplicationId);
            }
            for(WebSocketResourceDto webSocketResource : webSocketApplication.getResources()){
                if(webSocketResource.getId() == null){
                    String webSocketResourceId = generateId();
                    webSocketResource.setId(webSocketResourceId);
                }
                for(WebSocketMockResponseDto webSocketMockResponse : webSocketResource.getMockResponses()){
                    if(webSocketMockResponse.getId() == null){
                        String webSocketMockResponseId = generateId();
                        webSocketMockResponse.setId(webSocketMockResponseId);
                    }
                }
            }
        }
        return super.save(webSocketProject);
    }

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        final String query = searchQuery.getQuery();
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        for(WebSocketProject webSocketProject : collection.values()){
            List<SearchResult> webSocketProjectSearchResult = searchWebSocketProject(webSocketProject, query);
            searchResults.addAll(webSocketProjectSearchResult);
        }
        return searchResults;
    }


    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see WebSocketProjectDto
     */
    @Override
    public WebSocketProjectDto findWebSocketProject(final String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");
        for(WebSocketProject webSocketProject : collection.values()){
            if(webSocketProject.getName().equalsIgnoreCase(name)) {
                return mapper.map(webSocketProject, WebSocketProjectDto.class);
            }
        }
        return null;
    }

    public WebSocketApplication findWebSocketApplicationType(final String webSocketProjectId, final String webSocketApplicationId) {
        Preconditions.checkNotNull(webSocketProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(webSocketApplicationId, "Application id cannot be null");
        final WebSocketProject webSocketProject = collection.get(webSocketProjectId);

        if(webSocketProject == null){
            throw new IllegalArgumentException("Unable to find a WebSocket project with id " + webSocketProjectId);
        }

        for(WebSocketApplication webSocketApplication : webSocketProject.getApplications()){
            if(webSocketApplication.getId().equals(webSocketApplicationId)){
                return webSocketApplication;
            }
        }
        throw new IllegalArgumentException("Unable to find a WebSocket application with id " + webSocketApplicationId);
    }

    public WebSocketResource findWebSocketResourceType(final String webSocketProjectId, final String webSocketApplicationId, final String webSocketResourceId){
        Preconditions.checkNotNull(webSocketResourceId, "Resource id cannot be null");
        final WebSocketApplication webSocketApplication = findWebSocketApplicationType(webSocketProjectId, webSocketApplicationId);
        for(WebSocketResource webSocketResource : webSocketApplication.getResources()){
            if(webSocketResource.getId().equals(webSocketResourceId)){
                return webSocketResource;
            }
        }
        throw new IllegalArgumentException("Unable to find a WebSocket resource with id " + webSocketResourceId);
    }

    public WebSocketMockResponse findWebSocketMockResponseType(final String webSocketProjectId, final String webSocketApplicationId, final String webSocketResourceId, final String webSocketMockResponseId){
        Preconditions.checkNotNull(webSocketResourceId, "Resource id cannot be null");
        final WebSocketResource webSocketResource = findWebSocketResourceType(webSocketProjectId, webSocketApplicationId, webSocketResourceId);
        for(WebSocketMockResponse webSocketMockResponse : webSocketResource.getMockResponses()){
            if(webSocketMockResponse.getId().equals(webSocketMockResponseId)){
                return webSocketMockResponse;
            }
        }
        throw new IllegalArgumentException("Unable to find a WebSocket mock response with id " + webSocketMockResponseId);
    }




    /**
     * Find a WebSocket resource with a project id, application id and a set of resource parts
     * @param webSocketProjectId The id of the project that the resource belongs to
     * @param webSocketApplicationId The id of the application that the resource belongs to
     * @param otherWebSocketResourceUriParts The set of resources that will be used to identify the WebSocket resource
     * @return A WebSocket resource that matches the search criteria. Null otherwise
     */
    public WebSocketResource findWebSocketResourceType(final String webSocketProjectId, final String webSocketApplicationId, final String[] otherWebSocketResourceUriParts) {
        final WebSocketApplication webSocketApplication = findWebSocketApplicationType(webSocketProjectId, webSocketApplicationId);

        for(WebSocketResource webSocketResource : webSocketApplication.getResources()){
            final String[] webSocketResourceUriParts = webSocketResource.getUri().split(SLASH);

            if(compareWebSocketResourceUri(webSocketResourceUriParts, otherWebSocketResourceUriParts)){
                return webSocketResource;
            }
        }

        return null;
    }


    /**
     * The method provides the functionality to compare two sets of WebSocket resource URI parts.
     * @param webSocketResourceUriParts THe first set of resource URI parts
     * @param otherWebSocketResourceUriParts The second set of resource URI parts
     * @return True if the provided URIs are matching. False otherwise
     */
    protected boolean compareWebSocketResourceUri(final String[] webSocketResourceUriParts, final String[] otherWebSocketResourceUriParts){
        if(webSocketResourceUriParts.length != otherWebSocketResourceUriParts.length){
            return false;
        }

        for(int index = 0; index < webSocketResourceUriParts.length; index++){
            final String webSocketResourceUriPart = webSocketResourceUriParts[index];
            final String otherWebSocketResourceUriPart = otherWebSocketResourceUriParts[index];

            if(webSocketResourceUriPart.startsWith(START_BRACKET) && webSocketResourceUriPart.endsWith(END_BRACKET)){
                continue;
            }

            if(!webSocketResourceUriPart.equalsIgnoreCase(otherWebSocketResourceUriPart)){
                return false;
            }
        }
        return true;
    }

    @Override
    public WebSocketApplicationDto findWebSocketApplication(String webSocketProjectId, String webSocketApplicationId) {
        Preconditions.checkNotNull(webSocketApplicationId, "Application id cannot be null");
        final WebSocketApplication webSocketApplication = findWebSocketApplicationType(webSocketProjectId, webSocketApplicationId);
        return mapper.map(webSocketApplication, WebSocketApplicationDto.class);
    }

    @Override
    public WebSocketResourceDto findWebSocketResource(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId) {
        Preconditions.checkNotNull(webSocketResourceId, "Resource id cannot be null");
        final WebSocketResource webSocketResource = findWebSocketResourceType(webSocketProjectId, webSocketApplicationId, webSocketResourceId);
        return mapper.map(webSocketResource, WebSocketResourceDto.class);
    }

    @Override
    public WebSocketMockResponseDto findWebSocketMockResponse(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId, String webSocketMockResponseId) {
        Preconditions.checkNotNull(webSocketResourceId, "Mock response id cannot be null");
        final WebSocketMockResponse webSocketMockResponse = findWebSocketMockResponseType(webSocketProjectId, webSocketApplicationId, webSocketResourceId, webSocketMockResponseId);
        return mapper.map(webSocketMockResponse, WebSocketMockResponseDto.class);
    }

    @Override
    public WebSocketApplicationDto saveWebSocketApplication(String webSocketProjectId, WebSocketApplicationDto webSocketApplicationDto) {
        WebSocketProject webSocketProject = collection.get(webSocketProjectId);
        WebSocketApplication webSocketApplication = mapper.map(webSocketApplicationDto, WebSocketApplication.class);
        webSocketProject.getApplications().add(webSocketApplication);
        save(webSocketProjectId);
        return mapper.map(webSocketApplication, WebSocketApplicationDto.class);
    }

    @Override
    public WebSocketResourceDto saveWebSocketResource(String webSocketProjectId, String webSocketApplicationId, WebSocketResourceDto webSocketResourceDto) {
        WebSocketApplication webSocketApplication = findWebSocketApplicationType(webSocketProjectId, webSocketApplicationId);
        WebSocketResource webSocketResource = mapper.map(webSocketResourceDto, WebSocketResource.class);
        webSocketApplication.getResources().add(webSocketResource);
        save(webSocketProjectId);
        return mapper.map(webSocketResource, WebSocketResourceDto.class);
    }

    @Override
    public WebSocketMockResponseDto saveWebSocketMockResponse(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId, WebSocketMockResponseDto webSocketMockResponseDto) {
        WebSocketResource webSocketResource = findWebSocketResourceType(webSocketProjectId, webSocketApplicationId, webSocketResourceId);
        WebSocketMockResponse webSocketMockResponse = mapper.map(webSocketMockResponseDto, WebSocketMockResponse.class);
        webSocketResource.getMockResponses().add(webSocketMockResponse);
        save(webSocketProjectId);
        return mapper.map(webSocketMockResponse, WebSocketMockResponseDto.class);
    }

    @Override
    public WebSocketApplicationDto updateWebSocketApplication(String webSocketProjectId, String webSocketApplicationId, WebSocketApplicationDto updatedWebSocketApplicationDto) {
        WebSocketApplication webSocketApplication = findWebSocketApplicationType(webSocketProjectId, webSocketApplicationId);
        webSocketApplication.setName(updatedWebSocketApplicationDto.getName());
        return updatedWebSocketApplicationDto;
    }

    @Override
    public WebSocketResourceDto updateWebSocketResource(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId, WebSocketResourceDto webSocketResourceDto) {
        WebSocketResource webSocketResource = findWebSocketResourceType(webSocketProjectId, webSocketApplicationId, webSocketResourceId);
        webSocketResource.setName(webSocketResourceDto.getName());
        webSocketResource.setUri(webSocketResourceDto.getUri());
        webSocketResource.setStatus(webSocketResourceDto.getStatus());
        return webSocketResourceDto;
    }

    @Override
    public WebSocketMockResponseDto updateWebSocketMockResponse(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId, String webSocketMockResponseId, WebSocketMockResponseDto webSocketMockResponseDto) {
        WebSocketMockResponse webSocketMockResponse = findWebSocketMockResponseType(webSocketProjectId, webSocketApplicationId, webSocketResourceId, webSocketMockResponseId);
        webSocketMockResponse.setName(webSocketMockResponseDto.getName());
        webSocketMockResponse.setBody(webSocketMockResponseDto.getBody());
        webSocketMockResponse.setHttpStatusCode(webSocketMockResponseDto.getHttpStatusCode());
        webSocketMockResponse.setStatus(webSocketMockResponseDto.getStatus());
        return webSocketMockResponseDto;
    }

    @Override
    public WebSocketApplicationDto deleteWebSocketApplication(String webSocketProjectId, String webSocketApplicationId) {
        WebSocketProject webSocketProject = collection.get(webSocketProjectId);
        Iterator<WebSocketApplication> webSocketApplicationIterator = webSocketProject.getApplications().iterator();
        WebSocketApplication deletedWebSocketApplication = null;
        while(webSocketApplicationIterator.hasNext()){
            deletedWebSocketApplication = webSocketApplicationIterator.next();
            if(webSocketApplicationId.equals(deletedWebSocketApplication.getId())){
                webSocketApplicationIterator.remove();
                break;
            }
        }

        if(deletedWebSocketApplication != null){
            save(webSocketProjectId);
        }
        return deletedWebSocketApplication != null ? mapper.map(deletedWebSocketApplication, WebSocketApplicationDto.class) : null;
    }

    @Override
    public WebSocketResourceDto deleteWebSocketResource(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId) {
        WebSocketApplication webSocketApplication = findWebSocketApplicationType(webSocketProjectId, webSocketApplicationId);
        Iterator<WebSocketResource> webSocketResourceIterator = webSocketApplication.getResources().iterator();
        WebSocketResource deletedWebSocketResource = null;
        while(webSocketResourceIterator.hasNext()){
            deletedWebSocketResource = webSocketResourceIterator.next();
            if(webSocketApplicationId.equals(deletedWebSocketResource.getId())){
                webSocketResourceIterator.remove();
                break;
            }
        }

        if(deletedWebSocketResource != null){
            save(webSocketProjectId);
        }
        return deletedWebSocketResource != null ? mapper.map(deletedWebSocketResource, WebSocketResourceDto.class) : null;
    }

    @Override
    public WebSocketMockResponseDto deleteWebSocketMockResponse(String webSocketProjectId, String webSocketApplicationId, String webSocketResourceId, String webSocketMockResponseId) {
        WebSocketResource webSocketResource = findWebSocketResourceType(webSocketProjectId, webSocketApplicationId, webSocketResourceId);
        Iterator<WebSocketMockResponse> webSocketMockResponseIterator = webSocketResource.getMockResponses().iterator();
        WebSocketMockResponse deletedWebSocketMockResponse = null;
        while(webSocketMockResponseIterator.hasNext()){
            deletedWebSocketMockResponse = webSocketMockResponseIterator.next();
            if(webSocketApplicationId.equals(deletedWebSocketMockResponse.getId())){
                webSocketMockResponseIterator.remove();
                break;
            }
        }

        if(deletedWebSocketMockResponse != null){
            save(webSocketProjectId);
        }
        return deletedWebSocketMockResponse != null ? mapper.map(deletedWebSocketMockResponse, WebSocketMockResponseDto.class) : null;
    }


    /**
     * Search through a WebSocket project and all its resources
     * @param webSocketProject The WebSocket project which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchWebSocketProject(final WebSocketProject webSocketProject, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(webSocketProject.getName(), query)){
            final String projectType = messageSource.getMessage("general.type.project", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(webSocketProject.getName());
            searchResult.setLink(WEBSOCKET + SLASH + PROJECT + SLASH + webSocketProject.getId());
            searchResult.setDescription(WEBSOCKET_TYPE + COMMA + projectType);
            searchResults.add(searchResult);
        }


        for(WebSocketApplication webSocketApplication : webSocketProject.getApplications()){
            List<SearchResult> webSocketOperationSearchResult = searchWebSocketApplication(webSocketProject, webSocketApplication, query);
            searchResults.addAll(webSocketOperationSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a WebSocket application and all its resources
     * @param webSocketProject The WebSocket project which will be searched
     * @param webSocketApplication The WebSocket application which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchWebSocketApplication(final WebSocketProject webSocketProject, final WebSocketApplication webSocketApplication, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(webSocketApplication.getName(), query)){
            final String portType = messageSource.getMessage("webSocket.type.port", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(webSocketApplication.getName());
            searchResult.setLink(WEBSOCKET + SLASH + PROJECT + SLASH + webSocketProject.getId() + SLASH + APPLICATION + SLASH + webSocketApplication.getId());
            searchResult.setDescription(WEBSOCKET_TYPE + COMMA + portType);
            searchResults.add(searchResult);
        }

        for(WebSocketResource webSocketResource : webSocketApplication.getResources()){
            List<SearchResult> webSocketOperationSearchResult = searchWebSocketResource(webSocketProject, webSocketApplication, webSocketResource, query);
            searchResults.addAll(webSocketOperationSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a WebSocket resource and all its resources
     * @param webSocketProject The WebSocket project which will be searched
     * @param webSocketApplication The WebSocket application which will be searched
     * @param webSocketResource The WebSocket resource which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchWebSocketResource(final WebSocketProject webSocketProject, final WebSocketApplication webSocketApplication, final WebSocketResource webSocketResource, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(webSocketResource.getName(), query)){
            final String operationType = messageSource.getMessage("webSocket.type.operation", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(webSocketResource.getName());
            searchResult.setLink(WEBSOCKET + SLASH + PROJECT + SLASH + webSocketProject.getId() + SLASH + APPLICATION + SLASH + webSocketApplication.getId() + SLASH + RESOURCE + SLASH + webSocketResource.getId());
            searchResult.setDescription(WEBSOCKET_TYPE + COMMA + operationType);
            searchResults.add(searchResult);
        }

        for(WebSocketMockResponse webSocketMockResponse : webSocketResource.getMockResponses()){
            SearchResult webSocketMockResponseSearchResult = searchWebSocketMockResponse(webSocketProject, webSocketApplication, webSocketResource, webSocketMockResponse, query);
            if(webSocketMockResponseSearchResult != null){
                searchResults.add(webSocketMockResponseSearchResult);
            }
        }
        return searchResults;
    }

    /**
     * Search through a WebSocket response
     * @param webSocketProject The WebSocket project which will be searched
     * @param webSocketApplication The WebSocket application which will be searched
     * @param webSocketResource The WebSocket resource which will be searched
     * @param webSocketMockResponse The WebSocket response that will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private SearchResult searchWebSocketMockResponse(final WebSocketProject webSocketProject, final WebSocketApplication webSocketApplication, final WebSocketResource webSocketResource, final WebSocketMockResponse webSocketMockResponse, final String query){
        SearchResult searchResult = null;

        if(SearchValidator.validate(webSocketMockResponse.getName(), query)){
            final String mockResponseType = messageSource.getMessage("webSocket.type.mockresponse", null , LocaleContextHolder.getLocale());
            searchResult = new SearchResult();
            searchResult.setTitle(webSocketMockResponse.getName());
            searchResult.setLink(WEBSOCKET + SLASH + PROJECT + SLASH + webSocketProject.getId() + SLASH + APPLICATION + SLASH + webSocketApplication.getId() + SLASH + RESOURCE + SLASH + webSocketResource.getId() + SLASH + RESPONSE + SLASH + webSocketMockResponse.getId());
            searchResult.setDescription(WEBSOCKET_TYPE + COMMA + mockResponseType);
        }

        return searchResult;
    }
}
