package org.kercoin.android.fewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class M3UParser implements PlaylistParser {

    private BufferedReader reader;

    private static enum Token {
        EXTENDED_M3U("EXTM3U"),
        INFO("EXTINF") {
            public String[] parse(String line) {
                return line.split(":")[1].split(",");
            }
        },
        VLC_OPT("EXTVLCOPT") {
            @Override
            public String[] parse(String line) {
                return line.split(":")[1].split("=");
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

    public M3UParser() {}

    public M3UParser(InputStream data, String charset) throws UnsupportedEncodingException {
        open(data, charset);
    }

    @Override
    public M3UParser open(InputStream data, String charset) throws UnsupportedEncodingException {
        this.reader = new BufferedReader(new InputStreamReader(data, charset));
        return this;
    }

    private boolean extended = false;

    static class StringToIntegerComparator implements Comparator<String> {
        public int compare(String o1, String o2) {
            return new Integer(o1).compareTo(new Integer(o2));
        }
        
    }

    @Override
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
                    List<Option> options = new ArrayList<Option>();
                    while (buffer.startsWith("#EXT")) {
                        if (Token.VLC_OPT.matches(buffer)) {
                            String[] option = Token.VLC_OPT.parse(buffer);
                            if (option.length == 1) {
                                options.add(new Option(option[0]));
                            } else {
                                options.add(new Option(option[0], option[1]));
                            }
                        }
                        buffer = reader.readLine();
                    }
                    Channel c = channels.get(key);
                    if (c == null) {
                        c = new Channel(key);
                        channels.put(key, c);
                    }
                    Stream s = new Stream(name, buffer, options.toArray(new Option[0]));
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
