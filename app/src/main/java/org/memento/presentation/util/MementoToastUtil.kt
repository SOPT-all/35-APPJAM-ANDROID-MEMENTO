package org.memento.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

object MementoToastUtil {
    @Composable
    fun SetView(
        messageTxt: String,
        resourceIcon: Int,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier =
                    Modifier
                        .background(
                            color = darkModeColors.gray07,
                            shape = RoundedCornerShape(size = 30.dp),
                        )
                        .padding(horizontal = 30.dp, vertical = 12.dp),
            ) {
                Image(
                    painter = painterResource(id = resourceIcon),
                    contentDescription = "toastIcon",
                    modifier =
                        Modifier
                            .padding(end = 7.dp),
                )
                Text(
                    text = messageTxt,
                    style =
                        MementoTheme.typography.detail_r_12.copy(
                            color = darkModeColors.white,
                        ),
                )
            }
        }
    }
}
