package org.kobudei.website

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = backgroundGradient)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header(title = "Kobudei", contactLabel = "Contact Us!")

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFFFFDF5),
                                Color(0xFFE8F5F2),
                                Color(0xFFE8EDF7),
                            )
                        )
                    )
                    .border(
                        border = BorderStroke(1.dp, Color(0xFFD7E6E1)),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(28.dp),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "Kotlin Community in Bucharest",
                        color = Forest,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = "We share practical knowledge that helps developers write better code.",
                        color = Ink,
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold,
                            lineHeight = 46.sp
                        )
                    )
                    Text(
                        text = "Kobudei brings together Kotlin developers who care about useful ideas, clear talks, and long-term engineering growth.",
                        color = Color(0xFF3A5060),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = Forest.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = "Built with Kotlin + Compose Multiplatform",
                            color = Forest,
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Our Goals",
                color = Ink,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val useRowLayout = maxWidth > 840.dp
                if (useRowLayout) {
                    val cardWidth = (maxWidth - 16.dp) / 2
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        GoalCard(
                            title = "Talks & Events",
                            description = "We organize events where developers present their take on Kotlin topics, tools, and software architecture.",
                            modifier = Modifier.width(cardWidth)
                        )
                        GoalCard(
                            title = "Mentorship",
                            description = "Seasoned engineers help newcomers understand how Kotlin can make their projects cleaner, safer, and easier to scale.",
                            modifier = Modifier.width(cardWidth)
                        )
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        GoalCard(
                            title = "Talks & Events",
                            description = "We organize events where developers present their take on Kotlin topics, tools, and software architecture."
                        )
                        GoalCard(
                            title = "Mentorship",
                            description = "Seasoned engineers help newcomers understand how Kotlin can make their projects cleaner, safer, and easier to scale."
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFFE3F0EC),
                tonalElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Join the Kobudei community",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = Forest
                    )
                    Text(
                        text = "If you are building with Kotlin and want to share or learn, this is your place. Reach out and get involved in the next meetup.",
                        color = Ink,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    ContactButton(label = "Join via Email")
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Footer(url = "kobudei.org",
                copyright = "Copyright - 2026",
                text = "Kotlin developers in Bucharest, building with passion."
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
