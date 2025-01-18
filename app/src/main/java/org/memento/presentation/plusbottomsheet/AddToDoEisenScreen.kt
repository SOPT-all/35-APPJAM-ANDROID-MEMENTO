package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun AddToDoEisenScreen(
    onClose: () -> Unit,
    onDone: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "뒤로가기 버튼",
            )
            Text(
                text = "Done",
                modifier =
                    Modifier
                        .padding(horizontal = 18.dp, vertical = 12.dp)
                        .noRippleClickable {
                            onDone()
                        },
                style =
                    MementoTheme.typography.body_r_16.copy(
                        color = darkModeColors.gray07,
                    ),
            )
        }

        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 26.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
        }
    }
}
