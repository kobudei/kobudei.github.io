package org.kobudei.website

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.description
import website.composeapp.generated.resources.work_in_progress

@Composable
fun Content(padding: PaddingValues) {
    MaterialTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(padding),
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // wip
            item {
                WorkInProgressBar("___${stringResource(Res.string.work_in_progress)}___")
            }

            // description
            item {
                val paragraphs = stringArrayResource(Res.array.description)

                SectionSurface {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        if (paragraphs.isNotEmpty()) {
                            Text(
                                text = paragraphs.first(),
                                style = MaterialTheme.typography.headlineSmall
                            )

                            paragraphs.drop(1).forEach { paragraph ->
                                Text(
                                    text = paragraph,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        lineHeight = 30.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // upcoming events
            item {
                EventsSection(
                    title = "Upcoming Events",
                    events = getUpcomingEvents()
                )
            }

            // previous events
            item {
                EventsSection(
                    title = "Previous Events",
                    events = getPreviousEvents()
                )
            }

            // mwm
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    MadeWithMessage()
                }
            }
        }
    }
}
