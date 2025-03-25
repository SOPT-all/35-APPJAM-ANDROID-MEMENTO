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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    onBack: () -> Unit,
    onDone: () -> Unit,
    tags: List<Tag>,
) {
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
            items(tags) { tag ->
                val isEditable = tag.name != "Untitled"

                SettingTag(
                    tagColor = tag.colorCode,
                    tagName = tag.name,
                    isEditable = isEditable,
                    onClick = { },
                )
            }
            item {
                SettingAddTag(onClick = {})
            }
        }
    }
}

data class Tag(
    val id: Int,
    val name: String,
    val colorCode: String,
)

val dummyTags =
    listOf(
        Tag(id = 163, name = "Untitled", colorCode = "#A9ADBB"),
        Tag(id = 164, name = "Family", colorCode = "#FF426E"),
        Tag(id = 165, name = "Hobby", colorCode = "#FF8162"),
        Tag(id = 166, name = "Self-Development", colorCode = "#149C95"),
        Tag(id = 167, name = "Work", colorCode = "#6CA9E1"),
        Tag(id = 168, name = "Personal", colorCode = "#3867FF"),
    )

@Preview
@Composable
private fun SettingTagScreen() {
    SettingTagScreen(onBack = {}, onDone = {}, tags = dummyTags)
}
