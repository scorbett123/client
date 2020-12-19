package me.zeroeightsix.kami.seedCracker.Structures;

import me.zeroeightsix.kami.seedCracker.Structure;

import java.util.Random;

public class OceanMonument extends Structure {

    int chunkX, chunkZ;
    int distance = 32;

    public OceanMonument(int chunkX, int chunkz){
        this.chunkX = chunkX;
        this.chunkZ = chunkz;
    }

    @Override
    public boolean test(long seed) {
        int i = chunkX;
        int j = chunkZ;

        if (i < 0)
        {
            i -= distance - 1;
        }

        if (j < 0)
        {
            j -= distance - 1;
        }

        int k = i / distance;
        int l = j / distance;
        Random random = setRandomSeed(k, l, 10387313, seed);
        k = k * distance;
        l = l * distance;
        k = k + (random.nextInt(27) + random.nextInt(27))/2;
        l = l + (random.nextInt(27) + random.nextInt(27))/2;

        return chunkX == k && chunkZ == l;
    }

    public Random setRandomSeed(int seedX, int seedY, int seedZ, long seed)
    {
        long j2 = (long)seedX * 341873128712L + (long)seedY * 132897987541L + seed + (long)seedZ;
        return new Random(j2);
    }
}