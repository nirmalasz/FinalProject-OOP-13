//special card with unique effects
package com.kelompok13.frontend.card;

import com.kelompok13.frontend.card.effects.CardEffect;

public class JokerCard extends BaseCard{
    private CardEffect effect;

    public JokerCard(CardEffect effect){
        super("Joker", CardType.JOKER);
        this.effect = effect;
    }

    public CardEffect getEffect(){
        return effect;
    }

    public void setEffect(CardEffect effect){
        this.effect = effect;
    }

    // apply the effect of the joker card
    public int applyEffect(JokerCard jokerCard, int score){
        return jokerCard.getEffect().applyEffect(score);
    }
}
