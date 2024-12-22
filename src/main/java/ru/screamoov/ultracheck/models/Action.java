package ru.screamoov.ultracheck.models;

public class Action {
    public String actionString;
    public boolean processed;

    public Action(String actionString) {
        this.actionString = actionString;
        this.processed = false;
    }

    public void process() {
        String[] actionStrings = this.actionString.split(";");
    }
}
