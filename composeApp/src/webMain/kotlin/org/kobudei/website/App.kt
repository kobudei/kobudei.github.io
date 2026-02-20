package org.kobudei.website

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.delay
import org.w3c.fetch.Response

private data class Metric(
    val value: String,
    val label: String,
    val details: String,
)

private data class Goal(
    val id: String,
    val title: String,
    val description: String,
)

private val streamMetrics = listOf(
    Metric("12+", "Community events / year", "Meetups, demos, panel sessions."),
    Metric("40m", "Talk cadence", "Short, technical and practical sessions."),
    Metric("1:1", "Mentor loops", "Guided sessions for newcomers."),
)

private val goals = listOf(
    Goal(
        id = "Track 01",
        title = "Events Built by Engineers",
        description = "We host sessions where developers present architecture decisions, tooling tradeoffs and lessons learned from production.",
    ),
    Goal(
        id = "Track 02",
        title = "Knowledge Transfer",
        description = "From backend to multiplatform, we create a shared knowledge stream so teams can ship better Kotlin code faster.",
    ),
    Goal(
        id = "Track 03",
        title = "Mentorship Culture",
        description = "Seasoned engineers help newcomers structure learning paths, review code and avoid common design pitfalls.",
    ),
    Goal(
        id = "Track 04",
        title = "Project Clinics",
        description = "Members can bring real project blockers and receive feedback on architecture, APIs and testing strategies.",
    ),
)

private const val MeetupSnapshotResource = "meetup-snapshot.properties"
private val fallbackTopics = listOf(
    "Open Source",
    "Cloud Computing",
    "Software Development",
    "Data Science",
)

private data class MeetupSnapshot(
    val groupName: String,
    val city: String,
    val country: String,
    val timezone: String,
    val foundedDate: String,
    val organizer: String,
    val members: Int?,
    val upcomingEvents: Int?,
    val topics: List<String>,
    val description: String,
    val updatedAt: String,
    val sourceUrl: String,
)

private sealed interface MeetupSnapshotUiState {
    data object Loading : MeetupSnapshotUiState
    data class Ready(val snapshot: MeetupSnapshot) : MeetupSnapshotUiState
    data class Error(val message: String) : MeetupSnapshotUiState
}

@Composable
fun App() {
    val meetupState by produceState<MeetupSnapshotUiState>(
        initialValue = MeetupSnapshotUiState.Loading
    ) {
        while (true) {
            value = runCatching { loadMeetupSnapshot() }
                .fold(
                    onSuccess = { MeetupSnapshotUiState.Ready(it) },
                    onFailure = {
                        MeetupSnapshotUiState.Error(
                            message = it.message ?: "Could not load Meetup snapshot."
                        )
                    },
                )
            delay(60_000)
        }
    }

    MaterialTheme {
        val ambient = rememberInfiniteTransition(label = "ambient")
        val drift by ambient.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(9000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "drift",
        )
        val pulse by ambient.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2200, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "pulse",
        )
        val topBorder = lerp(ElectricCyan, NeonCoral, pulse)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            DeepSpace,
                            OrbitBlue,
                            DeepSpace,
                        )
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                ElectricCyan.copy(alpha = 0.08f + drift * 0.10f),
                                Color.Transparent,
                                LaserBlue.copy(alpha = 0.05f + (1f - drift) * 0.07f),
                                NeonCoral.copy(alpha = 0.07f + (1f - drift) * 0.08f),
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 18.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                TopBar(borderColor = topBorder)
                AnimatedSection(order = 0) { HeroSection(pulse = pulse) }
                AnimatedSection(order = 1) { SignalStreamSection(pulse = pulse) }
                AnimatedSection(order = 2) { AboutSection(pulse = pulse) }
                AnimatedSection(order = 3) { GoalsSection(pulse = pulse) }
                AnimatedSection(order = 4) { ProgramSection(pulse = pulse) }
                AnimatedSection(order = 5) { MentorshipSection(pulse = pulse) }
                AnimatedSection(order = 6) { MeetupSnapshotSection(state = meetupState, pulse = pulse) }
                AnimatedSection(order = 7) { CtaSection(pulse = pulse) }
                Footer()
            }
        }
    }
}

