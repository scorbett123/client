package me.zeroeightsix.kami.gui.mapgui

import me.zeroeightsix.kami.gui.mapgui.components.MapRenderer
import me.zeroeightsix.kami.module.ModuleManager
import me.zeroeightsix.kami.module.modules.client.Mapping
import me.zeroeightsix.kami.util.color.ColorHolder
import me.zeroeightsix.kami.util.graphics.GlStateUtils
import me.zeroeightsix.kami.util.graphics.RenderUtils2D
import me.zeroeightsix.kami.util.graphics.VertexHelper
import me.zeroeightsix.kami.util.math.Vec2d
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.world.gen.structure.MapGenVillage

object MapGui : GuiScreen() {

    var vertexHelper : VertexHelper = VertexHelper(true) // Use this so it doesn't need to be passed between every different rendering function.

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        vertexHelper = VertexHelper(GlStateUtils.useVbo())
        drawBackground(vertexHelper)
        drawMap()
    }

    private fun drawBackground(vertexHelper: VertexHelper) {
            GlStateUtils.rescaleActual()
            val color = ColorHolder(32, 30, 40, 200)
            drawRect(0.0,0.0, mc.displayWidth.toDouble(), mc.displayHeight.toDouble(), color)
    }

    private fun drawMap(){
        MapRenderer.render()
    }

    override fun initGui() {
        super.initGui()
        MapRenderer.init()
    }

    override fun onGuiClosed() {
        super.onGuiClosed()
        if(Mapping.isEnabled)
            Mapping.disable()
    }

    override fun onResize(mcIn: Minecraft, w: Int, h: Int) {
        super.onResize(mcIn, w, h)
        MapRenderer.updateSize(0,0, mc.displayWidth, mc.displayHeight)
    }

    fun drawRect(startX : Double, startY : Double, endX : Double, endY : Double, colour : ColorHolder ){
        RenderUtils2D.drawRectFilled(vertexHelper, Vec2d(startX, startY),Vec2d(endX, endY), colour)
    }
}