package org.kobudei.website

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainBanner(title: String, mainText: String, subText: String) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFFFDF5),
                            Color(0xFFE8F5F2),
                            Color(0xFFE8EDF7),
                        )
                    )
                )
                .border(
                    border = BorderStroke(1.dp, Color(0xFFD7E6E1)),
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(28.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = title,
                    color = Forest,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = mainText,
                    color = Ink,
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        lineHeight = 46.sp
                    )
                )
                Text(
                    text = subText,
                    color = Color(0xFF3A5060),
                    style = MaterialTheme.typography.titleMedium
                )
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = Forest.copy(alpha = 0.12f),
                ) {
                    Text(
                        text = "Built with Kotlin + Compose Multiplatform",
                        color = Forest,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}