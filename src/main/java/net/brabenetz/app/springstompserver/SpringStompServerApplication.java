package net.brabenetz.app.springstompserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SpringStompServerApplication {
    private static final Logger LOG = LoggerFactory.getLogger(SpringStompServerApplication.class);

    public static void main(String[] args) {

        new SpringApplicationBuilder(SpringStompServerApplication.class)
                .bannerMode(Mode.OFF)
                .parent(new SpringApplicationBuilder(AppPrepare.class)
                        .banner(new SpringStompServerBanner())
                        .web(WebApplicationType.NONE)
                        .run(args))
                .run(args);
	}

    public static class AppPrepare implements InitializingBean {
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
