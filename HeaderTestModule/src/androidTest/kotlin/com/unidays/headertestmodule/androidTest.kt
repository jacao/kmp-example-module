package com.unidays.headertestmodule

import com.unidays.headertestmodule.HeaderBuilder.Companion.TEST_HEADER_ID
import org.junit.Test
import kotlin.test.assertEquals

class AndroidGreetingTest {
    @Test
    fun testExample() {
        assertEquals(HeaderBuilder().getHeader(TEST_HEADER_ID), HeaderBuilder.TEST_HEADER)
    }
}