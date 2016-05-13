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

package com.castlemock.web.mock.websocket.stomp.model.project.service;

import com.castlemock.core.basis.model.*;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompApplication;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompMockResponse;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompProject;
import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompResource;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.SearchWebSocketStompProjectInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.output.SearchWebSocketStompProjectOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * The service provides the functionality to search for WebSocket Stomp resources.
 * @author Karl Dahlgren
 * @since 1.5
 */
@org.springframework.stereotype.Service
public class SearchWebSocketStompProjectService extends AbstractWebSocketStompProjectService implements Service<SearchWebSocketStompProjectInput, SearchWebSocketStompProjectOutput> {

    @Autowired
    private MessageSource messageSource;

    private static final String SLASH = "/";
    private static final String WEBSOCKET_STOMP = "WebSocketStomp";
    private static final String PROJECT = "project";
    private static final String APPLICATION = "application";
    private static final String RESOURCE = "resource";
    private static final String RESPONSE = "response";
    private static final String COMMA = ", ";
    private static final String WEBSOCKET_STOMP_TYPE = "WebSocket Stomp";

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<SearchWebSocketStompProjectOutput> process(final ServiceTask<SearchWebSocketStompProjectInput> serviceTask) {
        final SearchWebSocketStompProjectInput input = serviceTask.getInput();
        final SearchQuery searchQuery = input.getSearchQuery();
        final String query = searchQuery.getQuery();
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        for(WebSocketStompProject webSocketStompProject : findAllTypes()){
            List<SearchResult> webSocketStompProjectSearchResult = searchWebSocketStompProject(webSocketStompProject, query);
            searchResults.addAll(webSocketStompProjectSearchResult);
        }

        return createServiceResult(new SearchWebSocketStompProjectOutput(searchResults));
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
