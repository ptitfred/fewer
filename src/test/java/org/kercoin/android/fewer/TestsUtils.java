package org.kercoin.android.fewer;

import java.io.InputStream;

final class TestsUtils {

    private TestsUtils() {}

    static InputStream loadRelativeTo(Class<?> type, String fileName) {
        String prefix = type.getPackage().getName().replaceAll("\\.", "/") + "/";
        return type.getClassLoader().getResourceAsStream(prefix + fileName);
    }

}
