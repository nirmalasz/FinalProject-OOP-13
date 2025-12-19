//standard card
package com.kelompok13.frontend.card;

import java.util.Map;

public class PlayingCard extends BaseCard{
    private CardSuit suit;
    private CardRank rank;
    private int value;

    private static final Map<CardRank, Integer> RANK_VALUES = Map.ofEntries(
        Map.entry(CardRank.ACE, 14),
        Map.entry(CardRank.KING, 13),
        Map.entry(CardRank.QUEEN, 12),
        Map.entry(CardRank.JACK, 11),
        Map.entry(CardRank.TWO, 2),
        Map.entry(CardRank.THREE, 3),
        Map.entry(CardRank.FOUR, 4),
        Map.entry(CardRank.FIVE, 5),
        Map.entry(CardRank.SIX, 6),
        Map.entry(CardRank.SEVEN, 7),
        Map.entry(CardRank.EIGHT, 8),
        Map.entry(CardRank.NINE, 9),
        Map.entry(CardRank.TEN, 10)
    );

    public PlayingCard(CardSuit suit, CardRank rank) {
        super(rank.toString() + " of " + suit.toString(), CardType.PLAYING);
        this.suit = suit;
        this.rank = rank;
        this.value = computeCardValue(rank);
    }

    public int computeCardValue(CardRank cardRank) {
        return RANK_VALUES.getOrDefault(cardRank, 0);
    }

    public int getValue() {
        return value;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardRank getRank() {
        return rank;
    }
}
