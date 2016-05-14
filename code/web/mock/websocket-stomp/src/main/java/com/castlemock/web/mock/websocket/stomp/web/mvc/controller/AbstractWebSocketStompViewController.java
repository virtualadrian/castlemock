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

package com.castlemock.web.mock.websocket.stomp.web.mvc.controller;

import com.castlemock.core.mock.websocket.stomp.model.project.domain.WebSocketStompResourceStatus;
import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * The class operates as a shared base for all the view related to the WebSocket Stomp module
 * @author Karl Dahlgren
 * @since 1.5
 * @see AbstractViewController
 */
public class AbstractWebSocketStompViewController extends AbstractViewController {

    protected static final String WEBSOCKET_STOMP = "websocket-stomp";
    protected static final String WEBSOCKET_STOMP_PROJECT = "webSocketStompProject";
    protected static final String WEBSOCKET_STOMP_PROJECT_ID = "webSocketStompProjectId";
    protected static final String WEBSOCKET_STOMP_RESOURCE_STATUSES = "webSocketStompResourceStatuses";
    protected static final String WEBSOCKET_STOMP_RESOURCES = "webSocketStompResources";


    private static final Logger LOGGER = Logger.getLogger(AbstractWebSocketStompViewController.class);

    /**
     * The method provides the functionality to create the address which is used to invoke a WebSocket Stomp service
     * @param protocol THe protocol
     * @param serverPort The server port
     * @param projectId The id of the project
     * @param urlPath The URL path (The end of the URL, which is used to identify the WebSocket Stomp service)
     * @return A URL based on all the incoming parameters
     */
    protected String getWebSocketStompInvokeAddress(final String protocol, int serverPort, final String projectId, final String urlPath){
        try {
            final String hostAddress = getHostAddress();
            return protocol + hostAddress + ":" + serverPort + getContext() + SLASH + MOCK + SLASH + WEBSOCKET_STOMP + SLASH + PROJECT + SLASH + projectId + SLASH + urlPath;
        } catch (Exception exception) {
            LOGGER.error("Unable to generate invoke URL", exception);
            throw new IllegalStateException("Unable to generate invoke URL for " + projectId);
        }
    }


    /**
     * The method returns a list of WebSocket Stomp operation statuses. The method will only return DISABLED and MOCKED
     * if the server is configured to run on demo mode. If not configured to run on demo mode,
     * all the WebSocket Stomp operation statuses will be returned.
     * @return A list of WebSocket Stomp operation statuses
     */
    protected List<WebSocketStompResourceStatus> getWebSocketStompResourceStatuses(){
        List<WebSocketStompResourceStatus> webSocketStompResourceStatuses = new LinkedList<WebSocketStompResourceStatus>();
        webSocketStompResourceStatuses.add(WebSocketStompResourceStatus.MOCKED);
        webSocketStompResourceStatuses.add(WebSocketStompResourceStatus.DISABLED);

        if(!demoMode) {
            webSocketStompResourceStatuses.add(WebSocketStompResourceStatus.FORWARDED);
            webSocketStompResourceStatuses.add(WebSocketStompResourceStatus.RECORDING);
            webSocketStompResourceStatuses.add(WebSocketStompResourceStatus.RECORD_ONCE);
        }
        return webSocketStompResourceStatuses;
    }

}
