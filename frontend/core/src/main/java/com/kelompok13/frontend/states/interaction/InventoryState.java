package com.kelompok13.frontend.states.interaction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;
import com.kelompok13.frontend.states.gameplay.PlayingState;

public class InventoryState implements GameState {

    private GameStateManager gsm;
    private Stage stage;
    private PlayingState playingState;

    public InventoryState(GameStateManager gsm, PlayingState state) {
        this.gsm = gsm;
        this.playingState = state;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        buildUI();
    }

    private void buildUI() {
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("=== INVENTORY ===", skin);
        Label itemSlot = new Label("| ITEM SLOT |", skin);
        Label jokerSlot = new Label("| JOKER SLOT |", skin);

        TextButton closeButton = new TextButton("CLOSE", skin);
        closeButton.addListener( new ClickListener() {
            @Override
                public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        table.add(title).padBottom(30);
        table.row();
        table.add(itemSlot).padBottom(15);
        table.row();
        table.add(jokerSlot).padBottom(30);
        table.row();
        table.add(closeButton).width(200).height(50);
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
        Gdx.input.setInputProcessor(null); 
    }
}
