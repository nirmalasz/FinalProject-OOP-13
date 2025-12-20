
package com.kelompok13.frontend.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bartender extends BaseCharacter {
    private Texture texture;
    private int dialogueIndex;
    private boolean openingDialoguePlayed;

    public Bartender(String name, Vector2 position, String[] dialogues, Texture texture) {
        super(name, position, 100, 150);
        this.texture = texture;
        this.dialogueIndex = 0;
        this.dialogues = dialogues;
        this.openingDialoguePlayed = false;
    }

    @Override
    public void onInteract(){

        if (dialogues == null || dialogues.length == 0) {
            System.out.println(name + ": ...");
            return;
        }

        if (!openingDialoguePlayed && dialogueIndex <4){
            System.out.println(name + ": " + dialogues[dialogueIndex]);
            dialogueIndex++;

            // After playing the opening dialogue, mark as played
            if (dialogueIndex >= 4) {
                openingDialoguePlayed = true;
                dialogueIndex = 4; // Set to last dialogue index
            }
        }  else {
            System.out.println(name + ": " + dialogues[4]);
            if (dialogues.length > 5) {
                int randomIndex = 4 + (int)(Math.random() * (dialogues.length - 4));
                System.out.println(name + ": " + dialogues[randomIndex]);
            }
        }
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        if(texture != null){
            batch.draw(texture, position.x, position.y, bound.width, bound.height);
        }
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }

    public void dispose(){
        if (texture != null){
            texture.dispose();
        }
    }

}
