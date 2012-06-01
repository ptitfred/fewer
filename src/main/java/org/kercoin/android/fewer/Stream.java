package org.kercoin.android.fewer;

import java.io.Serializable;
import java.util.Arrays;

public class Stream implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String name;
    private final String sourceURL;
    private final Option[] options;

    public Stream(String name, String sourceURL, Option[] options) {
        this.name = name;
        this.sourceURL = sourceURL;
        this.options = Arrays.copyOf(options, options.length);
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return sourceURL;
    }

    public Option[] getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "Stream [name=" + name + ", sourceURL=" + sourceURL
                + ", options=" + Arrays.toString(options) + "]";
    }


}
