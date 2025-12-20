package com.kelompok13.frontend.factories.character;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kelompok13.frontend.characters.Enemy;
import com.kelompok13.frontend.characters.BaseCharacter;
import com.kelompok13.frontend.factories.CharacterFactory;
import com.kelompok13.frontend.strategies.JackStrategy;

public class QueenCreator implements CharacterFactory.CharacterCreator {
    @Override
    public BaseCharacter createCharacter() {
        String[] challengeDialogue = {
            //
        };
        String[] defeatDialogue = {
            //
        };
        Enemy queen = new Enemy("Queen", new Vector2(150, 100), new JackStrategy(), 200,
            challengeDialogue, defeatDialogue);
        queen.setTexture(new Texture("characters/queen.png"));
        return queen;
    }
}
