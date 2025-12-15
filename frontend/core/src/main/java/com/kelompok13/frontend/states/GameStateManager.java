package com.kelompok13.frontend.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private final Stack<GameState> states;

    public GameStateManager(){
        states = new Stack<>();
    }

    public void push(GameState state){
        states.push(state);
    }

    public void pop(){
        states.pop().dispose();
    }

    public void update(GameState state){
        states.pop().dispose();
        states.push(state);
    }

    public void render(SpriteBatch batch){
        states.peek().render(batch);
    }

}
