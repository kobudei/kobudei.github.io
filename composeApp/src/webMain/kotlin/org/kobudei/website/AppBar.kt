package org.kobudei.website

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    MaterialTheme {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(
                    "Kobudei",
                    maxLines = 1
                )
            },
            actions = {
                ContactButton()
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

