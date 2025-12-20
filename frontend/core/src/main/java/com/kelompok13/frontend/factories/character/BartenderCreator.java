package com.kelompok13.frontend.factories.character;
import com.kelompok13.frontend.characters.Bartender;
import com.kelompok13.frontend.characters.BaseCharacter;

public class BartenderCreator {

    public BaseCharacter createCharacter() {
        return new Bartender();
    }
}
