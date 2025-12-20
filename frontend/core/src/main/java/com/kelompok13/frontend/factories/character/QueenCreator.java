package com.kelompok13.frontend.factories.character;
import com.kelompok13.frontend.characters.Queen;
import com.kelompok13.frontend.characters.BaseCharacter;

public class QueenCreator {
    public BaseCharacter createCharacter() {
        return new Queen();
    }
}
