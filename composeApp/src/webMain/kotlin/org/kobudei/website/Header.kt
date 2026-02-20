package org.kobudei.website

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import website.composeapp.generated.resources.Kotlin_UG_logo
import website.composeapp.generated.resources.Res

@Composable
fun Header(title: String, contactLabel: String){
    MaterialTheme {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(
                    resource = Res.drawable.Kotlin_UG_logo
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight()
            )
            Text(
                text = title,
                color = Ink,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            )
            ContactButton(label = contactLabel)
        }
    }
}