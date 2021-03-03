package org.kamiblue.client.gui.rgui.component

import baritone.utils.IRenderer.buffer
import baritone.utils.IRenderer.tessellator
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation
import org.kamiblue.client.util.graphics.VertexHelper
import org.kamiblue.client.util.math.Vec2d
import org.kamiblue.client.util.math.Vec2f
import org.lwjgl.opengl.GL11.*

class IconButton(name: String, val icon:ResourceLocation, action: (Button) -> Unit): Button(name, action) {

    override fun onRender(vertexHelper: VertexHelper, absolutePos: Vec2f) {
        mc.renderEngine.bindTexture(icon)
        GlStateManager.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)

        buffer.begin(GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_TEX)
        buffer.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).endVertex()
        buffer.pos(0.0, this.height.toDouble(), 0.0).tex(0.0, 1.0).endVertex()
        buffer.pos(this.width.toDouble(), 0.0, 0.0).tex(1.0, 0.0).endVertex()
        buffer.pos(this.width.toDouble(), this.height.toDouble(), 0.0).tex(1.0, 1.0).endVertex()
        tessellator.draw()

        GlStateManager.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
    }
}