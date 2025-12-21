package com.kelompok13.frontend.card;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class CardTextureManager {
    private Map<String, Texture> cardTextures;


    private static CardTextureManager instance;

    private CardTextureManager(){
        cardTextures = new HashMap<>();
        loadAllTexture();
    }

    public static CardTextureManager getInstance() {
        if (instance == null) {
            instance = new CardTextureManager();
        }
        return instance;
    }

    private void loadAllTexture(){
        System.out.println("Loading card textures...");

        for (CardSuit  suit: CardSuit.values()){
            for (CardRank rank: CardRank.values()){
                String key = getCardKey(suit, rank);
                Texture texture = loadCardTexture(suit, rank);
                if (texture != null){
                    cardTextures.put(key, texture);
                }
            }
        }
        System.out.println("Loaded");
    }

    private String getCardKey(CardSuit suit, CardRank rank){
        return suit.toString().toLowerCase() + "_" + rank.toString().toLowerCase();
    }

    private Texture loadCardTexture(CardSuit suit, CardRank rank){
        String  suitName = suit.toString().toLowerCase();
        String rankName = rank.toString().toLowerCase();
        String filePath = "playingcard/"+ suitName + "_" + rankName + ".png";

        try {
            if (Gdx.files.internal(filePath).exists()){
                return new Texture(Gdx.files.internal(filePath));
            }
        } catch (Exception e){
            System.err.println("Error loading texture: " + filePath);
            e.printStackTrace();
        }
        return null;
    }

    public void dispose(){
        for (Texture texture: cardTextures.values()){
            if (texture!= null){
                texture.dispose();
            }
        }
        cardTextures.clear();
        instance = null;
    }

    public Texture getCardTexture(PlayingCard card){
        String key = getCardKey(card.getSuit(), card.getRank());
        return cardTextures.get(key);
    }
}
