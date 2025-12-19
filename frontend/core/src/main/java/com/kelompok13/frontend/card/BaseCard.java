//abstract base class for all cards
package com.kelompok13.frontend.card;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class BaseCard {
    private String name;
    private CardType type;

    // Texture support (BaseCard does NOT load textures itself)
    protected TextureRegion textureRegion;   // texture to draw for this card
    private boolean ownsTexture = false;     // true only if this card created the Texture

    public BaseCard(String name, CardType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }


    // Optional convenience: set from a Texture (use owns=true if you want this card to dispose it)
    public void setTexture(Texture texture, boolean ownsTexture) {
        if (texture == null) {
            this.textureRegion = null;
            this.ownsTexture = false;
            return;
        }
        this.textureRegion = new TextureRegion(texture);
        this.ownsTexture = ownsTexture;
    }

    // Or set directly from a TextureRegion (useful when getting region from an atlas)
    public void setTextureRegion(TextureRegion region, boolean ownsTexture) {
        this.textureRegion = region != null ?
            new TextureRegion(region) : null;
        this.ownsTexture = ownsTexture;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    // Draw helper: call from your render code, don't call batch.begin()/end() here
    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        if (textureRegion != null) {
            batch.draw(textureRegion, x, y, width, height);
        }
    }

    // Dispose only if this card owns the texture (usually false when using AssetManager)
    public void disposeTextureIfOwned() {
        if (ownsTexture && textureRegion != null && textureRegion.getTexture() != null) {
            textureRegion.getTexture().dispose();
            textureRegion = null;
            ownsTexture = false;
        }
    }

}
