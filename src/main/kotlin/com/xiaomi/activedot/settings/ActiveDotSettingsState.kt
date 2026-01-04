
package com.xiaomi.activedot.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "com.xiaomi.activedot.settings.ActiveDotSettingsState",
    storages = [Storage("activeDot.xml")]
)
class ActiveDotSettingsState : PersistentStateComponent<ActiveDotSettingsState> {

    var dotColor: String = "ff0000"

    override fun getState(): ActiveDotSettingsState {
        return this
    }

    override fun loadState(state: ActiveDotSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): ActiveDotSettingsState {
            return ApplicationManager.getApplication().getService(ActiveDotSettingsState::class.java)
        }
    }
}
