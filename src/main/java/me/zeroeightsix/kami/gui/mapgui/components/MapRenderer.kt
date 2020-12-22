package me.zeroeightsix.kami.gui.mapgui.components

import me.zeroeightsix.kami.util.Mapping.BiomeGen
import me.zeroeightsix.kami.util.math.Vec2d
import net.minecraft.world.WorldType
import net.minecraft.world.gen.ChunkGeneratorSettings
import net.minecraft.world.gen.layer.GenLayer

class MapRenderer {
    var seed = 4892758963420916476L
var layers : GenLayer = BiomeGen().initializeAllBiomeGenerators(seed, WorldType.DEFAULT, null)[1]
    var data : MutableList<MappedChunk> = ArrayList()

    init {
        for (x in 0..8){
            for (y in 0..6){
                data.add(MappedChunk(x,y,layers))
            }
        }
    }

    fun render(){
        val size = 50.0
        for (x in data){
                x.render(Vec2d(x.chunkX.toDouble() * size,x.chunkY.toDouble() * size),size)
        }
    }

}