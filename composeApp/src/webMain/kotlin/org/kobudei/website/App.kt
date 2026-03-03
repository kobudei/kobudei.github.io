package org.kobudei.website

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior().nestedScrollConnection)
                .safeContentPadding()
                .fillMaxSize(),
            topBar = { AppBar() },
            content = { innerPadding ->
                LazyColumn(
                    modifier = Modifier.consumeWindowInsets(innerPadding),
                    contentPadding = innerPadding,
                ) {
                    item {
                        WorkInProgressBar("___Work in Progress___")
                    }
                    item {
                        Text("Lorem Ipsum")
                    }
                }
            }
        )
    }
}
