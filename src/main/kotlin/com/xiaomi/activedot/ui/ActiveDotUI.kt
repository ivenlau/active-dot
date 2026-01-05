
package com.xiaomi.activedot.ui

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.Project
import com.intellij.ui.tabs.impl.JBEditorTabs
import com.xiaomi.activedot.settings.ActiveDotSettingsState
import java.awt.Color
import java.awt.Graphics
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
        val tabs = currentWindow.tabbedPane.component as? JBEditorTabs ?: return
        val selectedTab = tabs.selectedInfo ?: return
        val tabLabel = tabs.getTabLabel(selectedTab) ?: return
        val layeredPane = this.parent ?: return

        val location = SwingUtilities.convertPoint(tabLabel.parent, tabLabel.location, layeredPane)

        val g2d = g.create()
        try {
            g2d.color = Color.decode("#" + settings.dotColor)
            val x = location.x + 5
            val y = location.y + tabLabel.height / 2 - 3
            g2d.fillOval(x, y, 6, 6)
        } finally {
            g2d.dispose()
        }
    }
}

