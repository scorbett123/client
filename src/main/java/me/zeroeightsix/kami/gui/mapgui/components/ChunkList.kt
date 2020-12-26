package me.zeroeightsix.kami.gui.mapgui.components

class ChunkList : ArrayList<MappedChunk>() {

    fun doesContainChunk(x : Int, z : Int) : Boolean{
        for (i in 0 until size){
            if((get(i).chunkX == x) and (get(i).chunkY == z)){
                return true
            }
        }
        return false
    }
}