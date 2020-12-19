package me.zeroeightsix.kami.seedCracker.finder;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class EndPillarSearcher extends Finder{

    List<Integer> pillars = new ArrayList<>();


    public List<Integer> getPillars(){
        return pillars;
    }

    public boolean ping(){
        for (int i = 0; i < 10; i++) {
            int x = (int)(42.0D * Math.cos(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
            int z = (int)(42.0D * Math.sin(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
            int height = searchXZ(x,z);
            if(height == -1)
                continue;
            if(pillars.size()>i)
                pillars.add(i, height);
            else{
                pillars.add(height);
            }
        }

        if(pillars.size()>10){
            pillars.clear();
        }

      return !pillars.contains(-1) & pillars.size() == 10;
    }

    public int searchXZ(int x, int z){
        for (int i = 0; i < 10; i++) {
            if(mc.world.getBlockState(new BlockPos(x, 76 + (i*3), z)).getBlock()== Blocks.BEDROCK){
                return i;
            }
        }
        return -1;
    }
}
