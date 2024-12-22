package ru.screamoov.ultracheck.configurations;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Logs {
    public static final File file = new File("plugins/UltraCheck/logs.yml");
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
    }

    public static void save(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
