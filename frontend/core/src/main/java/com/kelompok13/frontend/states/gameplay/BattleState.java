package com.kelompok13.frontend.states.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kelompok13.frontend.characters.Enemy;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;

//yayy
public class BattleState implements GameState {
    private PlayingState playingState; // Reference to the main playing state
    private GameStateManager gsm;
    private Enemy enemy;

    // Battle state
    private boolean battleStarted;
    private boolean battleEnded;
    private boolean playerWon;
    private float battleTimer;
    private float resultDisplayTime;

    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    private int playerScore;


    public BattleState(GameStateManager gsm, Enemy enemy, PlayingState playingState){
        this.gsm = gsm;
        this.enemy = enemy;
        this.playingState = playingState;

        this.battleStarted = false;
        this.battleEnded = false;
        this.playerWon = false;
        this.battleTimer = 0;
        this.resultDisplayTime = 0;

        this.playerScore = 0;

        // Initialize UI
        this.font = new BitmapFont();
        this.shapeRenderer = new ShapeRenderer();
        System.out.println("Battle against " + enemy.getName());
    }

    private void startBattle(){
        this.battleStarted = true;
        System.out.println("Battle started!");

        //need to draw cards, setuphand, and start combaat resolver
    }

    private void endBattle(boolean playerWon){
        if(playerWon){
            System.out.println("Player won battle");
            if(playingState.getPlayer() != null){
                playingState.getPlayer().getMoney().addMoney(enemy.getRewardMoney());
            }
            playingState.onEnemyDefeated(enemy);
        } else{
            System.out.println("Better luck next time!");
        }

        resultDisplayTime = 0;
    }

    @Override
    public void update(float delta) {
        if (!battleStarted) {
            startBattle();
            return;
        }

        if (battleEnded) {
            resultDisplayTime += delta;

            // Return to playing state after 3 seconds
            if (resultDisplayTime > 3.0f) {
                gsm.pop();
            }
            return;
        }

        //check if player play card
        //evaluate hand
        //determine win

    }

    @Override
    public void render(SpriteBatch batch) {
        //batch.begin();

        float centerX = Gdx.graphics.getWidth() / 2;
        float centerY = Gdx.graphics.getHeight() / 2;

        if(!battleEnded){
            font.draw(batch, "BATTLE: " + enemy.getName(), centerX - 50, centerY + 100);
        } else {
            // Draw result
            if (playerWon) {
                font.draw(batch, "VICTORY!", centerX - 40, centerY + 50);
                font.draw(batch, "Reward: $" + enemy.getRewardMoney(),
                    centerX - 50, centerY);
            } else {
                font.draw(batch, "DEFEAT!", centerX - 30, centerY + 50);
            }
            font.draw(batch, "Returning in " +
                    String.format("%.1f", 3.0f - resultDisplayTime) + "s...",
                centerX - 70, centerY - 50);
        }
        //batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
    }
}
