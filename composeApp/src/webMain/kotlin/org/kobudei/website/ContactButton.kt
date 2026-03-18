package org.kobudei.website

import androidx.compose.material3.*
import androidx.compose.material3.TooltipDefaults.rememberTooltipPositionProvider
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.*

// commonMain
expect fun openEmail(
    to: String,
    subject: String,
    body: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactButton(tooltipText: String = stringResource(Res.string.contact_us)) {
    MaterialTheme {
        TooltipBox(
            positionProvider = rememberTooltipPositionProvider(
                positioning = TooltipAnchorPosition.Above,
                spacingBetweenTooltipAndAnchor = 4.dp),
            tooltip = {
                PlainTooltip {
                    Text(tooltipText)
                }
            },
            state = rememberTooltipState()
        ) {
            val subject = stringResource(Res.string.contact_mail_subject)
            val body = stringResource(Res.string.contact_mail_body)

            FilledIconButton(
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
                    contentDescription = stringResource(Res.string.contact),
                )
            }
        }
    }
}