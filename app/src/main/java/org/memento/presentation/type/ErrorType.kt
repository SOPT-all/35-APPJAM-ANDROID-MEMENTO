package org.memento.domain.type

import androidx.compose.ui.graphics.Color
import org.memento.ui.theme.darkModeColors

enum class ErrorType(val message: String) {
    // 시간 역전 에러
    TIME_PRECEDE_ERROR("Cannot save event. Starts must precede Ends."),
    NETWORK_ERROR("Network error occurred!"),
}

enum class SuccessType(val message: String) {
    // 성공 타입
    CREATE_SUCCESS("Created Successfully!"),
}

sealed class ToastType(val backgroundColor: Color) {
    class SUCCESS(val successType: SuccessType) : ToastType(darkModeColors.green)

    class ERROR(val errorType: ErrorType) : ToastType(darkModeColors.gray07)
}
