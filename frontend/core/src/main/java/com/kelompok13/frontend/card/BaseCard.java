//abstract base class for all cards
package com.kelompok13.frontend.card;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class BaseCard {
    private String name;
    private CardType type;


    protected TextureRegion textureRegion;
    private boolean ownsTexture = false;

    public BaseCard(String name, CardType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }


    // set from a Texture (use owns=true card to dispose texture itself)
    public void setTexture(Texture texture, boolean ownsTexture) {
        if (texture == null) {
            this.textureRegion = null;
            this.ownsTexture = false;
            return;
        }
        this.textureRegion = new TextureRegion(texture);
        this.ownsTexture = ownsTexture;
    }

    // call from your render code,
    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        if (textureRegion != null) {
            batch.draw(textureRegion, x, y, width, height);
        }
    }

}
