package com.kelompok13.frontend.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kelompok13.frontend.strategies.EnemyStrategy;

public class Enemy extends BaseCharater{
    private Texture texture;
    private EnemyStrategy battleStrategy;
    private int rewardMoney;
    private boolean isDefeated;
    private Texture idleTexture;
    private Texture defeatedTexture;

    private String[] challengeDialogues;
    private String[] defeatedDialogues;


    public Enemy(String name, Vector2 position, EnemyStrategy strategy
    , int reward, String[] challengeDialogues, String[] defeatedDialogues) {
        super(name, position, 120,180);

        this.battleStrategy = strategy;
        this.rewardMoney = reward;
        this.isDefeated = false;
        this.challengeDialogues = challengeDialogues;
        this.defeatedDialogues = defeatedDialogues;
        this.dialogues = challengeDialogues;

    }

    private void startBattle(){

    }

    public void markAsDefeated(){
        isDefeated = true;
        this.dialogues = defeatedDialogues;

        //reward to player or nanti di combat resolver
    }

    @Override
    public String getInteractPrompt(){
        return "Challenge " + name + " (Press E)";
    }

    @Override
    void onInteract() {
        if (!isDefeated){
            //will trigger dialogue before defeated
        } else {
            //after defeated dialogue
        }
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture,
                position.x,
                position.y,
                bound.width,
                bound.height);
        }
    }
}
