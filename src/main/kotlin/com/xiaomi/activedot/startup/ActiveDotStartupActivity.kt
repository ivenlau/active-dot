package com.xiaomi.activedot.startup

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.xiaomi.activedot.ui.ActiveDotUI
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.JLayeredPane

class ActiveDotStartupActivity : StartupActivity {
    override fun runActivity(project: Project) {
        val frame = com.intellij.openapi.wm.WindowManager.getInstance().getFrame(project)
        val layeredPane = frame?.rootPane?.layeredPane ?: return
        val ui = ActiveDotUI(project)

        ui.bounds = layeredPane.bounds
        layeredPane.add(ui, JLayeredPane.PALETTE_LAYER)

        layeredPane.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                ui.bounds = layeredPane.bounds
            }
        })

        // Register disposal when project is closed
        project.messageBus.connect().subscribe(
            com.intellij.openapi.project.ProjectManager.TOPIC,
            object : com.intellij.openapi.project.ProjectManagerListener {
                override fun projectClosing(beforeProject: Project) {
                    if (beforeProject == project) {
                        ui.dispose()
                    }
                }
            }
        )
    }
}
