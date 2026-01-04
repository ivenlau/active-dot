
package com.xiaomi.activedot.settings

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.ColorPanel
import com.intellij.ui.dsl.builder.panel
import java.awt.Color

class ActiveDotSettingsPanel {

    private val settings = ActiveDotSettingsState.getInstance()

    private val colorPanel = ColorPanel()

    fun createPanel(): DialogPanel {
        return panel {
            row("Dot Color:") {
                cell(colorPanel)
            }
        }
    }

    fun isModified(): Boolean {
        return colorPanel.selectedColor != Color.decode("#" + settings.dotColor)
    }

    fun apply() {
        settings.dotColor = colorPanel.selectedColor?.let { Integer.toHexString(it.rgb).substring(2) } ?: "ff0000"
    }

    fun reset() {
        colorPanel.selectedColor = Color.decode("#" + settings.dotColor)
    }
}
