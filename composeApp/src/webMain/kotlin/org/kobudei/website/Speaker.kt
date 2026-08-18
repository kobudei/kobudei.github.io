package org.kobudei.website

data class Speaker(
    val name: String,
    val bio: String,
    val imageUrl: String,
    val socialLinks: SocialLinks,
) {
    companion object {
        val IvanCanet = Speaker(
            name = "Ivan “CLOVIS” Canet",
            bio = "Fullstack 100% Kotlin, auteur de bibliothèques open source via OpenSavvy. Développeur et formateur chez 4SH. Pour les DSLs, contre la magie. Contributeur à Arrow et actif sur les forums.",
            imageUrl = "",
            socialLinks = SocialLinks(
                website = "https://ivan.canet.dev/",
                gitlab = "https://gitlab.com/clovis-ai",
                github = "https://github.com/clovis-ai",
                bluesky = "https://bsky.app/profile/ivcanet.bsky.social",
                linkedin = "https://www.linkedin.com/in/ivan-canet/",
            )
        )
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