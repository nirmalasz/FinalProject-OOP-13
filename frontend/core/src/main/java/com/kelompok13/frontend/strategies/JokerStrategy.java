package com.kelompok13.frontend.strategies;

import com.kelompok13.frontend.card.CardSuit;
import com.kelompok13.frontend.card.CardType;
import com.kelompok13.frontend.card.PlayingCard;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

public class JokerStrategy implements EnemyStrategy {
    CardSuit debuffedSuit;

    private CardSuit randomSuit(){
        int rand = random.nextInt(3);
        CardSuit suit;

        switch (rand) {
            case 0:
                suit = CardSuit.HEART;
                break;
            case 1:
                suit = CardSuit.DIAMOND;
                break;
            case 2:
                suit = CardSuit.SPADE;
                break;
            default:
                suit = CardSuit.CLUB;
        }
        return suit;
    }

    @Override
    public void applyDebuff() {
        System.out.println("Joker Enemy: All" + debuffedSuit.toString() + "are now debuffed!");
    }

    @Override
    public void modifyCardValue(List<PlayingCard> card, int originalValue) {
        CardSuit debuffedSuit = randomSuit();
        applyDebuff();
        for (PlayingCard playingCard : card) {
            if (playingCard.getSuit() == debuffedSuit) {
                playingCard.setValue(0);
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
