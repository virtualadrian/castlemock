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
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.UpdateWebSocketStompResourceInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.UpdateWebSocketStompResourcesForwardedEndpointInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.output.ReadWebSocketStompResourceOutput;
import com.castlemock.web.mock.websocket.stomp.web.mvc.command.resource.UpdateWebSocketStompResourcesEndpointCommand;
import com.castlemock.web.mock.websocket.stomp.web.mvc.controller.AbstractWebSocketStompViewController;
import com.google.common.base.Preconditions;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The project controller provides functionality to retrieve a specific project
 * @author Karl Dahlgren
 * @since 1.5
 */
@Controller
@RequestMapping("/web/wss/project")
public class UpdateWebSocketStompResourceController extends AbstractWebSocketStompViewController {

    private static final String PAGE = "mock/websocket/stomp/resource/updateWebSocketStompResource";

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketStompProjectId}/application/{webSocketStompApplicationId}/resource/{webSocketStompResourceId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String webSocketStompProjectId, @PathVariable final String webSocketStompApplicationId, @PathVariable final String webSocketStompResourceId) {
        final ReadWebSocketStompResourceOutput output = serviceProcessor.process(new ReadWebSocketStompResourceInput(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_STOMP_RESOURCE, output.getWebSocketStompResource());
        model.addObject(WEBSOCKET_STOMP_PROJECT_ID, webSocketStompProjectId);
        model.addObject(WEBSOCKET_STOMP_APPLICATION_ID, webSocketStompApplicationId);
        model.addObject(WEBSOCKET_STOMP_RESOURCE_ID, webSocketStompResourceId);
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketStompProjectId}/application/{webSocketStompApplicationId}/resource/{webSocketStompResourceId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String webSocketStompProjectId, @PathVariable final String webSocketStompApplicationId, @PathVariable final String webSocketStompResourceId,  @ModelAttribute final WebSocketStompResourceDto webSocketStompResourceDto) {
        serviceProcessor.process(new UpdateWebSocketStompResourceInput(webSocketStompProjectId, webSocketStompApplicationId, webSocketStompResourceId, webSocketStompResourceDto));
        return redirect("/wss/project/" + webSocketStompProjectId + "/application/" + webSocketStompApplicationId + "/resource/" + webSocketStompResourceId);
    }

    /**
     * The method provides the functionality to update the endpoint address for multiple
     * resources at once
     * @param webSocketStompProjectId The id of the project that the resources belongs to
     * @param webSocketStompApplicationId The id of the application that the resources belongs to
     * @param updateWebSocketStompResourcesEndpointCommand The command object contains both the resources that
     *                                          will be updated and the new forwarded address
     * @return Redirects the user to the application page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketStompProjectId}/application/{webSocketStompApplicationId}/resource/update/confirm", method = RequestMethod.POST)
    public ModelAndView updateEndpoint(@PathVariable final String webSocketStompProjectId, @PathVariable final String webSocketStompApplicationId, @ModelAttribute final UpdateWebSocketStompResourcesEndpointCommand updateWebSocketStompResourcesEndpointCommand) {
        Preconditions.checkNotNull(updateWebSocketStompResourcesEndpointCommand, "The update application endpoint command cannot be null");
        serviceProcessor.process(new UpdateWebSocketStompResourcesForwardedEndpointInput(webSocketStompProjectId, webSocketStompApplicationId, updateWebSocketStompResourcesEndpointCommand.getWebSocketStompResources(), updateWebSocketStompResourcesEndpointCommand.getForwardedEndpoint()));
        return redirect("/wss/project/" + webSocketStompProjectId + "/application/" + webSocketStompApplicationId);
    }

}

