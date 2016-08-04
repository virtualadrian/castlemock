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

package com.castlemock.web.mock.websocket.model.event.service.adapter;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.TypeIdentifier;
import com.castlemock.core.basis.model.event.dto.EventDto;
import com.castlemock.core.mock.websocket.model.event.dto.WebSocketEventDto;
import com.castlemock.core.mock.websocket.model.event.service.message.input.ReadAllWebSocketEventInput;
import com.castlemock.core.mock.websocket.model.event.service.message.input.ReadWebSocketEventInput;
import com.castlemock.core.mock.websocket.model.event.service.message.output.ReadAllWebSocketEventOutput;
import com.castlemock.core.mock.websocket.model.event.service.message.output.ReadWebSocketEventOutput;
import com.castlemock.web.mock.websocket.model.WebSocketTypeIdentifier;
import com.castlemock.web.mock.websocket.model.event.WebSocketEventDtoGenerator;
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
public class WebSocketEventServiceAdapterTest {

    @Mock
    private ServiceProcessor serviceProcessor;

    @InjectMocks
    private WebSocketEventServiceAdapter serviceAdapter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreate(){
        final WebSocketEventDto webSocketEventDto = WebSocketEventDtoGenerator.generateWebSocketEventDto();
        serviceAdapter.create(webSocketEventDto);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDelete(){
        final WebSocketEventDto webSocketEventDto = WebSocketEventDtoGenerator.generateWebSocketEventDto();
        serviceAdapter.delete(webSocketEventDto.getProjectId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdate(){
        final WebSocketEventDto webSocketEventDto = WebSocketEventDtoGenerator.generateWebSocketEventDto();
        serviceAdapter.update(webSocketEventDto.getProjectId(), webSocketEventDto);
    }

    @Test
    public void testReadAll(){
        final List<WebSocketEventDto> webSocketEventDtos = new ArrayList<WebSocketEventDto>();
        for(int index = 0; index < 3; index++){
            final WebSocketEventDto webSocketEventDto = WebSocketEventDtoGenerator.generateWebSocketEventDto();
            webSocketEventDtos.add(webSocketEventDto);

        }

        final ReadAllWebSocketEventOutput output = new ReadAllWebSocketEventOutput(webSocketEventDtos);
        Mockito.when(serviceProcessor.process(Mockito.any(ReadAllWebSocketEventInput.class))).thenReturn(output);

        final List<WebSocketEventDto> returnedWebSocketEventDtos = serviceAdapter.readAll();

        for(int index = 0; index < 3; index++){
            final WebSocketEventDto webSocketEvent = webSocketEventDtos.get(index);
            final WebSocketEventDto returnedWebSocketEvent = returnedWebSocketEventDtos.get(index);

            Assert.assertEquals(webSocketEvent.getId(), returnedWebSocketEvent.getId());
            Assert.assertEquals(webSocketEvent.getTopicId(), returnedWebSocketEvent.getTopicId());
            Assert.assertEquals(webSocketEvent.getResourceId(), returnedWebSocketEvent.getResourceId());
            Assert.assertEquals(webSocketEvent.getProjectId(), returnedWebSocketEvent.getProjectId());
        }
    }

    @Test
    public void testRead(){
        final WebSocketEventDto webSocketEvent = WebSocketEventDtoGenerator.generateWebSocketEventDto();
        final ReadWebSocketEventOutput output = new ReadWebSocketEventOutput(webSocketEvent);
        Mockito.when(serviceProcessor.process(Mockito.any(ReadWebSocketEventInput.class))).thenReturn(output);

        final WebSocketEventDto returnedWebSocketEvent = serviceAdapter.read(webSocketEvent.getId());

        Assert.assertEquals(webSocketEvent.getId(), returnedWebSocketEvent.getId());
        Assert.assertEquals(webSocketEvent.getTopicId(), returnedWebSocketEvent.getTopicId());
        Assert.assertEquals(webSocketEvent.getResourceId(), returnedWebSocketEvent.getResourceId());
        Assert.assertEquals(webSocketEvent.getProjectId(), returnedWebSocketEvent.getProjectId());
    }

    @Test
    public void testGetTypeIdentifier(){
        final WebSocketTypeIdentifier webSocketTypeIdentifier = new WebSocketTypeIdentifier();
        final TypeIdentifier returnedWebSocketTypeIdentifier = serviceAdapter.getTypeIdentifier();

        Assert.assertEquals(webSocketTypeIdentifier.getType(), returnedWebSocketTypeIdentifier.getType());
        Assert.assertEquals(webSocketTypeIdentifier.getTypeUrl(), returnedWebSocketTypeIdentifier.getTypeUrl());
    }

    @Test
    public void testConvertType(){
        EventDto eventDto = WebSocketEventDtoGenerator.generateWebSocketEventDto();
        WebSocketEventDto returnedWebSocketEventDto = serviceAdapter.convertType(eventDto);
        Assert.assertEquals(eventDto.getId(), returnedWebSocketEventDto.getId());
        Assert.assertEquals(eventDto.getEndDate(), returnedWebSocketEventDto.getEndDate());
        Assert.assertEquals(eventDto.getResourceLink(), returnedWebSocketEventDto.getResourceLink());
        Assert.assertEquals(eventDto.getStartDate(), returnedWebSocketEventDto.getStartDate());
        Assert.assertEquals(eventDto.getResourceName(), returnedWebSocketEventDto.getResourceName());
    }

    @Test
    public void testGenerateResourceLink(){
        final WebSocketEventDto webSocketEventDto = WebSocketEventDtoGenerator.generateWebSocketEventDto();
        final String generatedResourceLink = serviceAdapter.generateResourceLink(webSocketEventDto);
        Assert.assertEquals("/web/websocket/project/" + webSocketEventDto.getProjectId() + "/topic/" + webSocketEventDto.getTopicId() + "/resource/" + webSocketEventDto.getResourceId(), generatedResourceLink);
    }
}
