
package com.xiaomi.activedot.ui

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.Project
import com.intellij.ui.tabs.JBTabs
import com.xiaomi.activedot.settings.ActiveDotSettingsState
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JComponent
import javax.swing.SwingUtilities

class ActiveDotUI(private val project: Project) : JComponent() {

    private val settings = ActiveDotSettingsState.getInstance()

    init {
        isOpaque = false
    }

    override fun contains(x: Int, y: Int): Boolean {
        return false
    }

    override fun paintComponent(g: Graphics) {
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
            // 开启抗锯齿和高质量渲染
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)

            val dotColor = Color.decode("#" + settings.dotColor)
            g2d.color = dotColor

            val dotSize = 6
            val x = location.x + 5
            val y = location.y + tabLabel.height / 2 - dotSize / 2
            g2d.fillOval(x, y, dotSize, dotSize)
        } finally {
            g2d.dispose()
        }
    }
}

