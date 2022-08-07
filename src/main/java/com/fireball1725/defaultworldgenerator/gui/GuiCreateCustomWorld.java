package com.fireball1725.defaultworldgenerator.gui;

import com.fireball1725.defaultworldgenerator.config.ConfigGeneralSettings;
import com.fireball1725.defaultworldgenerator.lib.Log;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.world.WorldType;

import java.lang.reflect.Field;

public class GuiCreateCustomWorld extends GuiCreateWorld {
    public GuiCreateCustomWorld(GuiScreen screen) {
        super(screen);
        try {
            //The world type to generate
            Field field_146331_K = GuiCreateWorld.class.getDeclaredField("field_146331_K");
            field_146331_K.setAccessible(true);

            this.field_146334_a = "";

            int worldGenType = 0;
            //Find all valid world gen types
            for (WorldType worldType : WorldType.worldTypes) {
                if (worldType != null && worldType.getCanBeCreated()) {
                    //Default world type query
                    if (worldType.getWorldTypeName().equalsIgnoreCase(ConfigGeneralSettings.generalWorldGenerator)) {
                        worldGenType = worldType.getWorldTypeID();
//                        Log.debug("Changed world type to " + worldType.getTranslateName());
                    }
                    //Superflat world type...
                    if (worldType.getWorldTypeName().equalsIgnoreCase("flat")) {
                        StringBuilder flatworldconfig = new StringBuilder();
                        for (int j = 0; j < ConfigGeneralSettings.generalFlatWorldConfig.length; j++) {
                            String[] tmp = ConfigGeneralSettings.generalFlatWorldConfig[j].split(" ");
                            if (tmp.length == 1) {
                                flatworldconfig.append(tmp[0]);
                            } else {
                                String blockName = tmp[1];
                                int blockQty = Integer.parseInt(tmp[0]);
                                int blockMeta = 0;
                                if (tmp.length == 3) {
                                    blockMeta = Integer.parseInt(tmp[2]);
                                }

                                int blockID = Block.getIdFromBlock(Block.getBlockFromName(blockName));

                                if (blockID == -1) {
                                    Log.fatal(blockName + " not found, please check the config file...");
                                } else {

                                    flatworldconfig.append(blockQty)
                                                   .append("x")
                                                   .append(blockID);
                                    if (blockMeta != 0) {
                                        flatworldconfig.append(":")
                                                       .append(blockMeta);
                                    }
                                }
                            }
                            if (j != ConfigGeneralSettings.generalFlatWorldConfig.length - 1) {
                                if (!flatworldconfig.toString().endsWith(";")) {
                                    flatworldconfig.append(",");
                                }
                            }
                        }
                        //Set preset
                        this.field_146334_a = flatworldconfig.toString();
                    }
                }
            }
            //Update GUI
            field_146331_K.setInt(this, worldGenType);

        } catch(Exception ex) {
            Log.fatal("Fatal Error:");
            Log.fatal(ex);
        }
    }

}
