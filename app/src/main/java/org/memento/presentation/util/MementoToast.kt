package org.memento.presentation.util

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class MementoToast(context: Context) : Toast(context) {
    @Composable
    fun MakeText(
        message: String,
        icon: Int,
        duration: Int = LENGTH_SHORT,
    ) {
        val context = LocalContext.current
        val views = ComposeView(context)

        views.setContent {
            MementoToastUtil.SetView(
                messageTxt = message,
                resourceIcon = icon,
            )
        }

        views.setViewTreeLifecycleOwner(LocalLifecycleOwner.current)
        views.setViewTreeSavedStateRegistryOwner(LocalSavedStateRegistryOwner.current)
        views.setViewTreeViewModelStoreOwner(LocalViewModelStoreOwner.current)

        this.duration = duration
        this.view = views
        this.show()
    }
}
