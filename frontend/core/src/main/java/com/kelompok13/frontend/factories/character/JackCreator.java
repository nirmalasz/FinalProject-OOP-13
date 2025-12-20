package com.kelompok13.frontend.factories.character;
import com.kelompok13.frontend.characters.Jack;
import com.kelompok13.frontend.characters.BaseCharacter;

public class JackCreator {
    public BaseCharacter createCharacter() {
        return new Jack();
    }
}
