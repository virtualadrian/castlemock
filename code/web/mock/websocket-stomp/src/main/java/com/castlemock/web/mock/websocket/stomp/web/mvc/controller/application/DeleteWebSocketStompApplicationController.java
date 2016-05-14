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

import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.DeleteWebSocketStompApplicationInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.DeleteWebSocketStompApplicationsInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.input.ReadWebSocketStompApplicationInput;
import com.castlemock.core.mock.websocket.stomp.model.project.service.message.output.ReadWebSocketStompApplicationOutput;
import com.castlemock.web.mock.websocket.stomp.web.mvc.command.application.DeleteWebSocketStompApplicationsCommand;
import com.castlemock.web.mock.websocket.stomp.web.mvc.controller.AbstractWebSocketStompViewController;
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
public class DeleteWebSocketStompApplicationController extends AbstractWebSocketStompViewController {

    private static final String PAGE = "mock/websocket/stomp/application/deleteWebSocketStompApplication";


    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/delete", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String restProjectId, @PathVariable final String restApplicationId) {
        final ReadWebSocketStompApplicationOutput ouput = serviceProcessor.process(new ReadWebSocketStompApplicationInput(restProjectId, restApplicationId));
        ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_STOMP_PROJECT_ID, restProjectId);
        model.addObject(WEBSOCKET_STOMP_APPLICATION, ouput.getWebSocketStompApplication());
        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final String restProjectId, @PathVariable final String restApplicationId) {
        serviceProcessor.process(new DeleteWebSocketStompApplicationInput(restProjectId, restApplicationId));
        return redirect("/wss/project/" + restProjectId);
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleProjects(@PathVariable final String restProjectId, @ModelAttribute final DeleteWebSocketStompApplicationsCommand deleteWebSocketStompApplicationsCommand) {
        serviceProcessor.process(new DeleteWebSocketStompApplicationsInput(restProjectId, deleteWebSocketStompApplicationsCommand.getWebSocketStompApplications()));
        return redirect("/wss/project/" + restProjectId);
    }


}

