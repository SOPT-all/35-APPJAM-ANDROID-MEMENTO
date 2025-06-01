package org.memento.presentation.type

import androidx.compose.ui.graphics.Color
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors

enum class TagColorType(val color: Color) {
    Red(color = mementoColors.red),
    Pink(color = mementoColors.pink),
    Orange(color = mementoColors.orange),
    Yellow(color = mementoColors.yellow),
    LightGreen(color = mementoColors.lightGreen),
    Mint(color = mementoColors.mint),
    Cyan(color = mementoColors.cyan),
    Blue(color = mementoColors.blue),
    Purple(color = mementoColors.purple),
    Gray(color = darkModeColors.gray05);

}
