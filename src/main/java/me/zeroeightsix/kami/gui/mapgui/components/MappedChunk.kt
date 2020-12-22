package me.zeroeightsix.kami.gui.mapgui.components

import me.zeroeightsix.kami.gui.mapgui.Colours
import me.zeroeightsix.kami.gui.mapgui.MapGui
import me.zeroeightsix.kami.util.color.ColorHolder
import me.zeroeightsix.kami.util.graphics.GlStateUtils
import me.zeroeightsix.kami.util.graphics.RenderUtils2D
import me.zeroeightsix.kami.util.graphics.VertexHelper
import me.zeroeightsix.kami.util.math.Vec2d
import net.minecraft.world.biome.BiomeProvider
import net.minecraft.world.gen.layer.GenLayer

class MappedChunk(var chunkX : Int, var chunkY : Int, var layers : GenLayer ) {
    val defaultColor = ColorHolder(0,0,0,0)//Completely see-through
    var colours : IntArray = IntArray(256)

    init {
        colours =  layers.getInts((chunkX shl 4),(chunkY shl 4),16,16)
    }


    fun render( startPos : Vec2d, size : Double){
        val vertexHelper = VertexHelper(GlStateUtils.useVbo())
        for ( x in 0..15){
            for (y in 0..15){
                val colorToDraw : ColorHolder = Colours.colours.getOrDefault(colours[x + (y shl 4)], defaultColor)

                MapGui.drawRect(startPos.x + (x * (size / 16)), startPos.y + (y * (size / 16)),
                    startPos.x + ((x + 1) * (size / 16)), startPos.y + ((y + 1) * (size / 16)),
                    colorToDraw)
            }
        }
        val colour = ColorHolder(255,0,0)
        RenderUtils2D.drawRectOutline(vertexHelper, Vec2d(startPos.x, startPos.y),Vec2d(startPos.x + size, startPos.y + size),
            size.toFloat()/20, colour)

    }
}