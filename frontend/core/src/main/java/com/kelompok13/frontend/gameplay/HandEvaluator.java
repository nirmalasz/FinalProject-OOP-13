package com.kelompok13.frontend.gameplay;


import com.kelompok13.frontend.card.*;
import com.kelompok13.frontend.card.effects.JokerEffect;

import java.util.*;

//evaluate the poker hand
//for now will be
//1. high card
//2. pair
//3. two pair
//4. full house
public class HandEvaluator {
       private static final Map<String, Integer> HAND_RANKINGS = Map.ofEntries(
        Map.entry("High Card", 1),
        Map.entry("Pair", 2),
        Map.entry("Two Pair", 3),
        Map.entry("Three of a Kind", 4),
        Map.entry("Straight", 5),
        Map.entry("Flush", 6),
        Map.entry("Full House", 7),
        Map.entry("Four of a Kind", 8),
        Map.entry("Straight Flush", 9),
        Map.entry("Royal Flush", 10)
    );

    //untuk sementara beberapa aja
    Map<String, Integer> handRankings = new HashMap<>(){{
        put("High Card", 1);
        put("Pair", 2);
        put("Two Pair", 3);
        put("Three of a Kind", 4);
        put("Straight", 5);
        put("Flush", 6);
        put("Full House", 7);
        put("Four of a Kind", 8);
        put("Straight Flush", 9);
        put("Royal Flush", 10);
    }};

    private String currentHandType = "No Cards";
    private List<Integer> scoringCards = new ArrayList<>();

    List<Integer> countedValues = new ArrayList<>();
    List<Integer> valuesCounted = new ArrayList<>();

    private int countResult(int multiplier){
        int result = 0;
        for(Integer card: scoringCards){
            result += card;
        }
        result *= multiplier;
        return result;
    }


    public int evaluateHandScore(List<PlayingCard> selectedCard){
        int result = 0;
        if (selectedCard.isEmpty()){
            currentHandType = "No Cards";
        }

        if (checkFlush(selectedCard)){
            result =  countResult(handRankings.get("Flush"));
        }
        if (checkStraight(selectedCard)){
            result =  countResult(handRankings.get("Straight"));
        }


        return result;
    }

    private boolean checkFlush(List<PlayingCard> cards) {
        boolean flush = true;
        CardSuit firstSuit = cards.get(0).getSuit();
        for (PlayingCard card : cards) {
            if (card.getSuit() != firstSuit) {
                flush = false;
                break;
            }
        }
        if (flush) {
            scoringCards.clear();
            for (PlayingCard card : cards) {
                scoringCards.add(card.getValue());
            }
            currentHandType = "Flush";
            return flush;
        }
        return flush;
    }

    private boolean checkStraight(List<PlayingCard> selectedCard) {
        List<Integer> values = new ArrayList<>();
        for (PlayingCard card : selectedCard) {
            values.add(card.getValue());
        }
        Collections.sort(values);

        scoringCards.clear();
        for (int i = 0; i < values.size() - 1; i++) {
            if (values.get(i + 1) - values.get(i) != 1 && i < 3) {
                return  false;
            }
            if (i < 5) scoringCards.add(values.get(i));
        }
        currentHandType = "Straight";
        return true;
    }

    private int checkHighCard(List<PlayingCard> selectedCard){
        int maxValue = 0;
        for (PlayingCard card: selectedCard){
            if (card.getValue() > maxValue){
                maxValue = card.getValue();
            }
        }
        countedValues.add(maxValue);
        return handRankings.get("High Card");
    }

    //for one pair and two pair
    private int pairCount(List<PlayingCard> selectedCard){
        // Map<Integer, Integer> valueCount = new HashMap<>();
        List<Integer> valuesCounted = new ArrayList<>();
        for (PlayingCard card: selectedCard){
            valuesCounted.add(card.getValue());
        }
        int pairs = 0;

        for (int i: valuesCounted){
            int freq = Collections.frequency(valuesCounted, i);
            if (freq == 2 && !countedValues.contains(i)){
                pairs++;
                countedValues.add(i);
            }

        }

        if (pairs == 1){
            return handRankings.get("Pair");
        } else if (pairs == 2){
            return handRankings.get("Two Pair");
        }
        return 0;
    }

    //three of a kind
    private int checkThreeOfAKind(List<PlayingCard> selectedCard){
        List<Integer> valuesCounted = new ArrayList<>();
        for (PlayingCard card: selectedCard){
            valuesCounted.add(card.getValue());
        }
        for (int i: valuesCounted){
            int freq = Collections.frequency(valuesCounted, i);
            if (freq == 3){
                countedValues.add(i);
                return handRankings.get("Three of a Kind");
            }
        }
        return 0;
    }

}
