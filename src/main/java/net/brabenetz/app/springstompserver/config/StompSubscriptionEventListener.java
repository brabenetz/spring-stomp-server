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
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;

@Component
public class StompSubscriptionEventListener implements ApplicationListener<SessionSubscribeEvent> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private WebSocketInitLoadConfigProperties initLoadConfigProperties;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void onApplicationEvent(final SessionSubscribeEvent event) {
        if (StringUtils.isEmpty(initLoadConfigProperties.getProxyUrl())) {
            // nothing to proxy
            return;
        }

        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        String destination = sha.getDestination();

        Matcher userDestMatcher = initLoadConfigProperties.getDestinationPatterns().matcher(destination);
        if (userDestMatcher.matches()) {
            String proxyUrl = getProxyUrl(userDestMatcher, initLoadConfigProperties.getProxyUrl());
            // TODO handle Headers from sha.getMessageHeaders()?
            ResponseEntity<String> response = restTemplate.getForEntity(proxyUrl, String.class);
            String body = response.getBody();
            Map<String, ?> headers = null; // TODO handle Headers from Response?
            messagingTemplate.send(destination, MessageBuilder.withPayload(body.getBytes(StandardCharsets.UTF_8)).copyHeaders(headers).build());
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

