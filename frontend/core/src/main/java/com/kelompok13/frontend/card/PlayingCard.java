//standard card
package com.kelompok13.frontend.card;

public class PlayingCard extends BaseCard{
    private CardSuit suit;
    private CardRank rank;
    private int value;

    public PlayingCard(CardSuit suit, CardRank rank) {
        super(rank.toString() + " of " + suit.toString(), CardType.PLAYING);
        this.suit = suit;
        this.rank = rank;
        this.value = computeCardValue(rank);
    }

    public int computeCardValue(CardRank cardRank){
        switch (cardRank) {
            case ACE: return 14;
            case KING_CARD: return 13;
            case QUEEN_CARD: return 12;
            case JACK_CARD: return 11;
            case NO2: return 2;
            case NO3: return 3;
            case NO4: return 4;
            case NO5: return 5;
            case NO6: return 6;
            case NO7: return 7;
            case NO8: return 8;
            case NO9: return 9;
            case NO10: return 10;
            default: return 0; // fallback for unexpected enum values
        }
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
