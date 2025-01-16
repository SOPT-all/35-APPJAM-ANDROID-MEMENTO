package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.MementoBottomSheet
import org.memento.ui.RepeatSelectorContent
import org.memento.ui.theme.darkModeColors

@Composable
fun MainPlusBottomSheet(
    tagColor: String,
    deadLineText : String,
    onNavigateDeadLineSetting: () -> Unit,
    onNavigateTagSetting: () -> Unit
) {
    val pages = listOf(R.drawable.ic_check_tab, R.drawable.ic_check_tab, R.drawable.ic_check_tab)
    val pagerState = rememberPagerState(
        pageCount = { 3 }
    )
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier
            .padding(vertical = 13.dp),
        containerColor = darkModeColors.gray09,
        indicator = { tabPositions ->
            Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
        },
        divider = { }
    ) {
        pages.forEachIndexed { index, _ ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                },
                modifier = Modifier.padding(all = 3.dp)
            ) {
                Image(
                    painter = painterResource(pages[index]),
                    contentDescription = "탭 아이콘",
                    modifier = Modifier.padding(all = 3.dp)
                )
            }
        }
    }

    HorizontalPager(
        state = pagerState,
    ) { page ->
        when (page) {
            0 -> AddToDoScreen (
                deadLineText = deadLineText,
                tagColor = tagColor,
                onNavigateDeadLineSetting = onNavigateDeadLineSetting,
                onNavigateTagSetting = onNavigateTagSetting
            )
            1 -> AddPlanScreen()
            2 -> BrainDumpScreen()
            else -> AddToDoScreen(
                deadLineText = deadLineText,
                tagColor = tagColor,
                onNavigateDeadLineSetting = onNavigateDeadLineSetting,
                onNavigateTagSetting = onNavigateTagSetting
            )
        }
    }
}