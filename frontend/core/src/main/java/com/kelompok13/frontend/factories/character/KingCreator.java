package com.kelompok13.frontend.factories.character;
import com.kelompok13.frontend.characters.King;
import com.kelompok13.frontend.characters.BaseCharacter;

public class KingCreator {
    public BaseCharacter createCharacter() {
        return new King();
    }
}
