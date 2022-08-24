package com.fireball1725.defaultworldgenerator.config;

import com.fireball1725.defaultworldgenerator.helper.ConfigurationHelper;
import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationFile {
    public static Configuration configuration;

    private static final String[] defaultFlatWorldConfig = {
        "2;", "1 minecraft:bedrock", "52 minecraft:sandstone", "1 minecraft:dirt 2", ";2;", "biome_1", "village"
    };

    public static Configuration init(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
        return configuration;
    }

    public static void loadConfiguration() {
        ConfigGeneralSettings.generalWorldGenerator = ConfigurationHelper.getString(
                configuration,
                "World Generator",
                "general",
                "default",
                "The world generator to select by default",
                true);
        ConfigGeneralSettings.generalShowDebugWorldGenerators = ConfigurationHelper.getBoolean(
                configuration,
                "Show World Generators in Log",
                "general",
                false,
                "Enabling this will display all world generators installed, useful for debug");
        ConfigGeneralSettings.generalLockWorldGenerator = ConfigurationHelper.getBoolean(
                configuration,
                "Lock World Generator",
                "general",
                false,
                "Enable this to lock world generator to one specified");
        ConfigGeneralSettings.generalFlatWorldConfig = ConfigurationHelper.getString(
                configuration,
                "Flat World Config",
                "flatworld",
                defaultFlatWorldConfig,
                "If you set world generator to flat, specify the flat template here. (Format: Qty BlockName Meta");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
