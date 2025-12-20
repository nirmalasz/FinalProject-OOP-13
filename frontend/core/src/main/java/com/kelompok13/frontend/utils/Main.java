package com.kelompok13.frontend.utils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kelompok13.frontend.states.GameStateManager;
import com.kelompok13.frontend.states.menu.OpeningState;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private GameStateManager gsm;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager();

        // Start with opening state
        gsm.push(new OpeningState(gsm));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update game logic
        gsm.update(Gdx.graphics.getDeltaTime());

        // Render everything
        batch.begin();
        gsm.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        gsm.dispose();
    }
}
