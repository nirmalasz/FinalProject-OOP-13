package com.kelompok13.frontend.factories.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kelompok13.frontend.characters.BaseCharacter;
import com.kelompok13.frontend.characters.Enemy;
import com.kelompok13.frontend.factories.CharacterFactory;
import com.kelompok13.frontend.strategies.JackStrategy;

public class JackCreator implements CharacterFactory.CharacterCreator {
    @Override
    public BaseCharacter createCharacter() {
        String[] challengeDialogue = {
                //
        };
        String[] defeatDialogue = {
                //
        };
        Enemy jack = new Enemy("Jack", new Vector2(950, 800), new JackStrategy(), 100,
            challengeDialogue, defeatDialogue);
        jack.setTexture(new Texture("characters/Jack.png"));
        jack.setWidthHeight(1200, 1500);
        return jack;
    }
}
