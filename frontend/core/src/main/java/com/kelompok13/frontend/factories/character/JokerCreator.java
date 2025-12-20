package com.kelompok13.frontend.factories.character;
import com.kelompok13.frontend.characters.Joker;
import com.kelompok13.frontend.characters.BaseCharacter;

public class JokerCreator {
    public BaseCharacter createCharacter() {
        return new Joker();
    }
}
