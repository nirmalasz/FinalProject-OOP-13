package com.kelompok13.frontend.states.menu;
//game intro (optional)

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kelompok13.frontend.background.BackgroundType;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;
import com.kelompok13.frontend.states.gameplay.PlayingState;
import com.kelompok13.frontend.utils.GameManager;
import com.kelompok13.frontend.utils.GameManager;

import java.awt.*;

import static com.kelompok13.frontend.background.BackgroundType.MAIN;

public class OpeningState implements GameState {

    private GameStateManager gsm;
    private Stage stage;
    private Skin skin;
    private TextField nameField;
    private TextButton startButton;
    private boolean opening = false;

    public OpeningState(GameStateManager gsm){
        this.gsm = gsm;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createBasicSkin();
        buildUI();


    }

    private void createBasicSkin(){
        skin = new Skin();
        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        Pixmap white = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        white.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        white.fill();
        skin.add("white", new Texture(white));
        white.dispose();

        Pixmap gray = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        gray.setColor(com.badlogic.gdx.graphics.Color.GRAY);
        gray.fill();
        skin.add("gray", new Texture(gray));
        gray.dispose();

        Pixmap dark_gray = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        dark_gray.setColor(com.badlogic.gdx.graphics.Color.DARK_GRAY);
        dark_gray.fill();
        skin.add("dark_gray", new Texture(dark_gray));
        dark_gray.dispose();

        com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle labelStyle = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = com.badlogic.gdx.graphics.Color.WHITE;
        skin.add("default", labelStyle);

        com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle textFieldStyle = new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.background = skin.newDrawable("dark_gray");
        textFieldStyle.cursor = skin.newDrawable("white");
        textFieldStyle.selection = skin.newDrawable("gray");
        skin.add("default", textFieldStyle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.newDrawable("gray");
        textButtonStyle.down = skin.newDrawable("white");
        textButtonStyle.over = skin.newDrawable("dark_gray");
        skin.add("default", textButtonStyle);
    }

    private void buildUI(){
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setBackground((Drawable) null);

        com.badlogic.gdx.scenes.scene2d.ui.Label
            title = new com.badlogic.gdx.scenes.scene2d.ui.Label("WILD CARD", skin);
        title.setScale(2f);
        com.badlogic.gdx.scenes.scene2d.ui.Label
            prompt = new com.badlogic.gdx.scenes.scene2d.ui.Label("Enter Your Name:", skin);
        com.badlogic.gdx.scenes.scene2d.ui.TextField
            name = new com.badlogic.gdx.scenes.scene2d.ui.TextField("", skin);
        name.setMessageText("Username...");
        name.setAlignment(Align.center);
        startButton = new TextButton("START GAME", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String usernameInput = name.getText().trim();
                if (usernameInput.isEmpty()) {
                    usernameInput = "Guest";
                }
                GameManager.getInstance().registerPlayer(usernameInput);
                gsm.set(new PlayingState(gsm), BackgroundType.MAIN);
            }
        });
        table.add(title).padBottom(50f);
        table.row();
        table.add(prompt).padBottom(20f);
        table.row();
        table.add(name).width(200f).height(40f).padBottom(20f);
        table.row();
        table.add(startButton).width(200f).height(60f);
    }

    @Override
    public void update(float delta){
        if(opening) return;
        opening = true;

        System.out.println("WILD CARD");

//        GameManager.getInstance().registerPlayer("Player :");
//        GameManager.getInstance().startGame();
//
//        // gsm.replace(new MenuState(gsm));
        stage.act(delta);
    }

    @Override
        public void render(SpriteBatch batch) {

        stage.getViewport().apply();
        stage.draw();
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
