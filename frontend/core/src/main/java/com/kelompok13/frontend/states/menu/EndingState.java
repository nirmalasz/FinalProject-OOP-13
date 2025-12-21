package com.kelompok13.frontend.states.menu;
// when finish the game

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kelompok13.frontend.background.BackgroundType;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;
import com.kelompok13.frontend.states.gameplay.PlayingState;
import com.kelompok13.frontend.states.menu.MenuState;

public class EndingState implements GameState {

    private GameStateManager gsm;

    private Texture newGameButtonTexture;
    private Texture newGameButtonPressedTexture;
    private Texture closeGameButtonTexture;
    private Texture closeGameButtonPressedTexture;

    private ImageButton newGameButton;
    private ImageButton closeGameButton;
    private Stage stage;


    public EndingState(GameStateManager gsm) {
        this.gsm = gsm;
        this.stage = new Stage(new ScreenViewport());

        loadTextures();
        buildUI();

        Gdx.input.setInputProcessor(stage);
    }

    private void loadTextures() {
        newGameButtonTexture = new Texture("button/new_game_button.png");
        newGameButtonPressedTexture = new Texture("button/new_game_button_pressed.png");
        closeGameButtonTexture = new Texture("button/close_game_button.png");
        closeGameButtonPressedTexture = new Texture("button/close_game_button_pressed.png");
    }

    private void buildUI(){
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        int buttonWidth = 300;
        int buttonHeight = 100;

        int spacing = 100;


        TextureRegionDrawable newGameDrawable = new TextureRegionDrawable(newGameButtonTexture);
        TextureRegionDrawable newGamePressedDrawable = new TextureRegionDrawable(newGameButtonPressedTexture);
        newGameButton = new ImageButton(newGameDrawable, newGamePressedDrawable);

        TextureRegionDrawable closeGameDrawable = new TextureRegionDrawable(closeGameButtonTexture);
        TextureRegionDrawable closeGamePressedDrawable = new TextureRegionDrawable(closeGameButtonPressedTexture);
        closeGameButton = new ImageButton(closeGameDrawable, closeGamePressedDrawable);

        newGameButton.setSize(buttonWidth, buttonHeight);
        newGameButton.setPosition(spacing, spacing-50);

        closeGameButton.setSize(buttonWidth, buttonHeight);
        closeGameButton.setPosition(screenWidth - buttonWidth - spacing, spacing-50);

        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Starting New Game...");
                gsm.set(new PlayingState(gsm), BackgroundType.MAIN);
            }
        });

        closeGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Closing Game...");
                Gdx.app.exit();
            }
        });

        stage.addActor(newGameButton);
        stage.addActor(closeGameButton);
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
