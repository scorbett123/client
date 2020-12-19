package me.zeroeightsix.kami.seedCracker;

import me.zeroeightsix.kami.seedCracker.Structures.OceanMonument;
import me.zeroeightsix.kami.seedCracker.Structures.ScatteredFeature;
import me.zeroeightsix.kami.seedCracker.Structures.Village;
import me.zeroeightsix.kami.seedCracker.finder.Searcher;
import me.zeroeightsix.kami.util.text.MessageSendHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.storage.WorldInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("BusyWait")
public class SeedCracker extends Thread{

    private final long multiplier = 0x5DEECE66DL;
    long addend = 0xBL;

    private static final long mask = (1L << 48) - 1;
    public boolean stopAll = false;
    float percent = 0;
    Searcher searcher = new Searcher();
    StructureSeeds structureSeedFinder = null;
    Pillars pillars;
    List<Long> structureSeeds = new ArrayList<>();
Minecraft mc = Minecraft.getMinecraft();
    public void waitForPillarSeed(){
        BiomeProvider biomeProvider = new BiomeProvider(mc.world.getSeed());
        while(!searcher.ping()){
            try {
                Thread.sleep(50);
                BlockPos playerPos = Minecraft.getMinecraft().player.getPosition();
                System.out.println(biomeProvider.getBiome(playerPos).getBiomeName());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MessageSendHelper.sendChatMessage("Searching for pillar seed with the heights : " + searcher.getPillars().toString());
        MessageSendHelper.sendChatMessage(searcher.getPillars().toString());
        pillars = new Pillars(searcher.getPillars());
        if(pillars.pillarSeeds.size() == 0){
            MessageSendHelper.sendChatMessage("No pillar seeds found, has the bedrock at the top of each tower been removed? Retrying");
            waitForPillarSeed();
        }
    }
    int threadsDone = 0;
    public void run(){

        waitForPillarSeed();
        MessageSendHelper.sendChatMessage("Found a pillarSeed : " +pillars.pillarSeeds.toString());

        structureSeedFinder = new StructureSeeds(new Village(97,51), new Village(81,41),
            new Village(14, 98),new ScatteredFeature(33,50), new ScatteredFeature(1088, -27),
            new OceanMonument(140,105));

        MessageSendHelper.sendChatMessage(String.format("Searching with %d structures", searcher.getAllElements().size()));
        for (long pillarSeed:pillars.pillarSeeds
        ) {
            for(int threadId = 0; threadId < 4; threadId++) {
                int fThreadId = threadId;

                new Thread(() -> {

                    long lower = (long)fThreadId * (1L << 14);
                    long upper = (long)(fThreadId + 1) * (1L << 14);

                    for (long j = lower; j < upper; j++) {
                        for (int k = 0; k < 1L<<16; k++) {
                            long seeds = timeMachine((j <<32) | (pillarSeed << 16) | k, 2);

                            if(j % (1L<<10) == 0 & k == 0){
                                update();
                            }

                            if(structureSeedFinder.test(seeds)){
                                gotStructureSeed(seeds);
                            }
                        }
                    }
                    threadsDone ++;
                }).start();
            }
        }
        while(threadsDone != 4){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (long structureSeed:structureSeeds
             ) {
            for (long i = 0; i < 1<<16; i++) {
                long seedToTest = (i<<48) | structureSeed;
                //Why do you complain, the last option is not "notNull"
                GenLayer intLayer = GenLayer.initializeAllBiomeGenerators(seedToTest, WorldType.DEFAULT,null)[1];
                BlockPos playerPos = Minecraft.getMinecraft().player.getPosition();
                intLayer.getInts(playerPos.getX(), playerPos.getZ(),1,1);
            }
        }

   }

    public long timeMachine(long currentSeed, int steps)
    {
        for (int i = 0; i < steps; i++) {
            currentSeed = ((currentSeed - addend) * 0xdfe05bcb1365L);
        }
        currentSeed ^= multiplier;
        return currentSeed  & mask;
    }

    public synchronized void update(){
        percent += 100f/64;
        MessageSendHelper.sendChatMessage(String.format("Currently searched %f structure seeds", percent));
    }

    public synchronized void gotStructureSeed(long seed){
        MessageSendHelper.sendChatMessage(String.format("Possible structure seed found : %d", seed));
        structureSeeds.add(seed);
    }

    public synchronized void getDataFromChunk(Chunk chunk){
        searcher.getDataFromChunk(chunk);
    }
}
