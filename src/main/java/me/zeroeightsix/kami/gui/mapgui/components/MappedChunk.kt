package me.zeroeightsix.kami.gui.mapgui.components

import me.zeroeightsix.kami.gui.mapgui.Colours
import me.zeroeightsix.kami.gui.mapgui.MapGui
import me.zeroeightsix.kami.seed.layer.GenLayer
import me.zeroeightsix.kami.seed.layer.IntCache
import me.zeroeightsix.kami.util.color.ColorHolder
import me.zeroeightsix.kami.util.graphics.GlStateUtils
import me.zeroeightsix.kami.util.graphics.RenderUtils2D
import me.zeroeightsix.kami.util.graphics.VertexHelper
import me.zeroeightsix.kami.util.math.Vec2d


class MappedChunk(var chunkX : Int, var chunkY : Int, var layers : GenLayer ) {
    var colours : ByteArray = ByteArray(0)
    var singleColor = false
    var colorIfSingle : Byte = 0

    init {
        val colorsToCheck = layers.getInts((chunkX shl 4),(chunkY shl 4),16,16)
        val first = colorsToCheck[0]
        if(colorsToCheck.all{ i -> i == first}){
            singleColor = true
            colorIfSingle = first.toByte()
        }else {
            colours = ByteArray(256)
            for (i in colorsToCheck.indices){
                colours[i] = colorsToCheck[i].toByte()
            }
        }
        IntCache.resetIntCache()

    }


    fun render( startPos : Vec2d, size : Double){
        val vertexHelper = VertexHelper(GlStateUtils.useVbo())
        if (!singleColor) {
            for (x in 0..15) {
                for (y in 0..15) {
                    val colorToDraw: ColorHolder = Colours.getColor(colours[x + (y shl 4)])

                    MapGui.drawRect(startPos.x + (x * (size / 16)), startPos.y + (y * (size / 16)),
                        startPos.x + ((x + 1) * (size / 16)), startPos.y + ((y + 1) * (size / 16)),
                        colorToDraw)
                }
            }
        }else{
            RenderUtils2D.drawRectFilled(vertexHelper, Vec2d(startPos.x, startPos.y), Vec2d(startPos.x + size, startPos.y + size), Colours.getColor(colorIfSingle))

        }
    }

    override fun equals(other: Any?): Boolean {
        if(other is MappedChunk){
            return (this.chunkX == other.chunkX) and (this.chunkY == other.chunkY)
        }
        return false
    }

    override fun hashCode(): Int {
        var result = chunkX
        result = 31 * result + chunkY
        return result
    }

}