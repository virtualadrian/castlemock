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

package com.castlemock.web.mock.websocket.stomp.web.mvc.controller.application;

import com.castlemock.core.mock.websocket.stomp.model.project.dto.WebSocketStompApplicationDto;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.ReadWebSocketStompApplicationInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.UpdateWebSocketStompApplicationInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.UpdateWebSocketStompApplicationsForwardedEndpointInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.output.ReadWebSocketStompApplicationOutput;
import com.castlemock.web.mock.websocket.stomp.web.mvc.command.application.UpdateWebSocketStompApplicationsEndpointCommand;
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
public class UpdateWebSocketStompApplicationController extends AbstractWebSocketStompViewController {

    private static final String PAGE = "mock/websocket/stomp/application/updateWebSocketStompApplication";


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String projectId, @PathVariable final String applicationId) {
        final ReadWebSocketStompApplicationOutput output = serviceProcessor.process(new ReadWebSocketStompApplicationInput(projectId, applicationId));
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_STOMP_APPLICATION, output.getWebSocketStompApplication());
        model.addObject(WEBSOCKET_STOMP_PROJECT_ID, projectId);
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final String projectId, @PathVariable final String applicationId,  @ModelAttribute final WebSocketStompApplicationDto webSocketStompApplicationDto) {
        serviceProcessor.process(new UpdateWebSocketStompApplicationInput(projectId, applicationId, webSocketStompApplicationDto));
        return redirect("/wss/project/" + projectId + "/application/" + applicationId);
    }

    /**
     * The method provides the functionality to update the endpoint address for multiple
     * applications at once
     * @param projectId The id of the project that the ports belongs to
     * @param updateWebSocketStompApplicationsEndpointCommand The command object contains both the application that
     *                                          will be updated and the new forwarded address
     * @return Redirects the user to the project page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/update/confirm", method = RequestMethod.POST)
    public ModelAndView updateEndpoint(@PathVariable final String projectId, @ModelAttribute final UpdateWebSocketStompApplicationsEndpointCommand updateWebSocketStompApplicationsEndpointCommand) {
        Preconditions.checkNotNull(updateWebSocketStompApplicationsEndpointCommand, "The update application endpoint command cannot be null");
        serviceProcessor.process(new UpdateWebSocketStompApplicationsForwardedEndpointInput(projectId, updateWebSocketStompApplicationsEndpointCommand.getWebSocketStompApplications(), updateWebSocketStompApplicationsEndpointCommand.getForwardedEndpoint()));
        return redirect("/wss/project/" + projectId);
    }
}

