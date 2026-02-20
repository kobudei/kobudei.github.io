package org.kobudei.website

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Yellow

@Composable
fun WorkInProgressBanner(){
    MaterialTheme {
        Row(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxWidth()
                .background(Yellow),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🚧 Work In Progress 🚧",
                style = MaterialTheme.typography.bodyMedium,
                color = Black
            )
        }
    }
}