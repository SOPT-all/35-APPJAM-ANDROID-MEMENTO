package org.memento.core.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.ui.focus.FocusManager

/**
 * 키보드를 및 포커스 관련 함수
 */
object KeyboardUtil {
    /**
     * 포커스를 제거하고 키보드를 숨깁니다.
     *
     * @param context Context 객체
     * @param focusManager Compose FocusManager
     */
    fun hideKeyboard(
        context: Context,
        focusManager: FocusManager,
    ) {
        // 포커스 제거
        focusManager.clearFocus(force = true)

        // 키보드 숨기기
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(
            (context as? Activity)?.currentFocus?.windowToken,
            0,
        )
    }

    /**
     * 키보드만 숨기는 함수 (포커스 제거 X).
     *
     * @param context Context 객체
     */
    fun hideKeyboardOnly(context: Context) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(
            (context as? Activity)?.currentFocus?.windowToken,
            0,
        )
    }

    /**
     * 포커스만 제거 함수 (키보드 숨김 X).
     *
     * @param focusManager Compose의 FocusManager
     */
    fun clearFocusOnly(focusManager: FocusManager) {
        focusManager.clearFocus(force = true)
    }
} 
