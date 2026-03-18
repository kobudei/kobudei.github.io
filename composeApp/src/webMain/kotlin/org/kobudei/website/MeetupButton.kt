package org.kobudei.website

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.find_on_meetup
import website.composeapp.generated.resources.meetup_logo
import website.composeapp.generated.resources.meetup_logo_description

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetupButton(tooltipText: String = stringResource(Res.string.find_on_meetup)) {
    MaterialTheme {
        TooltipIconButton(
            tooltipText = tooltipText,
            icon = Res.drawable.meetup_logo,
            description = Res.string.meetup_logo_description,
            url = "https://www.meetup.com/kobudei/"
        )
    }
}