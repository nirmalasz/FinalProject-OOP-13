package com.kelompok13.frontend.factories.card;

import com.badlogic.gdx.graphics.Texture;
import com.kelompok13.frontend.card.BaseCard;
import com.kelompok13.frontend.card.JokerCard;
import com.kelompok13.frontend.card.effects.CardEffect;
import com.kelompok13.frontend.card.effects.JokerEffect;
import com.kelompok13.frontend.factories.CardFactory;

import static com.kelompok13.frontend.card.effects.CardEffect.EffectType.MULTIPLY_SCORE;

public class JokerCardCreator implements CardFactory.CardCreator {
    @Override
    public BaseCard createCard() {
        JokerEffect effect= new JokerEffect(3, MULTIPLY_SCORE);
        JokerCard jokerCard = new JokerCard(effect, "Multiply Joker");
        Texture texture = new Texture("cards/joker_card.png");
        jokerCard.setTexture(texture, false);
        return jokerCard;
    }

}
