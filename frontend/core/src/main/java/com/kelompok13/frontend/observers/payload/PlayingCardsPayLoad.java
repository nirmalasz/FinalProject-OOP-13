package com.kelompok13.frontend.observers.payload;

import com.kelompok13.frontend.card.PlayingCard;
import java.util.List;
import java.util.UUID;

public class PlayingCardsPayLoad {
    public UUID playerId;
    public List<PlayingCard> cards;
    public int totalValue;
    public String combination;
    public int handType;

    public PlayingCardsPayLoad(UUID playerId, List<PlayingCard> cards,
        int totalValue, String combination, int currentScore) {
        this.playerId = playerId;
        this.cards = cards;
        this.totalValue = totalValue;
        this.combination = combination;
        this.handType = currentScore;
    }
}
