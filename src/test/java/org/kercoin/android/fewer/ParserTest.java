package org.kercoin.android.fewer;

import static org.fest.assertions.Assertions.assertThat;

import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserTest {

    private static final Logger logger = LoggerFactory .getLogger(ParserTest.class);

    Parser parser;

    private void load(final String dataset) {
        String prefix = "org/kercoin/android/fewer/";
        InputStream is = getClass().getClassLoader().getResourceAsStream(prefix + dataset);
        parser = new Parser(is);
    }

    @Test
    public void shouldFind() {
        // given
        load("playlist-short-20120529.m3u");

        // when
        Channel[] channels = parser.parse();

        // then
        assertThat(channels).isNotNull().hasSize(10);
        assertThat(channels[0].getKey()).isEqualTo("2");
        assertThat(channels[0].getName()).isEqualTo("France 2");
    }

}
