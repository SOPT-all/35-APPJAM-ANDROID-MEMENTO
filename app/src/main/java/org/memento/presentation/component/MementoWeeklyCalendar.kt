package org.memento.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import java.time.LocalDate

@Composable
fun MementoWeeklyCalendar(
    onDateClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val today = LocalDate.now()
    val selectedDate = remember { mutableStateOf(today) }

    val pagerState = rememberPagerState(
        initialPage = 5000,
        pageCount = { Int.MAX_VALUE }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = darkModeColors.black)
            .padding(horizontal = 20.dp)
    ) {
        val daysOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = darkModeColors.black)
                .padding(top = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            daysOfWeek.forEachIndexed { index, day ->
                val startOfWeek = today.minusDays(today.dayOfWeek.value.toLong() % 7)
                    .plusWeeks((pagerState.currentPage - 5000).toLong())
                val endOfWeek = startOfWeek.plusDays(6)

                val isWithinCurrentPage = selectedDate.value in startOfWeek..endOfWeek
                val isSelectedDay = isWithinCurrentPage &&
                        selectedDate.value.dayOfWeek.value % 7 == index

                Text(
                    text = day,
                    style = MementoTheme.typography.detail_b_12,
                    color = if (isSelectedDay) Color.White else darkModeColors.gray08,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = darkModeColors.black)
                .padding(vertical = 10.dp),
            pageSpacing = 16.dp,
        ) { page ->
            val startOfWeek = today.minusDays(today.dayOfWeek.value.toLong() % 7)
                .plusWeeks((page - 5000).toLong())

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                (0..6).forEach { offset ->
                    val currentDate = startOfWeek.plusDays(offset.toLong())
                    val isToday = currentDate == today
                    val isSelected = currentDate == selectedDate.value

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .weight(1f)
                            .noRippleClickable {
                                selectedDate.value = currentDate
                                onDateClick(currentDate)
                            }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = when {
                                        isSelected -> Color.White
                                        isToday -> Color.Transparent
                                        else -> Color.Transparent
                                    },
                                    shape = CircleShape
                                )
                        ) {
                            Text(
                                text = currentDate.dayOfMonth.toString(),
                                style = MementoTheme.typography.body_b_16,
                                color = when {
                                    isSelected && currentDate == today -> Color.Black
                                    isSelected -> Color.Black
                                    isToday -> Color.Green
                                    else -> darkModeColors.gray06
                                },
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF0F0F1E)
@Composable
private fun WeekCalendarPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        MementoWeeklyCalendar(
            onDateClick = { clickedDate ->
                println("Clicked date: $clickedDate")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
