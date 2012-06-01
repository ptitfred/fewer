package org.kercoin.android.fewer;

import java.io.InputStream;

public interface PlaylistParser {

	PlaylistParser open(InputStream data);

	Channel[] parse();

}
