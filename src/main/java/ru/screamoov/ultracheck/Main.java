package ru.screamoov.ultracheck;

import org.bukkit.plugin.java.JavaPlugin;
import ru.screamoov.ultracheck.configurations.Configuration;

public final class Main extends JavaPlugin {
    static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        Configuration.init();

    }

    @Override
    public void onDisable() {
        if (Configuration.config != null) {
            if (Configuration.config.getBoolean("execute-actions-on-disable")) {
                for ()
            }
        }
    }
}
