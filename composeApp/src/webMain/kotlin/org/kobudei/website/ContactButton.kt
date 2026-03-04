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
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.contact
import website.composeapp.generated.resources.contact_mail_body
import website.composeapp.generated.resources.contact_mail_subject
import website.composeapp.generated.resources.mail_24

// commonMain
expect fun openEmail(
    to: String,
    subject: String,
    body: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactButton(tooltipText: String) {
    MaterialTheme {
        TooltipBox(
            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
            tooltip = {
                PlainTooltip {
                    Text(tooltipText)
                }
            },
            state = rememberTooltipState()
        ) {
            val subject = stringResource(Res.string.contact_mail_subject)
            val body = stringResource(Res.string.contact_mail_body)

            FilledTonalButton(
                onClick = {
                    openEmail(
                        to = "contact@kobudei.org",
                        subject = subject,
                        body = body
                    )
                }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.mail_24),
                    contentDescription = stringResource(Res.string.contact)
                )
            }
        }
    }
}