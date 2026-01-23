package com.xiaomi.activedot.ui

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.Project
import com.intellij.ui.tabs.JBTabs
import com.xiaomi.activedot.service.BreathingEffectService
import com.xiaomi.activedot.settings.ActiveDotSettingsState
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JComponent
import javax.swing.SwingUtilities

class ActiveDotUI(private val project: Project) : JComponent() {

    private val settings = ActiveDotSettingsState.getInstance()
    private val breathingService = BreathingEffectService.getInstance()
    private var currentBreathingColor: Color? = null

    private val breathingListener = object : BreathingEffectService.Listener {
        override fun onBreathingColorChanged(color: Color?) {
            if (currentBreathingColor != color) {
                currentBreathingColor = color
                repaint()
            }
        }
    }

    init {
        isOpaque = false
        breathingService.addListener(breathingListener)
    }

    override fun contains(x: Int, y: Int): Boolean {
        return false
    }

    override fun paintComponent(g: Graphics) {
        if (!isShowing) return
        super.paintComponent(g)

        val fileEditorManager = FileEditorManagerEx.getInstanceEx(project)
        val currentWindow = fileEditorManager.currentWindow ?: return
        val tabs = currentWindow.tabbedPane.component as? JBTabs ?: return
        val selectedTab = tabs.selectedInfo ?: return
        val tabLabel = tabs.getTabLabel(selectedTab) ?: return
        val layeredPane = this.parent ?: return

        val location = SwingUtilities.convertPoint(tabLabel.parent, tabLabel.location, layeredPane)

        val g2d = g.create() as? Graphics2D ?: return
        try {
            // Enable anti-aliasing and high quality rendering
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)

            // Use breathing color if enabled, otherwise use the configured dot color
            val dotColor = if (settings.breathingLightEnabled) {
                currentBreathingColor ?: Color.decode("#" + settings.dotColor)
            } else {
                Color.decode("#" + settings.dotColor)
            }
            g2d.color = dotColor

            // Use configured dot size and position offset
            val dotSize = settings.dotSize
            val x = location.x + settings.offsetX
            val y = location.y + tabLabel.height / 2 - dotSize / 2 + settings.offsetY
            g2d.fillOval(x, y, dotSize, dotSize)
        } catch (e: Exception) {
            // Prevent painting errors from breaking the UI
        } finally {
            g2d.dispose()
        }
    }

    fun dispose() {
        breathingService.removeListener(breathingListener)
    }
}
