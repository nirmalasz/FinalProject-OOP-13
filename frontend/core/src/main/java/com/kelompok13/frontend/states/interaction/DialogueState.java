package com.kelompok13.frontend.states.interaction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;

public class DialogueState implements GameState {

    private GameStateManager gsm;
    private Stage stage;

    private String[] dialogues = {
        "Welcome to WILD CARD."
    };
    private int index = 0;

    private Label dialogueLabel;

    public DialogueState(GameStateManager gsm) {
        this.gsm = gsm;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        buildUI();
    }

    private void buildUI() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        dialogueLabel = new Label(dialogues[index], skin);
        dialogueLabel.setAlignment(Align.center);

        TextButton nextButton = new TextButton("NEXT", skin);
        nextButton.addListener(e -> {
            index++;

            //balik ke PlayingState
            if (index >= dialogues.length) {
                gsm.pop();
            } else {
                dialogueLabel.setText(dialogues[index]);
            }
            return true;
        });

        table.add(dialogueLabel).padBottom(30);
        table.row();
        table.add(nextButton).width(200).height(50);
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
