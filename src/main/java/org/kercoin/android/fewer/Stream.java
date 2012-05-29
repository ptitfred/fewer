package org.kercoin.android.fewer;


public class Stream {
    private final String name;
    private final String sourceURL;

    public Stream(String name, String sourceURL) {
        this.name = name;
        this.sourceURL = sourceURL;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return sourceURL;
    }

    @Override
    public String toString() {
        return "Stream [name=" + name + ", sourceURL=" + sourceURL + "]";
    }

}
