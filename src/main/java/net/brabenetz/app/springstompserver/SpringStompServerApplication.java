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

import net.brabenetz.app.springstompserver.config.WebSocketConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Simple Spring Stomp Server as described in <a href="https://spring.io/guides/gs/messaging-stomp-websocket/">Spring Websocket Guide</a> .
 */
@SpringBootApplication
@EnableConfigurationProperties
@SuppressWarnings("PMD.UseUtilityClass")
public class SpringStompServerApplication {

    /**
     * Spring boot start.
     *
     * @param args Override Conifg-Properties. See: {@link WebSocketConfigProperties}.
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        if (System.getProperty("spring.config.name") == null) {
            System.setProperty("spring.config.name", "spring-stomp-server");
        }

        new SpringApplicationBuilder(SpringStompServerApplication.class)
                .bannerMode(Mode.OFF)
                .parent(new SpringApplicationBuilder(AppPrepare.class)
                        .banner(new SpringStompServerBanner())
                        .web(WebApplicationType.NONE)
                        .run(args))
                .run(args);
    }

    /**
     * AppPrepare to run before SpringStompServerApplication starts.
     */
    public static class AppPrepare implements InitializingBean {
        private static final Logger LOG = LoggerFactory.getLogger(AppPrepare.class);

        @Override
        public void afterPropertiesSet() throws Exception {
            LOG.info("Current Java-Version: {}; OS: {}; Timezone: {}; Lang: {}",
                    System.getProperty("java.version"),
                    System.getProperty("os.name"),
                    System.getProperty("user.timezone"),
                    System.getProperty("user.language"));
        }
    }

}
