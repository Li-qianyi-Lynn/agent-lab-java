package com.socops.service;

import com.socops.model.ScavengerHuntItem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Validates scavenger hunt assembly, item toggling, progress tracking,
 * and victory detection provided by {@link ScavengerHuntService}.
 */
class ScavengerHuntTests {

    /* ── hunt creation ────────────────────────────────────────── */

    @Test
    @DisplayName("Assembled hunt contains exactly twenty-four items")
    void assembledHuntHasTwentyFourItems() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();
        assertEquals(24, hunt.size(),
                "A scavenger hunt must contain 24 items (one fewer than bingo grid size)");
    }

    @Test
    @DisplayName("Every item starts out uncompleted")
    void allItemsStartUncompleted() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();
        for (ScavengerHuntItem item : hunt) {
            assertFalse(item.completed(),
                    "Item id=" + item.id() + " should begin uncompleted");
        }
    }

    @Test
    @DisplayName("Every item has a unique ID from 0 to 23")
    void itemsHaveSequentialIds() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();
        for (int i = 0; i < 24; i++) {
            assertEquals(i, hunt.get(i).id(),
                    "Item at position " + i + " should have id " + i);
        }
    }

    /* ── item toggling ────────────────────────────────────────── */

    @Test
    @DisplayName("Toggling an item marks it as completed")
    void toggleItemMarksAsCompleted() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();
        int targetId = 0;

        List<ScavengerHuntItem> afterToggle = ScavengerHuntService.toggleItemCompletion(hunt, targetId);
        assertTrue(afterToggle.get(targetId).completed(),
                "First toggle should mark the item as completed");
    }

    @Test
    @DisplayName("Toggling an item twice reverts it to uncompleted")
    void toggleItemTwiceRevertsStatus() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();
        int targetId = 5;

        List<ScavengerHuntItem> afterFirstToggle =
            ScavengerHuntService.toggleItemCompletion(hunt, targetId);
        assertTrue(afterFirstToggle.get(targetId).completed(),
                "First toggle should mark as completed");

        List<ScavengerHuntItem> afterSecondToggle =
            ScavengerHuntService.toggleItemCompletion(afterFirstToggle, targetId);
        assertFalse(afterSecondToggle.get(targetId).completed(),
                "Second toggle should revert to uncompleted");
    }

    @Test
    @DisplayName("Toggling one item does not affect others")
    void toggleOneItemDoesNotAffectOthers() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();
        int targetId = 10;

        List<ScavengerHuntItem> afterToggle = ScavengerHuntService.toggleItemCompletion(hunt, targetId);

        for (int i = 0; i < hunt.size(); i++) {
            if (i != targetId) {
                assertEquals(hunt.get(i).completed(), afterToggle.get(i).completed(),
                        "Item " + i + " should remain unchanged");
            }
        }
    }

    /* ── progress tracking ────────────────────────────────────── */

    @Test
    @DisplayName("Progress starts at 0%")
    void progressStartsAtZero() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();
        int progress = ScavengerHuntService.calculateProgress(hunt);
        assertEquals(0, progress, "Progress should start at 0%");
    }

    @Test
    @DisplayName("Progress increases correctly as items are completed")
    void progressIncreaseCorrectly() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();

        // Complete first item
        hunt = ScavengerHuntService.toggleItemCompletion(hunt, 0);
        int progressAfterOne = ScavengerHuntService.calculateProgress(hunt);
        assertEquals((100 / 24), progressAfterOne,
                "Progress should be approximately 4% after 1 of 24 items");

        // Complete half
        for (int i = 1; i < 12; i++) {
            hunt = ScavengerHuntService.toggleItemCompletion(hunt, i);
        }
        int progressAtHalf = ScavengerHuntService.calculateProgress(hunt);
        assertEquals(50, progressAtHalf,
                "Progress should be 50% when half items are completed");
    }

    @Test
    @DisplayName("Completed item counter works correctly")
    void completedCounterWorks() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();
        assertEquals(0, ScavengerHuntService.countCompletedItems(hunt),
                "Should start with 0 completed items");

        hunt = ScavengerHuntService.toggleItemCompletion(hunt, 0);
        hunt = ScavengerHuntService.toggleItemCompletion(hunt, 1);
        hunt = ScavengerHuntService.toggleItemCompletion(hunt, 2);

        assertEquals(3, ScavengerHuntService.countCompletedItems(hunt),
                "Should have 3 completed items");
    }

    /* ── victory detection ────────────────────────────────────── */

    @Test
    @DisplayName("Hunt is not complete at start")
    void huntIsNotCompleteAtStart() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();
        assertFalse(ScavengerHuntService.isHuntComplete(hunt),
                "Hunt should not be complete at the start");
    }

    @Test
    @DisplayName("Hunt is complete when all items are marked")
    void huntCompleteWhenAllItemsMarked() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();

        // Complete all items
        hunt = ScavengerHuntService.completeAllItems(hunt);

        assertTrue(ScavengerHuntService.isHuntComplete(hunt),
                "Hunt should be complete when all 24 items are marked");
        assertEquals(100, ScavengerHuntService.calculateProgress(hunt),
                "Progress should be 100% when hunt is complete");
    }

    @Test
    @DisplayName("Hunt completion is detected with 23 items complete but not 22")
    void huntCompletionDetectedAccurately() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();

        // Complete 23 items
        for (int i = 0; i < 23; i++) {
            hunt = ScavengerHuntService.toggleItemCompletion(hunt, i);
        }
        assertFalse(ScavengerHuntService.isHuntComplete(hunt),
                "Hunt should not be complete with 23 of 24 items");

        // Complete the last one
        hunt = ScavengerHuntService.toggleItemCompletion(hunt, 23);
        assertTrue(ScavengerHuntService.isHuntComplete(hunt),
                "Hunt should be complete with all 24 items");
    }

    /* ── item lookup ──────────────────────────────────────────── */

    @Test
    @DisplayName("Finding an item by ID returns the correct item")
    void findItemByIdWorks() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();
        Optional<ScavengerHuntItem> found = ScavengerHuntService.findItemById(hunt, 5);

        assertTrue(found.isPresent(), "Should find item with ID 5");
        assertEquals(5, found.get().id(), "Found item should have ID 5");
    }

    @Test
    @DisplayName("Finding a non-existent item returns empty Optional")
    void findNonExistentItemReturnsEmpty() {
        List<ScavengerHuntItem> hunt = ScavengerHuntService.assembleNewHunt();
        Optional<ScavengerHuntItem> found = ScavengerHuntService.findItemById(hunt, 999);

        assertFalse(found.isPresent(), "Should not find non-existent item");
    }
}