@Composable
private fun TopBar(borderColor: Color) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = CardBase.copy(alpha = 0.76f),
        border = BorderStroke(1.dp, borderColor.copy(alpha = 0.62f)),
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 14.dp)) {
            val wide = maxWidth > 620.dp
            if (wide) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BrandBlock()
                    ContactButton(label = "Join the Network")
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    BrandBlock()
                    ContactButton(
                        label = "Join the Network",
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun BrandBlock() {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = "KOBUDEI",
            color = BrightText,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.6.sp,
                fontFamily = FontFamily.Monospace,
            ),
        )
        Text(
            text = "Bucharest Kotlin community node",
            color = SoftText,
            style = MaterialTheme.typography.bodyMedium.copy(
                letterSpacing = 0.35.sp,
            ),
        )
    }
}

@Composable
private fun HeroSection(pulse: Float) {
    NeonPanel(pulse = pulse) {
        SectionEyebrow(text = "Community Uplink")
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val wide = maxWidth > 860.dp
            if (wide) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        HeroTextBlock()
                    }
                    GlowOrb(pulse = pulse)
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    HeroTextBlock()
                    GlowOrb(pulse = pulse)
                }
            }
        }
    }
}

@Composable
private fun HeroTextBlock() {
    Text(
        text = "Future-ready Kotlin community for developers who care about clean code.",
        color = BrightText,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            lineHeight = 42.sp,
        ),
    )
    Text(
        text = "Meetups, architecture talks, project clinics and mentorship loops focused on practical engineering decisions.",
        color = SoftText,
        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 27.sp),
    )
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val wide = maxWidth > 700.dp
        if (wide) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SignalChip(text = "Monthly meetups")
                SignalChip(text = "Hands-on demos")
                SignalChip(text = "Mentorship office hours")
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SignalChip(text = "Monthly meetups")
                SignalChip(text = "Hands-on demos")
                SignalChip(text = "Mentorship office hours")
            }
        }
    }
}

@Composable
private fun GlowOrb(pulse: Float) {
    val orbColor = lerp(ElectricCyan, NeonCoral, pulse)
    Box(
        modifier = Modifier
            .size(150.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        orbColor.copy(alpha = 0.60f),
                        LaserBlue.copy(alpha = 0.35f),
                        Color.Transparent,
                    )
                ),
                shape = CircleShape,
            )
    )
}

@Composable
private fun SignalStreamSection(pulse: Float) {
    NeonPanel(pulse = pulse * 0.8f) {
        SectionEyebrow(text = "Signal Stream")
        Text(
            text = "A high-frequency loop of sessions, practical examples and feedback from teams building with Kotlin.",
            color = SoftText,
            style = MaterialTheme.typography.bodyLarge,
        )
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val wide = maxWidth > 820.dp
            if (wide) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    streamMetrics.forEach { metric ->
                        MetricCard(metric = metric, modifier = Modifier.weight(1f))
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    streamMetrics.forEach { metric ->
                        MetricCard(metric = metric)
                    }
                }
            }
        }
    }
}

@Composable
private fun AboutSection(pulse: Float) {
    NeonPanel(pulse = pulse * 0.6f) {
        SectionEyebrow(text = "About Us")
        Text(
            text = "Kobudei represents the community of Kotlin developers based in Bucharest, looking to share knowledge on how it helped them write better code.",
            color = SoftText,
            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 27.sp),
        )
        Text(
            text = "Our goals are to organize events where devs can present their takes on different topics and to create a community where the more seasoned can help newcomers learn how Kotlin can help them in their projects.",
            color = SoftText,
            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 27.sp),
        )
    }
}

