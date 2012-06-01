package org.kercoin.android.fewer;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class PLSParserTest {

    PLSParser parser;

	private void load(final String dataset) {
        parser = new PLSParser(TestsUtils.loadRelativeTo(M3UParserTest.class, dataset));
    }

    @Test
    public void shouldFind() {
        // given
        load("playlist-short-20120529.pls");

        // when
        Channel[] channels = parser.parse();

        // then
        assertThat(channels).isNotNull().hasSize(3);
        assertThat(channels[0].getKey()).isNull();
        assertThat(channels[0].getName()).isEqualTo("My Favorite Online Radio");
        assertThat(channels[0].getStreams()).hasSize(1);
        assertThat(channels[0].getStreams()[0].getName()).isEqualTo("My Favorite Online Radio");
        assertThat(channels[0].getStreams()[0].getSource()).isEqualTo("http://streamexample.com:80");
        assertThat(channels[0].getStreams()[0].getOptions()).isNotNull().hasSize(0);
        assertThat(channels[1].getStreams()[0].getSource()).isEqualTo("http://example.com/song.mp3?bitrate=96kbps");
    }
}
