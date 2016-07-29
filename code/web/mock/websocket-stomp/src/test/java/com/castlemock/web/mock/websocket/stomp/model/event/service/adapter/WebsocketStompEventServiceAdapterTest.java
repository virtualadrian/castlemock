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

package com.castlemock.web.mock.websocket.stomp.model.event.service.adapter;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.TypeIdentifier;
import com.castlemock.core.basis.model.event.dto.EventDto;
import com.castlemock.core.mock.websocket.stomp.model.event.dto.WebSocketStompEventDto;
import com.castlemock.core.mock.websocket.stomp.model.event.service.message.input.ReadAllWebSocketStompEventInput;
import com.castlemock.core.mock.websocket.stomp.model.event.service.message.input.ReadWebSocketStompEventInput;
import com.castlemock.core.mock.websocket.stomp.model.event.service.message.output.ReadAllWebSocketStompEventOutput;
import com.castlemock.core.mock.websocket.stomp.model.event.service.message.output.ReadWebSocketStompEventOutput;
import com.castlemock.web.mock.websocket.stomp.model.WebSocketStompTypeIdentifier;
import com.castlemock.web.mock.websocket.stomp.model.event.WebSocketStompEventDtoGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dalhgren
 * @since 1.5
 */
public class WebSocketStompEventServiceAdapterTest {

    @Mock
    private ServiceProcessor serviceProcessor;

    @InjectMocks
    private WebSocketStompEventServiceAdapter serviceAdapter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreate(){
        final WebSocketStompEventDto websocketStompEventDto = WebSocketStompEventDtoGenerator.generateWebSocketStompEventDto();
        serviceAdapter.create(websocketStompEventDto);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDelete(){
        final WebSocketStompEventDto websocketStompEventDto = WebSocketStompEventDtoGenerator.generateWebSocketStompEventDto();
        serviceAdapter.delete(websocketStompEventDto.getProjectId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdate(){
        final WebSocketStompEventDto websocketStompEventDto = WebSocketStompEventDtoGenerator.generateWebSocketStompEventDto();
        serviceAdapter.update(websocketStompEventDto.getProjectId(), websocketStompEventDto);
    }

    @Test
    public void testReadAll(){
        final List<WebSocketStompEventDto> websocketStompEventDtos = new ArrayList<WebSocketStompEventDto>();
        for(int index = 0; index < 3; index++){
            final WebSocketStompEventDto websocketStompEventDto = WebSocketStompEventDtoGenerator.generateWebSocketStompEventDto();
            websocketStompEventDtos.add(websocketStompEventDto);

        }

        final ReadAllWebSocketStompEventOutput output = new ReadAllWebSocketStompEventOutput(websocketStompEventDtos);
        Mockito.when(serviceProcessor.process(Mockito.any(ReadAllWebSocketStompEventInput.class))).thenReturn(output);

        final List<WebSocketStompEventDto> returnedWebSocketStompEventDtos = serviceAdapter.readAll();

        for(int index = 0; index < 3; index++){
            final WebSocketStompEventDto websocketStompEvent = websocketStompEventDtos.get(index);
            final WebSocketStompEventDto returnedWebSocketStompEvent = returnedWebSocketStompEventDtos.get(index);

            Assert.assertEquals(websocketStompEvent.getId(), returnedWebSocketStompEvent.getId());
            Assert.assertEquals(websocketStompEvent.getApplicationId(), returnedWebSocketStompEvent.getApplicationId());
            Assert.assertEquals(websocketStompEvent.getResourceId(), returnedWebSocketStompEvent.getResourceId());
            Assert.assertEquals(websocketStompEvent.getProjectId(), returnedWebSocketStompEvent.getProjectId());
        }
    }

    @Test
    public void testRead(){
        final WebSocketStompEventDto websocketStompEvent = WebSocketStompEventDtoGenerator.generateWebSocketStompEventDto();
        final ReadWebSocketStompEventOutput output = new ReadWebSocketStompEventOutput(websocketStompEvent);
        Mockito.when(serviceProcessor.process(Mockito.any(ReadWebSocketStompEventInput.class))).thenReturn(output);

        final WebSocketStompEventDto returnedWebSocketStompEvent = serviceAdapter.read(websocketStompEvent.getId());

        Assert.assertEquals(websocketStompEvent.getId(), returnedWebSocketStompEvent.getId());
        Assert.assertEquals(websocketStompEvent.getApplicationId(), returnedWebSocketStompEvent.getApplicationId());
        Assert.assertEquals(websocketStompEvent.getResourceId(), returnedWebSocketStompEvent.getResourceId());
        Assert.assertEquals(websocketStompEvent.getProjectId(), returnedWebSocketStompEvent.getProjectId());
    }

    @Test
    public void testGetTypeIdentifier(){
        final WebSocketStompTypeIdentifier websocketStompTypeIdentifier = new WebSocketStompTypeIdentifier();
        final TypeIdentifier returnedWebSocketStompTypeIdentifier = serviceAdapter.getTypeIdentifier();

        Assert.assertEquals(websocketStompTypeIdentifier.getType(), returnedWebSocketStompTypeIdentifier.getType());
        Assert.assertEquals(websocketStompTypeIdentifier.getTypeUrl(), returnedWebSocketStompTypeIdentifier.getTypeUrl());
    }

    @Test
    public void testConvertType(){
        EventDto eventDto = WebSocketStompEventDtoGenerator.generateWebSocketStompEventDto();
        WebSocketStompEventDto returnedWebSocketStompEventDto = serviceAdapter.convertType(eventDto);
        Assert.assertEquals(eventDto.getId(), returnedWebSocketStompEventDto.getId());
        Assert.assertEquals(eventDto.getEndDate(), returnedWebSocketStompEventDto.getEndDate());
        Assert.assertEquals(eventDto.getResourceLink(), returnedWebSocketStompEventDto.getResourceLink());
        Assert.assertEquals(eventDto.getStartDate(), returnedWebSocketStompEventDto.getStartDate());
        Assert.assertEquals(eventDto.getResourceName(), returnedWebSocketStompEventDto.getResourceName());
    }

    @Test
    public void testGenerateResourceLink(){
        final WebSocketStompEventDto websocketStompEventDto = WebSocketStompEventDtoGenerator.generateWebSocketStompEventDto();
        final String generatedResourceLink = serviceAdapter.generateResourceLink(websocketStompEventDto);
        Assert.assertEquals("/web/websocket-stomp/project/" + websocketStompEventDto.getProjectId() + "/application/" + websocketStompEventDto.getApplicationId() + "/resource/" + websocketStompEventDto.getResourceId(), generatedResourceLink);
    }
}
