package ru.screamoov.ultracheck.models;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.screamoov.ultracheck.Main;
import ru.screamoov.ultracheck.configurations.Configuration;
import ru.screamoov.ultracheck.configurations.Logs;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    public LocalDate startDate;

    public Check(Player player, Player moderator) {
        this.player = player;
        this.moderator = moderator;
        startDate = LocalDate.now();
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

                LocalDate localDate = LocalDate.now();
                String stopTime = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                Logs.config.set(uuid + ".start-time", startDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
                Logs.config.set(uuid + ".stop-time", stopTime);
                Logs.config.set(uuid + ".seconds", time);
                Logs.config.set(uuid + ".status", checkStatus);
                Logs.config.set(uuid + ".uuid", uuid.toString());
                Logs.config.set(uuid + ".moderator", moderator.getName() + " (IP: " + player.getAddress().getAddress().getHostAddress() + ")");
                Logs.config.set(uuid + ".player", player.getName() + " (IP: " + player.getAddress().getAddress().getHostAddress() + ")");
                Logs.config.set(uuid + ".stopped", stopped);

                Logs.save(Logs.config, Logs.file);
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
