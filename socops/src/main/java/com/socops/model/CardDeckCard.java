package com.socops.model;

/**
 * Represents a single card in the Card Deck Shuffle game mode.
 * Each card has a question and metadata for rendering and animation.
 */
public record CardDeckCard(
    int id,
    String question,
    boolean revealed
) {
    /**
     * Creates a copy of this card with the revealed status toggled.
     */
    public CardDeckCard toggleRevealed() {
        return new CardDeckCard(this.id, this.question, !this.revealed);
    }

    /**
     * Creates a copy with the revealed status changed.
     */
    public CardDeckCard withRevealed(boolean revealed) {
        return new CardDeckCard(this.id, this.question, revealed);
    }
}
