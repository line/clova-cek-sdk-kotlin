package com.linecorp.clova.extension.model.core

enum class Orientation {
    Portrait,
    Landscape;

    companion object {
        @JvmStatic
        fun of(value: String?): Orientation {
            return when(value?.toLowerCase()) {
                "portrait" -> Portrait
                else -> Landscape
            }
        }
    }
}

enum class DisplaySize(val value: String) {
    None("none"),
    Low("s100"),
    Medium("m100"),
    High("l100"),
    XHigh("xl100"),
    Custom("custom");

    companion object {
        @JvmStatic
        fun of(value: String): DisplaySize {
            return when(value.toLowerCase()) {
                "none" -> None
                "s100" -> Low
                "m100" -> Medium
                "l100" -> High
                "xl100" -> XHigh
                else -> Custom
            }
        }
    }
}

data class Device(
        val id: String,
        val display: Display
)

data class Display(
        val size: DisplaySize = DisplaySize.None,
        val orientation: Orientation? = null,
        val dpi: Int? = null,
        val contentLayer: ContentLayer? = null
)

data class ContentLayer(
        val width: Int,
        val height: Int
)
