package org.memento.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingScreen(
    onBack: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Setting뷰 입니다")
        Button(onClick = {}) {
            Text("다음으로 넘어갈거야아")
        }
        Button(onClick = {
            onBack()
        }) {
            Text("뒤로 가버릴거야아")
        }
    }
}

@Preview
@Composable
fun SettingScreenPreview() {
}
