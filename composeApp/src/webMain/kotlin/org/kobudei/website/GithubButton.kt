package org.kobudei.website

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.find_on_github
import website.composeapp.generated.resources.github_logo
import website.composeapp.generated.resources.github_logo_description

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GithubButton(tooltipText: String = stringResource(Res.string.find_on_github)) {
    MaterialTheme {
        TooltipIconButton(
            tooltipText = tooltipText,
            icon = Res.drawable.github_logo,
            description = Res.string.github_logo_description,
            url = "https://github.com/kobudei"
        )
    }
}