
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
    var dotSize: Int = 6
    var offsetX: Int = 6
    var offsetY: Int = 0

    // Breathing light settings
    var breathingLightEnabled: Boolean = false
    var breathingMode: String = "fixed"  // "fixed", "random", "custom"
    var breathingFrequency: Int = 5  // 1-10
    var customColors: List<String> = listOf("ff0000", "00ff00", "0000ff", "ffff00")

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
