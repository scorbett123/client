package me.zeroeightsix.kami.seedCracker.Structures;

import me.zeroeightsix.kami.seedCracker.Structure;

import java.util.Random;

public class WoodlandMansion extends Structure {

    int chunkX, chunkZ;

    public WoodlandMansion(int chunkX, int chunkz){
        this.chunkX = chunkX;
        this.chunkZ = chunkz;
    }

    @Override
    public boolean test(long seed) {
        int i = chunkX;
        int j = chunkZ;

        if (i < 0)
        {
            i -= 79;
        }

        if (j < 0)
        {
            j -= 79;
        }

        int k = i / 80;
        int l = j / 80;
        Random random = setRandomSeed(k, l, 10387319, seed);
        k = k * 80;
        l = l * 80;
        k = k + (random.nextInt(60) + random.nextInt(60))/2;
        l = l + (random.nextInt(60) + random.nextInt(60))/2;

        return chunkX == k && chunkZ == l;
    }

    public Random setRandomSeed(int seedX, int seedY, int seedZ, long seed)
    {
        long j2 = (long)seedX * 341873128712L + (long)seedY * 132897987541L + seed + (long)seedZ;
        return new Random(j2);
    }
}