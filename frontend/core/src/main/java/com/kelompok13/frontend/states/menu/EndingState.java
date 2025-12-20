package com.kelompok13.frontend.states.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kelompok13.frontend.background.BackgroundType;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;
import com.kelompok13.frontend.states.gameplay.PlayingState;

public class EndingState implements GameState {

    private GameStateManager gsm;
    private Stage stage;

    public EndingState(GameStateManager gsm) {
        this.gsm = gsm;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("=== GAME FINISHED ===", skin);

        TextButton restartButton = new TextButton("RESTART", skin);
        TextButton quitButton = new TextButton("QUIT", skin);

        restartButton.addListener(e -> {
            gsm.set(new PlayingState(gsm), BackgroundType.MAIN);
            return true;
        });

        quitButton.addListener(e -> {
            Gdx.app.exit();
            return true;
        });

        table.add(title).padBottom(30);
        table.row();
        table.add(restartButton).width(200).height(50).padBottom(15);
        table.row();
        table.add(quitButton).width(200).height(50);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
