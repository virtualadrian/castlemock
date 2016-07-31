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

import com.castlemock.core.mock.websocket.model.project.service.message.input.DeleteWebSocketApplicationInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.DeleteWebSocketApplicationsInput;
import com.castlemock.core.mock.websocket.model.project.service.message.input.ReadWebSocketApplicationInput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.ReadWebSocketApplicationOutput;
import com.castlemock.web.mock.websocket.web.mvc.command.application.DeleteWebSocketApplicationsCommand;
import com.castlemock.web.mock.websocket.web.mvc.controller.AbstractWebSocketViewController;
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
public class DeleteWebSocketApplicationController extends AbstractWebSocketViewController {

    private static final String PAGE = "mock/websocket/application/deleteWebSocketApplication";


    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/application/{webSocketApplicationId}/delete", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketApplicationId) {
        final ReadWebSocketApplicationOutput ouput = serviceProcessor.process(new ReadWebSocketApplicationInput(webSocketProjectId, webSocketApplicationId));
        ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_PROJECT_ID, webSocketProjectId);
        model.addObject(WEBSOCKET_APPLICATION, ouput.getWebSocketApplication());
        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/application/{webSocketApplicationId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final String webSocketProjectId, @PathVariable final String webSocketApplicationId) {
        serviceProcessor.process(new DeleteWebSocketApplicationInput(webSocketProjectId, webSocketApplicationId));
        return redirect("/wss/project/" + webSocketProjectId);
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{webSocketProjectId}/application/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleProjects(@PathVariable final String webSocketProjectId, @ModelAttribute final DeleteWebSocketApplicationsCommand deleteWebSocketApplicationsCommand) {
        serviceProcessor.process(new DeleteWebSocketApplicationsInput(webSocketProjectId, deleteWebSocketApplicationsCommand.getWebSocketApplications()));
        return redirect("/wss/project/" + webSocketProjectId);
    }


}

