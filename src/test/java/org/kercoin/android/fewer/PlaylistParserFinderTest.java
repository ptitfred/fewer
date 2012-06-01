package org.kercoin.android.fewer;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;


public class PlaylistParserFinderTest {

	PlaylistParserFinder finder;

	@Before
	public void setup() {
		finder = new PlaylistParserFinder();
	}

	@Test
	public void testM3UContentTypes() {
		assertThat(finder.fromMimeType("audio/x-mpegurl")).isInstanceOf(M3UParser.class);
	}

	@Test
	public void testPLSContentTypes() {
		assertThat(finder.fromMimeType("audio/x-scpls")).isInstanceOf(PLSParser.class);
	}

}
