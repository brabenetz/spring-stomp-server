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

import org.springframework.boot.Banner;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.util.Arrays;

/**
 * Ascii-Art Banner implementation for Spring-Stomp-Server.
 */
public class SpringStompServerBanner implements Banner {

    // example from http://patorjk.com/software/taag/#p=display&f=Big&t=Stomp-Server
    private static final String[] BANNER = {"",
            // Big Variant
            "   _____ _                              _____                          ",
            "  / ____| |                            / ____|                         ",
            " | (___ | |_ ___  _ __ ___  _ __ _____| (___   ___ _ ____   _____ _ __ ",
            "  \\___ \\| __/ _ \\| '_ ` _ \\| '_ \\______\\___ \\ / _ \\ '__\\ \\ / / _ \\ '__|",
            "  ____) | || (_) | | | | | | |_) |     ____) |  __/ |   \\ V /  __/ |   ",
            " |_____/ \\__\\___/|_| |_| |_| .__/     |_____/ \\___|_|    \\_/ \\___|_|   ",
            " _________________________ | | ________________________________________",
            " ------------------------- |_| ----------------------------------------",
    };

    private static final String APP_NAME = " :: Stomp-Server ::";

    private static final int STRAP_LINE_SIZE = Arrays.stream(BANNER).map(String::length).max(Integer::compare).orElse(0);

    @Override
    @SuppressWarnings("PMD.UseStringBufferForStringAppends")
    public void printBanner(final Environment environment, final Class<?> sourceClass, final PrintStream printStream) {
        for (String line : BANNER) {
            printStream.println(line);
        }
        String version = SpringStompServerApplication.class.getPackage().getImplementationVersion();
//        String version = "1.0-SNAPSHOT-2019-09-20T20:00:02Z"; // for manual testing
        version = (version != null) ? " v" + version : "";
        StringBuilder padding = new StringBuilder();
        while (padding.length() < STRAP_LINE_SIZE - (version.length() + APP_NAME.length())) {
            padding.append(' ');
        }

        printStream.println(AnsiOutput.toString(AnsiColor.GREEN, APP_NAME, AnsiColor.DEFAULT, padding.toString(),
                AnsiStyle.FAINT, version));
        printStream.println();
    }

}