@Composable
private fun GoalsSection(pulse: Float) {
    NeonPanel(pulse = pulse) {
        SectionEyebrow(text = "Mission Grid")
        Text(
            text = "Four active tracks that keep the community practical, welcoming and technically ambitious.",
            color = SoftText,
            style = MaterialTheme.typography.bodyLarge,
        )

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val wide = maxWidth > 900.dp
            if (wide) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        GoalCard(goal = goals[0])
                        GoalCard(goal = goals[1])
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        GoalCard(goal = goals[2])
                        GoalCard(goal = goals[3])
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    goals.forEach { goal -> GoalCard(goal = goal) }
                }
            }
        }
    }
}

@Composable
private fun ProgramSection(pulse: Float) {
    NeonPanel(pulse = pulse * 0.7f) {
        SectionEyebrow(text = "Event Program")
        Text(
            text = "Every cycle combines strategic talks with practical coding sessions and direct Q&A.",
            color = SoftText,
            style = MaterialTheme.typography.bodyLarge,
        )
        TimelineItem(
            phase = "Phase 01",
            title = "Architecture Spotlight",
            description = "Deep dives into modularity, concurrency and design choices for real Kotlin systems.",
        )
        TimelineItem(
            phase = "Phase 02",
            title = "Code Lab Sessions",
            description = "Live demos around Ktor, coroutines, Kotlin Multiplatform and testing workflows.",
        )
        TimelineItem(
            phase = "Phase 03",
            title = "Open Mic + Q&A",
            description = "Bring your project context, blockers and ideas; get actionable feedback from peers.",
        )
    }
}

@Composable
private fun MentorshipSection(pulse: Float) {
    NeonPanel(pulse = pulse * 0.9f) {
        SectionEyebrow(text = "Mentorship Pipeline")
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val wide = maxWidth > 780.dp
            if (wide) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MentorshipCard(
                        modifier = Modifier.weight(1f),
                        title = "For newcomers",
                        details = "Get guided learning paths, architecture fundamentals and code review habits that scale.",
                    )
                    MentorshipCard(
                        modifier = Modifier.weight(1f),
                        title = "For experienced devs",
                        details = "Share battle-tested practices, mentor live projects and raise the community baseline.",
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    MentorshipCard(
                        title = "For newcomers",
                        details = "Get guided learning paths, architecture fundamentals and code review habits that scale.",
                    )
                    MentorshipCard(
                        title = "For experienced devs",
                        details = "Share battle-tested practices, mentor live projects and raise the community baseline.",
                    )
                }
            }
        }
    }
}

