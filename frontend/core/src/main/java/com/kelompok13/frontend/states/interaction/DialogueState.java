package com.kelompok13.frontend.states.interaction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;
import com.kelompok13.frontend.states.gameplay.PlayingState;

public class DialogueState implements GameState {
    private GameStateManager gsm;

    private Texture[] dialogue;
    private int currentDialogue = 0;

    public DialogueState(GameStateManager gsm) {
        this.gsm = gsm;

        dialogue = new Texture[] {
            new Texture("dialogue1"),
            new Texture("dialogue2"),
            new Texture("dialogue3")
        };
    }

    @Override
    public void update(float delta){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            currentDialogue++;

            if (currentDialogue >= dialogue.length) {
                //gsm.update(new PlayingState(gsm));
            }
        }

    }

    @Override
        public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(dialogue[currentDialogue], 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        for (Texture tex : dialogue) {
            tex.dispose();
        }
    }
}
