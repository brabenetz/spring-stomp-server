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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

import java.util.regex.Pattern;

/**
 * Websocket Stomp Properties use in the {@link WebSocketConfig}.
 */
@Component
@ConfigurationProperties("spring-stomp-server.init-load")
@SuppressWarnings("PMD.DataClass")
@Validated
public class WebSocketInitLoadConfigProperties {

    /**
     * The Websocket Subscription destination pattern like "/user/00001111-2222-3333-4444-555566667777/topic/...".
     * <p>
     * Default is "^/user/[^/]+/topic/(.*)$"
     */
    private Pattern destinationPatterns = Pattern.compile("^/user/[^/]+/topic/(.*)$");

    /**
     * The proxy url from where the init load should be get, like: "http://localhost:8181/my-fe-cmp/api/${group-1}".
     * <p>
     * Only GET requests are supported.<br>
     * ${group-1}, ${group-2}, ${group-3}, ... ${group-X} reference to the group-pattern from {@link #getDestinationPatterns()}.<br>
     * The Proxy-Server can then be a Mock-Server like https://wiremock.org/
     */
    @NotEmpty
    private String proxyUrl;

    /**
     * Gets the Websocket Subscription destination pattern like "/user/00001111-2222-3333-4444-555566667777/topic/..".
     * <p>
     * Default is "^/user/[^/]+/topic/(.*)$".
     *
     * @return the Websocket Subscription destination pattern like "/user/00001111-2222-3333-4444-555566667777/topic/
     */
    public Pattern getDestinationPatterns() {
        return destinationPatterns;
    }

    /**
     * Sets the Websocket Subscription destination pattern like "/user/00001111-2222-3333-4444-555566667777/topic/..".
     * <p>
     * Default is "^/user/[^/]+/topic/(.*)$".
     *
     * @param destinationPatterns the new Websocket Subscription destination pattern like "/user/00001111-2222-3333-4444-555566667777/topic/
     */
    public void setDestinationPatterns(Pattern destinationPatterns) {
        this.destinationPatterns = destinationPatterns;
    }

    /**
     * Gets the proxy url from where the init load should be get, like: "http://localhost:8181/my-fe-cmp/api/${group-1}".
     * <p>
     * Only GET requests are supported.<br>
     * ${group-1}, ${group-2}, ${group-3}, .. ${group-X} reference to the group-pattern from {@link #getDestinationPatterns()}.<br>
     * The Proxy-Server can then be a Mock-Server like https://wiremock.org/.
     *
     * @return the proxy url from where the init load should be get, like: "http://localhost:8181/my-fe-cmp/api/${group-1}"
     */
    public String getProxyUrl() {
        return proxyUrl;
    }

    /**
     * Sets the proxy url from where the init load should be get, like: "http://localhost:8181/my-fe-cmp/api/${group-1}".
     * <p>
     * Only GET requests are supported.<br>
     * ${group-1}, ${group-2}, ${group-3}, .. ${group-X} reference to the group-pattern from {@link #getDestinationPatterns()}.<br>
     * The Proxy-Server can then be a Mock-Server like https://wiremock.org/.
     *
     * @param proxyUrl the new proxy url from where the init load should be get, like: "http://localhost:8181/my-fe-cmp/api/${group-1}"
     */
    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

}
