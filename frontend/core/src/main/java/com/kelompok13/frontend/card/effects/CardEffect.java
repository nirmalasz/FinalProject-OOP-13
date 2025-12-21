//interface for card ability (mult score, bonus point)
package com.kelompok13.frontend.card.effects;

public interface CardEffect {
    enum EffectType {
        MULTIPLY_SCORE,
        BONUS_POINT
    }

    int applyEffect(int currentValue);
    EffectType getEffectType();
}
