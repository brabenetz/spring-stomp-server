package net.brabenetz.app.springstompserver.testtools;

import org.springframework.messaging.simp.stomp.StompHeaders;

public class WebSocketPayload<T> {
    private final StompHeaders headers;
    private final T body;

    public WebSocketPayload(StompHeaders headers, T body) {
        this.headers = headers;
        this.body = body;
    }

    public StompHeaders getHeaders() {
        return headers;
    }

    public T getBody() {
        return body;
    }
}
