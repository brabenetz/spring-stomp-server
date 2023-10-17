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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * The Websocket Stomp Configuration.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private WebSocketConfigProperties properties;

    @Autowired
    private TaskScheduler messageBrokerTaskScheduler;

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(properties.getDestinationPrefixes())
                .setTaskScheduler(messageBrokerTaskScheduler);
        // By default, messages from the application to the message broker are sentsynchronously.
        registry.configureBrokerChannel().taskExecutor().corePoolSize(properties.getChannelBrokerCorePoolSize());
        // The default cache limit there is 1024.
        registry.setCacheLimit(properties.getBrokerRegistryCacheLimit());
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint(properties.getWebsocketEndpoints()); // normal WebSocket

        if (properties.isWithSockJs()) {
            registry.addEndpoint(properties.getWebsocketEndpoints()).withSockJS(); // SockJs Legacy support.
        }
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        if (properties.getMessageSizeLimit() != null) {
            registry.setMessageSizeLimit(properties.getMessageSizeLimit()); // The default value is 64K (i.e. 64 * 1024).
        }
        if (properties.getSendBufferSizeLimit() != null) {
            registry.setSendBufferSizeLimit(properties.getSendBufferSizeLimit()); // The default value is 512K (i.e. 512 * 1024).
        }
        if (properties.getSendTimeLimit() != null) {
            registry.setSendTimeLimit(properties.getSendTimeLimit()); // The default value is 10 seconds (i.e. 10 * 10000).
        }
        if (properties.getTimeToFirstMessage() != null) {
            registry.setTimeToFirstMessage(1000); // The default is set to 60,000 (1 minute).
        }
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // By default the channel is backed by a thread pool of size 1.
        registration.taskExecutor().corePoolSize(properties.getChannelInboundCorePoolSize());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        // By default the channel is backed by a thread pool of size 1.
        registration.taskExecutor().corePoolSize(properties.getChannelOutboundCorePoolSize());
    }

}
