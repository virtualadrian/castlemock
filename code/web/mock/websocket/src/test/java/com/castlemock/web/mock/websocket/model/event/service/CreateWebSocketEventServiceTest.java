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

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.websocket.model.event.dto.WebSocketEventDto;
import com.castlemock.core.mock.websocket.model.event.service.message.input.CreateWebSocketEventInput;
import com.castlemock.core.mock.websocket.model.event.service.message.output.CreateWebSocketEventOutput;
import com.castlemock.web.mock.websocket.model.event.WebSocketEventDtoGenerator;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public class CreateWebSocketEventServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private CreateWebSocketEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(service, "webSocketMaxEventCount", 5);
    }

    @Test
    public void testProcess(){
        final WebSocketEventDto webSocketEvent = WebSocketEventDtoGenerator.generateWebSocketEventDto();
        Mockito.when(repository.save(Mockito.any(WebSocketEventDto.class))).thenReturn(WebSocketEventDtoGenerator.generateWebSocketEventDto());

        final CreateWebSocketEventInput input = new CreateWebSocketEventInput(webSocketEvent);

        final ServiceTask<CreateWebSocketEventInput> serviceTask = new ServiceTask<CreateWebSocketEventInput>(input);
        final ServiceResult<CreateWebSocketEventOutput> serviceResult = service.process(serviceTask);
        final CreateWebSocketEventOutput createWebSocketApplicationOutput = serviceResult.getOutput();
        final WebSocketEventDto returnedWebSocketEventDto = createWebSocketApplicationOutput.getCreatedWebSocketEvent();

        Assert.assertEquals(webSocketEvent.getApplicationId(), returnedWebSocketEventDto.getApplicationId());
        Assert.assertEquals(webSocketEvent.getResourceId(), returnedWebSocketEventDto.getResourceId());
        Assert.assertEquals(webSocketEvent.getProjectId(), returnedWebSocketEventDto.getProjectId());
    }

}
