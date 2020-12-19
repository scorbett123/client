package me.zeroeightsix.kami.seedCracker;

import java.util.Random;

public class Structure {

    public boolean test(long seed){
        return true;
    }

    public Random setRandomSeed(int seedX, int seedY, int seedZ, long seed)
    {
        long j2 = (long)seedX * 341873128712L + (long)seedY * 132897987541L + seed + (long)seedZ;
        return new Random(j2);
    }
}
