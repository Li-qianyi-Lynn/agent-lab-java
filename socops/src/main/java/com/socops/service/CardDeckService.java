package com.socops.service;

import com.socops.data.IcebreakerPrompts;
import com.socops.model.CardDeckCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Service for managing Card Deck Shuffle game logic.
 * Assembles decks, retrieves random cards, and tracks card reveal state.
 */
public final class CardDeckService {

    private static final Random RANDOM = new Random();

    /**
     * Creates a new card deck with all 24 prompts shuffled and unrevealed.
     */
    public static List<CardDeckCard> assembleNewDeck() {
        List<String> shuffledPrompts = new ArrayList<>(IcebreakerPrompts.ALL_PROMPTS);
        Collections.shuffle(shuffledPrompts);

        List<CardDeckCard> cards = new ArrayList<>();
        for (int i = 0; i < shuffledPrompts.size(); i++) {
            cards.add(new CardDeckCard(i, shuffledPrompts.get(i), false));
        }
        return cards;
    }

    /**
     * Returns a random unrevealed card from the deck.
     * If all cards are revealed, returns a random card regardless of state.
     */
    public static CardDeckCard getRandomCard(List<CardDeckCard> deck) {
        List<CardDeckCard> unrevealed = deck.stream()
            .filter(card -> !card.revealed())
            .toList();

        if (unrevealed.isEmpty()) {
            return deck.get(RANDOM.nextInt(deck.size()));
        }
        return unrevealed.get(RANDOM.nextInt(unrevealed.size()));
    }

    /**
     * Toggles the revealed status of a card by ID.
     * Returns a new list with the card's state flipped.
     */
    public static List<CardDeckCard> toggleCardRevealed(
        List<CardDeckCard> deck,
        int cardId
    ) {
        return deck.stream()
            .map(card -> card.id() == cardId ? card.toggleRevealed() : card)
            .toList();
    }

    /**
     * Reveals all cards in the deck.
     */
    public static List<CardDeckCard> revealAllCards(List<CardDeckCard> deck) {
        return deck.stream()
            .map(card -> card.withRevealed(true))
            .toList();
    }

    /**
     * Calculates the percentage of cards revealed.
     */
    public static int calculateRevealedPercentage(List<CardDeckCard> deck) {
        if (deck.isEmpty()) {
            return 0;
        }
        long revealedCount = deck.stream()
            .filter(CardDeckCard::revealed)
            .count();
        return (int) ((revealedCount * 100) / deck.size());
    }

    /**
     * Returns the count of revealed cards.
     */
    public static int countRevealedCards(List<CardDeckCard> deck) {
        return (int) deck.stream()
            .filter(CardDeckCard::revealed)
            .count();
    }

    /**
     * Checks if all cards have been revealed.
     */
    public static boolean allCardsRevealed(List<CardDeckCard> deck) {
        return deck.stream().allMatch(CardDeckCard::revealed);
    }

    private CardDeckService() {
        /* service only — no instances */
    }
}
