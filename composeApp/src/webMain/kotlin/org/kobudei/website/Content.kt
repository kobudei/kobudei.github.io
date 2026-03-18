package org.kobudei.website

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.work_in_progress

@Composable
fun Content(padding: PaddingValues) {
    MaterialTheme {
        LazyColumn(
            modifier = Modifier.consumeWindowInsets(padding),
            contentPadding = padding,
        ) {
            item {
                WorkInProgressBar("___${stringResource(Res.string.work_in_progress)}___")
            }
            item {
                Text("Lorem Ipsum")
            }
            item {
                Text(
                    buildAnnotatedString {
                        append("Website made with ")
                        withLink(
                            LinkAnnotation.Url(
                                "https://kotlinlang.org/compose-multiplatform/",
                                TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant))
                            )
                        ) {
                            append("Compose Multiplatform ")
                        }
                        append("""<3""")
                    }
                )
            }
        }
    }
}