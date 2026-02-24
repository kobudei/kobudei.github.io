package org.kobudei.website

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.mail_24

// commonMain
expect fun openEmail(
    to: String,
    subject: String,
    body: String
)

@Composable
fun ContactButton(
    label: String = "Contact Us",
    modifier: Modifier = Modifier
) {
    FilledTonalButton(
        modifier = modifier,
        shape = RoundedCornerShape(999.dp),
        border = BorderStroke(1.dp, ElectricCyan),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = ElectricCyan.copy(alpha = 0.14f),
            contentColor = BrightText
        ),
        onClick = {
            openEmail(
                to = "contact@kobudei.org",
                subject = "Kobudei Community",
                body = "Hello Kobudei team,\n\nI would like to get involved in the Kotlin Bucharest community."
            )
        }
    ) {
        Icon(
            painter = painterResource(Res.drawable.mail_24),
            contentDescription = "Send email",
            tint = ElectricCyan
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.3.sp
        )
    }
}
