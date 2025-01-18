package org.memento.presentation.todo.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.memento.presentation.util.todoFormatDate
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography
import java.time.LocalDate

@Composable
fun TodoDateLine(
    date: LocalDate,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            Modifier
                .then(modifier)
                .fillMaxWidth(),
    ) {
        HorizontalDivider(
            modifier =
                Modifier
                    .padding(vertical = 5.dp),
            thickness = 1.dp,
            color = darkModeColors.gray07,
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = todoFormatDate(date),
            color = darkModeColors.gray05,
            style = defaultMementoTypography.body_b_14,
            modifier =
                Modifier
                    .padding(start = 22.dp, top = 2.dp, bottom = 2.dp),
        )
    }
}
