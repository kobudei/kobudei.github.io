package org.kobudei.website

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Content(padding: PaddingValues) {
    MaterialTheme {
        LazyColumn(
            modifier = Modifier.consumeWindowInsets(padding),
            contentPadding = padding,
        ) {
            item {
                WorkInProgressBar("___Work in Progress___")
            }
            item {
                Text("Lorem Ipsum")
            }
        }
    }
}