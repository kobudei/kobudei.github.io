package org.kobudei.website

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

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
                AppBarIcon()
            },
            actions = {
                ContactButton()
                MeetupButton()
                GithubButton()
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

