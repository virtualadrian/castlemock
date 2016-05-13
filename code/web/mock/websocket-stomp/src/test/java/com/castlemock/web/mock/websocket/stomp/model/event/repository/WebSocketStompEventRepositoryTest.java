package com.castlemock.web.mock.websocket.stomp.model.event.repository;

import com.castlemock.core.mock.websocket.stomp.model.event.domain.WebSocketStompEvent;
import com.castlemock.web.basis.support.FileRepositorySupport;
import com.castlemock.web.mock.websocket.stomp.model.event.WebSocketStompEventDtoGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

    @InjectMocks
    private WebSocketStompEventRepositoryImpl repository;
    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(repository, "WebSocketStompEventFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "WebSocketStompEventFileExtension", EXTENSION);
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
        final WebSocketStompEvent WebSocketStompEvent = save();
        final WebSocketStompEvent returnedWebSocketStompEvent = repository.findOne(WebSocketStompEvent.getId());
        Assert.assertEquals(WebSocketStompEvent, returnedWebSocketStompEvent);
    }

    @Test
    public void testFindAll(){
        final WebSocketStompEvent WebSocketStompEvent = save();
        final List<WebSocketStompEvent> WebSocketStompEvents = repository.findAll();
        Assert.assertEquals(WebSocketStompEvents.size(), 1);
        Assert.assertEquals(WebSocketStompEvents.get(0), WebSocketStompEvent);
    }

    @Test
    public void testSave(){
        final WebSocketStompEvent WebSocketStompEvent = save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(WebSocketStompEvent, DIRECTORY + File.separator + WebSocketStompEvent.getId() + EXTENSION);
    }

    @Test
    public void testDelete(){
        final WebSocketStompEvent WebSocketStompEvent = save();
        repository.delete(WebSocketStompEvent.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + WebSocketStompEvent.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final WebSocketStompEvent WebSocketStompEvent = save();
        final Integer count = repository.count();
        Assert.assertEquals(new Integer(1), count);
    }

    private WebSocketStompEvent save(){
        final WebSocketStompEvent WebSocketStompEvent = WebSocketStompEventDtoGenerator.generateWebSocketStompEvent();
        repository.save(WebSocketStompEvent);
        return WebSocketStompEvent;
    }

}
