package com.kelompok13.frontend.factories;

import com.kelompok13.frontend.card.BaseCard;
import com.kelompok13.frontend.factories.card.JokerCardCreator;
//import com.kelompok13.frontend.factories.card.PlayingCardCreator;

import java.util.HashMap;
import java.util.Map;

public class CardFactory {
    public interface CardCreator{
        BaseCard createCard();
    }

    private Map<String, CardCreator> cardCreator;

    public CardFactory(){
        cardCreator = new HashMap<>();
        registerCardCreator();
    }

    private void registerCardCreator(){
        cardCreator.put("joker_card", new JokerCardCreator());
//        cardCreator.put("playing_card", new PlayingCardCreator());
    }

    public BaseCard createCard(String type){
        CardCreator creator = cardCreator.get(type.toLowerCase());
        if(creator != null){
            return creator.createCard();
        }
        throw new IllegalArgumentException("Unknown card type: " + type);
    }
    public BaseCard createJokerCard(){
        return createCard("joker_card");
    }
//    public BaseCard createPlayingCard(){
//        return createCard("playing_card");
//    }

}
