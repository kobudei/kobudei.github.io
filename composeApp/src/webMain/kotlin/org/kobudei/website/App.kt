package org.kobudei.website

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = androidx.compose.ui.graphics.Color.Yellow)
                    .safeContentPadding(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🚧 Work in Progress 🚧",
                    color = androidx.compose.ui.graphics.Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(50f)
                ) {
                    Text("Kobudei")
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(50f)
                ) {
                    ContactButton()
                }
            }
        }
    }
}
