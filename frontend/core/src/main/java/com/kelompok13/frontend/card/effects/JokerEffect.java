//add flat bonus to score
package com.kelompok13.frontend.card.effects;

public class JokerEffect implements CardEffect {
    private int amount;
    private EffectType type;

    public JokerEffect(int modifier, EffectType type) {
        this.amount = modifier;
        this.type = type;
    }

    @Override
    public int applyEffect(int currentValue) {
        switch(type) {
            case MULTIPLY_SCORE:
                System.out.println("Multiply score by" + amount);
                return currentValue * amount;
            case BONUS_POINT:
                System.out.println("Add score by" + amount);
                return currentValue + amount;
            default:
                return currentValue;
        }
    }

    @Override
    public EffectType getEffectType() {
        return type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
