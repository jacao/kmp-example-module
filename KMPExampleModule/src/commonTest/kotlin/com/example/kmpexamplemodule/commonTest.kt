package com.example.kmpexamplemodule

import kotlin.test.Test
import kotlin.test.assertTrue

class CommonGreetingTest {

    @Test
    fun testGreeting() {
        assertTrue(Greeting().greeting().contains("Hello"), "Check 'Hello' is mentioned")
    }

    @Test
    fun testExtraGreeting() {
        assertTrue(Greeting().extraGreeting().contains("Hello"), "Check 'Hello' is mentioned")
        assertTrue(Greeting().extraGreeting().contains("friend"), "Check 'friend' is mentioned")
    }
}