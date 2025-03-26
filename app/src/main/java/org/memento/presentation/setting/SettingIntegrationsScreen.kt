package org.memento.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.setting.component.SettingAlertDialog
import org.memento.presentation.setting.component.SettingSocialChip
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun SettingIntegrationsScreen() {
    val dummyData = dummyIntegrations
    val connected = dummyData.filter { it.isConnected }
    val notConnected = dummyData.filter { !it.isConnected }
    var showConnectedDialog by remember { mutableStateOf(false) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = darkModeColors.black)
                .padding(horizontal = 21.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "여기에 Top bar 들어갑니다.",
            style =
                MementoTheme.typography.body_b_16.copy(
                    color = darkModeColors.white,
                ),
            modifier =
                Modifier.fillMaxWidth()
                    .padding(top = 15.dp),
            textAlign = TextAlign.Center,
        )

        if (connected.isNotEmpty()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(
                            color = darkModeColors.gray09,
                            shape = RoundedCornerShape(size = 4.dp),
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(space = 8.dp),
            ) {
                Text(
                    text = "Connected",
                    style =
                        MementoTheme.typography.detail_r_12.copy(
                            color = darkModeColors.gray05,
                        ),
                )

                connected.forEach { data ->
                    SettingSocialChip(
                        icon = nameToIcon(data.name),
                        content = data.name,
                        onClick = { showConnectedDialog = true },
                    )
                }
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            Text(
                text = "Add",
                style =
                    MementoTheme.typography.detail_r_12.copy(
                        color = darkModeColors.gray05,
                    ),
            )

            notConnected.forEach { data ->
                SettingSocialChip(
                    icon = nameToIcon(data.name),
                    content = data.name,
                    onClick = {
                        // TODO: 플랫폼 별 연동 로직
                    },
                )
            }
        }
    }

    if (showConnectedDialog) {
        SettingAlertDialog(
            content = R.string.alert_setting_tag_exist,
            leftButtonText = R.string.alert_ok_button,
            onLeftButtonClick = { showConnectedDialog = false },
            onRightButtonClick = { },
        )
    }
}

private fun nameToIcon(name: String): Int {
    return when (name) {
        "Google Calendar" -> R.drawable.img_google
        "Notion" -> R.drawable.ic_notion
        "Slack" -> R.drawable.ic_notion
        else -> R.drawable.img_google
    }
}

data class IntegrationDummy(
    val name: String,
    val isConnected: Boolean,
)

val dummyIntegrations =
    listOf(
        IntegrationDummy(name = "Google Calendar", isConnected = true),
        IntegrationDummy(name = "Notion", isConnected = false),
        IntegrationDummy(name = "Slack", isConnected = false),
    )

@Preview
@Composable
fun SettingIntegrationsScreenPreview() {
    SettingIntegrationsScreen()
}
