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

package com.castlemock.web.mock.websocket.web.mvc.controller.application;

import com.castlemock.core.mock.websocket.model.project.dto.WebSocketApplicationDto;
import com.castlemock.core.mock.websocket.model.project.service.message.input.ReadWebSocketApplicationInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.UpdateWebSocketApplicationInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.UpdateWebSocketApplicationsForwardedEndpointInput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketApplicationOutput;
import com.castlemock.web.mock.websocket.web.mvc.command.application.UpdateWebSocketApplicationsEndpointCommand;
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
public class UpdateWebSocketApplicationController extends AbstractWebSocketViewController {

    private static final String PAGE = "mock/websocket/application/updateWebSocketApplication";


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String projectId, @PathVariable final String applicationId) {
        final ReadWebSocketApplicationOutput output = serviceProcessor.process(new ReadWebSocketApplicationInput(projectId, applicationId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_APPLICATION, output.getWebSocketApplication());
        model.addObject(WEBSOCKET_PROJECT_ID, projectId);
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String projectId, @PathVariable final String applicationId,  @ModelAttribute final WebSocketApplicationDto webSocketApplicationDto) {
        serviceProcessor.process(new UpdateWebSocketApplicationInput(projectId, applicationId, webSocketApplicationDto));
        return redirect("/wss/project/" + projectId + "/application/" + applicationId);
    }

    /**
     * The method provides the functionality to update the endpoint address for multiple
     * applications at once
     * @param projectId The id of the project that the ports belongs to
     * @param updateWebSocketApplicationsEndpointCommand The command object contains both the application that
     *                                          will be updated and the new forwarded address
     * @return Redirects the user to the project page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/update/confirm", method = RequestMethod.POST)
    public ModelAndView updateEndpoint(@PathVariable final String projectId, @ModelAttribute final UpdateWebSocketApplicationsEndpointCommand updateWebSocketApplicationsEndpointCommand) {
        Preconditions.checkNotNull(updateWebSocketApplicationsEndpointCommand, "The update application endpoint command cannot be null");
        serviceProcessor.process(new UpdateWebSocketApplicationsForwardedEndpointInput(projectId, updateWebSocketApplicationsEndpointCommand.getWebSocketApplications(), updateWebSocketApplicationsEndpointCommand.getForwardedEndpoint()));
        return redirect("/wss/project/" + projectId);
    }
}

