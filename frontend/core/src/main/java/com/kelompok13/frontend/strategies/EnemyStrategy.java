package com.kelompok13.frontend.strategies;

import com.kelompok13.frontend.card.PlayingCard;

import java.util.List;

public interface EnemyStrategy {
    void applyDebuff();
    void modifyCardValue(List<PlayingCard> card, int originalValue);
    String getEnemyName();
    String getEffectDescription();
}
