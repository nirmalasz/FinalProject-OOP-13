package com.kelompok13.frontend.states.interaction;
// show player item only

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kelompok13.frontend.card.JokerCard;
import com.kelompok13.frontend.item.BaseItem;
import com.kelompok13.frontend.models.Inventory;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;
import com.kelompok13.frontend.states.gameplay.PlayingState;

public class InventoryState implements GameState {

    private GameStateManager gsm;
    private PlayingState playingState;
    private Stage stage;
    private Inventory inventory;

    public InventoryState(GameStateManager gsm, PlayingState previousState) {
        this.gsm = gsm;
        this.playingState = previousState;
        this.inventory = playingState.getInventory();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        buildUI();
    }

    private void buildUI(){
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("=== INVENTORY ===", skin);
        Label itemsTitle = new Label("ITEMS:", skin);
        Label jokersTitle = new Label("JOKERS:", skin);

        table.add(title).padBottom(30);
        table.row();
        table.add(itemsTitle).padBottom(10);
        table.row();
        table.add(jokersTitle).padBottom(10);
        table.row();

        Table itemsTable = new Table();
        for (BaseItem item : inventory.getItems()) {
            Label itemLabel = new Label("- " + item.getName(), skin);
            itemsTable.add(itemLabel).left();
            itemsTable.row();
        }
        if (inventory.getItems().isEmpty()) {
            itemsTable.add(new Label("(empty)", skin)).left();
        }
        table.add(itemsTable).padBottom(20);
        table.row();

        Table jokersTable = new Table();
        for (JokerCard joker : inventory.getJokerCards()) {
            Label jokerLabel = new Label("- " + joker.getName(), skin);
            jokersTable.add(jokerLabel).left();
            jokersTable.row();
        }
        if (inventory.getJokerCards().isEmpty()) {
            jokersTable.add(new Label("(empty)", skin)).left();
        }
        table.add(jokersTable).padBottom(30);
        table.row();

        TextButton closeButton = new TextButton("CLOSE", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        table.add(closeButton).width(200).height(50);
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.getInputProcessor() != stage) {
            Gdx.input.setInputProcessor(stage);
        }
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
