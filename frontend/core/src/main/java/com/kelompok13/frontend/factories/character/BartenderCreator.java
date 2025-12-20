package com.kelompok13.frontend.factories.character;
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
        return new Bartender("NamaBartender", new Vector2(10, 10),  dialogues, texture);
    }
}
