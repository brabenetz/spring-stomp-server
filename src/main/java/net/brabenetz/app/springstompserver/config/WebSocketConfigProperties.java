package net.brabenetz.app.springstompserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Component
@ConfigurationProperties("spring-stomp-server")
public class WebSocketConfigProperties {

    /**
     * The Simple Message Broker destination prefixes which are handled by the WebSocket Stomp-Endpoint.
     */
    private String[] destinationPrefixes = new String[] {"/topic", "/app", "/user"};

    /**
     * The Websocket Endpoint which are handled by the {@link StompEndpointRegistry}.
     * <p>
     * Both variants with and without sockJs are supported.
     */
    private String[] websocketEndpoints = new String[] {"/websocket"};

    public String[] getDestinationPrefixes() {
        return destinationPrefixes;
    }

    public void setDestinationPrefixes(String[] destinationPrefixes) {
        this.destinationPrefixes = destinationPrefixes;
    }

    public String[] getWebsocketEndpoints() {
        return websocketEndpoints;
    }

    public void setWebsocketEndpoints(String[] websocketEndpoints) {
        this.websocketEndpoints = websocketEndpoints;
    }

}
