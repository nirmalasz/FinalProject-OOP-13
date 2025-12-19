//add flat bonus to score
package com.kelompok13.frontend.card.effects;

public class JokerEffect implements CardEffect {
    private int modifier;
    private EffectType type;

    public JokerEffect(int modifier, EffectType type) {
        this.modifier = modifier;
        this.type = type;
    }

    @Override
    public int applyEffect(int currentValue) {
        switch(type) {
            case MULTIPLY_SCORE:
                System.out.println("Multiply score by" + modifier);
                return currentValue * modifier;
            case BONUS_POINT:
                System.out.println("Add score by" + modifier);
                return currentValue + modifier;
            default:
                return currentValue;
        }
    }

    @Override
    public EffectType getEffectType() {
        return type;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public int getModifier() {
        return modifier;
    }
}
