package me.zeroeightsix.kami.gui.mapgui.components

import me.zeroeightsix.kami.module.modules.client.Mapping
import me.zeroeightsix.kami.util.Mapping.BiomeGen
import me.zeroeightsix.kami.util.math.Vec2d
import net.minecraft.client.Minecraft
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.WorldType
import net.minecraft.world.gen.layer.GenLayer

object MapRenderer {
    var seed = 4892758963420916476L
var layers : GenLayer = BiomeGen().initializeAllBiomeGenerators(seed, WorldType.DEFAULT, null)[1]
    var data : ChunkList = ChunkList()
    var centerPos : ChunkPos = ChunkPos(0,0)
    var mapGen : Thread = Thread{
        while(Mapping.isEnabled) {
            for (x in -40..40) {
                for (y in -30..30) {
                    if(data.doesContainChunk(x,y)){
                        continue
                    }
                    val chunk = MappedChunk(x, y, layers)
                    data.add(chunk)
                }
            }
        }
    }

    fun init(){
        println(mapGen.isAlive)
        println(mapGen.state)
          mapGen.start()
    }

    fun render(){
        val size = 10.00
        val width = Minecraft.getMinecraft().displayWidth
        val height = Minecraft.getMinecraft().displayHeight
        val iterator : MutableList<MappedChunk> = ArrayList()
        iterator.addAll(data)
        for( x in iterator){
                val posX = (width/2)+((x.chunkX + centerPos.x).toDouble() * size)
                val posY = (height/2)+((x.chunkY + centerPos.z).toDouble() * size)
//                if((posX < 0) or (posX>width) or (posY < 0) or (posY>height)){
//                    data.remove(x)
//                    return
//                }
                x.render(Vec2d(posX, posY),size)
        }
    }

}