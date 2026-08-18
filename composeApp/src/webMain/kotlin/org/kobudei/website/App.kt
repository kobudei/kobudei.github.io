package org.kobudei.website

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.LayoutDirection
import androidx.window.core.layout.WindowSizeClass
import org.jetbrains.compose.resources.stringResource
import org.kobudei.website.ui.theme.KobudeiTheme
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.app_name

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
) {
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
                val isAtBreakpointForExtraPadding = windowSizeClass
                    .isWidthAtLeastBreakpoint(
                        widthDpBreakpoint = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND
                    )

                val contentHorizontalPadding = if (isAtBreakpointForExtraPadding) {
                    ScreenHorizontalPadding + ContentExtraHorizontalPadding
                } else {
                    ScreenHorizontalPadding
                }

                val combinedPadding = PaddingValues(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + contentHorizontalPadding,
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + contentHorizontalPadding,
                    top = innerPadding.calculateTopPadding() + ContentTopPadding,
                    bottom = innerPadding.calculateBottomPadding() + ContentBottomPadding,
                )
                Content(padding = combinedPadding)
            }
        )
    }
}
