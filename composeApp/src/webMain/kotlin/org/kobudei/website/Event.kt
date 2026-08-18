package org.kobudei.website

import kotlinx.datetime.LocalDateTime

data class Event(
    val title: String,
    val date: LocalDateTime,
    val location: Location,
    val eventUrl: String? = null,
    val talks: List<Talk>,
)