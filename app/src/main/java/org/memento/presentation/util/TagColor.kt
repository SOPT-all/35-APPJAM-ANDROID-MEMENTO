package org.memento.presentation.util

import org.memento.presentation.type.TagColorType

object TagColor {

    private val hexToTagColorTypeMap = mapOf(
        "#FF426E" to TagColorType.Red,
        "#EE8AAD" to TagColorType.Pink,
        "#FF8162" to TagColorType.Orange,
        "#FFE483" to TagColorType.Yellow,
        "#7BD27D" to TagColorType.LightGreen,
        "#149C95" to TagColorType.Mint,
        "#6CA9E1" to TagColorType.Cyan,
        "#3867FF" to TagColorType.Blue,
        "#7B5DFF" to TagColorType.Purple,
        "#A9ADBB" to TagColorType.Gray,
    )

    private val tagColorTypeToHexMap = hexToTagColorTypeMap.entries.associate { (hex, type) -> type to hex }

    fun fromHexCode(hex: String): TagColorType? {
        return hexToTagColorTypeMap[hex.uppercase()]
    }

    fun toHexCode(type: TagColorType): String? {
        return tagColorTypeToHexMap[type]
    }
}
