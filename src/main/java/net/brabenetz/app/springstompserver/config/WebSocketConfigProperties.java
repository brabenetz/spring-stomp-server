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
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

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

    /**
     * The WebSocketTransportRegistration messageSizeLimit. Optional, The default value is 64K (i.e. 64 * 1024).
     *
     * @see WebSocketTransportRegistration#setMessageSizeLimit(int)
     */
    private Integer messageSizeLimit;

    /**
     * The WebSocketTransportRegistration sendBufferSizeLimit. Optional, The default value is 512K (i.e. 512 * 1024).
     *
     * @see WebSocketTransportRegistration#setSendBufferSizeLimit(int)
     */
    private Integer sendBufferSizeLimit;

    /**
     * The WebSocketTransportRegistration sendTimeLimit. Optional, The default value is 10 seconds (i.e. 10 * 10000).
     *
     * @see WebSocketTransportRegistration#setSendTimeLimit(int)
     */
    private Integer sendTimeLimit;

    /**
     * The WebSocketTransportRegistration timeToFirstMessage. Optional, The default is set to 60,000 (1 minute).
     *
     * @see WebSocketTransportRegistration#setTimeToFirstMessage(int)
     */
    private Integer timeToFirstMessage;

    /**
     * Set to true if a SockJs-Supported Endpoint should be registered.
     * <p>
     * Default is false, because nowadays (2020) all Browsers support Websockets natively.
     *
     * @see <a href="https://stomp-js.github.io/guide/stompjs/rx-stomp/ng2-stompjs/using-stomp-with-sockjs.html">StompJs: using-stomp-with-sockjs.html</a>
     */
    private boolean withSockJs = false;

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

    public Integer getMessageSizeLimit() {
        return messageSizeLimit;
    }

    public void setMessageSizeLimit(Integer messageSizeLimit) {
        this.messageSizeLimit = messageSizeLimit;
    }

    public Integer getSendBufferSizeLimit() {
        return sendBufferSizeLimit;
    }

    public void setSendBufferSizeLimit(Integer sendBufferSizeLimit) {
        this.sendBufferSizeLimit = sendBufferSizeLimit;
    }

    public Integer getSendTimeLimit() {
        return sendTimeLimit;
    }

    public void setSendTimeLimit(Integer sendTimeLimit) {
        this.sendTimeLimit = sendTimeLimit;
    }

    public Integer getTimeToFirstMessage() {
        return timeToFirstMessage;
    }

    public void setTimeToFirstMessage(Integer timeToFirstMessage) {
        this.timeToFirstMessage = timeToFirstMessage;
    }

    public boolean isWithSockJs() {
        return withSockJs;
    }

    public void setWithSockJs(boolean withSockJs) {
        this.withSockJs = withSockJs;
    }

}
