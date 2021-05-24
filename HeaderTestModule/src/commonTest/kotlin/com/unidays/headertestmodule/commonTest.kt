package com.unidays.headertestmodule

import kotlin.test.Test
import kotlin.test.assertTrue

class CommonHeaderBuilderTest {

    @Test
    fun testExample() {
        assertTrue(HeaderBuilder().getHeader().contains("Header String"), "Header string")
    }
}