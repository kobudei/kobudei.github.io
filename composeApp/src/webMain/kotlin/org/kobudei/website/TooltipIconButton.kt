package org.kobudei.website

import androidx.compose.material3.*
import androidx.compose.material3.TooltipDefaults.rememberTooltipPositionProvider
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

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