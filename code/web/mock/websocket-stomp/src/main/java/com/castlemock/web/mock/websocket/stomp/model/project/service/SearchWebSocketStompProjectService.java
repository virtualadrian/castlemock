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
        final List<SearchResult> searchResults = repository.search(input.getSearchQuery());
        return createServiceResult(new SearchWebSocketStompProjectOutput(searchResults));
    }



}
