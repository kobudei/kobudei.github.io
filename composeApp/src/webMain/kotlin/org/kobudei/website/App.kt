package org.kobudei.website

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.mail_24

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
