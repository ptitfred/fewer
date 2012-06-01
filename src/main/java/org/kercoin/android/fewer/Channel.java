package org.kercoin.android.fewer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Channel implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String key;
    private final Collection<Stream> streams;

    private String name;

    public Channel(String key) {
        this.key = key;
        this.streams = new ArrayList<Stream>();
    }

    public boolean add(Stream e) {
        try {
            return streams.add(e);
        } finally {
            updateName();
        }
    }

    private void updateName() {
        String name = null;
        for (Stream s : streams) {
            String candidate = s.getName();
            name = StringUtils.findLongerCommonPrefix(name, candidate).trim();
        }
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public Stream[] getStreams() {
        return streams.toArray(new Stream[0]);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Channel [key=" + key + ", name=" + name + ", streams="
                + streams + "]";
    }

}
