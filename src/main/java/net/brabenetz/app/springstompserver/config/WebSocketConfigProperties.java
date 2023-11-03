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
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
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
     * The activation of SocketJs: Set to true if a SockJs-Supported Endpoint should be registered.
     * <p>
     * Default is false, because nowadays (2020) all Browsers support Websockets natively.
     *
     * @see <a href="https://stomp-js.github.io/guide/stompjs/rx-stomp/ng2-stompjs/using-stomp-with-sockjs.html">StompJs: using-stomp-with-sockjs.html</a>
     */
    private boolean withSockJs;

    /**
     * The cache limit to apply for registrations with the broker.
     * <p>
     * This is currently only applied for the destination cache in the subscription registry. The default cache limit there is 1024.
     * <p>
     * For performance-testing read https://programmersought.com/article/58855147686/ <br>
     * ... There is a fatal weakness in SimpleBrokerMessageHandler ... <br>
     * ... If the cache size exceeds the cacheLimit value, the original old The data is cleaned up, and when it is read again ...
     */
    private int brokerRegistryCacheLimit = 1024;

    /**
     * The MessageChannel PoolSize used for incoming messages from WebSocket clients.<br>
     * See {@link WebSocketMessageBrokerConfigurer#configureClientInboundChannel(org.springframework.messaging.simp.config.ChannelRegistration)}
     * <p>
     * By default the channel is backedby a thread pool of size 1.
     */
    private int channelInboundCorePoolSize = 1;

    /**
     * The MessageChannel PoolSize used for outbound messages to WebSocket clients.<br>
     * See {@link WebSocketMessageBrokerConfigurer#configureClientOutboundChannel(org.springframework.messaging.simp.config.ChannelRegistration)}
     * <p>
     * By default the channel is backed by a thread pool of size 1.
     */
    private int channelOutboundCorePoolSize = 1;

    /**
     * The channel PoolSize used to send messages from the application to the message broker.
     * <p>
     * By default, messages from the application to the message broker are sent synchronously, which means application code sending a message will find out if
     * the message cannot be sent through an exception. <br>
     * However, this can be changed if the broker channel is configured here with task executor properties.
     */
    private int channelBrokerCorePoolSize = 1;

    /**
     * Gets the Simple Message Broker destination prefixes which are handled by the WebSocket Stomp-Endpoint.
     *
     * @return the Simple Message Broker destination prefixes which are handled by the WebSocket Stomp-Endpoint
     */
    public String[] getDestinationPrefixes() {
        return destinationPrefixes;
    }

    /**
     * Sets the Simple Message Broker destination prefixes which are handled by the WebSocket Stomp-Endpoint.
     *
     * @param destinationPrefixes the new Simple Message Broker destination prefixes which are handled by the WebSocket Stomp-Endpoint
     */
    public void setDestinationPrefixes(final String... destinationPrefixes) {
        this.destinationPrefixes = destinationPrefixes;
    }

    /**
     * Gets the Websocket Endpoint which are handled by the {@link StompEndpointRegistry}.
     * <p>
     * Both variants with and without sockJs are supported.
     *
     * @return the Websocket Endpoint which are handled by the {@link StompEndpointRegistry}
     */
    public String[] getWebsocketEndpoints() {
        return websocketEndpoints;
    }

    /**
     * Sets the Websocket Endpoint which are handled by the {@link StompEndpointRegistry}.
     * <p>
     * Both variants with and without sockJs are supported.
     *
     * @param websocketEndpoints the new Websocket Endpoint which are handled by the {@link StompEndpointRegistry}
     */
    public void setWebsocketEndpoints(final String... websocketEndpoints) {
        this.websocketEndpoints = websocketEndpoints;
    }

    /**
     * Gets the WebSocketTransportRegistration messageSizeLimit. Optional, The default value is 64K (i.e. 64 * 1024).
     *
     * @return the WebSocketTransportRegistration messageSizeLimit
     */
    public Integer getMessageSizeLimit() {
        return messageSizeLimit;
    }

    /**
     * Sets the WebSocketTransportRegistration messageSizeLimit. Optional, The default value is 64K (i.e. 64 * 1024).
     *
     * @param messageSizeLimit the new WebSocketTransportRegistration messageSizeLimit
     */
    public void setMessageSizeLimit(Integer messageSizeLimit) {
        this.messageSizeLimit = messageSizeLimit;
    }

    /**
     * Gets the WebSocketTransportRegistration sendBufferSizeLimit. Optional, The default value is 512K (i.e. 512 * 1024).
     *
     * @return the WebSocketTransportRegistration sendBufferSizeLimit
     */
    public Integer getSendBufferSizeLimit() {
        return sendBufferSizeLimit;
    }

    /**
     * Sets the WebSocketTransportRegistration sendBufferSizeLimit. Optional, The default value is 512K (i.e. 512 * 1024).
     *
     * @param sendBufferSizeLimit the new WebSocketTransportRegistration sendBufferSizeLimit
     */
    public void setSendBufferSizeLimit(Integer sendBufferSizeLimit) {
        this.sendBufferSizeLimit = sendBufferSizeLimit;
    }

    /**
     * Gets the WebSocketTransportRegistration sendTimeLimit. Optional, The default value is 10 seconds (i.e. 10 * 10000).
     *
     * @return the WebSocketTransportRegistration sendTimeLimit
     */
    public Integer getSendTimeLimit() {
        return sendTimeLimit;
    }

    /**
     * Sets the WebSocketTransportRegistration sendTimeLimit. Optional, The default value is 10 seconds (i.e. 10 * 10000).
     *
     * @param sendTimeLimit the new WebSocketTransportRegistration sendTimeLimit
     */
    public void setSendTimeLimit(Integer sendTimeLimit) {
        this.sendTimeLimit = sendTimeLimit;
    }

    /**
     * Gets the WebSocketTransportRegistration timeToFirstMessage. Optional, The default is set to 60,000 (1 minute).
     *
     * @return the WebSocketTransportRegistration timeToFirstMessage
     */
    public Integer getTimeToFirstMessage() {
        return timeToFirstMessage;
    }

    /**
     * Sets the WebSocketTransportRegistration timeToFirstMessage. Optional, The default is set to 60,000 (1 minute).
     *
     * @param timeToFirstMessage the new WebSocketTransportRegistration timeToFirstMessage
     */
    public void setTimeToFirstMessage(Integer timeToFirstMessage) {
        this.timeToFirstMessage = timeToFirstMessage;
    }

    /**
     * Checks if is activation of SocketJs: Set to true if a SockJs-Supported Endpoint should be registered.
     * <p>
     * Default is false, because nowadays (2020) all Browsers support Websockets natively.
     *
     * @return the activation of SocketJs: Set to true if a SockJs-Supported Endpoint should be registered
     */
    public boolean isWithSockJs() {
        return withSockJs;
    }

    /**
     * Sets the activation of SocketJs: Set to true if a SockJs-Supported Endpoint should be registered.
     * <p>
     * Default is false, because nowadays (2020) all Browsers support Websockets natively.
     *
     * @param withSockJs the new activation of SocketJs: Set to true if a SockJs-Supported Endpoint should be registered
     */
    public void setWithSockJs(boolean withSockJs) {
        this.withSockJs = withSockJs;
    }

    /**
     * Gets the cache limit to apply for registrations with the broker.
     * <p>
     * This is currently only applied for the destination cache in the subscription registry. The default cache limit there is 1024.
     * <p>
     * For performance-testing read https://programmersought.com/article/58855147686/ <br>
     * .. There is a fatal weakness in SimpleBrokerMessageHandler .. <br>
     * .. If the cache size exceeds the cacheLimit value, the original old The data is cleaned up, and when it is read again ..
     *
     * @return the cache limit to apply for registrations with the broker
     */
    public int getBrokerRegistryCacheLimit() {
        return brokerRegistryCacheLimit;
    }

    /**
     * Sets the cache limit to apply for registrations with the broker.
     * <p>
     * This is currently only applied for the destination cache in the subscription registry. The default cache limit there is 1024.
     * <p>
     * For performance-testing read https://programmersought.com/article/58855147686/ <br>
     * .. There is a fatal weakness in SimpleBrokerMessageHandler .. <br>
     * .. If the cache size exceeds the cacheLimit value, the original old The data is cleaned up, and when it is read again ..
     *
     * @param brokerRegistryCacheLimit the new cache limit to apply for registrations with the broker
     */
    public void setBrokerRegistryCacheLimit(int brokerRegistryCacheLimit) {
        this.brokerRegistryCacheLimit = brokerRegistryCacheLimit;
    }

    /**
     * Gets the MessageChannel PoolSize used for incoming messages from WebSocket clients.<br>
     * See {@link WebSocketMessageBrokerConfigurer#configureClientInboundChannel(org.springframework.messaging.simp.config.ChannelRegistration)}
     * <p>
     * By default the channel is backedby a thread pool of size 1.
     *
     * @return the MessageChannel PoolSize used for incoming messages from WebSocket clients
     */
    public int getChannelInboundCorePoolSize() {
        return channelInboundCorePoolSize;
    }

    /**
     * Sets the MessageChannel PoolSize used for incoming messages from WebSocket clients.<br>
     * See {@link WebSocketMessageBrokerConfigurer#configureClientInboundChannel(org.springframework.messaging.simp.config.ChannelRegistration)}
     * <p>
     * By default the channel is backedby a thread pool of size 1.
     *
     * @param channelInboundCorePoolSize the new MessageChannel PoolSize used for incoming messages from WebSocket clients
     */
    public void setChannelInboundCorePoolSize(int channelInboundCorePoolSize) {
        this.channelInboundCorePoolSize = channelInboundCorePoolSize;
    }

    /**
     * Gets the MessageChannel PoolSize used for outbound messages to WebSocket clients.<br>
     * See {@link WebSocketMessageBrokerConfigurer#configureClientOutboundChannel(org.springframework.messaging.simp.config.ChannelRegistration)}
     * <p>
     * By default the channel is backed by a thread pool of size 1.
     *
     * @return the MessageChannel PoolSize used for outbound messages to WebSocket clients
     */
    public int getChannelOutboundCorePoolSize() {
        return channelOutboundCorePoolSize;
    }

    /**
     * Sets the MessageChannel PoolSize used for outbound messages to WebSocket clients.<br>
     * See {@link WebSocketMessageBrokerConfigurer#configureClientOutboundChannel(org.springframework.messaging.simp.config.ChannelRegistration)}
     * <p>
     * By default the channel is backed by a thread pool of size 1.
     *
     * @param channelOutboundCorePoolSize the new MessageChannel PoolSize used for outbound messages to WebSocket clients
     */
    public void setChannelOutboundCorePoolSize(int channelOutboundCorePoolSize) {
        this.channelOutboundCorePoolSize = channelOutboundCorePoolSize;
    }

    /**
     * Gets the channel PoolSize used to send messages from the application to the message broker.
     * <p>
     * By default, messages from the application to the message broker are sent synchronously, which means application code sending a message will find out if
     * the message cannot be sent through an exception. <br>
     * However, this can be changed if the broker channel is configured here with task executor properties.
     *
     * @return the channel PoolSize used to send messages from the application to the message broker
     */
    public int getChannelBrokerCorePoolSize() {
        return channelBrokerCorePoolSize;
    }

    /**
     * Sets the channel PoolSize used to send messages from the application to the message broker.
     * <p>
     * By default, messages from the application to the message broker are sent synchronously, which means application code sending a message will find out if
     * the message cannot be sent through an exception. <br>
     * However, this can be changed if the broker channel is configured here with task executor properties.
     *
     * @param channelBrokerCorePoolSize the new channel PoolSize used to send messages from the application to the message broker
     */
    public void setChannelBrokerCorePoolSize(int channelBrokerCorePoolSize) {
        this.channelBrokerCorePoolSize = channelBrokerCorePoolSize;
    }

}
