package me.zeroeightsix.kami.module.modules.client

import me.zeroeightsix.kami.event.KamiEventBus
import me.zeroeightsix.kami.gui.mapgui.MapGui
import me.zeroeightsix.kami.gui.mapgui.components.MapRenderer
import me.zeroeightsix.kami.module.Module
import me.zeroeightsix.kami.module.ModuleManager
import org.lwjgl.input.Keyboard

@Module.Info(
    name = "Mapping",
    description = "Opens the mapping GUI",
    category = Module.Category.CLIENT,
    alwaysListening = true
)
object Mapping : Module() {
  

    override fun onEnable() {
        MapRenderer.init()
        if (mc.currentScreen !is MapGui) {
            HudEditor.disable()
            mc.displayGuiScreen(MapGui)
            KamiEventBus.subscribe(MapGui)
        }
    }

    override fun onDisable() {
        if (mc.currentScreen is MapGui) {
            mc.displayGuiScreen(null)
            KamiEventBus.unsubscribe(MapGui)
        }
    }

    init {
        bind.value.key = Keyboard.KEY_U
    }
}
