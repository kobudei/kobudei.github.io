package org.kobudei.website

import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.mail_24

// commonMain
expect fun openEmail(
    to: String,
    subject: String,
    body: String
)

@Composable
fun ContactButton(
    label: String = "Contact Us",
    modifier: Modifier = Modifier
) {
    MaterialTheme {
        FilledTonalButton(
            modifier = modifier,
            onClick = {
                openEmail(
                    to = "contact@kobudei.org",
                    subject = "Kobudei Community",
                    body = "Hello Kobudei team,\n\nI would like to get involved in the Kotlin Bucharest community."
                )
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.mail_24),
                contentDescription = "Send email"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(label)
        }
    }
}
