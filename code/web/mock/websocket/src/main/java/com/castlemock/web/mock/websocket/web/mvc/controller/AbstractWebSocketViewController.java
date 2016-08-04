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

package com.castlemock.web.mock.websocket.web.mvc.controller;

import com.castlemock.core.mock.websocket.model.project.domain.WebSocketResourceStatus;
import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * The class operates as a shared base for all the view related to the WebSocket module
 * @author Karl Dahlgren
 * @since 1.5
 * @see AbstractViewController
 */
public class AbstractWebSocketViewController extends AbstractViewController {

    protected static final String TOPIC = "topic";
    protected static final String WEBSOCKET = "wss";
    protected static final String WEBSOCKET_PROJECT = "webSocketProject";
    protected static final String WEBSOCKET_PROJECT_ID = "webSocketProjectId";
    protected static final String WEBSOCKET_RESOURCE_STATUSES = "webSocketResourceStatuses";
    protected static final String WEBSOCKET_RESOURCES = "webSocketResources";
    protected static final String WEBSOCKET_TOPIC = "webSocketTopic";
    protected static final String WEBSOCKET_RESOURCE_ID = "webSocketResourceId";
    protected static final String WEBSOCKET_TOPIC_ID = "webSocketTopicId";
    protected static final String WEBSOCKET_RESOURCE = "webSocketResource";
    protected static final String WEBSOCKET_TOPICS = "webSocketTopics";


    private static final Logger LOGGER = Logger.getLogger(AbstractWebSocketViewController.class);

    /**
     * The method provides the functionality to create the address which is used to invoke a WebSocket service
     * @param protocol THe protocol
     * @param serverPort The server port
     * @param projectId The id of the project
     * @param urlPath The URL path (The end of the URL, which is used to identify the WebSocket service)
     * @return A URL based on all the incoming parameters
     */
    protected String getWebSocketInvokeAddress(final String protocol, int serverPort, final String projectId, final String topicId, final String urlPath){
        try {
            final String hostAddress = getHostAddress();
            return protocol + hostAddress + ":" + serverPort + getContext() + SLASH + MOCK + SLASH + WEBSOCKET + SLASH + PROJECT + SLASH + projectId + SLASH + TOPIC + SLASH + topicId + urlPath;
        } catch (Exception exception) {
            LOGGER.error("Unable to generate invoke URL", exception);
            throw new IllegalStateException("Unable to generate invoke URL for " + projectId);
        }
    }


    /**
     * The method returns a list of WebSocket operation statuses. The method will only return DISABLED and MOCKED
     * if the server is configured to run on demo mode. If not configured to run on demo mode,
     * all the WebSocket operation statuses will be returned.
     * @return A list of WebSocket operation statuses
     */
    protected List<WebSocketResourceStatus> getWebSocketResourceStatuses(){
        List<WebSocketResourceStatus> webSocketResourceStatuses = new LinkedList<WebSocketResourceStatus>();
        webSocketResourceStatuses.add(WebSocketResourceStatus.MOCKED);
        webSocketResourceStatuses.add(WebSocketResourceStatus.DISABLED);

        if(!demoMode) {
            webSocketResourceStatuses.add(WebSocketResourceStatus.FORWARDED);
            webSocketResourceStatuses.add(WebSocketResourceStatus.RECORDING);
            webSocketResourceStatuses.add(WebSocketResourceStatus.RECORD_ONCE);
        }
        return webSocketResourceStatuses;
    }

}
