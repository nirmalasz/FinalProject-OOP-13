package com.kelompok13.frontend.gameplay;

import com.kelompok13.frontend.models.Deck;


public class Battle {
    private DeckManager deckManager;
    private CombatResolver combatResolver;
    private HandEvaluator handEvaluator;

    public void startRound(){
        deckManager = new DeckManager(new Deck().getCardList());
        combatResolver = new CombatResolver();
        handEvaluator = new HandEvaluator();
    }
}
