package ru.screamoov.ultracheck.models;

import org.bukkit.Bukkit;

import static ru.screamoov.ultracheck.utilities.Hex.color;

public class Action {
    public String actionString;
    public boolean processed;
    public Check check;

    public Action(String actionString, Check check) {
        this.actionString = actionString;
        this.check = check;
        this.processed = false;
    }

    public void process() {
        String[] actionStrings = this.actionString.split(";");
        switch (actionStrings[0]) {
            case "[BROADCAST]":
                Bukkit.broadcastMessage(color(actionStrings[1]));

            case "[CONSOLE]":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actionStrings[1].replaceAll("%player%", check.player.getName()));
        }
        processed = true;
    }
}
