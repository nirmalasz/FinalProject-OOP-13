package com.kelompok13.frontend.observers.game;

import com.kelompok13.frontend.observers.Event;
import com.kelompok13.frontend.observers.EventType;
import com.kelompok13.frontend.observers.Observer;
import com.kelompok13.frontend.observers.Subject;
import com.kelompok13.frontend.observers.payload.ScorePayLoad;

import java.util.ArrayList;
import java.util.List;


//keep score track and updates total score accross rounds
public class ScoreManager implements Subject {
    protected List<Observer> observers;
    protected int score;


    public ScoreManager(){
        this.score = 0;
        this.observers = new ArrayList<>();
    }


    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver(Event event) {
        for(Observer observer: observers)
            observer.update(event);
    }

    private void notifyScoreUpdate() {
        ScorePayLoad payload = new ScorePayLoad(score, "Game");
        Event event = new Event(EventType.SCORE_UPDATE, payload);
        notifyObserver(event);
    }
    public void addScore(int points) {
        this.score += points;
        notifyScoreUpdate();
        System.out.println("Score added: " + points + " | Total: " + score);
    }

    public void setScore(int newScore) {
        this.score = newScore;
        notifyScoreUpdate();
        System.out.println("Score set to: " + score);
    }

    public int getScore() {
        return score;
    }
}
