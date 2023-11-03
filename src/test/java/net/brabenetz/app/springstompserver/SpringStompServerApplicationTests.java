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
package net.brabenetz.app.springstompserver;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import net.brabenetz.app.springstompserver.config.WebSocketInitLoadConfigProperties;
import net.brabenetz.app.springstompserver.testtools.WebSocketPayload;
import net.brabenetz.app.springstompserver.testtools.WebSocketStompSessionHandler;
import net.brabenetz.app.springstompserver.testtools.WebSocketUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSession.Subscription;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableWireMock(@ConfigureWireMock(name = "wiremock", property = "wiremock.baseurl"))
class SpringStompServerApplicationTests {

    private static final Logger LOG = LoggerFactory.getLogger(SpringStompServerApplicationTests.class);

    @LocalServerPort
    private int port;

    @InjectWireMock("wiremock")
    private WireMockServer wiremock;

    @Autowired
    private Environment env;

    @Autowired
    private WebSocketInitLoadConfigProperties webSocketInitLoadConfigProperties;

    private StompSession currentSession;
    private List<Subscription> subscriptions;

    @BeforeEach
    public void init() {
        currentSession = null;
        subscriptions = new ArrayList<>();

        String newProxyUrl = webSocketInitLoadConfigProperties.getProxyUrl()
                .replaceAll("http://localhost:8181", env.getProperty("wiremock.baseurl"));
        webSocketInitLoadConfigProperties.setProxyUrl(newProxyUrl);

    }

    @AfterEach
    public void cleanupSession() {
        subscriptions.forEach(consumer -> consumer.unsubscribe());
        if (currentSession != null) {
            currentSession.disconnect();
        }
    }

    @Test
    public void testStompWebsocketEndpoint() throws Exception {
        String clientName = "STOMP-WebSocket";
        String websocketEndpoint = "ws://localhost:" + port + "/websocket";
        WebSocketStompClient stompClient = WebSocketUtils.createStompClient();

        testStompWebsocketEndpoint(clientName, websocketEndpoint, stompClient);
    }

    @Test
    public void testStompWebsocketEndpointWithSockJs() throws Exception {
        String clientName = "SOCKJS-WebSocket";
        String websocketEndpoint = "http://localhost:" + port + "/websocket";
        WebSocketStompClient stompClient = WebSocketUtils.createStompOverSocketJsClient();

        testStompWebsocketEndpoint(clientName, websocketEndpoint, stompClient);
    }

    private void testStompWebsocketEndpoint(String clientName, String websocketEndpoint, WebSocketStompClient client)
            throws Exception {
        // connect to the Websocket
        WebSocketStompSessionHandler sessionHandler = new WebSocketStompSessionHandler(clientName);
        ListenableFuture<StompSession> stompClientConnection = client.connect(websocketEndpoint, sessionHandler);
        // wait until Session is created
        currentSession = stompClientConnection.get(1, TimeUnit.SECONDS);

        // container to collect and sync the result
        CountDownLatch doneSignal = new CountDownLatch(1);
        List<WebSocketPayload<String>> messages = new ArrayList<>();

        // Register Subscription
        subscriptions.add(currentSession.subscribe("/topic/test/1234", WebSocketUtils.createStompFrameHandler((StompHeaders headers, String payload) -> {
            LOG.info(clientName + " FrameHandler got new Payload: " + payload);
            messages.add(new WebSocketPayload<>(headers, payload));
            doneSignal.countDown();
            headers.forEach((k, v) -> {
                LOG.info(String.format(clientName + " HEADER: %s = %s", k, v));
                // Example Output:
                // STOMP-WebSocket HEADER: destination = [/topic/test/1234]
                // STOMP-WebSocket HEADER: content-type = [application/json]
                // STOMP-WebSocket HEADER: subscription = [0]
                // STOMP-WebSocket HEADER: message-id = [6ff3affb-eacd-ea53-b403-60dfdf8fcd88-0]
                // STOMP-WebSocket HEADER: content-length = [6]
            });
        })));

        // Send Message
        currentSession.send("/topic/test/1234", "test");

        // Wait until Message is consumed
        doneSignal.await(1, TimeUnit.SECONDS);
        assertThat(doneSignal.getCount()).describedAs("CountDownLatch").isEqualTo(0);

        // Verify that consumed Message Payload.
        assertThat(messages).hasSize(1);
        assertThat(messages.get(0).getBody()).isEqualTo("test");
        assertThat(messages.get(0).getHeaders().get("destination")).containsExactly("/topic/test/1234");
        assertThat(messages.get(0).getHeaders().get("content-type")).containsExactly("application/json");
    }

    @Test
    public void testStompWebsocketEndpointWithInitialLoad() throws Exception {
        // prepare Wiremock:
        wiremock.stubFor(WireMock.get("/mocked-init-load/test/1234").willReturn(
                WireMock.ok("\"test\"")
                        .withHeader("content-type", "application/json")));

        String clientName = "STOMP-WebSocket";
        String websocketEndpoint = "ws://localhost:" + port + "/websocket";
        WebSocketStompClient stompClient = WebSocketUtils.createStompClient();

        // connect to the Websocket
        WebSocketStompSessionHandler sessionHandler = new WebSocketStompSessionHandler(clientName);
        WebSocketHttpHeaders connectionHeaders = new WebSocketHttpHeaders();
        connectionHeaders.add("x-test", "Dummy Connection Header");
        ListenableFuture<StompSession> stompClientConnection = stompClient.connect(websocketEndpoint, connectionHeaders, sessionHandler);
        // wait until Session is created
        currentSession = stompClientConnection.get(1, TimeUnit.SECONDS);

        // container to collect and sync the result
        CountDownLatch doneSignal = new CountDownLatch(1);
        List<WebSocketPayload<String>> messages = new ArrayList<>();

        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add("x-stomp-header", "Dummy Subscr. Header");
        stompHeaders.setDestination("/user/123456/topic/test/1234");

        subscriptions
                .add(currentSession.subscribe(stompHeaders, WebSocketUtils.createStompFrameHandler((StompHeaders headers, String payload) -> {
            LOG.info(clientName + " FrameHandler got new Payload: " + payload);
            messages.add(new WebSocketPayload<>(headers, payload));
            doneSignal.countDown();
            headers.forEach((k, v) -> {
                LOG.info(String.format(clientName + " HEADER: %s = %s", k, v));
                // Example Output:
                // STOMP-WebSocket HEADER: destination = [/topic/test/1234]
                // STOMP-WebSocket HEADER: content-type = [application/json]
                // STOMP-WebSocket HEADER: subscription = [0]
                // STOMP-WebSocket HEADER: message-id = [6ff3affb-eacd-ea53-b403-60dfdf8fcd88-0]
                // STOMP-WebSocket HEADER: content-length = [6]
            });
        })));

        // Wait until Message is consumed
        doneSignal.await(1, TimeUnit.SECONDS);
        assertThat(doneSignal.getCount()).describedAs("CountDownLatch").isEqualTo(0);

        // Verify that consumed Message Payload.
        assertThat(messages).hasSize(1);
        assertThat(messages.get(0).getBody()).isEqualTo("test");
        Map<String, List<String>> headers = messages.get(0).getHeaders();
        assertThat(headers.get("destination")).containsExactly("/user/123456/topic/test/1234");
        assertThat(headers.get("content-type")).containsExactly("application/json");
    }
}
