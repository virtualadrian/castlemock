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

import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.DeleteWebSocketStompResourceInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.DeleteWebSocketStompResourcesInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.ReadWebSocketStompResourceInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.output.ReadWebSocketStompResourceOutput;
import com.castlemock.web.mock.websocket.stomp.web.mvc.command.resource.DeleteWebSocketStompResourcesCommand;
import com.castlemock.web.mock.websocket.stomp.web.mvc.controller.AbstractWebSocketStompViewController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**e
 * The Delete WebSocket Stomp resource controller provides functionality to delete a specific WebSocket Stomp resource.
 * @author Karl Dahlgren
 * @since 1.5
 */
@Controller
@RequestMapping("/web/wss/project")
public class DeleteWebSocketStompResourceController extends AbstractWebSocketStompViewController {

    private static final String PAGE = "mock/websocket/stomp/resource/deleteWebSocketStompResource";
    /**
     * Retrieves a specific WebSocket Stomp resource with a project id, application id and a resource id
     * @param webSocketStompProjectId The id of the project that the WebSocket Stomp resource belongs to
     * @param webSocketStompApplicationId The id of the application that the WebSocket Stomp resource belongs to
     * @param webSocketStompResourceId The id of the WebSocket Stomp resource that should be retrieve
     * @return WebSocket Stomp resource that matches the provided resource id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketStompProjectId}/application/{webSocketStompApplicationId}/resource/{webSocketStompResourceId}/delete", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String webSocketStompProjectId, @PathVariable final String webSocketStompApplicationId, @PathVariable final String webSocketStompResourceId) {
        final ReadWebSocketStompResourceOutput output = serviceProcessor.process(new ReadWebSocketStompResourceInput(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId));

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_STOMP_PROJECT_ID, webSocketStompProjectId);
        model.addObject(WEBSOCKET_STOMP_APPLICATION_ID, webSocketStompApplicationId);
        model.addObject(WEBSOCKET_STOMP_RESOURCE, output.getWebSocketStompResource());
        return model;
    }

    /**
     * The method provides the functionality to delete a WebSocket Stomp resource
     * @param webSocketStompProjectId The id of the project that the WebSocket Stomp resource belongs to
     * @param webSocketStompApplicationId The id of the application that the WebSocket Stomp resource belongs to
     * @param webSocketStompResourceId The id of the WebSocket Stomp resource that should be deleted
     * @return Redirects the user to the WebSocket Stomp application page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketStompProjectId}/application/{webSocketStompApplicationId}/resource/{webSocketStompResourceId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final String webSocketStompProjectId, @PathVariable final String webSocketStompApplicationId, @PathVariable final String webSocketStompResourceId) {
        serviceProcessor.process(new DeleteWebSocketStompResourceInput(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId));
        return redirect("/wss/project/" + webSocketStompProjectId + "/application/" + webSocketStompApplicationId);
    }

    /**
     * The method provides the functionality to delete a list WebSocket Stomp resources
     * @param webSocketStompProjectId The id of the project that the WebSocket Stomp resources belongs to
     * @param webSocketStompApplicationId The id of the application that the WebSocket Stomp resources belongs to
     * @param deleteWebSocketStompResourcesCommand The command object contains the WebSocket Stomp resources that should be deleted
     * @return Redirects the user to the WebSocket Stomp application page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketStompProjectId}/application/{webSocketStompApplicationId}/resource/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleProjects(@PathVariable final String webSocketStompProjectId, @PathVariable final String webSocketStompApplicationId, @ModelAttribute final DeleteWebSocketStompResourcesCommand deleteWebSocketStompResourcesCommand) {
        serviceProcessor.process(new DeleteWebSocketStompResourcesInput(webSocketStompProjectId, webSocketStompApplicationId, deleteWebSocketStompResourcesCommand.getWebSocketStompResources()));
        return redirect("/wss/project/" + webSocketStompProjectId + "/application/" + webSocketStompApplicationId);
    }



}

