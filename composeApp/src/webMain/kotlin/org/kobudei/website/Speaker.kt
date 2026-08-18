package org.kobudei.website

data class Speaker(
    val name: String,
    val bio: String,
    val imageUrl: String,
    val socialLinks: SocialLinks,
) {
    companion object {
        val ViorelAlexandrescu = Speaker(
            name = "Viorel Alexandrescu",
            bio = "Kotlin Advocate, Platform Builder with a Test and Domain Driven mindset. @kobudei founder.",
            imageUrl = "",
            socialLinks = SocialLinks(
                github = "https://github.com/viorel-alexandrescu",
            )
        )
    }
}