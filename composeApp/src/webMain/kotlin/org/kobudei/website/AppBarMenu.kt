package org.kobudei.website

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
            AppBarMenuItem(
                text = "GitHub",
                icon = Res.drawable.github_logo,
                description = Res.string.github_logo_description,
                url = "https://github.com/kobudei",
            ) {
                expanded = false
            }

            AppBarMenuItem(
                text = "Meetup",
                icon = Res.drawable.meetup_logo,
                description = Res.string.meetup_logo_description,
                url = "https://www.meetup.com/kobudei/",
            ) {
                expanded = false
            }

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