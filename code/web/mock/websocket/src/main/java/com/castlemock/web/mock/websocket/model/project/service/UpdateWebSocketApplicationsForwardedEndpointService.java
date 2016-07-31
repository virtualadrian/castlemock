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

package com.castlemock.web.mock.websocket.model.project.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketApplicationDto;
import com.castlemock.core.mock.websocket.model.project.dto.WebSocketResourceDto;
import com.castlemock.core.mock.websocket.model.project.service.message.input.UpdateWebSocketApplicationsForwardedEndpointInput;
import com.castlemock.core.mock.websocket.model.project.service.message.output.UpdateWebSocketApplicationsForwardedEndpointOutput;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
@org.springframework.stereotype.Service
public class UpdateWebSocketApplicationsForwardedEndpointService extends AbstractWebSocketProjectService implements Service<UpdateWebSocketApplicationsForwardedEndpointInput, UpdateWebSocketApplicationsForwardedEndpointOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateWebSocketApplicationsForwardedEndpointOutput> process(final ServiceTask<UpdateWebSocketApplicationsForwardedEndpointInput> serviceTask) {
        final UpdateWebSocketApplicationsForwardedEndpointInput input = serviceTask.getInput();
        for(WebSocketApplicationDto webSocketApplicationDto : input.getWebSocketApplications()){
            WebSocketApplicationDto webSocketApplication = repository.findWebSocketApplication(input.getWebSocketProjectId(), webSocketApplicationDto.getId());
            for(WebSocketResourceDto webSocketResource : webSocketApplication.getResources()){
                // Set forward address
                repository.updateWebSocketResource(input.getWebSocketProjectId(), webSocketApplication.getId(), webSocketResource.getId(), webSocketResource);
            }
        }
        return createServiceResult(new UpdateWebSocketApplicationsForwardedEndpointOutput());
    }
}
