package org.kobudei.website

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.mail_24

// commonMain
expect fun openEmail(
    to: String,
    subject: String,
    body: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactButton() {
    MaterialTheme {
        TooltipBox(
            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
            tooltip = {
                PlainTooltip { Text("Contact us!") }
            },
            state = rememberTooltipState()
        ) {
            FilledTonalButton(
                onClick = {
                    openEmail(
                        to = "contact@kobudei.org",
                        subject = "Hello!",
                        body = "Hello Kobudei community! My name is..."
                    )
                }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.mail_24),
                    contentDescription = "Contact")
            }
        }
    }
}