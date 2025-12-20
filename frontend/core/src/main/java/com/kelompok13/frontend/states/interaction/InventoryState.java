package com.kelompok13.frontend.states.interaction;
// show player item only

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;

public class InventoryState implements GameState {

    private GameStateManager gsm;
    private GameState previousState;

    public InventoryState(GameStateManager gsm, GameState previousState) {
        this.gsm = gsm;
        this.previousState = previousState;
    }

    @Override
    public void update(float delta) {
        //enter untuk balik state
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gsm.update(previousState);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        //render item/card
    }

    @Override
    public void dispose() {
    }
}
