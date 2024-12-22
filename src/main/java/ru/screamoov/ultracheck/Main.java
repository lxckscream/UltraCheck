package ru.screamoov.ultracheck;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.screamoov.ultracheck.configurations.Configuration;
import ru.screamoov.ultracheck.configurations.Logs;
import ru.screamoov.ultracheck.controllers.PlayerController;
import ru.screamoov.ultracheck.managers.ChecksManager;
import ru.screamoov.ultracheck.models.Check;
import ru.screamoov.ultracheck.models.Status;

public final class Main extends JavaPlugin {
    static Main instance;
    static ChecksManager checksManager;

    @Override
    public void onEnable() {
        instance = this;

        checksManager = new ChecksManager();
        Configuration.init();
        Logs.init();

        Bukkit.getPluginManager().registerEvents(new PlayerController(), this);
    }

    @Override
    public void onDisable() {
        if (Configuration.config != null) {
            if (Configuration.config.getBoolean("execute-actions-on-disable")) {
                checksManager.checks.forEach(check -> {
                    check.checkStatus = Status.FAILED;
                    check.stopped = true;
                });
            }
        }
    }

    public static ChecksManager getChecksManager() {
        return checksManager;
    }

    public static Main getInstance() {
        return instance;
    }
}
