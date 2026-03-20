package org.kobudei.website

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.built_with


@Composable
fun MadeWithMessage() {
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
            text = buildAnnotatedString {
                append(stringResource(Res.string.built_with))
                withLink(
                    LinkAnnotation.Url(
                        "https://kotlinlang.org/compose-multiplatform/",
                        styles = TextLinkStyles(
                            style = defaultStyle,
                            hoveredStyle = interactionStyle,
                            focusedStyle = interactionStyle,
                            pressedStyle = pressedStyle
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