package org.kobudei.website

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import org.jetbrains.compose.resources.stringResource
import org.kobudei.website.ui.theme.KobudeiTheme
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.app_name

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    KobudeiTheme {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior().nestedScrollConnection)
                .safeContentPadding()
                .fillMaxSize(),
            topBar = {
                AppBar(
                    title = stringResource(Res.string.app_name),
                )
            },
            content = { innerPadding ->
                Content(innerPadding)
            }
        )
    }
}
