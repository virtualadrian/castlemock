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

package com.castlemock.web.mock.websocket.model.event.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.model.event.dto.EventDto;
import com.castlemock.core.mock.websocket.model.event.dto.WebSocketEventDto;
import com.castlemock.core.mock.websocket.model.event.service.message.input.CreateWebSocketEventInput;
import com.castlemock.core.mock.websocket.model.event.service.message.output.CreateWebSocketEventOutput;
import org.springframework.beans.factory.annotation.Value;

/**
 * The service provides the functionality to create new WebSocket event and save it to the repository.
 * @author Karl Dahlgren
 * @since 1.5
 */
@org.springframework.stereotype.Service
public class CreateWebSocketEventService extends AbstractWebSocketEventService implements Service<CreateWebSocketEventInput, CreateWebSocketEventOutput> {

    @Value("${websocket.event.max}")
    private Integer webSocketMaxEventCount;

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateWebSocketEventOutput> process(ServiceTask<CreateWebSocketEventInput> serviceTask) {
        final CreateWebSocketEventInput input = serviceTask.getInput();
        final WebSocketEventDto webSocketEventDto = input.getWebSocketEvent();
        if(count() >= webSocketMaxEventCount){
            EventDto eventDto = repository.getOldestEvent();
            delete(eventDto.getId());

        }
        final WebSocketEventDto createdWebSocketEventDto = save(webSocketEventDto);
        return createServiceResult(new CreateWebSocketEventOutput(createdWebSocketEventDto));
    }
}
