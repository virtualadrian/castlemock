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
import com.castlemock.core.mock.websocket.model.project.service.message.input.CreateWebSocketApplicationInput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.CreateWebSocketApplicationOutput;
import com.castlemock.web.mock.websocket.web.mvc.controller.AbstractWebSocketViewController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The project controller provides functionality to retrieve a specific {@link WebSocketApplicationDto}
 * @author Karl Dahlgren
 * @since 1.5
 */
@Controller
@RequestMapping("/web/wss/project")
public class CreateWebSocketApplicationController extends AbstractWebSocketViewController {

    private static final String PAGE = "mock/websocket/application/createWebSocketApplication";


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/create/application", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final String projectId) {
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(WEBSOCKET_PROJECT_ID, projectId);
        model.addObject(WEBSOCKET_APPLICATION, new WebSocketApplicationDto());
        return model;
    }


    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/create/application", method = RequestMethod.POST)
    public ModelAndView createProject(@PathVariable final String projectId, @ModelAttribute final WebSocketApplicationDto webSocketApplicationDto) {
        final CreateWebSocketApplicationOutput output = serviceProcessor.process(new CreateWebSocketApplicationInput(projectId, webSocketApplicationDto));
        return redirect("/wss/project/" + projectId + "/application/" + output.getSavedWebSocketApplication().getId());
    }

}

