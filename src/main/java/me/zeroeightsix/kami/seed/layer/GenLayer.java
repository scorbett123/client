package me.zeroeightsix.kami.seed.layer;

import net.minecraft.init.Biomes;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorSettings;

public abstract class GenLayer
{
    private long worldGenSeed;
    protected GenLayer parent;
    private long chunkSeed;
    protected long baseSeed;

    public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType worldType, ChunkGeneratorSettings chunkSettings)
    {
        GenLayer genLayerBase = new GenLayerIsland(1L);
                 genLayerBase = new GenLayerFuzzyZoom(2000L, genLayerBase);
        GenLayer genLayerAddIsland = new GenLayerAddIsland(1L, genLayerBase);
        GenLayer genLayerZoom = new GenLayerZoom(2001L, genLayerAddIsland);
        GenLayer genLayerAddIsland1 = new GenLayerAddIsland(2L, genLayerZoom);
                 genLayerAddIsland1 = new GenLayerAddIsland(50L, genLayerAddIsland1);
                 genLayerAddIsland1 = new GenLayerAddIsland(70L, genLayerAddIsland1);
        GenLayer genLayerOcean = new GenLayerRemoveTooMuchOcean(2L, genLayerAddIsland1);
        GenLayer genLayerSnow = new GenLayerAddSnow(2L, genLayerOcean);
        GenLayer genLayerAddIsland3 = new GenLayerAddIsland(3L, genLayerSnow);
        GenLayer genlayeredge = new GenLayerEdge(2L, genLayerAddIsland3, GenLayerEdge.Mode.COOL_WARM);
                 genlayeredge = new GenLayerEdge(2L, genlayeredge, GenLayerEdge.Mode.HEAT_ICE);
                 genlayeredge = new GenLayerEdge(3L, genlayeredge, GenLayerEdge.Mode.SPECIAL);
        GenLayer genLayerZoom1 = new GenLayerZoom(2002L, genlayeredge);
                 genLayerZoom1 = new GenLayerZoom(2003L, genLayerZoom1);
        GenLayer genLayerAddIsland4 = new GenLayerAddIsland(4L, genLayerZoom1);
        GenLayer genLayerAddMushroomIsland = new GenLayerAddMushroomIsland(5L, genLayerAddIsland4);
        GenLayer genLayerDeepOcean = new GenLayerDeepOcean(4L, genLayerAddMushroomIsland);
        GenLayer genLayerMagnify = GenLayerZoom.magnify(1000L, genLayerDeepOcean, 0);

        int i = 4;

        GenLayer genLayerMagnify1 = GenLayerZoom.magnify(1000L, genLayerMagnify, 0);
        GenLayer genLayerRiverInit = new GenLayerRiverInit(100L, genLayerMagnify1);
        GenLayer genLayerBiome = new GenLayerBiome(200L, genLayerMagnify, worldType, chunkSettings);
                 genLayerBiome = GenLayerZoom.magnify(1000L, genLayerBiome, 2);
                 genLayerBiome = new GenLayerBiomeEdge(1000L, genLayerBiome);
        GenLayer genLayerBiomeEdge = genLayerBiome;
        GenLayer lvt_9_1_ = GenLayerZoom.magnify(1000L, genLayerRiverInit, 2);
        GenLayer genlayerhills = new GenLayerHills(1000L, genLayerBiomeEdge, lvt_9_1_);
        GenLayer genLayerRiverZoom = GenLayerZoom.magnify(1000L, genLayerRiverInit, 2);
                 genLayerRiverZoom = GenLayerZoom.magnify(1000L, genLayerRiverZoom, i);
        GenLayer genlayerriver = new GenLayerRiver(1L, genLayerRiverZoom);
        GenLayer genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
                 genlayerhills = new GenLayerRareBiome(1001L, genlayerhills);

        for (int k = 0; k < i; ++k)
        {
            genlayerhills = new GenLayerZoom((long)(1000 + k), genlayerhills);

            if (k == 0)
            {
                genlayerhills = new GenLayerAddIsland(3L, genlayerhills);
            }

            if (k == 1 || i == 1)
            {
                genlayerhills = new GenLayerShore(1000L, genlayerhills);
            }
        }

        GenLayer genlayersmooth1 = new GenLayerSmooth(1000L, genlayerhills);
        GenLayer genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
        GenLayer genlayer3 = new GenLayerVoronoiZoom(10L, genlayerrivermix);
        genlayerrivermix.initWorldGenSeed(seed);
        genlayer3.initWorldGenSeed(seed);
        return new GenLayer[] {genlayerrivermix, genlayer3, genlayerrivermix};
    }

    public GenLayer(long p_i2125_1_)
    {
        this.baseSeed = p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
    }

    public void initWorldGenSeed(long seed)
    {
        this.worldGenSeed = seed;

        if (this.parent != null)
        {
            this.parent.initWorldGenSeed(seed);
        }

        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
    }

    public void initChunkSeed(long p_75903_1_, long p_75903_3_)
    {
        this.chunkSeed = this.worldGenSeed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_1_;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_3_;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_1_;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_3_;
    }

    protected int nextInt(int p_75902_1_)
    {
        int i = (int)((this.chunkSeed >> 24) % (long)p_75902_1_);

        if (i < 0)
        {
            i += p_75902_1_;
        }

        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return i;
    }

    public abstract int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight);

    protected static boolean biomesEqualOrMesaPlateau(int biomeIDA, int biomeIDB)
    {
        if (biomeIDA == biomeIDB)
        {
            return true;
        }
        else
        {
            Biome biome = Biome.getBiome(biomeIDA);
            Biome biome1 = Biome.getBiome(biomeIDB);

            if (biome != null && biome1 != null)
            {
                if (biome != Biomes.MESA_ROCK && biome != Biomes.MESA_CLEAR_ROCK)
                {
                    return biome == biome1 || biome.getBiomeClass() == biome1.getBiomeClass();
                }
                else
                {
                    return biome1 == Biomes.MESA_ROCK || biome1 == Biomes.MESA_CLEAR_ROCK;
                }
            }
            else
            {
                return false;
            }
        }
    }

    protected static boolean isBiomeOceanic(int p_151618_0_)
    {
        return net.minecraftforge.common.BiomeManager.oceanBiomes.contains(Biome.getBiome(p_151618_0_));
    }

    /* ======================================== FORGE START =====================================*/
    protected long nextLong(long par1)
    {
        long j = (this.chunkSeed >> 24) % par1;

        if (j < 0)
        {
            j += par1;
        }

        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return j;
    }

    public static int getModdedBiomeSize(WorldType worldType, int original)
    {
        net.minecraftforge.event.terraingen.WorldTypeEvent.BiomeSize event = new net.minecraftforge.event.terraingen.WorldTypeEvent.BiomeSize(worldType, original);
        net.minecraftforge.common.MinecraftForge.TERRAIN_GEN_BUS.post(event);
        return event.getNewSize();
    }
    /* ========================================= FORGE END ======================================*/

    protected int selectRandom(int... p_151619_1_)
    {
        return p_151619_1_[this.nextInt(p_151619_1_.length)];
    }

    protected int selectModeOrRandom(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_)
    {
        if (p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_)
        {
            return p_151617_2_;
        }
        else if (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_)
        {
            return p_151617_1_;
        }
        else if (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_)
        {
            return p_151617_1_;
        }
        else if (p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_)
        {
            return p_151617_1_;
        }
        else if (p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_)
        {
            return p_151617_1_;
        }
        else if (p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_)
        {
            return p_151617_1_;
        }
        else if (p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_)
        {
            return p_151617_1_;
        }
        else if (p_151617_2_ == p_151617_3_ && p_151617_1_ != p_151617_4_)
        {
            return p_151617_2_;
        }
        else if (p_151617_2_ == p_151617_4_ && p_151617_1_ != p_151617_3_)
        {
            return p_151617_2_;
        }
        else
        {
            return p_151617_3_ == p_151617_4_ && p_151617_1_ != p_151617_2_ ? p_151617_3_ : this.selectRandom(p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_);
        }
    }
}
