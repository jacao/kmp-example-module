package com.example.kmpexamplemodule

class Greeting {
    fun build(): String {
        return "Hello, ${Platform().name}!"
    }
}
