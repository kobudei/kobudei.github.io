package org.kobudei.website

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

val Events = listOf(
    Event(
        title = "Hello World!",
        date = LocalDateTime.parse("2026-09-23T17:30:00"),
        location = Location.AD01,
        eventUrl = "https://www.meetup.com/kobudei",
        talks = listOf(
            Talk(
                title = "http4k, in action",
                speakers = listOf(Speaker.ViorelAlexandrescu),
                summary = "A demonstration of how http4k can be a viable alternative to other backend building technologies"
            ),
        )
    )
).sortedBy { it.date }

fun getUpcomingEvents(clock: Clock = Clock.System): List<Event> =
    filterEvents(clock) { event, localDateTime ->
        event.date >= localDateTime
    }


fun getPreviousEvents(clock: Clock = Clock.System): List<Event> =
    filterEvents(clock) { event, localDateTime ->
        event.date < localDateTime
    }

private fun filterEvents(
    clock: Clock,
    predicate: (Event, LocalDateTime) -> Boolean
): List<Event> {
    val currentMoment: Instant = clock.now()
    val timezone = TimeZone.currentSystemDefault()
    val currentLocalDateTime = currentMoment.toLocalDateTime(timezone)
    return Events.filter { event -> predicate(event, currentLocalDateTime) }
}