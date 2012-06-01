package org.kercoin.android.fewer;

import java.io.Serializable;

public class Option implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String name;
    private final String value;
    private final boolean hasValue;

    public Option(String name, String value) {
        this(name, value, true);
    }

    public Option(String name) {
        this(name, null, false);
    }

    private Option(String name, String value, boolean hasValue) {
        this.name = name;
        this.value = value;
        this.hasValue = hasValue;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean hasValue() {
        return hasValue;
    }

    @Override
    public String toString() {
        if (hasValue) {
            return "Option [" + name + "=" + value + "]";
        }
        return "Option [" + name + "]";
    }

}
