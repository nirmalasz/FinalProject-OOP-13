package com.kelompok13.frontend.states.menu;
// when finish the game

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;
import com.kelompok13.frontend.states.menu.MenuState;

public class EndingState implements GameState {

    private GameStateManager gsm;

    public EndingState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void update(float delta) {
        //enter utk back
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
           // gsm.replace(new MenuState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        //batch.begin();
        //render layar ending
        //batch.end();
    }

    @Override
    public void dispose() {
    }
}
