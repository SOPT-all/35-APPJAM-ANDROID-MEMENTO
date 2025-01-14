package org.memento.presentation.reqres

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberImagePainter
import org.memento.core.util.UiState
import org.memento.domain.model.Reqres
import org.memento.ui.theme.MementoTheme

@Composable
fun ReqresScreen(
    onBack: () -> Unit,
    viewModel: ReqresViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchReqresData(page = 1)
    }

    when (uiState) {
        is UiState.Loading -> {
        }
        is UiState.Success -> {
            val data = (uiState as UiState.Success<List<Reqres>>).data
            LazyColumn {
                items(data) { reqres ->
                    ReqresItem(reqres = reqres)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBack) {
                Text(text = "Go Back")
            }
        }
        is UiState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("데이터를 불러오는 데 실패했습니다.")
            }
        }
    }
}

@Composable
fun ReqresItem(reqres: Reqres) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
    ) {
        Image(
            painter = rememberImagePainter(reqres.avatar),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "${reqres.firstName} ${reqres.lastName}",
                style = MementoTheme.typography.title_b_24,
            )
            Text(
                text = reqres.email,
                style = MementoTheme.typography.body_b_16,
            )
        }
    }
}
