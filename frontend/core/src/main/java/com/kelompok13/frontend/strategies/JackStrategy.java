package com.kelompok13.frontend.strategies;


import com.kelompok13.frontend.card.PlayingCard;

import java.util.List;

//debuff all jack cards to half points
public class JackStrategy implements EnemyStrategy{

    @Override
    public void applyDebuff() {
        System.out.println("Jack Enemy: All Jacks are now halfed!");
    }

    @Override
    public void modifyCardValue(List<PlayingCard> card, int originalValue) {
        applyDebuff();
        // If card is a Jack (value 11), return 0
        for(PlayingCard c : card){
            if(c.getValue() == 11){
                c.setValue(c.getValue() / 2);
            }
        }
    }

    @Override
    public String getEnemyName() {
        return "Jack Enemy";
    }

    @Override
    public String getEffectDescription() {
        return "All Jacks are worth half of its original value";
    }

}
