package ru.screamoov.ultracheck.models;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.screamoov.ultracheck.Main;
import ru.screamoov.ultracheck.configurations.Configuration;

public class Check {
    public Player player;
    public Player moderator;
    public int time;
    public boolean stopped;
    public Status checkStatus;

    public Check(Player player, Player moderator) {
        this.player = player;
        this.moderator = moderator;
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
}
