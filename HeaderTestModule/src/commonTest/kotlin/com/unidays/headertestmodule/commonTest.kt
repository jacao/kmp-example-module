package com.unidays.headertestmodule

import kotlin.test.Test
import kotlin.test.assertTrue

class CommonHeaderBuilderTest {

    @Test
    fun testGetHeader() {
        assertTrue(HeaderBuilder().getHeader().contains("Header String"), "Header string does not match")
    }

    @Test
    fun testGetExtraHeader() {
        assertTrue(HeaderBuilder().getExtraHeader().contains("Header String With Extras"), "Extra Header string does not match")
    }
}