package ru.screamoov.ultracheck.models;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.screamoov.ultracheck.Main;
import ru.screamoov.ultracheck.configurations.Configuration;
import ru.screamoov.ultracheck.configurations.Logs;

import java.util.Random;
import java.util.UUID;

public class Check {
    public Player player;
    public String playerName;
    public Player moderator;
    public int time;
    public boolean stopped;
    public Status checkStatus;
    public UUID uuid;

    public Check(Player player, Player moderator) {
        this.player = player;
        this.moderator = moderator;
        playerName = player.getName();
        uuid = new UUID(new Random().nextLong(), new Random().nextLong());
        time = 0;
        new BukkitRunnable() {
            public void run() {
                if (!stopped) {
                    time++;
                } else {
                    if (checkStatus == Status.FAILED) executeFailedActions();
                    else if (checkStatus == Status.KEEPING) executeKeepingActions();
                    else if (checkStatus == Status.LEAVED) executeLeaveActions();
                    else executePassedActions();
                    this.cancel();
                }

                Logs.config.set(String.valueOf(uuid), "");
            }
        }.runTaskTimer(Main.getInstance(), 20L, 20L);
    }

    private void executePassedActions() {
        Configuration.config.getStringList("actions.passed").forEach(action -> {
            Action actionModel = new Action(action, this);
            actionModel.process();
            if (!actionModel.processed) moderator.sendMessage(ChatColor.RED + "Error occurred while processing action: " + action + "!");
        });
    }

    private void executeLeaveActions() {
        Configuration.config.getStringList("actions.leave").forEach(action -> {
            Action actionModel = new Action(action, this);
            actionModel.process();
            if (!actionModel.processed) moderator.sendMessage(ChatColor.RED + "Error occurred while processing action: " + action + "!");
        });
    }

    private void executeKeepingActions() {
        Configuration.config.getStringList("actions.keeping").forEach(action -> {
            Action actionModel = new Action(action, this);
            actionModel.process();
            if (!actionModel.processed) moderator.sendMessage(ChatColor.RED + "Error occurred while processing action: " + action + "!");
        });
    }

    private void executeFailedActions() {
        Configuration.config.getStringList("actions.failed").forEach(action -> {
            Action actionModel = new Action(action, this);
            actionModel.process();
            if (!actionModel.processed) moderator.sendMessage(ChatColor.RED + "Error occurred while processing action: " + action + "!");
        });
    }
}
