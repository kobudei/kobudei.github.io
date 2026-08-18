package org.kobudei.website

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EventsSection(
    title: String,
    events: List<Event>
) {
    MaterialTheme {
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
                events.forEach { EventCard(it) }
            }
        }
    }
}