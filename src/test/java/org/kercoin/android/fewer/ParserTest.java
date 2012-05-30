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

    @Test
    public void shouldFind_supportVLCOPT() {
        // given
        load("playlist-vlc-opt-20120529.m3u");

        // when
        Channel[] channels = parser.parse();

        // then
        assertThat(channels).isNotNull().hasSize(4);
        final Channel europe1 = channels[0];
        assertThat(europe1.getKey()).isEqualTo("10001");
        assertThat(europe1.getName()).isEqualTo("Europe 1");
        assertThat(europe1.getStreams()).hasSize(1);
        final String europe1Flux = europe1.getStreams()[0].getSource();
        assertThat(europe1Flux).isEqualTo("rtsp://mafreebox.freebox.fr/fbxtv_pub/stream?namespace=1&service=100004");
        final Option[] europe1Options = europe1.getStreams()[0].getOptions();
        assertThat(europe1Options).hasSize(3);
        assertThat(europe1Options[0].hasValue()).isFalse();
        assertThat(europe1Options[0].getName()).isEqualTo("ts-es-id-pid");
        assertThat(europe1Options[1].hasValue()).isFalse();
        assertThat(europe1Options[1].getName()).isEqualTo("no-video");
        assertThat(europe1Options[2].hasValue()).isTrue();
        assertThat(europe1Options[2].getName()).isEqualTo("audio-track-id");
        assertThat(europe1Options[2].getValue()).isEqualTo("1001");
        logger.debug(Arrays.toString(channels));
    }

}
