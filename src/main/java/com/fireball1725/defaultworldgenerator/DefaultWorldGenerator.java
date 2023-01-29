package com.fireball1725.defaultworldgenerator;

import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import com.fireball1725.defaultworldgenerator.config.ConfigGeneralSettings;
import com.fireball1725.defaultworldgenerator.config.ConfigurationFile;
import com.fireball1725.defaultworldgenerator.events.GuiEvents;
import com.fireball1725.defaultworldgenerator.lib.Log;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class DefaultWorldGenerator {

    @Mod.Instance
    public static DefaultWorldGenerator instance;

    public static Configuration configuration;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configuration = ConfigurationFile.init(event.getSuggestedConfigurationFile());

        MinecraftForge.EVENT_BUS.register(new GuiEvents());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (ConfigGeneralSettings.generalShowDebugWorldGenerators) {
            Log.info("=======================[ World Generators ]=======================");
            for (int i = 0; i < WorldType.worldTypes.length; i++) {
                if (WorldType.worldTypes[i] != null && WorldType.worldTypes[i].getCanBeCreated()) {
                    Log.info("Name: " + WorldType.worldTypes[i].getWorldTypeName());
                }
            }
            Log.info("==================================================================");
        }
    }
}
