package me.zeroeightsix.kami.seedCracker;

public class BiomeSeedFinder {

    public void start(long structureSeed){
        for (long i = 0; i < 1<<16; i++) {
            long seed = i<<48 | structureSeed;
        }
    }
}
