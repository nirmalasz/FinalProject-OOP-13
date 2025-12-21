package com.kelompok13.frontend.factories.character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kelompok13.frontend.characters.Bartender;
import com.kelompok13.frontend.characters.BaseCharacter;
import com.kelompok13.frontend.factories.CharacterFactory;

public class BartenderCreator implements CharacterFactory.CharacterCreator {
//placeholder
    Texture texture = new Texture("characters/bartender.png");

    @Override
    public BaseCharacter createCharacter() {
        String[] dialogues = {
           "Assalamualaikum, welcome to my bar!"
        };
        Bartender bartender = new Bartender("Dodi", new Vector2(1800
            , 2800),
            dialogues,
            texture);
        bartender.setWidthHeight(1700, 2200);
        return bartender;
    }
}
