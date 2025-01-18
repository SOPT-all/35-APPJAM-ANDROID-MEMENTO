package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors

@Composable
fun MainPlusBottomSheet(
    tagColor: String,
    deadLineText: String,
    onNavigateDeadLineSetting: () -> Unit,
    onNavigateTagSetting: () -> Unit,
    onNavigateEisenSetting: () -> Unit,
) {
    val pages = listOf(R.drawable.ic_check_tab, R.drawable.ic_calendar_tab, R.drawable.ic_brain_tab)
    val pagerState =
        rememberPagerState(
            pageCount = { 3 },
        )
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 13.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier =
                Modifier
                    .background(
                        color = darkModeColors.gray08,
                        shape = CircleShape,
                    )
                    .padding(all = 3.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            pages.forEachIndexed { index, image ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier =
                        Modifier
                            .size(38.dp)
                            .background(
                                color = if (isSelected) darkModeColors.black else darkModeColors.gray08,
                                shape = CircleShape,
                            )
                            .noRippleClickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = "탭 아이콘",
                    )
                }
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
    ) { page ->
        when (page) {
            0 ->
                AddToDoScreen(
                    deadLineText = deadLineText,
                    tagColor = tagColor,
                    onNavigateDeadLineSetting = onNavigateDeadLineSetting,
                    onNavigateTagSetting = onNavigateTagSetting,
                    onNavigateEisenHourSetting = onNavigateEisenSetting,
                )

            1 -> AddPlanScreen()
            2 -> BrainDumpScreen()
            else ->
                AddToDoScreen(
                    deadLineText = deadLineText,
                    tagColor = tagColor,
                    onNavigateDeadLineSetting = onNavigateDeadLineSetting,
                    onNavigateTagSetting = onNavigateTagSetting,
                    onNavigateEisenHourSetting = onNavigateEisenSetting,
                )
        }
    }
}
