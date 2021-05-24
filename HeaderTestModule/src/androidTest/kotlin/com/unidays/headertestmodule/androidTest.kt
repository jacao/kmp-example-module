package com.unidays.headertestmodule

import org.junit.Assert.assertTrue
import org.junit.Test

class AndroidGreetingTest {

    @Test
    fun testExample() {
        kotlin.test.assertTrue(
            HeaderBuilder().getHeader().contains("Header String"),
            "Header string"
        )
    }
}