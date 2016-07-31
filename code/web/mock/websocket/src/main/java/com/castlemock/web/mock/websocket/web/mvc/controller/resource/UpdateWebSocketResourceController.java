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
import com.castlemock.core.mock.websocket.model.project.service.message.input.UpdateWebSocketResourceInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.UpdateWebSocketResourcesForwardedEndpointInput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketResourceOutput;
import com.castlemock.web.mock.websocket.web.mvc.command.resource.UpdateWebSocketResourcesEndpointCommand;
import com.castlemock.web.mock.websocket.web.mvc.controller.AbstractWebSocketViewController;
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
public class UpdateWebSocketResourceController extends AbstractWebSocketViewController {

    private static final String PAGE = "mock/websocket/resource/updateWebSocketResource";

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/application/{webSocketApplicationId}/resource/{webSocketResourceId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketApplicationId, @PathVariable final String webSocketResourceId) {
        final ReadWebSocketResourceOutput output = serviceProcessor.process(new ReadWebSocketResourceInput(webSocketProjectId, webSocketApplicationId, webSocketResourceId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_RESOURCE, output.getWebSocketResource());
        model.addObject(WEBSOCKET_PROJECT_ID, webSocketProjectId);
        model.addObject(WEBSOCKET_APPLICATION_ID, webSocketApplicationId);
        model.addObject(WEBSOCKET_RESOURCE_ID, webSocketResourceId);
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/application/{webSocketApplicationId}/resource/{webSocketResourceId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketApplicationId, @PathVariable final String webSocketResourceId,  @ModelAttribute final WebSocketResourceDto webSocketResourceDto) {
        serviceProcessor.process(new UpdateWebSocketResourceInput(webSocketProjectId, webSocketApplicationId, webSocketResourceId, webSocketResourceDto));
        return redirect("/wss/project/" + webSocketProjectId + "/application/" + webSocketApplicationId + "/resource/" + webSocketResourceId);
    }

    /**
     * The method provides the functionality to update the endpoint address for multiple
     * resources at once
     * @param webSocketProjectId The id of the project that the resources belongs to
     * @param webSocketApplicationId The id of the application that the resources belongs to
     * @param updateWebSocketResourcesEndpointCommand The command object contains both the resources that
     *                                          will be updated and the new forwarded address
     * @return Redirects the user to the application page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/application/{webSocketApplicationId}/resource/update/confirm", method = RequestMethod.POST)
    public ModelAndView updateEndpoint(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketApplicationId, @ModelAttribute final UpdateWebSocketResourcesEndpointCommand updateWebSocketResourcesEndpointCommand) {
        Preconditions.checkNotNull(updateWebSocketResourcesEndpointCommand, "The update application endpoint command cannot be null");
        serviceProcessor.process(new UpdateWebSocketResourcesForwardedEndpointInput(webSocketProjectId, webSocketApplicationId, updateWebSocketResourcesEndpointCommand.getWebSocketResources(), updateWebSocketResourcesEndpointCommand.getForwardedEndpoint()));
        return redirect("/wss/project/" + webSocketProjectId + "/application/" + webSocketApplicationId);
    }

}