@Composable
private fun MeetupSnapshotSection(
    state: MeetupSnapshotUiState,
    pulse: Float,
) {
    NeonPanel(pulse = pulse * 0.72f) {
        SectionEyebrow(text = "Live Community Snapshot")
        Text(
            text = "Auto-updated from Meetup (refresh every 60s).",
            color = SoftText,
            style = MaterialTheme.typography.bodyLarge,
        )

        when (state) {
            MeetupSnapshotUiState.Loading -> {
                Text(
                    text = "Syncing latest group data...",
                    color = MutedText,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            is MeetupSnapshotUiState.Error -> {
                Text(
                    text = "Live sync failed: ${state.message}",
                    color = NeonCoral,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                )
                SnapshotDetails(
                    snapshot = fallbackSnapshot(),
                    isFallback = true,
                )
            }

            is MeetupSnapshotUiState.Ready -> {
                SnapshotDetails(
                    snapshot = state.snapshot,
                    isFallback = false,
                )
            }
        }
    }
}

@Composable
private fun SnapshotDetails(
    snapshot: MeetupSnapshot,
    isFallback: Boolean,
) {
    val location = "${snapshot.city}, ${snapshot.country.uppercase()}"
    val membersText = snapshot.members?.toString() ?: "Unknown"
    val eventsText = snapshot.upcomingEvents?.toString() ?: "Unknown"
    val timezoneText = snapshot.timezone.ifBlank { "Unknown" }
    val foundedText = snapshot.foundedDate.ifBlank { "Unknown" }
    val topicsText = if (snapshot.topics.isEmpty()) "Unknown" else snapshot.topics.joinToString(", ")
    val descriptionText = snapshot.description.ifBlank { "No description available." }

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val useRowLayout = maxWidth > 840.dp
        if (useRowLayout) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                SnapshotStatCard("Group", snapshot.groupName, Modifier.weight(1f))
                SnapshotStatCard("Members", membersText, Modifier.weight(1f))
                SnapshotStatCard("Upcoming", eventsText, Modifier.weight(1f))
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SnapshotStatCard("Group", snapshot.groupName)
                SnapshotStatCard("Members", membersText)
                SnapshotStatCard("Upcoming", eventsText)
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = CardBase.copy(alpha = 0.84f),
        border = BorderStroke(1.dp, LaserBlue.copy(alpha = 0.42f)),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Organizer: ${snapshot.organizer}",
                color = BrightText,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            )
            Text(
                text = "Location: $location",
                color = SoftText,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "Timezone: $timezoneText",
                color = SoftText,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "Founded: $foundedText",
                color = SoftText,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "Topics: $topicsText",
                color = SoftText,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = descriptionText,
                color = SoftText,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = if (isFallback) {
                    "Showing fallback snapshot. Source: ${snapshot.sourceUrl}"
                } else {
                    "Updated: ${snapshot.updatedAt} • Source: ${snapshot.sourceUrl}"
                },
                color = ElectricCyan,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
            )
        }
    }
}

@Composable
private fun SnapshotStatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = CardBase.copy(alpha = 0.86f),
        border = BorderStroke(1.dp, ElectricCyan.copy(alpha = 0.35f)),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = label,
                color = ElectricCyan,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
            )
            Text(
                text = value,
                color = BrightText,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            )
        }
    }
}

private suspend fun loadMeetupSnapshot(): MeetupSnapshot {
    val cacheBustedResource = "$MeetupSnapshotResource?ts=${window.performance.now().toInt()}"
    val response: Response = window.fetch(cacheBustedResource).await()
    if (!response.ok) {
        error("HTTP ${response.status.toInt()}")
    }
    val body: String = response.text().await()
    return parseMeetupSnapshot(body)
}

private fun parseMeetupSnapshot(body: String): MeetupSnapshot {
    val values = linkedMapOf<String, String>()
    body.lineSequence()
        .map { it.trim() }
        .filter { it.isNotEmpty() && !it.startsWith("#") && it.contains("=") }
        .forEach { line ->
            val idx = line.indexOf('=')
            if (idx > 0) {
                values[line.substring(0, idx).trim()] = line.substring(idx + 1).trim()
            }
        }

    val topics = values["topics"]
        ?.split("|")
        ?.map { it.trim() }
        ?.filter { it.isNotEmpty() }
        .orEmpty()

    return MeetupSnapshot(
        groupName = values["name"].orEmpty().ifBlank { "Kobudei" },
        city = values["city"].orEmpty().ifBlank { "Bucharest" },
        country = values["country"].orEmpty().ifBlank { "RO" },
        timezone = values["timezone"].orEmpty(),
        foundedDate = values["foundedDate"].orEmpty(),
        organizer = values["organizer"].orEmpty().ifBlank { "Kobudei Team" },
        members = values["members"]?.toIntOrNull(),
        upcomingEvents = values["upcomingEvents"]?.toIntOrNull(),
        topics = if (topics.isEmpty()) fallbackTopics else topics,
        description = values["description"].orEmpty(),
        updatedAt = values["updatedAt"].orEmpty().ifBlank { "Unknown" },
        sourceUrl = values["sourceUrl"].orEmpty().ifBlank { "https://www.meetup.com/kobudei/" },
    )
}

