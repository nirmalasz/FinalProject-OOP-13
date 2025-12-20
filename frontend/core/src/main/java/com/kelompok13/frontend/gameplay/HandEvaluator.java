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


    public int countResult(int multiplier){
        int result = 0;
        for(Integer card: scoringCards){
            result += card;
        }
        result *= multiplier;
        return result;
    }


    public int evaluateHandScore(List<PlayingCard> selectedCard, int handsize){
        if (selectedCard.isEmpty()){
            currentHandType = "No Cards";
        }

        if (checkFlush(selectedCard, handsize) && checkStraight(selectedCard)){
            currentHandType = "Straight Flush";
            return countResult(handRankings.get("Straight Flush"));
        }
        if (checkFlush(selectedCard, handsize)){
            currentHandType = "Flush";
            return countResult(handRankings.get("Flush"));
        }
        if (checkStraight(selectedCard)){
            currentHandType = "Straight";
            return countResult(handRankings.get("Straight"));
        }
        if (checkPair(selectedCard)){
            if (currentHandType.equals("Two Pair")){
                return countResult(handRankings.get("Two Pair"));
            } else if (currentHandType.equals("Pair")){
                return countResult(handRankings.get("Pair"));
            }
        }

        //default high card
        checkHighCard(selectedCard);
        currentHandType = "High Card";
        return countResult(handRankings.get("High Card"));
    }

    public String getCurrentHandType() {
        return currentHandType;
    }

    private boolean checkFlush(List<PlayingCard> selectedCard, int handsize) {
        Map<CardSuit, Integer> suitCount = new HashMap<>();
        for (PlayingCard card : selectedCard) {
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }
        if (suitCount.size() < 5) return false;
        scoringCards.clear();
        for (Map.Entry<CardSuit, Integer> entry : suitCount.entrySet()) {
            if (entry.getValue() >= handsize) {
                CardSuit flushSuit = entry.getKey();

                for (PlayingCard card : selectedCard) {
                    if (card.getSuit() == flushSuit) {
                        scoringCards.add(card.getValue());
                    }
                }
                // sort descending and take only standard 5 highest cards
                Collections.sort(scoringCards, Collections.reverseOrder());
                if (scoringCards.size() > 5) {
                    scoringCards.remove(-1);
                }
                currentHandType = "Flush";
                return true;
            }
        }

        return false;
    }

    private boolean checkStraight(List<PlayingCard> selectedCard) {
        List<Integer> values = new ArrayList<>();
        for (PlayingCard card : selectedCard) {
            values.add(card.getValue());
        }
        if (values.size() < 5) return false;
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

    private boolean checkHighCard(List<PlayingCard> selectedCard){
        int maxValue = 0;
        scoringCards.clear();
        for (PlayingCard card: selectedCard){
            if (card.getValue() > maxValue){
                maxValue = card.getValue();
            }
        }
        scoringCards.add(maxValue);
        currentHandType = "High Card";
        return true;
    }

    private boolean checkPair(List<PlayingCard> selectedCard){
        int pairs = pairCount(selectedCard);

        if (pairs == 2){
            currentHandType = "Two Pair";
            return true;
        } else if (pairs == 1){
            currentHandType = "Pair";
            return true;
        }

        return false;
    }

    //for one pair and two pair
    private int pairCount(List<PlayingCard> selectedCard){
        Map<Integer, Integer> valueCount = new HashMap<>();
        for (PlayingCard card: selectedCard){
            int value = card.getValue();
            valueCount.put(value, valueCount.getOrDefault(value, 0) + 1);
        }

        int pairs = 0;
        scoringCards.clear();
        countedValues.clear();

        for (Map.Entry<Integer, Integer> entry : valueCount.entrySet()) {
            if (entry.getValue() == 2) {
                pairs++;
                countedValues.add(entry.getKey());
                scoringCards.add(entry.getKey());
                scoringCards.add(entry.getKey()); // add twice for the pair
            }
        }

        return pairs;
    }

//    //three of a kind
//    private int checkThreeOfAKind(List<PlayingCard> selectedCard){
//        List<Integer> valuesCounted = new ArrayList<>();
//        for (PlayingCard card: selectedCard){
//            valuesCounted.add(card.getValue());
//        }
//        for (int i: valuesCounted){
//            int freq = Collections.frequency(valuesCounted, i);
//            if (freq == 3){
//                countedValues.add(i);
//                return handRankings.get("Three of a Kind");
//            }
//        }
//        return 0;
//    }

}
