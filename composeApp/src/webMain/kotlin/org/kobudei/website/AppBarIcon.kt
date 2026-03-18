package org.kobudei.website

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Kotlin_UG_logo
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.find_other_kugs
import website.composeapp.generated.resources.logo_description

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarIcon(tooltipText: String = stringResource(Res.string.find_other_kugs)) {
    MaterialTheme {
        TooltipIconButton(
            tooltipText = tooltipText,
            icon = Res.drawable.Kotlin_UG_logo,
            description = Res.string.logo_description,
            url = "https://kotlinlang.org/community/user-groups/"
        )
    }
}