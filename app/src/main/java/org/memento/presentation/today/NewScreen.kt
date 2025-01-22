package org.memento.presentation.today

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import org.memento.core.util.UiState
import org.memento.domain.entity.ScheduleList
import java.time.LocalDate

@Composable
fun NewScreen(
    viewModel: TodayViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        val todayDate = LocalDate.now().toString()
        viewModel.getScheduleList(todayDate)
    }

    val scheduleState by viewModel.scheduleListState.collectAsState()

    LaunchedEffect(scheduleState) {
        when (scheduleState) {
            is UiState.Loading -> Log.d("TodayScreen", "Loading schedule list...")
            is UiState.Success -> {
                val schedules = (scheduleState as UiState.Success<List<ScheduleList.ScheduleWithOrderInfo>>).data
                Log.d("TodayScreen", "Successfully fetched schedule list: $schedules")
            }
            is UiState.Failure -> {
                val errorMessage = (scheduleState as UiState.Failure)
                Log.e("TodayScreen", "Failed to fetch schedule list: $errorMessage")
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Today Screen")
    }
}
