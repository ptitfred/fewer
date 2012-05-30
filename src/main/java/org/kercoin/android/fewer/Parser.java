package org.kercoin.android.fewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Parser {

    private final BufferedReader reader;

    private static enum Token {
        EXTENDED_M3U("EXTM3U"),
        INFO("EXTINF") {
            public String[] parse(String line) {
                return line.split(":")[1].split(",");
            }
        };

        private final String header;
        private Token(String key) {
            this.header = "#" + key;
        }

        public boolean matches(String line) {
            return line.startsWith(header);
        }

        public String[] parse(String line) {
            return new String[0];
        }

        public String getHeader() {
            return header;
        }
    }

    public Parser(BufferedReader reader) {
        this.reader = reader;
    }

    public Parser(InputStream is) {
        this(new BufferedReader(new InputStreamReader(is)));
    }

    private boolean extended = false;

    static class StringToIntegerComparator implements Comparator<String> {
        public int compare(String o1, String o2) {
            return new Integer(o1).compareTo(new Integer(o2));
        }
        
    }

    public Channel[] parse() {
        try {
            SortedMap<String, Channel> channels = new TreeMap<String, Channel>(new StringToIntegerComparator());
            String buffer = null;
            int line = 0;
            while ((buffer = reader.readLine()) != null) {
                line++;
                if (Token.EXTENDED_M3U.matches(buffer)) {
                    if (line != 1) {
                        throw new IllegalStateException("Unexpected " + Token.EXTENDED_M3U.getHeader() + " keyword");
                    }
                    // nothing
                    // should assert first line
                    extended = true;
                } else if (Token.INFO.matches(buffer)) {
                    assertExtended();
                    String[] support = Token.INFO.parse(buffer);
                    String[] streamDefinition = support[1].split(" - ");
                    String key = streamDefinition[0];
                    String name = streamDefinition[1];
                    buffer = reader.readLine();
                    Channel c = channels.get(key);
                    if (c == null) {
                        c = new Channel(key);
                        channels.put(key, c);
                    }
                    Stream s = new Stream(name, buffer);
                    c.add(s);
                }
            }
            return toArray(channels);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Channel[0];
    }

    private Channel[] toArray(SortedMap<String, Channel> channels) {
        final Set<String> keys = channels.keySet();
        Channel[] cs = new Channel[keys.size()];
        int i=0;
        for (String key : keys) {
            cs[i++] = channels.get(key);
        }
        return cs;
    }

    private void assertExtended() {
        if (!extended) {
            throw new IllegalStateException("Extended M3U not supported in the file");
        }
    }

}
