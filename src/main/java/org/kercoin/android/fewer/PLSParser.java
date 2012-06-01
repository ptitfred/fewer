package org.kercoin.android.fewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PLSParser implements PlaylistParser {

	private final BufferedReader reader;

	private static class Token {
		static final String PLAYLIST = "[playlist]";
		static final String NUMBER_OF_ENTRIES = "NumberOfEntries";
		static final String FILE = "File";
		static final String TITLE = "Title";
		static final String LENGTH = "Length";
		static final String VERSION = "Version";
		static final String SEPARATOR = "=";
	}
	public PLSParser(InputStream data) {
		this(new BufferedReader(new InputStreamReader(data)));
	}

	public PLSParser(BufferedReader bufferedReader) {
		this.reader = bufferedReader;
	}

	private static final Channel[] DEFAULT_ANSWER = new Channel[0];

	@Override
	public Channel[] parse() {
		try {
			if (!Token.PLAYLIST.equals(reader.readLine())) {
				return DEFAULT_ANSWER;
			}
			String buffer = reader.readLine();
			if (!Token.NUMBER_OF_ENTRIES.equals(getKey(buffer))) {
				return DEFAULT_ANSWER;
			}
			int numberOfEntries = Integer.parseInt(getValue(buffer));
			Channel[] channels = new Channel[numberOfEntries];
			for (int i = 1; i <= numberOfEntries; i++) {
				Channel c = channels[i-1] = new Channel(null);
				buffer = readWhileBlank();
				if (!getKey(buffer).equals(Token.FILE + i)) {
					return DEFAULT_ANSWER;
				}
				String file = getValue(buffer);
				buffer = readWhileBlank();
				if (!getKey(buffer).equals(Token.TITLE + i)) {
					return DEFAULT_ANSWER;
				}
				String title = getValue(buffer);
				buffer = readWhileBlank();
				if (!getKey(buffer).equals(Token.LENGTH + i)) {
					return DEFAULT_ANSWER;
				}
				c.add(new Stream(title, file, new Option[0]));
			}
			String version = readWhileBlank();
			if ((Token.VERSION + Token.SEPARATOR + "2").equals(version)) {
				return channels;
			}
		} catch (IOException ioe) {
		}
		return DEFAULT_ANSWER;
	}

	private String getKey(String buffer) {
		return buffer.split(Token.SEPARATOR)[0];
	}

	private String getValue(String buffer) {
		return buffer.split(Token.SEPARATOR, 2)[1];
	}

	private String readWhileBlank() throws IOException {
		String b = reader.readLine();
		while (b != null && b.trim().length()==0) {
			b = reader.readLine();
		}
		return b;
	}

}
