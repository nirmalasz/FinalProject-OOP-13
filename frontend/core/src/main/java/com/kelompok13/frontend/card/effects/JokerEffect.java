//add flat bonus to score
package com.kelompok13.frontend.card.effects;

public class JokerEffect implements CardEffect {
    private int point;
    private String type;

    public JokerEffect(int bonusPoints, String type) {
        this.type = type;
        this.point = bonusPoints;
    }

    @Override
    public int applyEffect(String type, int value) {
        int newValue;
        if (type.equals("ADD")) {
            newValue = value + point;
            System.out.println("Joker Add Applied: " + newValue);
        } else if(type.equals("MULTIPLY")) {
            newValue = value * point;
            System.out.println("Joker Multiply Applied: " + newValue);
        } else {
            return 0;
        }
        return newValue;
    }
}
