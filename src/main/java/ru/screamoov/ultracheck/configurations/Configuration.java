package ru.screamoov.ultracheck.configurations;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Configuration {
    public static final File file = new File("plugins/UltraCheck/config.yml");
    public static FileConfiguration config;

    public static void init() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
        setIfNotExist("execute-actions-on-disable", true);
        List<String> leave = new ArrayList<String>();
        leave.add("[CONSOLE] ban %player% Logging out while for checking for prohibited software. -s");
        leave.add("[BROADCAST] &c%player% &7was banned for logging out while checking for prohibited software.");
        setIfNotExist("actions.leave", leave);

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setIfNotExist(String path, Object value) {
        if (config.getString(path) == null) {
            config.set(path, value);
        }
    }
}
