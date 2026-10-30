package com.fireball1725.defaultworldgenerator.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.fireball1725.defaultworldgenerator.helper.ConfigurationHelper;

public class ConfigurationFile {

    public static Configuration configuration;

    private static final String[] defaultFlatWorldConfig = { "2;", "1 minecraft:bedrock", "52 minecraft:sandstone",
            "1 minecraft:dirt 2", ";2;", "biome_1", "village" };

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
        boolean lockWorldGenerator = configuration.hasKey("general", "Lock World Generator")
                && configuration.get("general", "Lock World Generator", false).getBoolean(false);
        ConfigGeneralSettings.generalWorldGeneratorWhitelist = ConfigurationHelper.getString(
                configuration,
                "World Generator Whitelist",
                "general",
                lockWorldGenerator ? new String[] { ConfigGeneralSettings.generalWorldGenerator } : new String[0],
                "Allowed world generators. Leave empty to allow all. Hold Alt while clicking to cycle through all generators.");
        configuration.getCategory("general").remove("Lock World Generator");
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
