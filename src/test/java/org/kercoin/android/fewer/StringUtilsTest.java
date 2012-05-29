package org.kercoin.android.fewer;


import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void simple() {
        assertThat(StringUtils.findLongerCommonPrefix("France 2 HD", "France 2 ")).isEqualTo("France 2 ");
        assertThat(StringUtils.findLongerCommonPrefix("France 2 HD", "France 2 (bas débit)")).isEqualTo("France 2 ");
    }

}
