package org.kobudei.website

import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppBarMenuItem(
    text: String,
    icon: DrawableResource,
    description: StringResource,
    url: String,
    onClick: () -> Unit
) {
    MaterialTheme {
        val uriHandler = LocalUriHandler.current
        DropdownMenuItem(
            text = { Text(text) },
            onClick = {
                uriHandler.openUri(url)
                onClick()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = stringResource(description),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp),
                )
            }
        )
    }
}