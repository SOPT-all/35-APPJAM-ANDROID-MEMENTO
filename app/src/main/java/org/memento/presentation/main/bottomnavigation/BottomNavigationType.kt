package org.memento.presentation.main.bottomnavigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.memento.R

enum class BottomNavigationType(
    val route: String,
    @DrawableRes val bottomNaviIcon: Int,
    @StringRes val bottomNaviTitle: Int
) {
    TODO("todo", R.drawable.ic_android_black_24, R.string.todo),
    TODAY("today", R.drawable.ic_android_black_24, R.string.today);

    companion object {
        val entries = listOf(TODO, TODAY)
    }
}
