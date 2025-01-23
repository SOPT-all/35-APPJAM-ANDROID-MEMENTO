package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors

@Composable
fun BrainDumpScreen(
    viewModel: BrainDumpViewModel = hiltViewModel(),
    onCloseBottomSheet: () -> Unit,
) {
    val inputText by viewModel.inputText.collectAsStateWithLifecycle()
    val clipboardManager = LocalClipboardManager.current
    val isShowAnimation by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
        ) {
            BrainDumpAITextField(
                value = inputText,
                onValueChange = { viewModel.updateInputText(it) },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.9f)
                        .padding(top = 8.dp),
                placeholder = "Got any plans? I’ll summarize it for you.",
                isShowAnimation = isShowAnimation,
            )

            LazyRow(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(viewModel.dummyTexts) { text ->
                    Text(
                        text = stringResource(text),
                        style =
                            MementoTheme.typography.detail_r_12.copy(
                                color = darkModeColors.gray06,
                            ),
                        modifier =
                            Modifier
                                .width(195.dp)
                                .background(
                                    brush =
                                        Brush.verticalGradient(
                                            colors =
                                                listOf(
                                                    mementoColors.brainDumpExStart,
                                                    mementoColors.brainDumpExEnd,
                                                ),
                                        ),
                                )
                                .border(width = 0.5.dp, color = darkModeColors.gray07, shape = RoundedCornerShape(2.dp))
                                .padding(horizontal = 10.dp, vertical = 7.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(color = darkModeColors.gray10)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .align(alignment = Alignment.BottomCenter),
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
                        Modifier
                            .padding(horizontal = 54.dp, vertical = 11.dp)
                            .clickable {
                                val clipboardText = clipboardManager.getText()?.text
                                viewModel.pasteCipBoard(clipboardText)
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
                            .padding(top = 12.dp, bottom = 10.dp)
                            .noRippleClickable {
                                viewModel.postBrainDump()
                            },
                )
            }
        }
    }
}

@Preview
@Composable
fun BrainDumpScreenPreview() {
    BrainDumpScreen(
        onCloseBottomSheet = { },
    )
}
