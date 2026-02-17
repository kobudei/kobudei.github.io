package org.kobudei.website

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform