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
package net.brabenetz.app.springstompserver.testtools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class WebSocketStompSessionHandler extends StompSessionHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketStompSessionHandler.class);

    private String name;

    public WebSocketStompSessionHandler(String name) {
        this.name = name;
    }

    @Override
    public void afterConnected(StompSession stompSession, StompHeaders connectedHeaders) {
        LOG.info(name + " afterConnected: " + stompSession.getSessionId());
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        LOG.info(name + " StompSessionHandler got new Payload: " + payload);
    }

    @Override
    public void handleException(StompSession stompSession, @Nullable StompCommand command,
            StompHeaders headers, byte[] payload, Throwable e) {
        LOG.error(name + " handleException {}", e.getMessage(), e);
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable e) {
        LOG.error(name + " handleTransportError {}", e.getMessage(), e);
    }


}
