package me.zeroeightsix.kami.util.Mapping

import net.minecraft.world.WorldType
import net.minecraft.world.gen.ChunkGeneratorSettings
import net.minecraft.world.gen.layer.*

class BiomeGen {
    //Done this otherwise It thinks that ChunkGeneratorSettings are NotNull, therefore it crashes.
    fun initializeAllBiomeGenerators(seed: Long, p_180781_2_: WorldType, p_180781_3_: ChunkGeneratorSettings?): Array<GenLayer> {
        var genlayer: GenLayer = GenLayerIsland(1L)
        genlayer = GenLayerFuzzyZoom(2000L, genlayer)
        val genlayeraddisland: GenLayer = GenLayerAddIsland(1L, genlayer)
        val genlayerzoom: GenLayer = GenLayerZoom(2001L, genlayeraddisland)
        var genlayeraddisland1: GenLayer = GenLayerAddIsland(2L, genlayerzoom)
        genlayeraddisland1 = GenLayerAddIsland(50L, genlayeraddisland1)
        genlayeraddisland1 = GenLayerAddIsland(70L, genlayeraddisland1)
        val genlayerremovetoomuchocean: GenLayer = GenLayerRemoveTooMuchOcean(2L, genlayeraddisland1)
        val genlayeraddsnow: GenLayer = GenLayerAddSnow(2L, genlayerremovetoomuchocean)
        val genlayeraddisland2: GenLayer = GenLayerAddIsland(3L, genlayeraddsnow)
        var genlayeredge: GenLayer = GenLayerEdge(2L, genlayeraddisland2, GenLayerEdge.Mode.COOL_WARM)
        genlayeredge = GenLayerEdge(2L, genlayeredge, GenLayerEdge.Mode.HEAT_ICE)
        genlayeredge = GenLayerEdge(3L, genlayeredge, GenLayerEdge.Mode.SPECIAL)
        var genlayerzoom1: GenLayer = GenLayerZoom(2002L, genlayeredge)
        genlayerzoom1 = GenLayerZoom(2003L, genlayerzoom1)
        val genlayeraddisland3: GenLayer = GenLayerAddIsland(4L, genlayerzoom1)
        val genlayeraddmushroomisland: GenLayer = GenLayerAddMushroomIsland(5L, genlayeraddisland3)
        val genlayerdeepocean: GenLayer = GenLayerDeepOcean(4L, genlayeraddmushroomisland)
        val genlayer4 = GenLayerZoom.magnify(1000L, genlayerdeepocean, 0)
        val i = 4
        val lvt_7_1_ = GenLayerZoom.magnify(1000L, genlayer4, 0)
        val genlayerriverinit: GenLayer = GenLayerRiverInit(100L, lvt_7_1_)
        val genlayerbiomeedge = p_180781_2_.getBiomeLayer(seed, genlayer4, p_180781_3_)
        val lvt_9_1_ = GenLayerZoom.magnify(1000L, genlayerriverinit, 2)
        var genlayerhills: GenLayer = GenLayerHills(1000L, genlayerbiomeedge, lvt_9_1_)
        var genlayer5 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2)
        genlayer5 = GenLayerZoom.magnify(1000L, genlayer5, i)
        val genlayerriver: GenLayer = GenLayerRiver(1L, genlayer5)
        val genlayersmooth: GenLayer = GenLayerSmooth(1000L, genlayerriver)
        genlayerhills = GenLayerRareBiome(1001L, genlayerhills)
        for (k in 0 until i) {
            genlayerhills = GenLayerZoom((1000 + k).toLong(), genlayerhills)
            if (k == 0) {
                genlayerhills = GenLayerAddIsland(3L, genlayerhills)
            }
            if (k == 1 || i == 1) {
                genlayerhills = GenLayerShore(1000L, genlayerhills)
            }
        }
        val genlayersmooth1: GenLayer = GenLayerSmooth(1000L, genlayerhills)
        val genlayerrivermix: GenLayer = GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth)
        val genlayer3: GenLayer = GenLayerVoronoiZoom(10L, genlayerrivermix)
        genlayerrivermix.initWorldGenSeed(seed)
        genlayer3.initWorldGenSeed(seed)
        return arrayOf(genlayerrivermix, genlayer3, genlayerrivermix)
    }
}