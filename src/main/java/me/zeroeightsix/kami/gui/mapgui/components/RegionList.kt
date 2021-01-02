package me.zeroeightsix.kami.gui.mapgui.components

import java.util.function.Consumer


class RegionList : ArrayList<MappedRegion>() {

    fun doesContainRegion(x : Int, z : Int) : Boolean{
        for (i in 0 until size-1){
            if((get(i).posX == x) and (get(i).posY == z)){
                return true
            }
        }
        return false
    }

    override fun forEach(action: Consumer<in MappedRegion>) {
        val elementData: Array<MappedRegion> = this.toArray() as Array<MappedRegion>
        val size = this.size
        var i = 0
        while (i < size) {
            action.accept(elementData[i])
            i++
        }
    }
}