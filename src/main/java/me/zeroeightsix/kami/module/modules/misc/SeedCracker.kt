package me.zeroeightsix.kami.module.modules.misc

import me.zeroeightsix.kami.event.events.ChunkEvent
import me.zeroeightsix.kami.event.events.RenderWorldEvent
import me.zeroeightsix.kami.module.Module
import me.zeroeightsix.kami.module.modules.render.StorageESP
import me.zeroeightsix.kami.seedCracker.SeedCracker
import me.zeroeightsix.kami.util.event.listener
import me.zeroeightsix.kami.util.graphics.ESPRenderer

@Module.Info(
    name = "SeedCracker",
    description = "Cracks the seed of the minecraft world",
    category = Module.Category.MISC
)
object SeedCracker : Module() {

    var seedCracker : SeedCracker = SeedCracker();

    override fun onEnable() {
        super.onEnable()
seedCracker.stopAll
        seedCracker = SeedCracker()
        seedCracker.start()


    }


    init{
        listener<ChunkEvent> {  seedCracker.getDataFromChunk(it.chunk)}

        this.disable()
    }

    override fun onDisable() {
        super.onDisable()

        seedCracker.stopAll = true
    }

    override fun destroy() {
        super.destroy()
        seedCracker.stopAll = true
    }



}