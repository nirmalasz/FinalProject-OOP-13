package com.kelompok13.frontend.utils;

import com.badlogic.gdx.Gdx;

public class GameManager {
    private static GameManager instance;
    private boolean gameActive;
    //private ScoreManager scoreManager;
    // private BackendService backendService; // u req api
    private String currentPlayerId; //untuk uuid
    private int currencyCollected = 0;

    private GameManager(){
        //scoreManager = new ScoreManager();
        gameActive = false;
        //backendService = new BackendService();
    }

    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    public void registerPlayer(String username){

    }

    public void startGame(){
        gameActive = true;
        System.out.println("Game Started!");
        currencyCollected = 0;
    }

    public void endGame(){

    }

    public void addCurrency(int value){
        currencyCollected += value;
        Gdx.app.log("Currency Collected", "Total: " + currencyCollected );
    }

    //setter getter tp ntar aj
    //buat score
    //buat currency

    //buat observer


}
