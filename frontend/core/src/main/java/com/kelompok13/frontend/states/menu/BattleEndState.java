package com.kelompok13.frontend.states.menu;
//display win or lose after round


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;

public class BattleEndState implements GameState {

    private GameStateManager gsm;
    private Stage stage;

    public BattleEndState(GameStateManager gsm, boolean win) {
        this.gsm = gsm;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label resultLabel = new Label(win ? "YOU WIN" : "YOU LOSE", skin);

        TextButton continueButton = new TextButton("CONTINUE", skin);
        continueButton.addListener(e -> {
            gsm.pop(); //balik ke PlayingState
            return true;
        });

        table.add(resultLabel).padBottom(30);
        table.row();
        table.add(continueButton).width(200).height(50);
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
