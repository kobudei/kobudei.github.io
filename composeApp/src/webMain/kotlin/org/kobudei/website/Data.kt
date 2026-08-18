package org.kobudei.website

import kotlinx.datetime.*
import kotlin.time.Clock
import kotlin.time.Instant

val Events = listOf(
    Event(
        title = "Hello World!",
        date = LocalDateTime(
            date = LocalDate(
                year = 2026,
                month = 9,
                day = 23,
            ),
            time = LocalTime(
                hour = 17,
                minute = 30,
            )
        ),
        location = Location.AD01,
        eventUrl = "https://www.meetup.com/kobudei",
        talks = listOf(
            Talk(
                title = "http4k, in action",
                speakers = listOf(Speaker.ViorelAlexandrescu),
                summary = "A demonstration of how http4k can be a viable alternative to other backend building technologies"
            ),
            Talk(
                title = "WORA, Take Two",
                speakers = listOf(Speaker.ViorelAlexandrescu),
                summary = "Compose Multiplatform demo, showcasing how effective it can be at building applications destined to work on the most popular platforms."
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
    events: List<Event> = emptyList(),
    predicate: (Event, LocalDateTime) -> Boolean
): List<Event> {
    val currentMoment: Instant = clock.now()
    val timezone = TimeZone.currentSystemDefault()
    val currentLocalDateTime = currentMoment.toLocalDateTime(timezone)
    return events.filter { event -> predicate(event, currentLocalDateTime) }
}