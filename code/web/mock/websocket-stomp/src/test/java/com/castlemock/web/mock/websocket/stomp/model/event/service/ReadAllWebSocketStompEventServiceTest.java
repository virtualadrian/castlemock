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
import com.castlemock.core.mock.websocket.stomp.model.event.service.message.input.ReadAllWebSocketStompEventInput;
import com.castlemock.core.mock.websocket.stomp.model.event.service.message.output.ReadAllWebSocketStompEventOutput;
import com.castlemock.web.mock.websocket.stomp.model.event.WebSocketStompEventDtoGenerator;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public class ReadAllWebSocketStompEventServiceTest {


    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private ReadAllWebSocketStompEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final List<WebSocketStompEventDto> webSocketStompEvents = new ArrayList<WebSocketStompEventDto>();
        for(int index = 0; index < 3; index++){
            final WebSocketStompEventDto webSocketStompEvent = WebSocketStompEventDtoGenerator.generateWebSocketStompEventDto();
            webSocketStompEvents.add(webSocketStompEvent);
        }

        Mockito.when(repository.findAll()).thenReturn(webSocketStompEvents);

        final ReadAllWebSocketStompEventInput input = new ReadAllWebSocketStompEventInput();
        final ServiceTask<ReadAllWebSocketStompEventInput> serviceTask = new ServiceTask<ReadAllWebSocketStompEventInput>(input);
        final ServiceResult<ReadAllWebSocketStompEventOutput> serviceResult = service.process(serviceTask);
        final ReadAllWebSocketStompEventOutput output = serviceResult.getOutput();

        Assert.assertEquals(webSocketStompEvents.size(), output.getWebSocketStompEvents().size());

        for(int index = 0; index < 3; index++){
            final WebSocketStompEventDto webSocketStompEventDto = webSocketStompEvents.get(index);
            final WebSocketStompEventDto returnedWebSocketStompEvent = output.getWebSocketStompEvents().get(index);

            Assert.assertEquals(webSocketStompEventDto.getId(), returnedWebSocketStompEvent.getId());
            Assert.assertEquals(webSocketStompEventDto.getApplicationId(), returnedWebSocketStompEvent.getApplicationId());
            Assert.assertEquals(webSocketStompEventDto.getResourceId(), returnedWebSocketStompEvent.getResourceId());
            Assert.assertEquals(webSocketStompEventDto.getProjectId(), returnedWebSocketStompEvent.getProjectId());
        }
    }
}
