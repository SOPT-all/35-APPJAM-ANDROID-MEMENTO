package org.memento.presentation.util

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class MementoToast(private val context: Context) : Toast(context) {

    fun makeText(
        message: String,
        icon: Int,
        lifecycleOwner: LifecycleOwner,
        duration: Int = LENGTH_SHORT
    ) {
        val views = ComposeView(context)

        views.setContent {
            MementoToastContent(
                messageTxt = message,
                resourceIcon = icon
            )
        }

        views.setViewTreeLifecycleOwner(lifecycleOwner)
        views.setViewTreeSavedStateRegistryOwner(lifecycleOwner as? SavedStateRegistryOwner)
        views.setViewTreeViewModelStoreOwner(lifecycleOwner as? ViewModelStoreOwner)

        this.duration = duration
        this.view = views
        this.show()
    }
}

