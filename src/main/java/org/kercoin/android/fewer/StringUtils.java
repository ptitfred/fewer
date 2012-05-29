package org.kercoin.android.fewer;

public class StringUtils {

    static String findLongerCommonPrefix(String name2, String candidate) {
        if (name2 == null) {
            return candidate;
        } else {
            boolean isEqual = true;
            int i = 0;
            for (; isEqual && i<Math.min(name2.length(), candidate.length()); i++) {
                isEqual = name2.charAt(i) == candidate.charAt(i);
                if (!isEqual) {
                    i--;
                }
            }
            return name2.substring(0, i);
        }
    }

}
