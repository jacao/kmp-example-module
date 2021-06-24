package com.unidays.headertestmodule

import com.unidays.headertestmodule.HeaderBuilder.Companion.SOME_OTHER_HEADER
import com.unidays.headertestmodule.HeaderBuilder.Companion.TEST_HEADER
import com.unidays.headertestmodule.HeaderBuilder.Companion.TEST_HEADER_ID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CommonHeaderBuilderTest {
    @Test
    fun testExampleOne() {
        assertEquals(HeaderBuilder().getHeader(TEST_HEADER_ID), TEST_HEADER)
    }
    @Test
    fun testExampleTwo() {
        assertEquals(HeaderBuilder().getHeader(TEST_HEADER_ID + 1), SOME_OTHER_HEADER)
    }
}