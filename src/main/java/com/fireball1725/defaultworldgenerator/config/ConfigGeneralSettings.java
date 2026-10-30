package com.fireball1725.defaultworldgenerator.config;

public class ConfigGeneralSettings {

    public static String generalWorldGenerator;
    public static boolean generalShowDebugWorldGenerators;
    public static String[] generalWorldGeneratorWhitelist;
    public static String[] generalFlatWorldConfig;

    public static boolean isWorldGeneratorAllowed(String worldGenerator) {
        if (generalWorldGeneratorWhitelist.length == 0) {
            return true;
        }
        for (String allowedGenerator : generalWorldGeneratorWhitelist) {
            if (allowedGenerator.trim().equalsIgnoreCase(worldGenerator)) {
                return true;
            }
        }
        return false;
    }
}
