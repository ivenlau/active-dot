package com.xiaomi.activedot.settings

import com.intellij.ui.ColorPanel
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBSlider
import com.intellij.util.ui.FormBuilder
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.event.ItemEvent
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.event.ChangeEvent

class ActiveDotSettingsPanel {

    private val settings = ActiveDotSettingsState.getInstance()

    private val colorPanel = ColorPanel().apply {
        preferredSize = Dimension(60, 30)
    }
    private val colorPanelWrapper = JPanel(FlowLayout(FlowLayout.LEFT, 0, 0)).apply {
        maximumSize = Dimension(70, 32)
        alignmentX = java.awt.Component.LEFT_ALIGNMENT
        add(colorPanel)
    }
    private val dotSizeSlider = JBSlider(3, 10, settings.dotSize)
    private val offsetXSlider = JBSlider(0, 10, settings.offsetX)
    private val offsetYSlider = JBSlider(-10, 10, settings.offsetY)

    // Breathing light controls
    private val breathingEnabledCheckbox = JBCheckBox("Enable Breathing Light", settings.breathingLightEnabled)
    private val breathingModeComboBox = JComboBox(arrayOf("Fixed", "Random", "Custom")).apply {
        selectedItem = when (settings.breathingMode) {
            "fixed" -> "Fixed"
            "random" -> "Random"
            "custom" -> "Custom"
            else -> "Fixed"
        }
    }
    private val breathingFrequencySlider = JBSlider(1, 10, settings.breathingFrequency)
    private val customColorPanels = listOf(
        ColorPanel().apply { preferredSize = Dimension(40, 22) },
        ColorPanel().apply { preferredSize = Dimension(40, 22) },
        ColorPanel().apply { preferredSize = Dimension(40, 22) },
        ColorPanel().apply { preferredSize = Dimension(40, 22) }
    )
    private val customColorsPanel = createCustomColorsPanel()
    private val modeAndColorsPanel = createModeAndColorsPanel()

    fun createPanel(): JPanel {
        // Setup mode change listener to show/hide custom colors
        breathingModeComboBox.addItemListener { e ->
            if (e.stateChange == ItemEvent.SELECTED) {
                updateCustomColorsVisibility()
            }
        }
        // Initialize visibility
        updateCustomColorsVisibility()

        return FormBuilder.createFormBuilder()
            .addLabeledComponent("Dot Color:", colorPanelWrapper, 0, false)
            .addComponent(createSeparator())
            .addLabeledComponent("Dot Size (3-10 pixels):", createSliderWithLabel(dotSizeSlider, settings.dotSize.toString()))
            .addLabeledComponent("Horizontal Offset (0-10 pixels):", createSliderWithLabel(offsetXSlider, settings.offsetX.toString()))
            .addLabeledComponent("Vertical Offset (-10 to +10 pixels):", createSliderWithLabel(offsetYSlider, settings.offsetY.toString()))
            .addComponent(createSeparator())
            .addComponent(breathingEnabledCheckbox)
            .addLabeledComponent("Breathing Mode:", modeAndColorsPanel)
            .addLabeledComponent("Breathing Frequency (1-10):", createSliderWithLabel(breathingFrequencySlider, settings.breathingFrequency.toString()))
            .addComponentFillVertically(JBLabel("Adjust the dot size, position, and breathing effect to fit your preference."), 0)
            .panel
    }

    private fun createModeAndColorsPanel(): JPanel {
        val panel = JPanel(FlowLayout(FlowLayout.LEFT, 10, 0))
        panel.add(breathingModeComboBox)
        panel.add(customColorsPanel)
        return panel
    }

    private fun updateCustomColorsVisibility() {
        val isCustomMode = breathingModeComboBox.selectedItem == "Custom"
        customColorsPanel.isVisible = isCustomMode
    }

    private fun createCustomColorsPanel(): JPanel {
        val panel = JPanel(FlowLayout(FlowLayout.LEFT, 5, 0))

        // Initialize custom color panels with current settings
        settings.customColors.forEachIndexed { index, colorHex ->
            if (index < customColorPanels.size) {
                customColorPanels[index].selectedColor = Color.decode("#" + colorHex)
            }
        }

        customColorPanels.forEach { colorPanel ->
            panel.add(colorPanel)
        }

        return panel
    }

    private fun createSliderWithLabel(slider: JBSlider, initialValue: String): JPanel {
        val valueLabel = JBLabel(initialValue)
        slider.addChangeListener { e: ChangeEvent? ->
            val source = e?.source as? JBSlider
            valueLabel.text = source?.value?.toString() ?: initialValue
        }
        val panel = JPanel(java.awt.BorderLayout())
        panel.add(slider, java.awt.BorderLayout.CENTER)
        panel.add(valueLabel, java.awt.BorderLayout.EAST)
        panel.preferredSize = Dimension(200, 30)
        return panel
    }

    private fun createSeparator(): javax.swing.JComponent {
        return javax.swing.JSeparator().apply {
            preferredSize = Dimension(0, 10)
        }
    }

    fun isModified(): Boolean {
        val customColorsModified = settings.customColors.size == customColorPanels.size &&
            customColorPanels.mapIndexed { index, panel ->
                panel.selectedColor?.let { Integer.toHexString(it.rgb).substring(2) } != settings.customColors[index]
            }.any { it }

        return colorPanel.selectedColor != Color.decode("#" + settings.dotColor) ||
                dotSizeSlider.value != settings.dotSize ||
                offsetXSlider.value != settings.offsetX ||
                offsetYSlider.value != settings.offsetY ||
                breathingEnabledCheckbox.isSelected != settings.breathingLightEnabled ||
                getBreathingMode() != settings.breathingMode ||
                breathingFrequencySlider.value != settings.breathingFrequency ||
                customColorsModified
    }

    private fun getBreathingMode(): String {
        return when (breathingModeComboBox.selectedItem as String) {
            "Fixed" -> "fixed"
            "Random" -> "random"
            "Custom" -> "custom"
            else -> "fixed"
        }
    }

    fun apply() {
        settings.dotColor = colorPanel.selectedColor?.let { Integer.toHexString(it.rgb).substring(2) } ?: "ff0000"
        settings.dotSize = dotSizeSlider.value
        settings.offsetX = offsetXSlider.value
        settings.offsetY = offsetYSlider.value

        // Apply breathing light settings
        settings.breathingLightEnabled = breathingEnabledCheckbox.isSelected
        settings.breathingMode = getBreathingMode()
        settings.breathingFrequency = breathingFrequencySlider.value
        settings.customColors = customColorPanels.mapNotNull { panel ->
            panel.selectedColor?.let { Integer.toHexString(it.rgb).substring(2) }
        }
    }

    fun reset() {
        colorPanel.selectedColor = Color.decode("#" + settings.dotColor)
        dotSizeSlider.value = settings.dotSize
        offsetXSlider.value = settings.offsetX
        offsetYSlider.value = settings.offsetY

        // Reset breathing light settings
        breathingEnabledCheckbox.isSelected = settings.breathingLightEnabled
        breathingModeComboBox.selectedItem = when (settings.breathingMode) {
            "fixed" -> "Fixed"
            "random" -> "Random"
            "custom" -> "Custom"
            else -> "Fixed"
        }
        breathingFrequencySlider.value = settings.breathingFrequency
        updateCustomColorsVisibility()

        settings.customColors.forEachIndexed { index, colorHex ->
            if (index < customColorPanels.size) {
                customColorPanels[index].selectedColor = Color.decode("#" + colorHex)
            }
        }
    }
}
