package com.example.kmpexamplemodule

import kotlin.test.Test
import kotlin.test.assertTrue

class CommonGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().build().contains("Hello"), "Check 'Hello' is mentioned")
    }
}