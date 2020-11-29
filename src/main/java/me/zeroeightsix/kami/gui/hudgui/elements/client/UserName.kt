package me.zeroeightsix.kami.gui.hudgui.elements.client

import me.zeroeightsix.kami.gui.hudgui.HudElement
import me.zeroeightsix.kami.gui.hudgui.LabelHud
import me.zeroeightsix.kami.setting.GuiConfig.setting

@HudElement.Info(
        category = HudElement.Category.CLIENT,
        description = "User name"
)
object UserName : LabelHud("UserName") {

    private val prefix = setting("Prefix", "Welcome")
    private val suffix = setting("Suffix", "")

    override fun updateText() {
        displayText.add(prefix.value, primaryColor.value)
        displayText.add(mc.session.username, secondaryColor.value)
        displayText.add(suffix.value, primaryColor.value)
    }

}