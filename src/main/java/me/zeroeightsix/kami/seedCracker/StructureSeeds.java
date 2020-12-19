package me.zeroeightsix.kami.seedCracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StructureSeeds {

public List<Structure> structureList = new ArrayList<>();

public StructureSeeds(Structure... structures){
    structureList = Arrays.asList(structures.clone());
}

public synchronized void addToStructures(Structure structure){
    structureList.add(structure);
}

public boolean test(long seed){
  //  System.out.println(structureList.size());
    for (Structure structure: structureList
         ) {
        if(!structure.test(seed)){
            return false;
        }
    }

    return true;
}
}
