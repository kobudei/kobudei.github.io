package org.kobudei.website

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults.rememberTooltipPositionProvider
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.meetup_logo
import website.composeapp.generated.resources.meetup_logo_description

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TooltipIconButton(tooltipText: String,
                      icon: DrawableResource,
                      description: StringResource,
                      url: String) {
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
            val uriHandler = LocalUriHandler.current

            IconButton(onClick = {
                uriHandler.openUri(url)
            }) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = stringResource(description),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}