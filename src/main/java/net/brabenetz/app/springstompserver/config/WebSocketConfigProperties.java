/*-
 * #%L
 * Spring Stomp Server
 * ===============================================================
 * Copyright (C) 2020 Brabenetz Harald, Austria
 * ===============================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package net.brabenetz.app.springstompserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Additional Websocket Stomp Properties use in the {@link WebSocketConfig}.
 */
@Component
@ConfigurationProperties("spring-stomp-server")
@SuppressWarnings("PMD.DataClass")
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

    public void setDestinationPrefixes(final String... destinationPrefixes) {
        this.destinationPrefixes = destinationPrefixes;
    }

    public String[] getWebsocketEndpoints() {
        return websocketEndpoints;
    }

    public void setWebsocketEndpoints(final String... websocketEndpoints) {
        this.websocketEndpoints = websocketEndpoints;
    }

}
