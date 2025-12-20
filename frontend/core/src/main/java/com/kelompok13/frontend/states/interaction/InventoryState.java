package com.kelompok13.frontend.states.interaction;
// show player item only

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gsm.pop();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        //batch.begin();
        //render item/card
        com.badlogic.gdx.graphics.Color original = batch.getColor();
        batch.setColor(0.2f, 0.2f, 0.3f, 0.8f); // Semi-transparent dark blue
        batch.draw(new com.badlogic.gdx.graphics.Texture(Gdx.files.internal("white_pixel.png")),
            100, 100, 600, 280); // Background

        batch.setColor(original); // Reset color

        BitmapFont font = new BitmapFont();

        font.draw(batch, "INVENTORY", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-120);
        font.draw(batch, "Press ESC or ENTER to close", 250, 150);

        //batch.end();
        font.dispose();
    }

    @Override
    public void dispose() {
    }
}
