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
import com.castlemock.core.mock.websocket.model.project.domain.*;
import com.castlemock.core.mock.websocket.model.project.dto.*;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
    private static final String TOPIC = "topic";
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
     * file system upon topic startup. The method will throw an exception in case of the type not being acceptable.
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
    public WebSocketProjectDto save(final WebSocketProject webSocketProject) {
        for(WebSocketTopic webSocketTopic : webSocketProject.getTopics()){
            if(webSocketTopic.getId() == null){
                String webSocketTopicId = generateId();
                webSocketTopic.setId(webSocketTopicId);
            }
            for(WebSocketResource webSocketResource : webSocketTopic.getResources()){
                if(webSocketResource.getId() == null){
                    String webSocketResourceId = generateId();
                    webSocketResource.setId(webSocketResourceId);
                }
                for(WebSocketMockBroadcastMessage webSocketMockResponse : webSocketResource.getMockBroadcastMessages()){
                    if(webSocketMockResponse.getId() == null){
                        String webSocketMockResponseId = generateId();
                        webSocketMockResponse.setId(webSocketMockResponseId);
                    }
                }
            }
            for(WebSocketBroadcaster webSocketBroadcaster : webSocketTopic.getBroadcasters()){
                if(webSocketBroadcaster.getId() == null){
                    String webSocketBroadcasterId = generateId();
                    webSocketBroadcaster.setId(webSocketBroadcasterId);
                }
                for(WebSocketMockBroadcastMessage webSocketMockResponse : webSocketBroadcaster.getMockBroadcastMessages()){
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

    private WebSocketTopic findWebSocketTopicType(final String webSocketProjectId, final String webSocketTopicId) {
        Preconditions.checkNotNull(webSocketProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(webSocketTopicId, "Topic id cannot be null");
        final WebSocketProject webSocketProject = collection.get(webSocketProjectId);

        if(webSocketProject == null){
            throw new IllegalArgumentException("Unable to find a WebSocket project with id " + webSocketProjectId);
        }

        for(WebSocketTopic webSocketTopic : webSocketProject.getTopics()){
            if(webSocketTopic.getId().equals(webSocketTopicId)){
                return webSocketTopic;
            }
        }
        throw new IllegalArgumentException("Unable to find a WebSocket topic with id " + webSocketTopicId);
    }

    private WebSocketResource findWebSocketResourceType(final String webSocketProjectId, final String webSocketTopicId, final String webSocketResourceId){
        Preconditions.checkNotNull(webSocketResourceId, "Resource id cannot be null");
        final WebSocketTopic webSocketTopic = findWebSocketTopicType(webSocketProjectId, webSocketTopicId);
        for(WebSocketResource webSocketResource : webSocketTopic.getResources()){
            if(webSocketResource.getId().equals(webSocketResourceId)){
                return webSocketResource;
            }
        }
        throw new IllegalArgumentException("Unable to find a WebSocket resource with id " + webSocketResourceId);
    }

    private WebSocketBroadcaster findWebSocketBroadcasterType(final String webSocketProjectId, final String webSocketTopicId, final String webSocketBroadcasterId){
        Preconditions.checkNotNull(webSocketBroadcasterId, "Broadcaster id cannot be null");
        final WebSocketTopic webSocketTopic = findWebSocketTopicType(webSocketProjectId, webSocketTopicId);
        for(WebSocketBroadcaster webSocketBroadcaster : webSocketTopic.getBroadcasters()){
            if(webSocketBroadcaster.getId().equals(webSocketBroadcasterId)){
                return webSocketBroadcaster;
            }
        }
        throw new IllegalArgumentException("Unable to find a WebSocket broadcaster with id " + webSocketBroadcasterId);
    }

    private WebSocketMockBroadcastMessage findWebSocketMockResponseType(final String webSocketProjectId, final String webSocketTopicId, final String webSocketResourceId, final String webSocketMockResponseId){
        Preconditions.checkNotNull(webSocketResourceId, "Resource id cannot be null");
        final WebSocketResource webSocketResource = findWebSocketResourceType(webSocketProjectId, webSocketTopicId, webSocketResourceId);
        for(WebSocketMockBroadcastMessage webSocketMockResponse : webSocketResource.getMockBroadcastMessages()){
            if(webSocketMockResponse.getId().equals(webSocketMockResponseId)){
                return webSocketMockResponse;
            }
        }
        throw new IllegalArgumentException("Unable to find a WebSocket mock response with id " + webSocketMockResponseId);
    }


    /**
     * Find a WebSocket resource with a project id, topic id and a set of resource parts
     * @param webSocketProjectId The id of the project that the resource belongs to
     * @param webSocketTopicId The id of the topic that the resource belongs to
     * @param otherWebSocketResourceUriParts The set of resources that will be used to identify the WebSocket resource
     * @return A WebSocket resource that matches the search criteria. Null otherwise
     */
    public WebSocketResource findWebSocketResourceType(final String webSocketProjectId, final String webSocketTopicId, final String[] otherWebSocketResourceUriParts) {
        final WebSocketTopic webSocketTopic = findWebSocketTopicType(webSocketProjectId, webSocketTopicId);

        for(WebSocketResource webSocketResource : webSocketTopic.getResources()){
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
    public WebSocketTopicDto findWebSocketTopic(String webSocketProjectId, String webSocketTopicId) {
        Preconditions.checkNotNull(webSocketTopicId, "Topic id cannot be null");
        final WebSocketTopic webSocketTopic = findWebSocketTopicType(webSocketProjectId, webSocketTopicId);
        return mapper.map(webSocketTopic, WebSocketTopicDto.class);
    }

    @Override
    public WebSocketResourceDto findWebSocketResource(String webSocketProjectId, String webSocketTopicId, String webSocketResourceId) {
        Preconditions.checkNotNull(webSocketResourceId, "Resource id cannot be null");
        final WebSocketResource webSocketResource = findWebSocketResourceType(webSocketProjectId, webSocketTopicId, webSocketResourceId);
        return mapper.map(webSocketResource, WebSocketResourceDto.class);
    }

    @Override
    public WebSocketBroadcasterDto findWebSocketBroadcaster(String webSocketProjectId, String webSocketTopicId, String webSocketBroadcasterId) {
        Preconditions.checkNotNull(webSocketBroadcasterId, "Broadcaster id cannot be null");
        final WebSocketBroadcaster webSocketBroadcaster = findWebSocketBroadcasterType(webSocketProjectId, webSocketTopicId, webSocketBroadcasterId);
        return mapper.map(webSocketBroadcaster, WebSocketBroadcasterDto.class);
    }

    @Override
    public WebSocketMockBroadcastMessageDto findWebSocketMockResponse(String webSocketProjectId, String webSocketTopicId, String webSocketResourceId, String webSocketMockResponseId) {
        Preconditions.checkNotNull(webSocketResourceId, "Mock response id cannot be null");
        final WebSocketMockBroadcastMessage webSocketMockResponse = findWebSocketMockResponseType(webSocketProjectId, webSocketTopicId, webSocketResourceId, webSocketMockResponseId);
        return mapper.map(webSocketMockResponse, WebSocketMockBroadcastMessageDto.class);
    }

    @Override
    public WebSocketTopicDto saveWebSocketTopic(String webSocketProjectId, WebSocketTopicDto webSocketTopicDto) {
        WebSocketProject webSocketProject = collection.get(webSocketProjectId);
        WebSocketTopic webSocketTopic = mapper.map(webSocketTopicDto, WebSocketTopic.class);
        webSocketProject.getTopics().add(webSocketTopic);
        save(webSocketProjectId);
        return mapper.map(webSocketTopic, WebSocketTopicDto.class);
    }

    @Override
    public WebSocketBroadcasterDto saveWebSocketBroadcaster(String webSocketProjectId, String webSocketTopicId, WebSocketBroadcasterDto webSocketBroadcasterDto) {
        WebSocketTopic webSocketTopic = findWebSocketTopicType(webSocketProjectId, webSocketTopicId);
        WebSocketBroadcaster webSocketBroadcaster = mapper.map(webSocketBroadcasterDto, WebSocketBroadcaster.class);
        webSocketTopic.getBroadcasters().add(webSocketBroadcaster);
        save(webSocketProjectId);
        return mapper.map(webSocketBroadcaster, WebSocketBroadcasterDto.class);
    }

    @Override
    public WebSocketResourceDto saveWebSocketResource(String webSocketProjectId, String webSocketTopicId, WebSocketResourceDto webSocketResourceDto) {
        WebSocketTopic webSocketTopic = findWebSocketTopicType(webSocketProjectId, webSocketTopicId);
        WebSocketResource webSocketResource = mapper.map(webSocketResourceDto, WebSocketResource.class);
        webSocketTopic.getResources().add(webSocketResource);
        save(webSocketProjectId);
        return mapper.map(webSocketResource, WebSocketResourceDto.class);
    }

    @Override
    public WebSocketMockBroadcastMessageDto saveWebSocketMockResponse(String webSocketProjectId, String webSocketTopicId, String webSocketResourceId, WebSocketMockBroadcastMessageDto webSocketMockResponseDto) {
        WebSocketResource webSocketResource = findWebSocketResourceType(webSocketProjectId, webSocketTopicId, webSocketResourceId);
        WebSocketMockBroadcastMessage webSocketMockResponse = mapper.map(webSocketMockResponseDto, WebSocketMockBroadcastMessage.class);
        webSocketResource.getMockBroadcastMessages().add(webSocketMockResponse);
        save(webSocketProjectId);
        return mapper.map(webSocketMockResponse, WebSocketMockBroadcastMessageDto.class);
    }

    @Override
    public WebSocketTopicDto updateWebSocketTopic(String webSocketProjectId, String webSocketTopicId, WebSocketTopicDto updatedWebSocketTopicDto) {
        WebSocketTopic webSocketTopic = findWebSocketTopicType(webSocketProjectId, webSocketTopicId);
        webSocketTopic.setName(updatedWebSocketTopicDto.getName());
        return updatedWebSocketTopicDto;
    }

    @Override
    public WebSocketBroadcasterDto updateWebSocketBroadcaster(String webSocketProjectId, String webSocketTopicId, String webSocketBroadcasterId, WebSocketBroadcasterDto webSocketBroadcasterDto) {
        WebSocketBroadcaster webSocketBroadcaster = findWebSocketBroadcasterType(webSocketProjectId, webSocketTopicId, webSocketBroadcasterId);
        webSocketBroadcaster.setName(webSocketBroadcasterDto.getName());
        return webSocketBroadcasterDto;
    }

    @Override
    public WebSocketResourceDto updateWebSocketResource(String webSocketProjectId, String webSocketTopicId, String webSocketResourceId, WebSocketResourceDto webSocketResourceDto) {
        WebSocketResource webSocketResource = findWebSocketResourceType(webSocketProjectId, webSocketTopicId, webSocketResourceId);
        webSocketResource.setName(webSocketResourceDto.getName());
        webSocketResource.setUri(webSocketResourceDto.getUri());
        webSocketResource.setStatus(webSocketResourceDto.getStatus());
        return webSocketResourceDto;
    }

    @Override
    public WebSocketMockBroadcastMessageDto updateWebSocketMockResponse(String webSocketProjectId, String webSocketTopicId, String webSocketResourceId, String webSocketMockResponseId, WebSocketMockBroadcastMessageDto webSocketMockResponseDto) {
        WebSocketMockBroadcastMessage webSocketMockResponse = findWebSocketMockResponseType(webSocketProjectId, webSocketTopicId, webSocketResourceId, webSocketMockResponseId);
        webSocketMockResponse.setName(webSocketMockResponseDto.getName());
        webSocketMockResponse.setBody(webSocketMockResponseDto.getBody());
        webSocketMockResponse.setHttpStatusCode(webSocketMockResponseDto.getHttpStatusCode());
        webSocketMockResponse.setStatus(webSocketMockResponseDto.getStatus());
        return webSocketMockResponseDto;
    }

    @Override
    public WebSocketTopicDto deleteWebSocketTopic(String webSocketProjectId, String webSocketTopicId) {
        WebSocketProject webSocketProject = collection.get(webSocketProjectId);
        Iterator<WebSocketTopic> webSocketTopicIterator = webSocketProject.getTopics().iterator();
        WebSocketTopic deletedWebSocketTopic = null;
        while(webSocketTopicIterator.hasNext()){
            deletedWebSocketTopic = webSocketTopicIterator.next();
            if(webSocketTopicId.equals(deletedWebSocketTopic.getId())){
                webSocketTopicIterator.remove();
                break;
            }
        }

        if(deletedWebSocketTopic != null){
            save(webSocketProjectId);
        }
        return deletedWebSocketTopic != null ? mapper.map(deletedWebSocketTopic, WebSocketTopicDto.class) : null;
    }

    @Override
    public WebSocketBroadcasterDto deleteWebSocketBroadcaster(String webSocketProjectId, String webSocketTopicId, String webSocketBroadcasterId) {
        WebSocketTopic webSocketTopic = findWebSocketTopicType(webSocketProjectId, webSocketTopicId);
        Iterator<WebSocketBroadcaster> webSocketBroadcasterIterator = webSocketTopic.getBroadcasters().iterator();
        WebSocketBroadcaster deletedWebSocketBroadcaster = null;
        while(webSocketBroadcasterIterator.hasNext()){
            deletedWebSocketBroadcaster = webSocketBroadcasterIterator.next();
            if(webSocketBroadcasterId.equals(deletedWebSocketBroadcaster.getId())){
                webSocketBroadcasterIterator.remove();
                break;
            }
        }

        if(deletedWebSocketBroadcaster != null){
            save(webSocketProjectId);
        }
        return deletedWebSocketBroadcaster != null ? mapper.map(deletedWebSocketBroadcaster, WebSocketBroadcasterDto.class) : null;
    }

    @Override
    public WebSocketResourceDto deleteWebSocketResource(String webSocketProjectId, String webSocketTopicId, String webSocketResourceId) {
        WebSocketTopic webSocketTopic = findWebSocketTopicType(webSocketProjectId, webSocketTopicId);
        Iterator<WebSocketResource> webSocketResourceIterator = webSocketTopic.getResources().iterator();
        WebSocketResource deletedWebSocketResource = null;
        while(webSocketResourceIterator.hasNext()){
            deletedWebSocketResource = webSocketResourceIterator.next();
            if(webSocketResourceId.equals(deletedWebSocketResource.getId())){
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
    public WebSocketMockBroadcastMessageDto deleteWebSocketMockResponse(String webSocketProjectId, String webSocketTopicId, String webSocketResourceId, String webSocketMockResponseId) {
        WebSocketResource webSocketResource = findWebSocketResourceType(webSocketProjectId, webSocketTopicId, webSocketResourceId);
        Iterator<WebSocketMockBroadcastMessage> webSocketMockResponseIterator = webSocketResource.getMockBroadcastMessages().iterator();
        WebSocketMockBroadcastMessage deletedWebSocketMockResponse = null;
        while(webSocketMockResponseIterator.hasNext()){
            deletedWebSocketMockResponse = webSocketMockResponseIterator.next();
            if(webSocketTopicId.equals(deletedWebSocketMockResponse.getId())){
                webSocketMockResponseIterator.remove();
                break;
            }
        }

        if(deletedWebSocketMockResponse != null){
            save(webSocketProjectId);
        }
        return deletedWebSocketMockResponse != null ? mapper.map(deletedWebSocketMockResponse, WebSocketMockBroadcastMessageDto.class) : null;
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


        for(WebSocketTopic webSocketTopic : webSocketProject.getTopics()){
            List<SearchResult> webSocketOperationSearchResult = searchWebSocketTopic(webSocketProject, webSocketTopic, query);
            searchResults.addAll(webSocketOperationSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a WebSocket topic and all its resources
     * @param webSocketProject The WebSocket project which will be searched
     * @param webSocketTopic The WebSocket topic which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchWebSocketTopic(final WebSocketProject webSocketProject, final WebSocketTopic webSocketTopic, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(webSocketTopic.getName(), query)){
            final String portType = messageSource.getMessage("webSocket.type.port", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(webSocketTopic.getName());
            searchResult.setLink(WEBSOCKET + SLASH + PROJECT + SLASH + webSocketProject.getId() + SLASH + TOPIC + SLASH + webSocketTopic.getId());
            searchResult.setDescription(WEBSOCKET_TYPE + COMMA + portType);
            searchResults.add(searchResult);
        }

        for(WebSocketResource webSocketResource : webSocketTopic.getResources()){
            List<SearchResult> webSocketOperationSearchResult = searchWebSocketResource(webSocketProject, webSocketTopic, webSocketResource, query);
            searchResults.addAll(webSocketOperationSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a WebSocket resource and all its resources
     * @param webSocketProject The WebSocket project which will be searched
     * @param webSocketTopic The WebSocket topic which will be searched
     * @param webSocketResource The WebSocket resource which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchWebSocketResource(final WebSocketProject webSocketProject, final WebSocketTopic webSocketTopic, final WebSocketResource webSocketResource, final String query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(webSocketResource.getName(), query)){
            final String operationType = messageSource.getMessage("webSocket.type.operation", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(webSocketResource.getName());
            searchResult.setLink(WEBSOCKET + SLASH + PROJECT + SLASH + webSocketProject.getId() + SLASH + TOPIC + SLASH + webSocketTopic.getId() + SLASH + RESOURCE + SLASH + webSocketResource.getId());
            searchResult.setDescription(WEBSOCKET_TYPE + COMMA + operationType);
            searchResults.add(searchResult);
        }

        for(WebSocketMockBroadcastMessage webSocketMockResponse : webSocketResource.getMockBroadcastMessages()){
            SearchResult webSocketMockResponseSearchResult = searchWebSocketMockResponse(webSocketProject, webSocketTopic, webSocketResource, webSocketMockResponse, query);
            if(webSocketMockResponseSearchResult != null){
                searchResults.add(webSocketMockResponseSearchResult);
            }
        }
        return searchResults;
    }

    /**
     * Search through a WebSocket response
     * @param webSocketProject The WebSocket project which will be searched
     * @param webSocketTopic The WebSocket topic which will be searched
     * @param webSocketResource The WebSocket resource which will be searched
     * @param webSocketMockResponse The WebSocket response that will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private SearchResult searchWebSocketMockResponse(final WebSocketProject webSocketProject, final WebSocketTopic webSocketTopic, final WebSocketResource webSocketResource, final WebSocketMockBroadcastMessage webSocketMockResponse, final String query){
        SearchResult searchResult = null;

        if(SearchValidator.validate(webSocketMockResponse.getName(), query)){
            final String mockResponseType = messageSource.getMessage("webSocket.type.mockresponse", null , LocaleContextHolder.getLocale());
            searchResult = new SearchResult();
            searchResult.setTitle(webSocketMockResponse.getName());
            searchResult.setLink(WEBSOCKET + SLASH + PROJECT + SLASH + webSocketProject.getId() + SLASH + TOPIC + SLASH + webSocketTopic.getId() + SLASH + RESOURCE + SLASH + webSocketResource.getId() + SLASH + RESPONSE + SLASH + webSocketMockResponse.getId());
            searchResult.setDescription(WEBSOCKET_TYPE + COMMA + mockResponseType);
        }

        return searchResult;
    }
}
