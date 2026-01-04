
package com.xiaomi.activedot.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class ActiveDotSettingsConfigurable : Configurable {

    private var settingsPanel: ActiveDotSettingsPanel? = null

    override fun createComponent(): JComponent? {
        settingsPanel = ActiveDotSettingsPanel()
        return settingsPanel?.createPanel()
    }

    override fun isModified(): Boolean {
        return settingsPanel?.isModified() ?: false
    }

    override fun apply() {
        settingsPanel?.apply()
    }

    override fun reset() {
        settingsPanel?.reset()
    }

    override fun getDisplayName(): String {
        return "Active Tab Dot"
    }

    override fun disposeUIResources() {
        settingsPanel = null
    }
}
