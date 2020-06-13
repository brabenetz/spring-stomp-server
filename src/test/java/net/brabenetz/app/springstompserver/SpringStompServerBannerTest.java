package net.brabenetz.app.springstompserver;

import org.junit.jupiter.api.Test;

public class SpringStompServerBannerTest {

    @Test
    public void testPrintBanner() throws Exception {
        new SpringStompServerBanner().printBanner(null, null, System.out);
        // Must be validated manually -> assertThat(output).looksNice()
    }

}
