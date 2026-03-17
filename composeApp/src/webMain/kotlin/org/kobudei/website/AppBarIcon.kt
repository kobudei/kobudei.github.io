package org.kobudei.website

import androidx.compose.material3.*
import androidx.compose.material3.TooltipDefaults.rememberTooltipPositionProvider
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Kotlin_UG_logo
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.logo_description

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarIcon(tooltipText: String) {
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
                uriHandler.openUri("https://kotlinlang.org/community/user-groups/")
            }) {
                Icon(
                    painter = painterResource(Res.drawable.Kotlin_UG_logo),
                    contentDescription = stringResource(Res.string.logo_description),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}