package com.fireball1725.defaultworldgenerator.events;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.GuiScreenEvent;

import org.lwjgl.input.Keyboard;

import com.fireball1725.defaultworldgenerator.config.ConfigGeneralSettings;
import com.fireball1725.defaultworldgenerator.gui.GuiCreateCustomWorld;
import com.fireball1725.defaultworldgenerator.lib.Log;

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

        if (event.gui instanceof GuiCreateWorld && event.button.id == 5
                && ConfigGeneralSettings.generalWorldGeneratorWhitelist.length > 0
                && !Keyboard.isKeyDown(Keyboard.KEY_LMENU)
                && !Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {
            try {
                Field worldGenField = GuiCreateWorld.class.getDeclaredField("field_146331_K");
                worldGenField.setAccessible(true);
                int currentWorldGenType = worldGenField.getInt(event.gui);
                for (int offset = 1; offset <= WorldType.worldTypes.length; offset++) {
                    int worldGenType = (currentWorldGenType + offset) % WorldType.worldTypes.length;
                    WorldType worldType = WorldType.worldTypes[worldGenType];
                    if (worldType != null && worldType.getCanBeCreated()
                            && ConfigGeneralSettings.isWorldGeneratorAllowed(worldType.getWorldTypeName())) {
                        if (worldGenType != currentWorldGenType) {
                            // Vanilla advances once after this event and refreshes the GUI.
                            worldGenField.setInt(
                                    event.gui,
                                    (worldGenType + WorldType.worldTypes.length - 1) % WorldType.worldTypes.length);
                            return;
                        }
                        break;
                    }
                }
            } catch (ReflectiveOperationException ex) {
                Log.fatal(ex);
            }
            event.button.func_146113_a(Minecraft.getMinecraft().getSoundHandler());
            event.setCanceled(true);
        }

        // Log.error(event.button.id + " - " + event.gui.toString());
    }
}
