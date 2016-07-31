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

package com.castlemock.web.mock.websocket.web.mvc.controller.resource;

import com.castlemock.core.mock.websocket.model.project.dto.WebSocketResourceDto;
import com.castlemock.core.mock.websocket.model.project.service.message.input.ReadWebSocketResourceInput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketResourceOutput;
import com.castlemock.web.mock.websocket.web.mvc.controller.AbstractWebSocketViewController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;

/**
 * The WebSocket resource controller provides functionality to retrieve a specific WebSocket resource
 * @author Karl Dahlgren
 * @since 1.5
 */
@Controller
@RequestMapping("/web/wss/project")
public class WebSocketResourceController extends AbstractWebSocketViewController {

    private static final String PAGE = "mock/websocket/resource/webSocketResource";
    private static final Logger LOGGER = Logger.getLogger(WebSocketResourceController.class);
    private static final String DELETE_WEBSOCKET_METHODS = "delete";
    private static final String DELETE_WEBSOCKET_METHODS_PAGE = "mock/websocket/method/deleteWebSocketMethods";
    private static final String DELETE_WEBSOCKET_METHODS_COMMAND = "deleteWebSocketMethodsCommand";
    private static final String UPDATE_STATUS = "update";
    private static final String UPDATE_ENDPOINTS = "update-endpoint";
    private static final String UPDATE_WEBSOCKET_METHODS_ENDPOINT_PAGE = "mock/websocket/method/updateWebSocketMethodsEndpoint";
    private static final String UPDATE_WEBSOCKET_METHODS_ENDPOINT_COMMAND = "updateWebSocketMethodsEndpointCommand";

    /**
     * Retrieves a specific WebSocket resource with a project id, application and resource id
     * @param webSocketProjectId The id of the project that the WebSocket resource belongs to
     * @param webSocketApplicationId The id of the application that the WebSocket resource belongs to
     * @param webSocketResourceId The id of the WebSocket resource that should be retrieve
     * @param request The incoming servlet request. Used to extract the address used to invoke the WebSocket methods
     * @return WebSocket resource that matches the provided resource id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/application/{webSocketApplicationId}/resource/{webSocketResourceId}", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketApplicationId, @PathVariable final String webSocketResourceId, final ServletRequest request) {
        final ReadWebSocketResourceOutput output = serviceProcessor.process(new ReadWebSocketResourceInput(webSocketProjectId, webSocketApplicationId, webSocketResourceId));
        final WebSocketResourceDto webSocketResource = output.getWebSocketResource();

        final String protocol = getProtocol(request);
        final String invokeAddress = getWebSocketInvokeAddress(protocol, request.getServerPort(), webSocketProjectId, webSocketApplicationId, webSocketResource.getUri());
        webSocketResource.setInvokeAddress(invokeAddress);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_PROJECT_ID, webSocketProjectId);
        model.addObject(WEBSOCKET_APPLICATION_ID, webSocketApplicationId);
        model.addObject(WEBSOCKET_RESOURCE, webSocketResource);
        model.addObject(WEBSOCKET_RESOURCE_STATUSES, getWebSocketResourceStatuses());
        return model;
    }

}

