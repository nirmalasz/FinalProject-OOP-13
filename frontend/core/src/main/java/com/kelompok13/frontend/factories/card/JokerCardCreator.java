package com.kelompok13.frontend.factories.card;

import com.kelompok13.frontend.card.BaseCard;
import com.kelompok13.frontend.card.JokerCard;
import com.kelompok13.frontend.card.effects.CardEffect;
import com.kelompok13.frontend.card.effects.JokerEffect;
import com.kelompok13.frontend.factories.CardFactory;

import static com.kelompok13.frontend.card.effects.CardEffect.EffectType.MULTIPLY_SCORE;

public class JokerCardCreator implements CardFactory.CardCreator {
    @Override
    public BaseCard createCard() {
        JokerEffect 
        JokerCard jokerCard = new JokerCard();
    }

}
