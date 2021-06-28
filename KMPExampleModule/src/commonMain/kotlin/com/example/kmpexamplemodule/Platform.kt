package com.example.kmpexamplemodule

/* we need to suppress this as the expect / actual keywords do not work without an empty constructor.
*  those key words are still in beta so this should fix itself eventually */
@Suppress("EmptyDefaultConstructor")
expect class Platform() {
    val name: String
}
