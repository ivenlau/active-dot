
package com.xiaomi.activedot.settings

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.ColorPanel
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBSlider
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.UIUtil
import java.awt.Color
import javax.swing.JPanel
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener

class ActiveDotSettingsPanel {

    private val settings = ActiveDotSettingsState.getInstance()

    private val colorPanel = ColorPanel().apply {
        preferredSize = java.awt.Dimension(60, 30)
    }
    private val colorPanelWrapper = javax.swing.JPanel(java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0)).apply {
        maximumSize = java.awt.Dimension(70, 32)
        alignmentX = java.awt.Component.LEFT_ALIGNMENT
        add(colorPanel)
    }
    private val dotSizeSlider = JBSlider(3, 10, settings.dotSize)
    private val offsetXSlider = JBSlider(0, 10, settings.offsetX)
    private val offsetYSlider = JBSlider(-10, 10, settings.offsetY)

    fun createPanel(): JPanel {
        return FormBuilder.createFormBuilder()
            .addLabeledComponent("Dot Color:", colorPanelWrapper, 0, false)
            .addComponent(createSeparator())
            .addLabeledComponent("Dot Size (3-10 pixels):", createSliderWithLabel(dotSizeSlider, settings.dotSize.toString()))
            .addLabeledComponent("Horizontal Offset (0-10 pixels):", createSliderWithLabel(offsetXSlider, settings.offsetX.toString()))
            .addLabeledComponent("Vertical Offset (-10 to +10 pixels):", createSliderWithLabel(offsetYSlider, settings.offsetY.toString()))
            .addComponentFillVertically(JBLabel("Adjust the dot size and position to fit your preference."), 0)
            .panel
    }

    private fun createSliderWithLabel(slider: JBSlider, initialValue: String): JPanel {
        val valueLabel = JBLabel(initialValue)
        slider.addChangeListener { e: ChangeEvent? ->
            val source = e?.source as? JBSlider
            valueLabel.text = source?.value?.toString() ?: initialValue
        }
        val panel = javax.swing.JPanel(java.awt.BorderLayout())
        panel.add(slider, java.awt.BorderLayout.CENTER)
        panel.add(valueLabel, java.awt.BorderLayout.EAST)
        panel.preferredSize = java.awt.Dimension(200, 30)
        return panel
    }

    private fun createSeparator(): javax.swing.JComponent {
        return javax.swing.JSeparator().apply {
            preferredSize = java.awt.Dimension(0, 10)
        }
    }

    fun isModified(): Boolean {
        return colorPanel.selectedColor != Color.decode("#" + settings.dotColor) ||
                dotSizeSlider.value != settings.dotSize ||
                offsetXSlider.value != settings.offsetX ||
                offsetYSlider.value != settings.offsetY
    }

    fun apply() {
        settings.dotColor = colorPanel.selectedColor?.let { Integer.toHexString(it.rgb).substring(2) } ?: "ff0000"
        settings.dotSize = dotSizeSlider.value
        settings.offsetX = offsetXSlider.value
        settings.offsetY = offsetYSlider.value
    }

    fun reset() {
        colorPanel.selectedColor = Color.decode("#" + settings.dotColor)
        dotSizeSlider.value = settings.dotSize
        offsetXSlider.value = settings.offsetX
        offsetYSlider.value = settings.offsetY
    }
}
