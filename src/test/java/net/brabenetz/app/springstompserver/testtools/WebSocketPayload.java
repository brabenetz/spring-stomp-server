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

import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.List;
import java.util.Map;

public class WebSocketPayload<T> {
    LinkedCaseInsensitiveMap<List<String>> headers = new LinkedCaseInsensitiveMap<>();
    private final T body;

    public WebSocketPayload(StompHeaders headers, T body) {
        this.headers.putAll(headers);
        this.body = body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public T getBody() {
        return body;
    }
}
