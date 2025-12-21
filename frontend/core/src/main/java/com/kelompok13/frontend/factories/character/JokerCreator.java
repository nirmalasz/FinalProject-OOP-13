package com.kelompok13.frontend.factories.character;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kelompok13.frontend.characters.Enemy;
import com.kelompok13.frontend.characters.BaseCharacter;
import com.kelompok13.frontend.factories.CharacterFactory;
import com.kelompok13.frontend.strategies.JackStrategy;

public class JokerCreator implements CharacterFactory.CharacterCreator {
    @Override
    public BaseCharacter createCharacter() {
        String[] challengeDialogue = {
            //
        };
        String[] defeatDialogue = {
            //
        };
        Enemy joker = new Enemy("Joker", new Vector2(650, 350), new JackStrategy(), 0,
            challengeDialogue, defeatDialogue);
        joker.setTexture(new Texture("characters/joker.png"));
        return joker;
    }
}
