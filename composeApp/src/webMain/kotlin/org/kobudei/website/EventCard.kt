package org.kobudei.website

import androidx.compose.foundation.layout.*
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
import kotlinx.datetime.LocalDateTime

@Composable
fun EventCard(
    event: Event
) {
    MaterialTheme {
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
                                        url = event.location.url,
                                        styles = TextLinkStyles(
                                            style = defaultStyle,
                                            hoveredStyle = interactionStyle,
                                            focusedStyle = interactionStyle,
                                            pressedStyle = pressedStyle
                                        )
                                    )
                                ) {
                                    append(event.location.name)
                                }
                            }
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