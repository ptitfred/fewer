package org.kercoin.android.fewer;

import static org.fest.assertions.Assertions.assertThat;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class ParserTest {

    Parser parser;

    @Before
    public void loadData() {
        final String dataset = "org/kercoin/android/fewer/playlist-short-20120529.m3u";
        InputStream is = getClass().getClassLoader().getResourceAsStream(dataset);
        parser = new Parser(is);
    }

    @Test
    public void shouldFind() {
        // when
        Channel[] channels = parser.parse();

        // then
        assertThat(channels).isNotNull().hasSize(10);
        assertThat(channels[0].getKey()).isEqualTo("2");
        assertThat(channels[0].getName()).isEqualTo("France 2");
    }

}
