package me.zeroeightsix.kami.seedCracker.finder;

import me.zeroeightsix.kami.event.events.ChunkEvent;
import me.zeroeightsix.kami.seedCracker.Structure;
import me.zeroeightsix.kami.util.event.Listener;
import me.zeroeightsix.kami.util.graphics.ESPRenderer;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Searcher extends Finder{

    EndPillarSearcher endPillars = new EndPillarSearcher();
    List<StructureFinder> structureFinders = new ArrayList<>();


    public Searcher(){
        structureFinders.add(new VillageSearcher());
    }
    public boolean ping(){
        //search for items.
        if(mc.player.dimension == 1){
            return endPillars.ping();
        }else{
          return false;
        }

    }

    public List<Integer> getPillars(){
        return endPillars.getPillars();
    }

    public void getDataFromChunk(Chunk chunk) {
        structureFinders.forEach(structureFinder -> structureFinder.getDataFromChunks(chunk));
    }

    public List<Structure> getAllElements(){
//        List<Structure> elements = new ArrayList<>();
//
//        structureFinders.forEach(structureFinder -> elements.addAll(structureFinder.getElements()));
//        return elements;
        return new ArrayList<>();
    }
}
