package org.memento.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.memento.presentation.plusbottomsheet.AddToDoViewModel
import org.memento.presentation.setting.component.SettingAddTag
import org.memento.presentation.setting.component.SettingTag
import org.memento.presentation.setting.component.SettingTopBar
import org.memento.presentation.type.SettingTopBarType
import org.memento.ui.theme.darkModeColors

/**
 *  @param onBack  뒤로가기
 *  @param onDone modifier 수정 사항
 *  @param tags 서버로부터 받아오는 tag리스트
 */
@Composable
fun SettingTagScreen(
    viewModel: AddToDoViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onDone: () -> Unit,
    navigateToSettingEditTag: (tagId: Int, tagColor: String, tagName: String) -> Unit,
) {
    val tagList by viewModel.tagList.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getTagList()
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = darkModeColors.black)
                .padding(top = 16.dp),
    ) {
        SettingTopBar(
            type = SettingTopBarType.TAG,
            onBackClick = onBack,
            onDoneClick = onDone,
            isDoneVisible = true,
        )

        Spacer(modifier = Modifier.padding(top = 26.dp))

        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            items(tagList) { tag ->
                val isEditable = tag.name != "Untitled"

                SettingTag(
                    tagColor = tag.colorCode,
                    tagName = tag.name,
                    isEditable = isEditable,
                    onClick = {
                        navigateToSettingEditTag(
                            tag.id,
                            tag.colorCode,
                            tag.name,
                        )
                    },
                )
            }
            item {
                SettingAddTag(onClick = { navigateToSettingEditTag(0, "", "") })
            }
        }
    }
}

@Preview
@Composable
private fun SettingTagScreen() {
//    SettingTagScreen(onBack = {}, onDone = {}, tags = dummyTags, navigateToSettingEditTag = {})
}
