package org.kercoin.android.fewer;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public interface PlaylistParser {

	PlaylistParser open(InputStream data, String charset) throws UnsupportedEncodingException;

	Channel[] parse();

}
