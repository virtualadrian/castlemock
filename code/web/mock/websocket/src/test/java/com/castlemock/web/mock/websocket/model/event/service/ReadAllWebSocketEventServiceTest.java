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
import com.castlemock.core.mock.websocket.model.event.service.message.input.ReadAllWebSocketEventInput;
import com.castlemock.core.mock.websocket.model.event.service.message.output.ReadAllWebSocketEventOutput;
import com.castlemock.web.mock.websocket.model.event.WebSocketEventDtoGenerator;
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
public class ReadAllWebSocketEventServiceTest {


    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private ReadAllWebSocketEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final List<WebSocketEventDto> webSocketEvents = new ArrayList<WebSocketEventDto>();
        for(int index = 0; index < 3; index++){
            final WebSocketEventDto webSocketEvent = WebSocketEventDtoGenerator.generateWebSocketEventDto();
            webSocketEvents.add(webSocketEvent);
        }

        Mockito.when(repository.findAll()).thenReturn(webSocketEvents);

        final ReadAllWebSocketEventInput input = new ReadAllWebSocketEventInput();
        final ServiceTask<ReadAllWebSocketEventInput> serviceTask = new ServiceTask<ReadAllWebSocketEventInput>(input);
        final ServiceResult<ReadAllWebSocketEventOutput> serviceResult = service.process(serviceTask);
        final ReadAllWebSocketEventOutput output = serviceResult.getOutput();

        Assert.assertEquals(webSocketEvents.size(), output.getWebSocketEvents().size());

        for(int index = 0; index < 3; index++){
            final WebSocketEventDto webSocketEventDto = webSocketEvents.get(index);
            final WebSocketEventDto returnedWebSocketEvent = output.getWebSocketEvents().get(index);

            Assert.assertEquals(webSocketEventDto.getId(), returnedWebSocketEvent.getId());
            Assert.assertEquals(webSocketEventDto.getApplicationId(), returnedWebSocketEvent.getApplicationId());
            Assert.assertEquals(webSocketEventDto.getResourceId(), returnedWebSocketEvent.getResourceId());
            Assert.assertEquals(webSocketEventDto.getProjectId(), returnedWebSocketEvent.getProjectId());
        }
    }
}
