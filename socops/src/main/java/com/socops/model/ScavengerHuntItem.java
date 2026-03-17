package com.socops.model;

/**
 * Represents a single item in a Scavenger Hunt quest.
 * Tracks the prompt text and whether the player has found someone who matches.
 */
public record ScavengerHuntItem(
    int id,
    String prompt,
    boolean completed
) {
    /**
     * Creates a copy of this item with the completed status toggled.
     */
    public ScavengerHuntItem toggleCompletion() {
        return new ScavengerHuntItem(this.id, this.prompt, !this.completed);
    }

    /**
     * Creates a copy of this item with a new completion status.
     */
    public ScavengerHuntItem withCompleted(boolean completed) {
        return new ScavengerHuntItem(this.id, this.prompt, completed);
    }
}
