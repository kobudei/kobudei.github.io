package org.kobudei.website

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun EventLink(
    text: String,
    url: String
) {
    MaterialTheme {
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
}