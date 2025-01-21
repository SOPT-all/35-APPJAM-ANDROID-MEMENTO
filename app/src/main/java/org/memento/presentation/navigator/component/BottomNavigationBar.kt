package org.memento.presentation.navigator.component

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors

@Composable
fun CustomNavigationBarItem(
    context: Context,
    bottomNavigationType: BottomNavigationType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = onClick)
                .background(darkModeColors.navy),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Icon(
            painter = painterResource(id = bottomNavigationType.iconRes),
            tint = if (isSelected) darkModeColors.gray04 else darkModeColors.gray07,
            contentDescription = "",
        )
    }
}

@Composable
fun MainBottomBar(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    isVisible: Boolean,
    navigationBarItems: List<BottomNavigationType>,
    currentNavigationBarItem: BottomNavigationType?,
    onNavigationBarItemSelected: (BottomNavigationType) -> Unit,
    onAddButtonClick: () -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 1)),
        exit = fadeOut(animationSpec = tween(durationMillis = 1)),
    ) {
        Row(
            modifier =
                modifier
                    .background(darkModeColors.navy)
                    .fillMaxWidth()
                    .height(64.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            CustomNavigationBarItem(
                context = context,
                bottomNavigationType = navigationBarItems[0],
                isSelected = currentNavigationBarItem == navigationBarItems[0],
                onClick = { onNavigationBarItemSelected(navigationBarItems[0]) },
                modifier = Modifier.weight(1f),
            )

            Box(
                modifier =
                    Modifier
                        .aspectRatio(86f / 50f)
                        .padding(vertical = 8.dp)
                        .background(darkModeColors.gray09, shape = RoundedCornerShape(24.dp))
                        .noRippleClickable { onAddButtonClick() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Button",
                    tint = Color.White,
                )
            }

            CustomNavigationBarItem(
                context = context,
                bottomNavigationType = navigationBarItems[1],
                isSelected = currentNavigationBarItem == navigationBarItems[1],
                onClick = { onNavigationBarItemSelected(navigationBarItems[1]) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    var selectedItem by remember { mutableStateOf<BottomNavigationType?>(BottomNavigationType.TODO) }

    MainBottomBar(
        isVisible = true,
        navigationBarItems = BottomNavigationType.entries,
        currentNavigationBarItem = selectedItem,
        onNavigationBarItemSelected = { selectedItem = it },
        onAddButtonClick = { },
    )
}
