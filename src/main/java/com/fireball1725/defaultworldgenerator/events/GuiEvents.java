package com.fireball1725.defaultworldgenerator.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraftforge.client.event.GuiScreenEvent;

import org.lwjgl.input.Keyboard;

import com.fireball1725.defaultworldgenerator.config.ConfigGeneralSettings;
import com.fireball1725.defaultworldgenerator.gui.GuiCreateCustomWorld;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiEvents {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onButtonClickPre(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if (event.gui instanceof GuiSelectWorld) {
            if (event.button.id == 3) {
                GuiCreateCustomWorld guiCreateWorld = new GuiCreateCustomWorld(Minecraft.getMinecraft().currentScreen);
                event.gui.mc.displayGuiScreen(guiCreateWorld);
                event.button.func_146113_a(Minecraft.getMinecraft().getSoundHandler());
                event.setCanceled(true);
            }
        }

        if (event.gui instanceof GuiCreateWorld) {
            if (event.button.id == 5) {
                if (ConfigGeneralSettings.generalLockWorldGenerator && !Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                    event.button.func_146113_a(Minecraft.getMinecraft().getSoundHandler());
                    event.setCanceled(true);
                }
            }
        }

        // Log.error(event.button.id + " - " + event.gui.toString());
    }
}
