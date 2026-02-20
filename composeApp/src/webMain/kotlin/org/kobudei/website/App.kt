package org.kobudei.website

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


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

            MainBanner(
                title = "Kotlin Community in Bucharest",
                mainText = "We share practical knowledge that helps developers write better code.",
                subText = "Kobudei brings together Kotlin developers who care about ideas, community events, " +
                        "and long-term engineering growth."
            )

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

            WorkInProgressBanner()
        }
    }
}
