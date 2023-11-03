# Configuration

"java -jar spring-stomp-server.jar" is a valid call with following default behaviors:

The following config matches the default values which can be overwritten

``` yaml
server.port: 8182
spring-stomp-server:
  websocket-endpoints:
  - "/websocket"
  destination-prefixes:
  - "/topic"
  - "/app"
  - "/user"
  with-sock-js: false
  message-size-limit: 65536       # 64k
  send-buffer-size-limit: 524288  # 512k
  send-time-limit: 10000          # 10s
  time-to-first-message: 60000    # 1m
  broker-registry-cache-limit: 1024
  channel-inbound-core-pool-size: 1
  channel-outbound-core-pool-size: 1
  channel-broker-core-pool-size: 1
  init-load:
    destination-patterns: "^/user/[^/]+/topic/(.*)"
    proxy-url: "http://localhost:8181/mocked-init-load/${group-1}"
```

## server.port

The Server HTTP port on which the Spring-Stomp-Server should run.

Default is 8182

See also https://docs.spring.io/spring-boot/docs/2.4.0-M1/reference/html/appendix-application-properties.html#server-properties

## websocket-endpoints

The default endpoints is "/websocket".

See https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/socket/config/annotation/StompEndpointRegistry.html#addEndpoint-java.lang.String...-

## destination-prefixes

The default stomp destination prefixes are "/topic", "/app", "/user".

See https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/messaging/simp/config/MessageBrokerRegistry.html#enableSimpleBroker-java.lang.String...-

## with-sock-js

If true, it enables an additional Stomp-Listener for Stock-Js.

Default is false. 

See https://stomp-js.github.io/guide/stompjs/rx-stomp/ng2-stompjs/using-stomp-with-sockjs.html \
See https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/socket/config/annotation/StompWebSocketEndpointRegistration.html#withSockJS-- 

## message-size-limit

Configure the maximum size of an inbound sub-protocol message, such as a STOMP frame which may be aggregated from multiple WebSocket messages.

The default value is 64K (i.e. 64 * 1024). 

See https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/socket/config/annotation/WebSocketTransportRegistration.html#setMessageSizeLimit-int-

## send-buffer-size-limit

Configure the maximum amount of data to buffer when sending messages to a WebSocket session, or an HTTP response when SockJS fallback option are in use. 

The default value is 512K (i.e. 512 * 1024).

See https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/socket/config/annotation/WebSocketTransportRegistration.html#setSendBufferSizeLimit-int-

## send-time-limit

Configure a time limit (in milliseconds) for the maximum amount of a time allowed when sending messages to a WebSocket session or writing to an HTTP response when SockJS fallback option are in use. 

The default value is 10 seconds (i.e. 10 * 10000).

See https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/socket/config/annotation/WebSocketTransportRegistration.html#setSendTimeLimit-int-

## time-to-first-message

Set the maximum time allowed in milliseconds after the WebSocket connection is established and before the first subscribe-protocol message is received. 

By default this is set to 60,000 (1 minute).

See  https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/socket/config/annotation/WebSocketTransportRegistration.html#setTimeToFirstMessage-int-


## broker-registry-cache-limit

The cache limit to apply for registrations with the broker.

This is currently only applied for the destination cache in the subscription registry. The default cache limit there is 1024.

For performance-testing read https://programmersought.com/article/58855147686/ \
... There is a fatal weakness in SimpleBrokerMessageHandler ... \
... If the cache size exceeds the cacheLimit value, the original old The data is cleaned up, and when it is read again ...

## channel-inbound-core-pool-size

The MessageChannel PoolSize used for incoming messages from WebSocket clients. \
See WebSocketMessageBrokerConfigurer#configureClientInboundChannel(...)

By default the channel is backedby a thread pool of size 1.

## channel-outbound-core-pool-size

The MessageChannel PoolSize used for outbound messages to WebSocket clients. \
See {WebSocketMessageBrokerConfigurer#configureClientOutboundChannel(...)

By default the channel is backed by a thread pool of size 1.

## channel-broker-core-pool-size

The channel PoolSize used to send messages from the application to the message broker.

By default, messages from the application to the message broker are sent synchronously, which means application code sending a message will find out if
the message cannot be sent through an exception. \
However, this can be changed if the broker channel is configured here with task executor properties.

## init-load:

The init-load is optional, if not proxy-url is configured, then this feature is disabled.

## init-load.destinationPatterns

The Websocket Subscription destination pattern like "/user/00001111-2222-3333-4444-555566667777/topic/...".

Default: "^/user/[^/]+/topic/(.*)$"

## init-load.proxy-url

The proxy url from where the init load should be get, like: "http://localhost:8181/my-mock-endpoint/${group-1}".

Only GET requests are supported. \
${group-1}, ${group-2}, ${group-3}, ... ${group-X} reference to the group-pattern from getDestinationPatterns().
The Proxy-Server can then be a Mock-Server like https://wiremock.org/. With WireMock you can change the mocks on the fly via API for automatic tests.


