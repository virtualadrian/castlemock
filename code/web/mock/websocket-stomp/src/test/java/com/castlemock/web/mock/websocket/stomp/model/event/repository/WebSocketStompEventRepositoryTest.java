package com.castlemock.web.mock.websocket.stomp.model.event.repository;

import com.castlemock.core.mock.websocket.stomp.model.event.domain.WebSocketStompEvent;
import com.castlemock.core.mock.websocket.stomp.model.event.dto.WebSocketStompEventDto;
import com.castlemock.web.basis.support.FileRepositorySupport;
import com.castlemock.web.mock.websocket.stomp.model.event.WebSocketStompEventDtoGenerator;
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
public class WebSocketStompEventRepositoryTest {

    @Mock
    private FileRepositorySupport fileRepositorySupport;
    @Spy
    private DozerBeanMapper mapper;
    @InjectMocks
    private WebSocketStompEventRepositoryImpl repository;
    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(repository, "webSocketStompEventFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "webSocketStompEventFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<WebSocketStompEvent> WebSocketStompEvents = new ArrayList<WebSocketStompEvent>();
        WebSocketStompEvent WebSocketStompEvent = WebSocketStompEventDtoGenerator.generateWebSocketStompEvent();
        WebSocketStompEvents.add(WebSocketStompEvent);
        Mockito.when(fileRepositorySupport.load(WebSocketStompEvent.class, DIRECTORY, EXTENSION)).thenReturn(WebSocketStompEvents);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(WebSocketStompEvent.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final WebSocketStompEventDto WebSocketStompEvent = save();
        final WebSocketStompEventDto returnedWebSocketStompEvent = repository.findOne(WebSocketStompEvent.getId());
        Assert.assertEquals(WebSocketStompEvent.getId(), returnedWebSocketStompEvent.getId());
    }

    @Test
    public void testFindAll(){
        final WebSocketStompEventDto WebSocketStompEvent = save();
        final List<WebSocketStompEventDto> WebSocketStompEvents = repository.findAll();
        Assert.assertEquals(WebSocketStompEvents.size(), 1);
        Assert.assertEquals(WebSocketStompEvents.get(0).getId(), WebSocketStompEvent.getId());
    }

    @Test
    public void testSave(){
        final WebSocketStompEventDto WebSocketStompEvent = save();
        // TODO: Check verify
        //Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.isA(WebSocketStompEvent.class), DIRECTORY + File.separator + WebSocketStompEvent.getId() + EXTENSION);
    }

    @Test
    public void testDelete(){
        final WebSocketStompEventDto WebSocketStompEvent = save();
        repository.delete(WebSocketStompEvent.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + WebSocketStompEvent.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final WebSocketStompEventDto WebSocketStompEvent = save();
        final Integer count = repository.count();
        Assert.assertEquals(new Integer(1), count);
    }

    private WebSocketStompEventDto save(){
        final WebSocketStompEventDto WebSocketStompEvent = WebSocketStompEventDtoGenerator.generateWebSocketStompEventDto();
        repository.save(WebSocketStompEvent);
        return WebSocketStompEvent;
    }

}
