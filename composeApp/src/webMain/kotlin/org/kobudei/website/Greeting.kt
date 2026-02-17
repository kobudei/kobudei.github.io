package org.kobudei.website

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello World! This app is for ${platform.name}!"
    }
}