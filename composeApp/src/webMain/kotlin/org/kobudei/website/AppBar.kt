package org.kobudei.website

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Kotlin_UG_logo
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.contact_us
import website.composeapp.generated.resources.logo_description

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String) {
    MaterialTheme {
        CenterAlignedTopAppBar(
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            title = {
                Text(
                    title,
                    maxLines = 1
                )
            },
            navigationIcon = {
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
            },
            actions = {
                ContactButton(
                    tooltipText = stringResource(Res.string.contact_us)
                )
            },
            scrollBehavior = scrollBehavior(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun scrollBehavior(): TopAppBarScrollBehavior {
    return TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
}

