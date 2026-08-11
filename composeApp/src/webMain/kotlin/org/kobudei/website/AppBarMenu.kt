package org.kobudei.website

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.*

@Composable
fun AppBarMenu() {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                painter = painterResource(Res.drawable.menu),
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            val uriHandler = LocalUriHandler.current

            DropdownMenuItem(
                text = { Text("GitHub") },
                onClick = {
                    expanded = false
                    uriHandler.openUri("https://github.com/kobudei")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.github_logo),
                        contentDescription = stringResource(Res.string.github_logo_description),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp),
                    )
                }
            )

            DropdownMenuItem(
                text = { Text("Meetup") },
                onClick = {
                    expanded = false
                    uriHandler.openUri("https://www.meetup.com/kobudei/")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.meetup_logo),
                        contentDescription = stringResource(Res.string.meetup_logo_description),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp),
                    )
                }
            )

            val subject = stringResource(Res.string.contact_mail_subject)
            val body = stringResource(Res.string.contact_mail_body)
            DropdownMenuItem(
                text = { Text("Contact") },
                onClick = {
                    expanded = false
                    openEmail(
                        to = "contact@kobudei.org",
                        subject = subject,
                        body = body
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.mail_24),
                        contentDescription = stringResource(Res.string.contact),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp),
                    )
                }
            )
        }
    }
}