private fun fallbackSnapshot(): MeetupSnapshot {
    return MeetupSnapshot(
        groupName = "Kobudei",
        city = "Bucharest",
        country = "RO",
        timezone = "",
        foundedDate = "",
        organizer = "Kobudei Team",
        members = null,
        upcomingEvents = null,
        topics = fallbackTopics,
        description = "",
        updatedAt = "Unavailable",
        sourceUrl = "https://www.meetup.com/kobudei/",
    )
}

@Composable
private fun CtaSection(pulse: Float) {
    NeonPanel(pulse = pulse) {
        SectionEyebrow(text = "Join Kobudei")
        Text(
            text = "If you build with Kotlin and want to learn, share or mentor, you are already part of the signal.",
            color = BrightText,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        )
        Text(
            text = "Reach out and get onboarded for the next meetup. We keep content practical, direct and focused on writing better software.",
            color = SoftText,
            style = MaterialTheme.typography.bodyLarge,
        )
        ContactButton(label = "Contact the Community")
    }
}

@Composable
private fun Footer() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp, bottom = 8.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.Transparent,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "kobudei.org",
                color = ElectricCyan,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    fontFamily = FontFamily.Monospace,
                ),
            )
            Text(
                text = "Kotlin developers in Bucharest, building better software together.",
                color = MutedText,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun AnimatedSection(
    order: Int,
    content: @Composable () -> Unit,
) {
    content()
}

@Composable
private fun NeonPanel(
    pulse: Float,
    content: @Composable () -> Unit,
) {
    val border = lerp(ElectricCyan, NeonCoral, pulse)
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        border = BorderStroke(1.dp, border.copy(alpha = 0.76f)),
        colors = CardDefaults.cardColors(containerColor = CardInner),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            content()
        }
    }
}

@Composable
private fun SectionEyebrow(text: String) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = ElectricCyan.copy(alpha = 0.16f),
        border = BorderStroke(1.dp, ElectricCyan.copy(alpha = 0.72f)),
    ) {
        Text(
            text = text,
            color = ElectricCyan,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                fontFamily = FontFamily.Monospace,
            ),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        )
    }
}

@Composable
private fun SignalChip(text: String) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = LaserBlue.copy(alpha = 0.14f),
        border = BorderStroke(1.dp, LaserBlue.copy(alpha = 0.58f)),
    ) {
        Text(
            text = text,
            color = BrightText,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(horizontal = 11.dp, vertical = 6.dp),
        )
    }
}

@Composable
private fun MetricCard(
    metric: Metric,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, ElectricCyan.copy(alpha = 0.46f)),
        colors = CardDefaults.cardColors(containerColor = CardBase.copy(alpha = 0.95f)),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = metric.value,
                color = BrightText,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                ),
            )
            Text(
                text = metric.label,
                color = ElectricCyan,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
            )
            Text(
                text = metric.details,
                color = MutedText,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun GoalCard(goal: Goal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, LaserBlue.copy(alpha = 0.44f)),
        colors = CardDefaults.cardColors(containerColor = CardBase.copy(alpha = 0.92f)),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = goal.id,
                color = NeonCoral,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 0.8.sp,
                ),
            )
            Text(
                text = goal.title,
                color = BrightText,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            )
            Text(
                text = goal.description,
                color = SoftText,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun TimelineItem(
    phase: String,
    title: String,
    description: String,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = CardBase.copy(alpha = 0.92f),
        border = BorderStroke(1.dp, NeonCoral.copy(alpha = 0.46f)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = phase,
                color = NeonCoral,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                ),
                modifier = Modifier.width(84.dp),
            )
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = title,
                    color = BrightText,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    text = description,
                    color = SoftText,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
private fun MentorshipCard(
    title: String,
    details: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, ElectricCyan.copy(alpha = 0.45f)),
        colors = CardDefaults.cardColors(containerColor = CardBase.copy(alpha = 0.94f)),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = title,
                color = BrightText,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            )
            Text(
                text = details,
                color = SoftText,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
