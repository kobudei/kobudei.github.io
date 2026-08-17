package org.kobudei.website

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.description
import website.composeapp.generated.resources.work_in_progress
import kotlin.time.Clock
import kotlin.time.Instant

@Composable
fun Content(padding: PaddingValues) {
    MaterialTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(padding),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 24.dp,
                bottom = padding.calculateBottomPadding() + 32.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // wip
            item {
                WorkInProgressBar("___${stringResource(Res.string.work_in_progress)}___")
            }

            // description
            item {
                val paragraphs = stringArrayResource(Res.array.description)

                SectionSurface {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        if (paragraphs.isNotEmpty()) {
                            Text(
                                text = paragraphs.first(),
                                style = MaterialTheme.typography.headlineSmall
                            )

                            paragraphs.drop(1).forEach { paragraph ->
                                Text(
                                    text = paragraph,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        lineHeight = 30.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // upcoming events
            item {
                EventsSection(
                    title = "Upcoming Events",
                    events = getUpcomingEvents()
                )
            }

            item {
                EventsSection(
                    title = "Previous Events",
                    events = getPreviousEvents()
                )
            }

            // mwm
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    MadeWithMessage()
                }
            }
        }
    }
}

data class Talk(
    val title: String,
    val imageUrl: String? = null,
    val recordingUrl: String? = null,
    val speakers: List<Speaker>,
    val summary: String? = null,
)

data class Event(
    val title: String,
    val date: LocalDateTime,
    val location: Location,
    val eventUrl: String? = null,
    val talks: List<Talk>,
)

data class Location(
    val name: String,
    val address: String?,
    val url: String,
) {
    companion object {
        val QuatreSH = Location("4SH", "4SH, Le Haillan", "https://4sh.fr")
        val AD01 = Location("AD/01", "Bucharest, 165 Splaiul Unirii, Timpuri Noi Square, Building O3B", "https://ad01.com")
    }
}

data class SocialLinks(
    val website: String? = null,
    val gitlab: String? = null,
    val github: String? = null,
    val bluesky: String? = null,
    val linkedin: String? = null,
    val mastodon: String? = null,
    val youtube: String? = null,
)

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

fun getUpcomingEvents(): List<Event> {
    val currentMoment: Instant = Clock.System.now()
    return Events.filter { it.date >= currentMoment.toLocalDateTime(TimeZone.currentSystemDefault()) }
}

fun getPreviousEvents(): List<Event> {
    val currentMoment: Instant = Clock.System.now()
    return Events.filter { it.date < currentMoment.toLocalDateTime(TimeZone.currentSystemDefault()) }
}

@Composable
private fun SectionSurface(
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 1000.dp)
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            content = content
        )
    }
}

@Composable
private fun EventsSection(
    title: String,
    events: List<Event>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 1000.dp)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )

        if (events.isEmpty()) {
            EmptyEventsCard()
        } else {
            events.forEach { event ->
                EventCard(event)
            }
        }
    }
}

@Composable
private fun EventCard(
    event: Event
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.Top
            ) {
                EventDateBadge(event.date)

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = formatEventDate(event.date),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = event.location.name,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    event.location.address?.let { address ->
                        Text(
                            text = address,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            HorizontalDivider()

            event.talks.forEach { talk ->
                TalkCard(talk)
            }

            event.eventUrl?.let {
                EventLink(
                    text = "View event",
                    url = it
                )
            }
        }
    }
}

@Composable
private fun EventDateBadge(
    date: LocalDateTime
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            modifier = Modifier
                .width(72.dp)
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date.month.name
                    .take(3)
                    .uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = date.day.toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = date.year.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun TalkCard(
    talk: Talk
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = talk.title,
            style = MaterialTheme.typography.titleLarge
        )

        talk.summary?.let { summary ->
            Text(
                text = summary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 24.sp
            )
        }

        if (talk.speakers.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Presented by",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                talk.speakers.forEach { speaker ->
                    SpeakerRow(speaker)
                }
            }
        }
    }
}

@Composable
private fun SpeakerRow(
    speaker: Speaker
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier.size(44.dp),
            shape = RoundedCornerShape(50),
            color = MaterialTheme.colorScheme.secondaryContainer
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = speaker.name
                        .split(" ")
                        .take(2)
                        .mapNotNull { it.firstOrNull()?.uppercase() }
                        .joinToString(""),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = speaker.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = speaker.bio,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EmptyEventsCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No events to display",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatEventDate(
    date: LocalDateTime
): String {
    val month = date.month.name
        .lowercase()
        .replaceFirstChar { it.uppercase() }

    val hour = date.hour.toString().padStart(2, '0')
    val minute = date.minute.toString().padStart(2, '0')

    return "$month ${date.day}, ${date.year} · $hour:$minute"
}

@Composable
private fun EventLink(
    text: String,
    url: String
) {
    val defaultStyle = SpanStyle(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    val interactionStyle = defaultStyle.copy(
        textDecoration = TextDecoration.Underline
    )
    val pressedStyle = interactionStyle.copy(
        fontWeight = FontWeight.Bold,
    )
    Text(
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        text = buildAnnotatedString {
            withLink(
                LinkAnnotation.Url(
                    url = url,
                    styles = TextLinkStyles(
                        style = defaultStyle,
                        hoveredStyle = interactionStyle,
                        focusedStyle = interactionStyle,
                        pressedStyle = pressedStyle
                    )
                )
            ) {
                append(text)
            }
        }
    )
}