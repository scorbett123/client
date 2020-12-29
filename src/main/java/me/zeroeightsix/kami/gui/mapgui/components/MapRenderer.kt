package me.zeroeightsix.kami.gui.mapgui.components

import me.zeroeightsix.kami.gui.mapgui.MapGui
import me.zeroeightsix.kami.module.modules.client.GuiColors
import me.zeroeightsix.kami.module.modules.client.Mapping
import me.zeroeightsix.kami.seed.layer.GenLayer
import me.zeroeightsix.kami.util.Mapping.BiomeGen
import me.zeroeightsix.kami.util.math.Vec2d
import net.minecraft.client.Minecraft
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.WorldType

object MapRenderer{
    var seed : Long = 4892758963420916476L
    var layers : GenLayer = BiomeGen().initializeAllBiomeGenerators(seed, WorldType.DEFAULT, null)[1]
    var data : ChunkList = ChunkList()
    var centerPos : ChunkPos = ChunkPos(0,0)
    var currentThread : Thread? = null
    var ySize : Int = 100
    var xSize : Int = 120
    var renderSizeX : Int = 250
    var renderSizeY: Int = 250
    var renderPosX: Int = 200
    var renderPosY: Int = 200

    var mapGen : Runnable = Runnable {
        while(Mapping.isEnabled) {
            for (x in -(xSize/2)..(xSize/2)) {
                for (y in -(ySize/2)..(ySize/2)) {
                    if(data.doesContainChunk(x+ centerPos.x,
                            y + centerPos.z)){
                        continue
                    }
                    val chunk = MappedChunk(x+ centerPos.x,
                        y + centerPos.z, layers)
                    data.add(chunk)
                }
            }
        }
    }

    fun init(){
        centerPos = ChunkPos(Minecraft.getMinecraft().player.position)
        currentThread?.interrupt()
        currentThread = Thread(mapGen)
        currentThread?.start()
    }

    val borderWidth = 5

    fun render(){

        val size = ((renderSizeY - (borderWidth/2)) / ySize).toDouble()
        val x = renderPosX + (xSize * size)/2
        val y = renderPosY + (ySize * size)/2
        val borderDistY = (ySize * (size /2)) + borderWidth
        val borderDistX = (xSize * (size /2)) + borderWidth
        MapGui.drawRect(x - borderDistX, y - borderDistY,
            x + borderDistX + size, y + borderDistY + size,
            GuiColors.primary)
        val iterator : MutableList<MappedChunk> = ArrayList()
        iterator.addAll(data) //Do this to stop concurrent modification errors
        for( i in iterator){
                val posX =x+((i.chunkX - centerPos.x).toDouble() * size)
                val posY = y+((i.chunkY - centerPos.z).toDouble() * size)
                if((posX > renderPosX) and (posY > renderPosY) and (posX < renderPosX + renderSizeX) and (posY < renderPosY + renderSizeY) )
                i.render(Vec2d(posX, posY),size)
        }

    }

    fun setBiomeSeed(seed : Long){
        this.seed = seed
        data.clear()
        init()
    }


    fun updateSize(posX : Int, posY : Int, width : Int, height : Int){
        renderSizeX = width
        renderSizeY = height
        renderPosX = posX
        renderPosY = posY
    }
}