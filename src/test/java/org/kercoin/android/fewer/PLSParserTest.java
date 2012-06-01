package org.kercoin.android.fewer;

import static org.fest.assertions.Assertions.assertThat;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class PLSParserTest {

    PLSParser parser;

	private void load(final String dataset) {
        try {
			parser = new PLSParser(TestsUtils.loadRelativeTo(M3UParserTest.class, dataset), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// UTF8 unsupported, really?
			e.printStackTrace();
		}
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

    @Test
    public void test2() {
        // given
        load("playlist-di.pls");

        // when
        Channel[] channels = parser.parse();

        // then
        assertThat(channels).isNotNull().hasSize(1);
        assertThat(channels[0].getKey()).isNull();
        assertThat(channels[0].getName()).isEqualTo("Digitally Imported - Hardcore");
        assertThat(channels[0].getStreams()).hasSize(1);
        assertThat(channels[0].getStreams()[0].getName()).isEqualTo("Digitally Imported - Hardcore");
        assertThat(channels[0].getStreams()[0].getSource()).isEqualTo("http://u11aw.di.fm:80/di_hardcore");
        assertThat(channels[0].getStreams()[0].getOptions()).isNotNull().hasSize(0);
    }
}
