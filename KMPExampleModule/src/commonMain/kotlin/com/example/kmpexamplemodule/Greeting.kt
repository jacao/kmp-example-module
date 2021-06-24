package com.example.kmpexamplemodule

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }

    fun extraGreeting(): String {
        return "Hello, ${Platform().platform} friend!"
    }
}