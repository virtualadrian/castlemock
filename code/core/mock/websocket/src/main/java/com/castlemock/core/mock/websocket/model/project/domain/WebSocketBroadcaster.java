package com.castlemock.core.mock.websocket.model.project.domain;

import com.castlemock.core.basis.model.Saveable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * @since 1.6
 */
public class WebSocketBroadcaster implements Saveable<String> {

    private String id;
    private String name;
    private List<WebSocketMockBroadcastMessage> mockBroadcastMessages;
    private WebSocketResourceStatus status;

    @Override
    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "mockBroadcastMessages")
    @XmlElement(name = "mockResponse")
    public List<WebSocketMockBroadcastMessage> getMockBroadcastMessages() {
        return mockBroadcastMessages;
    }

    public void setMockBroadcastMessages(List<WebSocketMockBroadcastMessage> mockBroadcastMessages) {
        this.mockBroadcastMessages = mockBroadcastMessages;
    }

    @XmlElement
    public WebSocketResourceStatus getStatus() {
        return status;
    }

    public void setStatus(WebSocketResourceStatus status) {
        this.status = status;
    }

}
