package me.zeroeightsix.kami.seedCracker;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Pillars {

    List<Integer> pillarSeeds;

    public Pillars(List<Integer> pillarHeights){
        pillarSeeds = new PillarSeedFinder().getPillarSeed(pillarHeights);
    }

    public static class PillarSeedFinder{


        public List<Integer> getHeights(long seed){
            List<Integer> list = Lists.newArrayList(ContiguousSet.create(Range.closedOpen(0, 10), DiscreteDomain.integers()));
            Collections.shuffle(list, new Random(seed));
            return list;
        }

        public List<Integer> getPillarSeed(List<Integer> pillarHeights){
            List<Integer> pillarSeeds = new ArrayList<>();
            for (int i = 0; i < 1<<16; i++) {
                if(getHeights(i).equals(pillarHeights)){
                    pillarSeeds.add(i);
                }
            }
            return pillarSeeds;
        }
    }
}
