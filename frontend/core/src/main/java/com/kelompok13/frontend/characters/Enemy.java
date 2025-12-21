package com.kelompok13.frontend.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kelompok13.frontend.strategies.EnemyStrategy;

public class Enemy extends BaseCharacter {
    private Texture texture;
    private EnemyStrategy battleStrategy;
    private int rewardMoney;
    public boolean isDefeated;
    private int neededScoreToWin = 200;

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
    public void onInteract() {
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

    public boolean isDefeated() {
        return  isDefeated;
    }

    public EnemyStrategy getBattleStrategy() {
        return battleStrategy;
    }

    public int getRewardMoney() {
        return rewardMoney;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getNeededScoreToWin() {
        return neededScoreToWin;
    }
}
