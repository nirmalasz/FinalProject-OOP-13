package com.kelompok13.frontend.factories;

import com.badlogic.gdx.math.Vector2;
import com.kelompok13.frontend.characters.BaseCharacter;
import com.kelompok13.frontend.factories.character.*;

import java.util.HashMap;
import java.util.Map;

public class CharacterFactory {
    public interface CharacterCreator {
        BaseCharacter createCharacter();
    }

    private Map<String, CharacterCreator> characterCreators;

    public CharacterFactory(){
        characterCreators = new HashMap<>();
        registerCharacterCreator();
    }

    private void registerCharacterCreator(){
        characterCreators.put("bartender", new BartenderCreator());
        characterCreators.put("jack", new JackCreator());
        characterCreators.put("king", new KingCreator());
        characterCreators.put("queen", new QueenCreator());
        characterCreators.put("joker", new JokerCreator());
    }

    public BaseCharacter createCharacter(String charType, Vector2 position){
        CharacterCreator creator = characterCreators.get(charType.toLowerCase());
        if (creator == null) {
            throw new IllegalArgumentException("No creator");
        }
        return creator.createCharacter();
    }

    public BaseCharacter createBartender(Vector2 position) {
        return createCharacter("bartender", position);
    }

    public BaseCharacter createJack(Vector2 position) {
        return createCharacter("jack", position);
    }

    public BaseCharacter createQueen(Vector2 position) {
        return createCharacter("queen", position);
    }

    public BaseCharacter createKing(Vector2 position) {
        return createCharacter("king", position);
    }

    public BaseCharacter createJoker(Vector2 position) {
        return createCharacter("joker", position);
    }
}
