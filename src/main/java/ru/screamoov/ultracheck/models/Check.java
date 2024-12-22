package ru.screamoov.ultracheck.models;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Check {
    public Player player;
    public Player moderator;
    public int time;
    public boolean stopped;
    public Status

    public Check(Player player, Player moderator) {
        this.player = player;
        this.moderator = moderator;
        time = 0;
        new BukkitRunnable() {
            public void run() {

            }
        }
    }
}
