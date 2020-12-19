package me.zeroeightsix.kami.seedCracker.finder;

import me.zeroeightsix.kami.seedCracker.Structure;
import me.zeroeightsix.kami.seedCracker.Structures.Village;
import me.zeroeightsix.kami.util.color.ColorHolder;
import me.zeroeightsix.kami.util.graphics.ESPRenderer;
import me.zeroeightsix.kami.util.text.MessageSendHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VillageSearcher extends StructureFinder{

    List<Structure> villages = new ArrayList<Structure>();

    Map<BlockPos, Block> blockPos = new HashMap<>();

    public VillageSearcher(){


        villages.add(new Village(138,4));
        villages.add(new Village(81,41));
        villages.add(new Village(97,51));
        villages.add(new Village(13,287));
    }

    @Override
    public List<Structure> getElements() {
        return villages;
    }

    @Override
    public void getDataFromChunks(Chunk chunk) {
        if(chunk.isLoaded()){
            int villageYLevel = 0;
            int villagePosX = chunk.x << 4 +2;
            int villagePosZ = chunk.z << 4 +2;
            for (int y = 62; y < 100; y++) { //I don't know how high this can be. The lowest is 62. The lag shouldn't be too bad though.
                if(chunk.getBlockState(2,y,2).getBlock() == Blocks.COBBLESTONE){

                    villageYLevel = y - 11;
                    //Possible village, There is stone in the correct place, now check if it is a well.
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                           if(chunk.getBlockState(i+3, villageYLevel,j+3).getBlock() != Blocks.COBBLESTONE){
                               System.out.println(chunk.getBlockState(i+3, villageYLevel,j+3).getBlock());
                               System.out.println((i+3) + "  " + villageYLevel + "   " + (j+3));
                               return;
                           }
                        }
                    }

                    MessageSendHelper.sendChatMessage("Village found at.");
                    MessageSendHelper.sendChatMessage(chunk.x+"  "+ chunk.z);
                    villages.add(new Village(chunk.x, chunk.z));
                }
            }
        }
    }
}
