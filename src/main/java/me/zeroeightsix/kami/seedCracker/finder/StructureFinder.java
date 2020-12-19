package me.zeroeightsix.kami.seedCracker.finder;

import me.zeroeightsix.kami.seedCracker.Structure;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

public abstract class StructureFinder {

public abstract List<Structure> getElements();

public abstract void getDataFromChunks(Chunk chunk);
}
