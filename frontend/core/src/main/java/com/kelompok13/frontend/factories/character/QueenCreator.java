package com.kelompok13.frontend.factories.character;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kelompok13.frontend.characters.Enemy;
import com.kelompok13.frontend.characters.BaseCharacter;
import com.kelompok13.frontend.factories.CharacterFactory;
import com.kelompok13.frontend.strategies.QueenStrategy;

public class QueenCreator implements CharacterFactory.CharacterCreator {
    @Override
    public BaseCharacter createCharacter() {
        String[] challengeDialogue = {
            //
        };
        String[] defeatDialogue = {
            //
        };
        Enemy queen = new Enemy("Queen", new Vector2(3300, 600), new QueenStrategy(), 200,
            challengeDialogue, defeatDialogue);
        queen.setMapTexture(new Texture("characters/queen.png"));
        queen.setPortraitTexture(new Texture("characters/queen_scene.png"));
        queen.setWidthHeight(900, 1500);
        return queen;
    }
}
