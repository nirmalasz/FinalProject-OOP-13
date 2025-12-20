package com.kelompok13.frontend.states.menu;
//game intro (optional) 

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;
import com.kelompok13.frontend.GameManager; 
import com.kelompok13.frontend.utils.GameManager;

public class OpeningState implements GameState {

    private GameStateManager gsm;
    private boolean opening = false;

    public OpeningState(GameStateManager gsm){
        this.gsm = gsm;
    }

    @Override
    public void update(float delta){
        if(opening) return;
        opening = true;

        System.out.println("WILD CARD");

        GameManager.getInstance().registerPlayer("Player :");
        GameManager.getInstance().startGame();

        gsm.update(new MenuState(gsm)); //gamestate blm diimplements di menustate
    }

    @Override
        public void render(SpriteBatch batch) {
        //UI
    }


    @Override
    public void dispose() {
    }
}
