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


