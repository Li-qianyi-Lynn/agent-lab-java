package com.socops.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.socops.data.IcebreakerPrompts;
import com.socops.model.ScavengerHuntItem;

/**
 * Service for managing Scavenger Hunt game logic.
 * Assembles hunt lists, tracks item completion, and detects victory (all items found).
 */
public final class ScavengerHuntService {

    /**
     * Creates a new scavenger hunt with all 24 prompts in random order.
     * Items start with completed = false.
     */
    public static List<ScavengerHuntItem> assembleNewHunt() {
        List<String> shuffledPrompts = new ArrayList<>(IcebreakerPrompts.ALL_PROMPTS);
        Collections.shuffle(shuffledPrompts);

        List<ScavengerHuntItem> items = new ArrayList<>();
        for (int i = 0; i < shuffledPrompts.size(); i++) {
            items.add(new ScavengerHuntItem(i, shuffledPrompts.get(i), false));
        }
        return items;
    }

    /**
     * Toggles the completion status of an item by its ID.
     * Returns a new list with the item's state flipped.
     */
    public static List<ScavengerHuntItem> toggleItemCompletion(
        List<ScavengerHuntItem> hunt,
        int itemId
    ) {
        return hunt.stream()
            .map(item -> item.id() == itemId ? item.toggleCompletion() : item)
            .toList();
    }

    /**
     * Marks all items as completed (simulates a quick win for testing).
     */
    public static List<ScavengerHuntItem> completeAllItems(List<ScavengerHuntItem> hunt) {
        return hunt.stream()
            .map(item -> item.withCompleted(true))
            .toList();
    }

    /**
     * Calculates the progress as a percentage (0-100).
     */
    public static int calculateProgress(List<ScavengerHuntItem> hunt) {
        if (hunt.isEmpty()) {
            return 0;
        }
        long completedCount = hunt.stream()
            .filter(ScavengerHuntItem::completed)
            .count();
        return (int) ((completedCount * 100) / hunt.size());
    }

    /**
     * Detects if the hunt is complete (all items found).
     */
    public static boolean isHuntComplete(List<ScavengerHuntItem> hunt) {
        return hunt.stream().allMatch(ScavengerHuntItem::completed);
    }

    /**
     * Returns the count of completed items.
     */
    public static int countCompletedItems(List<ScavengerHuntItem> hunt) {
        return (int) hunt.stream()
            .filter(ScavengerHuntItem::completed)
            .count();
    }

    /**
     * Finds an item by ID.
     */
    public static Optional<ScavengerHuntItem> findItemById(
        List<ScavengerHuntItem> hunt,
        int itemId
    ) {
        return hunt.stream()
            .filter(item -> item.id() == itemId)
            .findFirst();
    }

    private ScavengerHuntService() {
        /* service only — no instances */
    }
}
