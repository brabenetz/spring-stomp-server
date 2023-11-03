/*-
 * #%L
 * Spring Stomp Server
 * ===============================================================
 * Copyright (C) 2020 - 2023 Brabenetz Harald, Austria
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
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Listen on all subscriptions, and if a subscription starts with {@link WebSocketInitLoadConfigProperties#getDestinationPatterns()}, then directly send an
 * initial load from {@link WebSocketInitLoadConfigProperties#getProxyUrl()} to the subscribed topic.
 */
@Component
public class StompSubscriptionEventListener implements ApplicationListener<SessionSubscribeEvent> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private WebSocketInitLoadConfigProperties initLoadConfigProperties;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void onApplicationEvent(final SessionSubscribeEvent event) {
        if (ObjectUtils.isEmpty(initLoadConfigProperties.getProxyUrl())) {
            // nothing to proxy
            return;
        }

        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        String destination = sha.getDestination();
        @SuppressWarnings("unchecked")
        Map<String, List<String>> nativHeaders = (Map<String, List<String>>) sha.getHeader(NativeMessageHeaderAccessor.NATIVE_HEADERS);

        Matcher userDestMatcher = initLoadConfigProperties.getDestinationPatterns().matcher(destination);
        if (userDestMatcher.matches()) {
            // call proxy-server
            String proxyUrl = getProxyUrl(userDestMatcher, initLoadConfigProperties.getProxyUrl());
            HttpHeaders headers = new HttpHeaders();
            nativHeaders.forEach((k, v) -> headers.addAll(k, v));
            ResponseEntity<byte[]> response = restTemplate.exchange(proxyUrl, HttpMethod.GET, new HttpEntity<>(headers), byte[].class);

            // relay response to the subscribed subject:
            HttpHeaders respHeaders = response.getHeaders();
            Message<byte[]> stompMessage = MessageBuilder
                    .withPayload(response.getBody())
                    .copyHeaders(Collections.singletonMap(NativeMessageHeaderAccessor.NATIVE_HEADERS, respHeaders))
                    .build();
            messagingTemplate.send(destination, stompMessage);
        }
    }

    private String getProxyUrl(final Matcher userDestMatcher, final String proxyUrlTemplate) {
        userDestMatcher.groupCount();
        String proxyUrl = proxyUrlTemplate;
        for (int i = 1; i <= userDestMatcher.groupCount(); i++) {
            proxyUrl = proxyUrl.replaceAll("\\$\\{group-" + i + "\\}", userDestMatcher.group(i));
        }
        return proxyUrl;
    }

}
