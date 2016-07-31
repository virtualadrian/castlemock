package com.castlemock.web.mock.websocket.model.event.repository;

import com.castlemock.core.mock.websocket.model.event.domain.WebSocketEvent;
import com.castlemock.core.mock.websocket.model.event.dto.WebSocketEventDto;
import com.castlemock.web.basis.support.FileRepositorySupport;
import com.castlemock.web.mock.websocket.model.event.WebSocketEventDtoGenerator;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.5
 */
public class WebSocketEventRepositoryTest {

    @Mock
    private FileRepositorySupport fileRepositorySupport;
    @Spy
    private DozerBeanMapper mapper;
    @InjectMocks
    private WebSocketEventRepositoryImpl repository;
    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(repository, "webSocketEventFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "webSocketEventFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<WebSocketEvent> webSocketEvents = new ArrayList<WebSocketEvent>();
        WebSocketEvent webSocketEvent = WebSocketEventDtoGenerator.generateWebSocketEvent();
        webSocketEvents.add(webSocketEvent);
        Mockito.when(fileRepositorySupport.load(WebSocketEvent.class, DIRECTORY, EXTENSION)).thenReturn(webSocketEvents);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(WebSocketEvent.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final WebSocketEventDto webSocketEvent = save();
        final WebSocketEventDto returnedWebSocketEvent = repository.findOne(webSocketEvent.getId());
        Assert.assertEquals(webSocketEvent.getId(), returnedWebSocketEvent.getId());
    }

    @Test
    public void testFindAll(){
        final WebSocketEventDto webSocketEvent = save();
        final List<WebSocketEventDto> webSocketEvents = repository.findAll();
        Assert.assertEquals(webSocketEvents.size(), 1);
        Assert.assertEquals(webSocketEvents.get(0).getId(), webSocketEvent.getId());
    }

    @Test
    public void testSave(){
        final WebSocketEventDto webSocketEvent = save();
        // TODO: Check verify
        //Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.isA(WebSocketEvent.class), DIRECTORY + File.separator + WebSocketEvent.getId() + EXTENSION);
    }

    @Test
    public void testDelete(){
        final WebSocketEventDto webSocketEvent = save();
        repository.delete(webSocketEvent.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + webSocketEvent.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final WebSocketEventDto webSocketEvent = save();
        final Integer count = repository.count();
        Assert.assertEquals(new Integer(1), count);
    }

    private WebSocketEventDto save(){
        final WebSocketEventDto webSocketEvent = WebSocketEventDtoGenerator.generateWebSocketEventDto();
        repository.save(webSocketEvent);
        return webSocketEvent;
    }

}
