package org.kobudei.website

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun MadeWithMessage() {
    MaterialTheme {
        Text(
            text = buildAnnotatedString {
                append("Website built with ")
                withLink(
                    LinkAnnotation.Url(
                        "https://kotlinlang.org/compose-multiplatform/",
                        styles = TextLinkStyles(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                            hoveredStyle = SpanStyle(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textDecoration = TextDecoration.Underline
                            ),
                            focusedStyle = SpanStyle(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textDecoration = TextDecoration.Underline
                            ),
                            pressedStyle = SpanStyle(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline
                            )
                        )
                    )
                ) {
                    append("Compose Multiplatform")
                }
                append(""" <3""")
            }
        )
    }
}