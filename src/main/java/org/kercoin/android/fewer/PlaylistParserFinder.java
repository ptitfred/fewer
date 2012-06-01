package org.kercoin.android.fewer;


public class PlaylistParserFinder {

	public PlaylistParser fromMimeType(String mimeType) {
		if ("audio/x-mpegurl".equals(mimeType)) {
			return new M3UParser();
		}
		if ("audio/x-scpls".equals(mimeType)) {
			return new PLSParser();
		}
		return null;
	}

}
