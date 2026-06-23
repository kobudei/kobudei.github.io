package org.kobudei.website

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
            modifier = Modifier.consumeWindowInsets(padding),
            contentPadding = padding,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            item {
                WorkInProgressBar("___${stringResource(Res.string.work_in_progress)}___")
            }
            item {
                val paragraphs = stringArrayResource(Res.array.description)

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    //surface
                    Surface(
                        modifier = Modifier
                            .widthIn(max = 950.dp)
                            .padding(24.dp),
                        shape = RoundedCornerShape(24.dp),
                        tonalElevation = 2.dp,
                    ) {
                        Column(
                            modifier = Modifier
                                .widthIn(max = 850.dp)
                                .padding(32.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            if (paragraphs.isNotEmpty()) {
                                Text(
                                    text = paragraphs.first(),
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                paragraphs.drop(1).forEach {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            lineHeight = 30.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
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