package org.kobudei.website

data class Talk(
    val title: String,
    val imageUrl: String? = null,
    val recordingUrl: String? = null,
    val speakers: List<Speaker>,
    val summary: String? = null,
)