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

package com.castlemock.web.mock.websocket.stomp.web.mvc.controller.resource;

import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompResourceDto;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.ReadWebSocketStompResourceInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.output.ReadWebSocketStompResourceOutput;
import com.castlemock.web.mock.websocket.stomp.web.mvc.controller.AbstractWebSocketStompViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;

/**
 * The WebSocket Stomp resource controller provides functionality to retrieve a specific WebSocket Stomp resource
 * @author Karl Dahlgren
 * @since 1.5
 */
@Controller
@RequestMapping("/web/wss/project")
public class WebSocketStompResourceController extends AbstractWebSocketStompViewController {

    private static final String PAGE = "mock/websocket/stomp/resource/webSocketStompResource";
    private static final Logger LOGGER = Logger.getLogger(WebSocketStompResourceController.class);
    private static final String DELETE_WEBSOCKET_STOMP_METHODS = "delete";
    private static final String DELETE_WEBSOCKET_STOMP_METHODS_PAGE = "mock/websocket/stomp/method/deleteWebSocketStompMethods";
    private static final String DELETE_WEBSOCKET_STOMP_METHODS_COMMAND = "deleteWebSocketStompMethodsCommand";
    private static final String UPDATE_STATUS = "update";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String UPDATE_WEBSOCKET_STOMP_METHODS_ENDPOINT_PAGE = "mock/websocket/stomp/method/updateWebSocketStompMethodsEndpoint";
    private static final String UPDATE_WEBSOCKET_STOMP_METHODS_ENDPOINT_COMMAND = "updateWebSocketStompMethodsEndpointCommand";

    /**
     * Retrieves a specific WebSocket Stomp resource with a project id, application and resource id
     * @param webSocketStompProjectId The id of the project that the WebSocket Stomp resource belongs to
     * @param webSocketStompApplicationId The id of the application that the WebSocket Stomp resource belongs to
     * @param webSocketStompResourceId The id of the WebSocket Stomp resource that should be retrieve
     * @param request The incoming servlet request. Used to extract the address used to invoke the WebSocket Stomp methods
     * @return WebSocket Stomp resource that matches the provided resource id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketStompProjectId}/application/{webSocketStompApplicationId}/resource/{webSocketStompResourceId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String webSocketStompProjectId, @PathVariable final String webSocketStompApplicationId, @PathVariable final String webSocketStompResourceId, final ServletRequest request) {
        final ReadWebSocketStompResourceOutput output = serviceProcessor.process(new ReadWebSocketStompResourceInput(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId));
        final WebSocketStompResourceDto webSocketStompResource = output.getWebSocketStompResource();

        final String protocol = getProtocol(request);
        final String invokeAddress = getWebSocketStompInvokeAddress(protocol, request.getServerPort(), webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResource.getUri());
        webSocketStompResource.setInvokeAddress(invokeAddress);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_STOMP_PROJECT_ID, webSocketStompProjectId);
        model.addObject(WEBSOCKET_STOMP_APPLICATION_ID, webSocketStompApplicationId);
        model.addObject(WEBSOCKET_STOMP_RESOURCE, webSocketStompResource);
        model.addObject(WEBSOCKET_STOMP_RESOURCE_STATUSES, getWebSocketStompResourceStatuses());
        return model;
    }

}

