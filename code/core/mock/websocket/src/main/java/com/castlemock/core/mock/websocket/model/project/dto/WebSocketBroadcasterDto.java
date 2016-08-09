package com.castlemock.core.mock.websocket.model.project.dto;

import com.castlemock.core.mock.websocket.model.project.domain.WebSocketResourceStatus;
import org.dozer.Mapping;

import java.util.LinkedList;
import java.util.List;

/**
 * @since 1.6
 */
public class WebSocketBroadcasterDto {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("mockBroadcastMessages")
    private List<WebSocketMockBroadcastMessageDto> mockBroadcastMessages = new LinkedList<WebSocketMockBroadcastMessageDto>();

    @Mapping("status")
    private WebSocketResourceStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WebSocketMockBroadcastMessageDto> getMockBroadcastMessages() {
        return mockBroadcastMessages;
    }

    public void setMockBroadcastMessages(List<WebSocketMockBroadcastMessageDto> mockBroadcastMessages) {
        this.mockBroadcastMessages = mockBroadcastMessages;
    }

    public WebSocketResourceStatus getStatus() {
        return status;
    }

    public void setStatus(WebSocketResourceStatus status) {
        this.status = status;
    }
}
