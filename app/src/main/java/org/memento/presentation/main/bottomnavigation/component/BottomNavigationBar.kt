package org.memento.presentation.main.bottomnavigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.main.bottomnavigation.BottomNavigationType

@Composable
fun BottomNavigationBar(
    currentNaviBarItemSelected: BottomNavigationType?,
    onBottomNaviBarItemSelected: (BottomNavigationType) -> Unit,
    onAddButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(Color.Black)
                .height(64.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        IconButton(
            onClick = { onBottomNaviBarItemSelected(BottomNavigationType.TODO) },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_android_black_24),
                contentDescription = stringResource(id = R.string.todo),
                tint =
                    if (currentNaviBarItemSelected == BottomNavigationType.TODO) {
                        Color.White
                    } else {
                        Color.Gray
                    },
            )
        }

        Box(
            modifier =
                Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .padding(vertical = 6.dp, horizontal = 14.dp)
                    .clickable { onAddButtonClick() },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add_white_24),
                contentDescription = null,
                tint = Color.White,
            )
        }

        IconButton(
            onClick = { onBottomNaviBarItemSelected(BottomNavigationType.TODAY) },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_android_black_24),
                contentDescription = stringResource(id = R.string.today),
                tint =
                    if (currentNaviBarItemSelected == BottomNavigationType.TODAY) {
                        Color.Gray
                    } else {
                        Color.White
                    },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}
