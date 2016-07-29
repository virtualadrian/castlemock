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

package com.castlemock.web.mock.websocket.stomp.model.event.service;

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.websocket.stomp.model.event.domain.WebSocketStompEvent;
import com.castlemock.core.mock.websocket.stomp.model.event.dto.WebSocketStompEventDto;
import com.castlemock.core.mock.websocket.stomp.model.event.service.message.input.CreateWebSocketStompEventInput;
import com.castlemock.core.mock.websocket.stomp.model.event.service.message.output.CreateWebSocketStompEventOutput;
import com.castlemock.web.mock.websocket.stomp.model.event.WebSocketStompEventDtoGenerator;
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
public class CreateWebSocketStompEventServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private CreateWebSocketStompEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(service, "webSocketStompMaxEventCount", 5);
    }

    @Test
    public void testProcess(){
        final WebSocketStompEventDto webSocketStompEvent = WebSocketStompEventDtoGenerator.generateWebSocketStompEventDto();
        Mockito.when(repository.save(Mockito.any(WebSocketStompEventDto.class))).thenReturn(WebSocketStompEventDtoGenerator.generateWebSocketStompEventDto());

        final CreateWebSocketStompEventInput input = new CreateWebSocketStompEventInput(webSocketStompEvent);

        final ServiceTask<CreateWebSocketStompEventInput> serviceTask = new ServiceTask<CreateWebSocketStompEventInput>(input);
        final ServiceResult<CreateWebSocketStompEventOutput> serviceResult = service.process(serviceTask);
        final CreateWebSocketStompEventOutput createWebSocketStompApplicationOutput = serviceResult.getOutput();
        final WebSocketStompEventDto returnedWebSocketStompEventDto = createWebSocketStompApplicationOutput.getCreatedWebSocketStompEvent();

        Assert.assertEquals(webSocketStompEvent.getApplicationId(), returnedWebSocketStompEventDto.getApplicationId());
        Assert.assertEquals(webSocketStompEvent.getResourceId(), returnedWebSocketStompEventDto.getResourceId());
        Assert.assertEquals(webSocketStompEvent.getProjectId(), returnedWebSocketStompEventDto.getProjectId());
    }

}
