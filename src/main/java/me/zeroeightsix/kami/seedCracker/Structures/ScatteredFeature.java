package me.zeroeightsix.kami.seedCracker.Structures;

import me.zeroeightsix.kami.seedCracker.Structure;

import java.util.Random;

public class ScatteredFeature extends Structure { // This is desert temples, jungle temples, Witch hut, igloo.


        int chunkX, chunkZ;
int distance = 32;

    public ScatteredFeature(int chunkX, int chunkz){
            this.chunkX = chunkX;
            this.chunkZ = chunkz;
        }

    @Override
    public boolean test(long seed) {
        int i = chunkX;
        int j = chunkZ;

        if (i < 0)
        {
            i -= this.distance - 1;
        }

        if (j < 0)
        {
            j -= this.distance - 1;
        }

        int k = i / this.distance;
        int l = j / this.distance;
        Random random = setRandomSeed(k, l, 14357617, seed);
        k = k * this.distance;
        l = l * this.distance;
        k = k + random.nextInt(this.distance - 8);
        l = l + random.nextInt(this.distance - 8);

        return chunkX == k && chunkZ == l;
    }

        public Random setRandomSeed(int seedX, int seedY, int seedZ, long seed)
        {
            long j2 = (long)seedX * 341873128712L + (long)seedY * 132897987541L + seed + (long)seedZ;
            return new Random(j2);
        }
}
