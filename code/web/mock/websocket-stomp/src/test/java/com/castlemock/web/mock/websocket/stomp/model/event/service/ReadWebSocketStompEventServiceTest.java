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
import com.castlemock.core.mock.websocket.stomp.model.event.service.message.input.ReadWebSocketStompEventInput;
import com.castlemock.core.mock.websocket.stomp.model.event.service.message.output.ReadWebSocketStompEventOutput;
import com.castlemock.web.mock.websocket.stomp.model.event.WebSocketStompEventDtoGenerator;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public class ReadWebSocketStompEventServiceTest {


    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private ReadWebSocketStompEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final WebSocketStompEvent webSocketStompEvent = WebSocketStompEventDtoGenerator.generateWebSocketStompEvent();
        Mockito.when(repository.findOne(webSocketStompEvent.getId())).thenReturn(webSocketStompEvent);

        final ReadWebSocketStompEventInput input = new ReadWebSocketStompEventInput(webSocketStompEvent.getId());
        final ServiceTask<ReadWebSocketStompEventInput> serviceTask = new ServiceTask<ReadWebSocketStompEventInput>(input);
        final ServiceResult<ReadWebSocketStompEventOutput> serviceResult = service.process(serviceTask);
        final ReadWebSocketStompEventOutput output = serviceResult.getOutput();
        final WebSocketStompEventDto returnedWebSocketStompEvent = output.getWebSocketStompEvent();

        Assert.assertEquals(webSocketStompEvent.getId(), returnedWebSocketStompEvent.getId());
        Assert.assertEquals(webSocketStompEvent.getApplicationId(), returnedWebSocketStompEvent.getApplicationId());
        Assert.assertEquals(webSocketStompEvent.getResourceId(), returnedWebSocketStompEvent.getResourceId());
        Assert.assertEquals(webSocketStompEvent.getProjectId(), returnedWebSocketStompEvent.getProjectId());
    }
}
