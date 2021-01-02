package me.zeroeightsix.kami.gui.mapgui.components

import me.zeroeightsix.kami.seed.layer.GenLayer
import me.zeroeightsix.kami.util.math.Vec2d

class MappedRegion(val posX : Int, val posY : Int, val genLayer : GenLayer) {//Represents a 512 by 512 region

    var dataArray = Array(1024){
        var posX = it shl 5
        var posY = it - (posX shr 5)
        return@Array MappedChunk(posX + this.posX, posY + this.posY, this.genLayer)
    }

    fun render(startPos : Vec2d, chunkSize : Float){
        for (chunk in dataArray){
            chunk.render(startPos.plus(chunk.chunkX*chunkSize.toDouble(), chunk.chunkY*chunkSize.toDouble()),
                chunkSize.toDouble())
        }
    }

}