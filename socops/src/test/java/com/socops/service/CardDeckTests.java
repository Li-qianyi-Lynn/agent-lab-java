package com.socops.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.socops.model.CardDeckCard;

/**
 * Validates card deck assembly, random card selection, reveal state,
 * and progress tracking provided by {@link CardDeckService}.
 */
class CardDeckTests {

    /* ── deck creation ────────────────────────────────────────── */

    @Test
    @DisplayName("Assembled deck contains exactly twenty-four cards")
    void assembledDeckHasTwentyFourCards() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        assertEquals(24, deck.size(),
                "A card deck must contain 24 cards");
    }

    @Test
    @DisplayName("All cards start out unrevealed")
    void allCardsStartUnrevealed() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        for (CardDeckCard card : deck) {
            assertFalse(card.revealed(),
                    "Card id=" + card.id() + " should start unrevealed");
        }
    }

    @Test
    @DisplayName("Each card has a unique sequential ID from 0 to 23")
    void cardsHaveSequentialIds() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        for (int i = 0; i < 24; i++) {
            assertEquals(i, deck.get(i).id(),
                    "Card at position " + i + " should have ID " + i);
        }
    }

    @Test
    @DisplayName("Each card has a non-empty question text")
    void cardsHaveQuestionText() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        for (CardDeckCard card : deck) {
            assertNotNull(card.question(), "Card should have a question");
            assertFalse(card.question().isEmpty(), "Question should not be empty");
        }
    }

    /* ── random card selection ────────────────────────────────── */

    @Test
    @DisplayName("Getting a random card returns an unrevealed card when available")
    void getRandomCardReturnsUnrevealedWhenAvailable() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        CardDeckCard randomCard = CardDeckService.getRandomCard(deck);

        assertNotNull(randomCard, "Should return a card");
        assertFalse(randomCard.revealed(),
                "Should return an unrevealed card when unrevealed cards exist");
    }

    @Test
    @DisplayName("Getting a random card from fully revealed deck returns any card")
    void getRandomCardFromRevealedDeckReturnsAnyCard() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        deck = CardDeckService.revealAllCards(deck);

        CardDeckCard randomCard = CardDeckService.getRandomCard(deck);
        assertNotNull(randomCard, "Should return a card even when all are revealed");
        assertTrue(randomCard.revealed(),
                "Should return a revealed card when all are revealed");
    }

    /* ── card reveal toggling ──────────────────────────────────── */

    @Test
    @DisplayName("Toggling a card reveals it")
    void toggleCardRevealsIt() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        int targetId = 5;

        List<CardDeckCard> afterToggle = CardDeckService.toggleCardRevealed(deck, targetId);
        assertTrue(afterToggle.get(targetId).revealed(),
                "First toggle should reveal the card");
    }

    @Test
    @DisplayName("Toggling a card twice unreveals it")
    void toggleCardTwiceUnrevealsIt() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        int targetId = 10;

        List<CardDeckCard> afterFirstToggle =
            CardDeckService.toggleCardRevealed(deck, targetId);
        assertTrue(afterFirstToggle.get(targetId).revealed(),
                "First toggle should reveal");

        List<CardDeckCard> afterSecondToggle =
            CardDeckService.toggleCardRevealed(afterFirstToggle, targetId);
        assertFalse(afterSecondToggle.get(targetId).revealed(),
                "Second toggle should unreveal");
    }

    @Test
    @DisplayName("Toggling one card does not affect others")
    void toggleOneCardDoesNotAffectOthers() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        int targetId = 7;

        List<CardDeckCard> afterToggle = CardDeckService.toggleCardRevealed(deck, targetId);

        for (int i = 0; i < deck.size(); i++) {
            if (i != targetId) {
                assertEquals(deck.get(i).revealed(), afterToggle.get(i).revealed(),
                        "Card " + i + " should remain unchanged");
            }
        }
    }

    /* ── progress tracking ────────────────────────────────────── */

    @Test
    @DisplayName("Revealed percentage starts at 0%")
    void revealedPercentageStartsAtZero() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        int percentage = CardDeckService.calculateRevealedPercentage(deck);
        assertEquals(0, percentage, "Should start at 0%");
    }

    @Test
    @DisplayName("Revealed percentage increases as cards are revealed")
    void revealedPercentageIncreases() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();

        // Reveal first card
        deck = CardDeckService.toggleCardRevealed(deck, 0);
        int percentageAfterOne = CardDeckService.calculateRevealedPercentage(deck);
        assertEquals((100 / 24), percentageAfterOne,
                "Percentage should increase after revealing one card");

        // Reveal 12 more (half)
        for (int i = 1; i < 12; i++) {
            deck = CardDeckService.toggleCardRevealed(deck, i);
        }
        int percentageAtHalf = CardDeckService.calculateRevealedPercentage(deck);
        assertEquals(50, percentageAtHalf,
                "Percentage should be 50% when half cards are revealed");
    }

    @Test
    @DisplayName("Revealed card counter works correctly")
    void revealedCounterWorks() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        assertEquals(0, CardDeckService.countRevealedCards(deck),
                "Should start with 0 revealed cards");

        deck = CardDeckService.toggleCardRevealed(deck, 0);
        deck = CardDeckService.toggleCardRevealed(deck, 1);
        deck = CardDeckService.toggleCardRevealed(deck, 2);

        assertEquals(3, CardDeckService.countRevealedCards(deck),
                "Should have 3 revealed cards");
    }

    /* ── completion detection ──────────────────────────────────── */

    @Test
    @DisplayName("Not all cards are revealed at start")
    void notAllCardsRevealedAtStart() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        assertFalse(CardDeckService.allCardsRevealed(deck),
                "Not all cards should be revealed at start");
    }

    @Test
    @DisplayName("All cards revealed is detected when all cards are revealed")
    void allCardsRevealedDetected() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();
        deck = CardDeckService.revealAllCards(deck);

        assertTrue(CardDeckService.allCardsRevealed(deck),
                "Should detect when all cards are revealed");
        assertEquals(100, CardDeckService.calculateRevealedPercentage(deck),
                "Percentage should be 100% when all are revealed");
    }

    @Test
    @DisplayName("All cards revealed is not detected with 23 of 24 revealed")
    void allCardsRevealedNotDetectedWith23of24() {
        List<CardDeckCard> deck = CardDeckService.assembleNewDeck();

        // Reveal 23 cards
        for (int i = 0; i < 23; i++) {
            deck = CardDeckService.toggleCardRevealed(deck, i);
        }
        assertFalse(CardDeckService.allCardsRevealed(deck),
                "Should not detect all cards revealed with 23 of 24");

        // Reveal the last one
        deck = CardDeckService.toggleCardRevealed(deck, 23);
        assertTrue(CardDeckService.allCardsRevealed(deck),
                "Should detect all cards revealed when 24 of 24 are revealed");
    }
}
