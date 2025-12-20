package com.kelompok13.frontend.gameplay;


import com.kelompok13.frontend.card.PlayingCard;
import com.kelompok13.frontend.utils.Player;

import java.util.List;

// handle logic of combat resolution
// determines round win or lose and currency reward
// trigger victory/defeat
public class CombatResolver {
    private HandEvaluator handEvaluator;

    public int calculateScore(List<PlayingCard> hand){
        return handEvaluator.evaluateHandScore(hand, hand.size());
    }

    public boolean determineVictory(int neededScore, List<PlayingCard> hand){
        if (neededScore <= calculateScore(hand)) return true;
        return false;
    }


}
