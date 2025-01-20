package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun BrainDumpScreen() {
    var inputText by remember { mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current

    val dummyTexts =
        listOf(
            stringResource(R.string.brain_dump_example_1),
            stringResource(R.string.brain_dump_example_2),
            stringResource(R.string.brain_dump_example_3),
            stringResource(R.string.brain_dump_example_4),
        )

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.9f)
                    .background(
                        color = darkModeColors.black,
                    ),
        ) {
            BasicTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp)
                        .padding(top = 8.dp),
                textStyle =
                    MementoTheme.typography.body_b_16.copy(
                        color = darkModeColors.white,
                    ),
            )
        }

        LazyRow(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(dummyTexts) { text ->
                Text(
                    text = text,
                    style =
                        MementoTheme.typography.detail_r_12.copy(
                            color = darkModeColors.gray06,
                        ),
                    modifier =
                        Modifier
                            .width(195.dp)
                            .background(darkModeColors.black)
                            .padding(horizontal = 10.dp, vertical = 7.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 10.dp)
                    .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier.background(
                        color = darkModeColors.gray08,
                        shape = RoundedCornerShape(100.dp),
                    ),
            ) {
                Text(
                    text = "Paste",
                    style =
                        MementoTheme.typography.body_r_14.copy(
                            color = darkModeColors.gray02,
                        ),
                    modifier =
                        Modifier.padding(horizontal = 54.dp, vertical = 11.dp)
                            .clickable {
                                val clipboardText = clipboardManager.getText()?.text
                                if (clipboardText != null) {
                                    inputText = clipboardText
                                }
                            },
                )
            }

            Box(
                modifier =
                    Modifier.background(
                        shape = CircleShape,
                        color = if (inputText == "") darkModeColors.green.copy(alpha = 0.3f) else darkModeColors.green,
                    ),
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = "전송 버튼",
                    modifier =
                        Modifier
                            .padding(horizontal = 13.dp)
                            .padding(top = 12.dp, bottom = 10.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun BrainDumpScreenPreview() {
    BrainDumpScreen()
}
