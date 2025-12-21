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
           //
        };
        Bartender bartender = new Bartender("NamaBartender", new Vector2(2000
            , 1700),
            dialogues,
            texture);
        bartender.setWidthHeight(2800, 3500);
        return bartender;
    }
}
