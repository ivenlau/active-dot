
package com.xiaomi.activedot.listeners

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile
import com.xiaomi.activedot.ui.ActiveDotUI

class ActiveDotTabListener : FileEditorManagerListener {

    override fun selectionChanged(event: com.intellij.openapi.fileEditor.FileEditorManagerEvent) {
        val project = event.manager.project
        val frame = com.intellij.openapi.wm.WindowManager.getInstance().getFrame(project)
        frame?.rootPane?.layeredPane?.components?.forEach { component ->
            if (component is ActiveDotUI) {
                component.repaint()
            }
        }
    }

    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
        // Not needed
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        // Not needed
    }
}
