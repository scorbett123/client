package me.zeroeightsix.kami.gui.mapgui

import me.zeroeightsix.kami.util.color.ColorHolder
import net.minecraft.world.biome.Biome


object Colours {
    var colours : MutableMap<Short, ColorHolder> = HashMap()
    //Biome colors are 0 - 170
    //Hopefully none of these are wrong. Colours sourced from https://minecraft.gamepedia.com/Biome/IDs_before_1.13
    init {
        colours.put(0, ColorHolder(0x000070))
        colours.put(1, ColorHolder(0x8DB360))
        colours.put(2, ColorHolder(0xFA9418))
        colours.put(3, ColorHolder(0x606060))
        colours.put(4, ColorHolder(0x056621))
        colours.put(5, ColorHolder(0x0B6659))
        colours.put(6, ColorHolder(0x07F9B2))
        colours.put(7, ColorHolder(0x0000FF))
        colours.put(8, ColorHolder(0xFF0000))
        colours.put(9, ColorHolder(0x8080FF))
        colours.put(10, ColorHolder(0x9090A0))
        colours.put(11, ColorHolder(0xA0A0FF))
        colours.put(12, ColorHolder(0xFFFFFF))
        colours.put(13, ColorHolder(0xA0A0A0))
        colours.put(14, ColorHolder(0xFF00FF))
        colours.put(15, ColorHolder(0xA000FF))
        colours.put(16, ColorHolder(0xFADE55))
        colours.put(17, ColorHolder(0xD25F12))
        colours.put(18, ColorHolder(0x22551C))
        colours.put(19, ColorHolder(0x163933))
        colours.put(20, ColorHolder(0x72789A))
        colours.put(21, ColorHolder(0x537B09))
        colours.put(22, ColorHolder(0x2C4205))
        colours.put(23, ColorHolder(0x628B17))
        colours.put(24, ColorHolder(0x000030))
        colours.put(25, ColorHolder(0xA2A284))
        colours.put(26, ColorHolder(0xFAF0C0))
        colours.put(27, ColorHolder(0x307444))
        colours.put(28, ColorHolder(0x1F5F32))
        colours.put(29, ColorHolder(0x40511A))
        colours.put(30, ColorHolder(0x31554A))
        colours.put(31, ColorHolder(0x243F36))
        colours.put(32, ColorHolder(0x596651))
        colours.put(33, ColorHolder(0x545F3E))
        colours.put(34, ColorHolder(0x507050))
        colours.put(35, ColorHolder(0xBDB25F))
        colours.put(36, ColorHolder(0xA79D64))
        colours.put(37, ColorHolder(0xD94515))
        colours.put(38, ColorHolder(0xB09765))
        colours.put(39, ColorHolder(0xCA8C65))

        colours.put(129, ColorHolder(0xB5DB88))
        colours.put(130, ColorHolder(0xFFBC40))
        colours.put(131, ColorHolder(0x888888))
        colours.put(132, ColorHolder(0x6A7425))
        colours.put(133, ColorHolder(0x596651))
        colours.put(134, ColorHolder(0x2FFFDA))
        colours.put(140, ColorHolder(0xB4DCDC))
        colours.put(149, ColorHolder(0x7BA331))
        colours.put(151, ColorHolder(0x8AB33F))
        colours.put(155, ColorHolder(0x589C6C))
        colours.put(156, ColorHolder(0x47875A))
        colours.put(157, ColorHolder(0x687942))
        colours.put(158, ColorHolder(0x597D72))
        colours.put(160, ColorHolder(0x6B5F4C))
        colours.put(161, ColorHolder(0x6D7766))
        colours.put(162, ColorHolder(0x789878))
        colours.put(163, ColorHolder(0xE5DA87))
        colours.put(164, ColorHolder(0xCFC58C))
        colours.put(165, ColorHolder(0xFF6D3D))
        colours.put(166, ColorHolder(0xD8DF8D))
        colours.put(167, ColorHolder(0xF2B48D))
    }

    fun getColor(id : Short): ColorHolder{
        if (id < 256){
            return colours.getOrDefault(id, ColorHolder(0,0,0,0))
        }
        return ColorHolder(0,0,0,0)
    }
}