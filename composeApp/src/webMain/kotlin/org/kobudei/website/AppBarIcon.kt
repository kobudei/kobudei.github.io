package org.kobudei.website

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Kotlin_UG_logo
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.logo_description

@Composable
fun AppBarIcon() {
    MaterialTheme {
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