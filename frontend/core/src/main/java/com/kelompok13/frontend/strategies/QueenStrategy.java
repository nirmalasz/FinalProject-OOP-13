package com.kelompok13.frontend.strategies;

import com.kelompok13.frontend.card.CardRank;
import com.kelompok13.frontend.card.CardSuit;
import com.kelompok13.frontend.card.PlayingCard;

import java.util.List;

public class QueenStrategy implements EnemyStrategy {
    @Override
    public void applyDebuff() {
        System.out.println("Queen Enemy: All Jacks and King are now debuffed!");
    }

    @Override
    public void modifyCardValue(List<PlayingCard> card, int originalValue) {
        applyDebuff();
        for (PlayingCard c: card){
            if (c.getRank().equals(CardRank.JACK) || c.getRank().equals(CardRank.KING)){
                c.setValue(0);
            }
        }
    }

    @Override
    public String getEnemyName() {
        return "";
    }

    @Override
    public String getEffectDescription() {
        return "";
    }
}
