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
import website.composeapp.generated.resources.meetup_logo
import website.composeapp.generated.resources.meetup_logo_description

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarIcon(tooltipText: String = "Find other Kotlin User Groups") {
    MaterialTheme {
        TooltipIconButton(
            tooltipText = tooltipText,
            icon = Res.drawable.Kotlin_UG_logo,
            description = Res.string.logo_description,
            url = "https://kotlinlang.org/community/user-groups/"
        )
    }
}