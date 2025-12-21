package com.kelompok13.frontend.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameState {
    void update(float delta);
    void render(SpriteBatch batch);
    void dispose();
    default void resume() {};
}
