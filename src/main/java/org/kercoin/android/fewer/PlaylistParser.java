package org.kercoin.android.fewer;

import java.io.InputStream;

public interface PlaylistParser {

	void open(InputStream data);

	Channel[] parse();

}
