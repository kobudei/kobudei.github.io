package org.kobudei.website

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun WorkInProgressBar(message: String) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = androidx.compose.ui.graphics.Color.Yellow)
                .safeContentPadding(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                color = androidx.compose.ui.graphics.Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}