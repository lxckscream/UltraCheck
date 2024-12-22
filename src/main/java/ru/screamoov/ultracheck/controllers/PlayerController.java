package ru.screamoov.ultracheck.controllers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.checkerframework.checker.units.qual.C;
import ru.screamoov.ultracheck.Main;
import ru.screamoov.ultracheck.models.Check;
import ru.screamoov.ultracheck.models.Status;

public class PlayerController implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Check check = new Check(player, player);
        Main.getChecksManager().checks.add(check);
        System.out.println(check.uuid);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Main.getChecksManager().checks.forEach(check -> {
            if (check.player.getName().equals(player.getName())) {
                check.checkStatus = Status.LEAVED;
                check.stopped = true;
            }
        });
    }
}
