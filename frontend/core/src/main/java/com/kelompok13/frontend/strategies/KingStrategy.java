package com.kelompok13.frontend.strategies;

import com.kelompok13.frontend.card.PlayingCard;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

public class KingStrategy implements EnemyStrategy{
    int randomDivider = random.nextInt(3) + 1;

    @Override
    public void applyDebuff() {
        System.out.println("King Enemy: Card value are now halfed!");
    }

    @Override
    public void modifyCardValue(List<PlayingCard> card, int originalValue) {
        applyDebuff();
        for (PlayingCard playingCard : card) {
            playingCard.setValue(playingCard.getValue() / randomDivider);
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
