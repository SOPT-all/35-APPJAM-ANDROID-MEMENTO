package org.memento.presentation.navigator.component

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
                .noRippleClickable(onClick = onClick)
                .background(if (isSelected) Color.White else Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(11.dp))
        Icon(
            painter = painterResource(id = bottomNavigationType.iconRes),
            tint = if (isSelected) Color.Black else Color.Gray,
            contentDescription = "",
        )
        Spacer(modifier = Modifier.height(4.dp))
        bottomNavigationType.label?.let {
        }
        Spacer(modifier = Modifier.height(6.dp))
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
                    .background(Color.White)
                    .border(1.dp, color = Color.Black)
                    .fillMaxWidth()
                    .height(64.dp),
            verticalAlignment = Alignment.CenterVertically,
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
                        .size(48.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(24.dp))
                        .clickable { onAddButtonClick() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_white_24),
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
