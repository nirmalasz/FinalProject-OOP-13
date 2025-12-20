package com.kelompok13.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kelompok13.frontend.background.Background;
import com.kelompok13.frontend.background.BackgroundFactory;
import com.kelompok13.frontend.background.BackgroundManager;
import com.kelompok13.frontend.background.BackgroundType;
import com.kelompok13.frontend.states.gameplay.BattleState;
import com.kelompok13.frontend.states.gameplay.PlayingState;
import com.kelompok13.frontend.states.interaction.ShopState;
import com.kelompok13.frontend.states.menu.MenuState;

import java.util.Stack;

public class GameStateManager {
    private final Stack<GameState> states;
    private BackgroundManager backgroundManager;
    private AssetManager assetManager;

    public GameStateManager(){
        this.assetManager = new AssetManager();
        this.backgroundManager = new BackgroundManager(assetManager);
        states = new Stack<>();
    }

    public void push(GameState state, BackgroundType bgType){
        states.push(state);
        startBackgroundTransition(bgType);
    }
    public void push(GameState state){
        BackgroundType bgType;
        if (state instanceof MenuState) {
            bgType = BackgroundType.MENU;
        } else if (state instanceof PlayingState) {
            bgType = BackgroundType.MAIN;
        } else if (state instanceof BattleState) {
            bgType = BackgroundType.BATTLE;
        } else if (state instanceof ShopState) {
            bgType = BackgroundType.SHOP;
        } else {
            bgType = BackgroundType.MAIN; // Default
        }
        push(state, bgType);
    }

    private void startBackgroundTransition(BackgroundType bgType){
        backgroundManager.transitionTo(
            BackgroundFactory.create(bgType), 0.8f);
    }

    public void pop(){
        if (!states.isEmpty()){
        states.pop().dispose();
        if (!states.isEmpty()){
            GameState previousState = states.peek();
            setBackgroundForState(previousState);
        }
        }
    }

    public void update(float delta) {
        assetManager.update();
        backgroundManager.update(delta);

        if (!states.isEmpty()) {
            states.peek().update(delta);
        }
    }

    public void replace(GameState state, BackgroundType bgType) {
        if (!states.isEmpty()) {
            states.pop().dispose();
        }
        states.push(state);
        startBackgroundTransition(bgType);
    }

    public void replace(GameState state) {
        BackgroundType bgType = BackgroundType.MAIN;
        if (state instanceof MenuState) bgType = BackgroundType.MENU;
        else if (state instanceof PlayingState) bgType = BackgroundType.MAIN;
        else if (state instanceof BattleState) bgType = BackgroundType.BATTLE;
        else if (state instanceof ShopState) bgType = BackgroundType.SHOP;
        replace(state, bgType);
    }

    public void render(SpriteBatch batch){
        renderBackground(batch);
        states.peek().render(batch);
    }

    private void renderBackground(SpriteBatch batch){
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        backgroundManager.render(batch, camera,
            Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    public void set(GameState state, BackgroundType bgType){
        while (!states.isEmpty()) {
            states.pop().dispose();
        }

        // Push new state
        states.push(state);
        startBackgroundTransition(bgType);
    }

    public void dispose() {
        while (!states.isEmpty()) {
            pop();
        }
        backgroundManager.dispose();
        assetManager.dispose();
    }

    public BackgroundManager getBackgroundManager() {
        return backgroundManager;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    // Helper methods for state management
    public GameState getCurrentState() {
        return states.isEmpty() ? null : states.peek();
    }

    private void setBackgroundForState(GameState state) {
        BackgroundType bgType;
        if (state instanceof MenuState) {
            bgType = BackgroundType.MENU;
        } else if (state instanceof PlayingState) {
            bgType = BackgroundType.MAIN;
        } else if (state instanceof BattleState) {
            bgType = BackgroundType.BATTLE;
        } else if (state instanceof ShopState) {
            bgType = BackgroundType.SHOP;
        } else {
            bgType = BackgroundType.MAIN;
        }
        startBackgroundTransition(bgType);
    }
}
