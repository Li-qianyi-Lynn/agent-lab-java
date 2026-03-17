package com.socops.model;

/** Represents the different game modes available in Soc Ops. */
public enum GameMode {
    BINGO("Classic 5x5 bingo grid"),
    SCAVENGER_HUNT("Checklist-based quest mode");

    private final String description;

    GameMode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
