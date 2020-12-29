package me.zeroeightsix.kami.gui.hudgui.elements.player

import me.zeroeightsix.kami.gui.hudgui.LabelHud
import me.zeroeightsix.kami.util.math.Direction

object Direction : LabelHud(
    name = "Direction",
    category = Category.PLAYER,
    description = "Direction of player facing to"
) {

    override fun updateText() {
        val entity = mc.renderViewEntity ?: mc.player ?: return
        val direction = Direction.fromEntity(entity)
        displayText.add(direction.displayName, secondaryColor.value)
        displayText.add("(${direction.displayNameXY})", primaryColor.value)
    }

}