package org.kamiblue.client.gui.rgui.windows

import net.minecraft.util.ResourceLocation
import org.kamiblue.client.gui.rgui.Component
import org.kamiblue.client.gui.rgui.component.IconButton
import org.kamiblue.client.module.modules.client.ClickGUI
import org.kamiblue.client.util.graphics.GlStateUtils
import org.kamiblue.client.util.graphics.VertexHelper
import org.kamiblue.client.util.graphics.font.HAlign
import org.kamiblue.client.util.graphics.font.VAlign
import org.kamiblue.client.util.math.Vec2f
import org.kamiblue.commons.extension.ceilToInt
import org.kamiblue.commons.extension.floorToInt
import org.lwjgl.opengl.GL11

object GuiSwitcher : ListWindow("", 0f, 0f, 40f, 40f, SettingGroup.NONE) {

    init {
        children.add(IconButton("test", ResourceLocation("kamiblue/kami_icon.png")) {})
        dockingH = HAlign.RIGHT
        dockingV = VAlign.TOP
    }

    override fun onResize() {
        super.onResize()
        dockingH = HAlign.RIGHT
        dockingV = VAlign.TOP
    }
}