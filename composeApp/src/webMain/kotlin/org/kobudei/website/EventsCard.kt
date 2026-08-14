package org.kobudei.website

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun EventsCard(title: String, events: List<Event>) {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
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
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    if (events.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Surface(
                                modifier = Modifier
                                    .widthIn(max = 950.dp)
                                    .padding(24.dp),
                                shape = RoundedCornerShape(24.dp),
                                tonalElevation = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            ) {
                                Column {
                                    Text("No events to display")
                                }
                            }
                        }
                    } else {
                        events.forEach { event ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .widthIn(max = 950.dp)
                                        .padding(24.dp),
                                    shape = RoundedCornerShape(24.dp),
                                    tonalElevation = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
                                ) {
                                    Column {
                                        Text(event.eventTitle())
                                        event.talks.forEach { talk ->
                                            Text(
                                                text = talk.title,
                                                fontWeight = FontWeight.Bold
                                            )
                                            talk.speakers.forEach { speaker ->
                                                Text(speaker.name)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// todo change date display
private fun Event.eventTitle() = "${this.title} | ${this.date} | ${this.location.name}"