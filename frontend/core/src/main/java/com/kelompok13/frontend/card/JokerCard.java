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

    public int applyEffect(String type, int value){
        if (effect != null){
            return effect.applyEffect(type, value);
        }
        return value;
    }
}
