package org.kobudei.website

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            modifier = Modifier.consumeWindowInsets(padding),
            contentPadding = padding,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // wip
            item {
                WorkInProgressBar("___${stringResource(Res.string.work_in_progress)}___")
            }

            // description
            item {
                val paragraphs = stringArrayResource(Res.array.description)

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    //surface
                    Surface(
                        modifier = Modifier
                            .widthIn(max = 950.dp)
                            .padding(24.dp),
                        shape = RoundedCornerShape(24.dp),
                        tonalElevation = 2.dp,
                    ) {
                        Column(
                            modifier = Modifier
                                .widthIn(max = 850.dp)
                                .padding(32.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            if (paragraphs.isNotEmpty()) {
                                Text(
                                    text = paragraphs.first(),
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                paragraphs.drop(1).forEach {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            lineHeight = 30.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // upcoming events
            item {
                EventsCard(title = "Upcoming Events", events = getUpcomingEvents())
            }

            // past events
            item {
                EventsCard(title = "Previous Events", events = getPreviousEvents())
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