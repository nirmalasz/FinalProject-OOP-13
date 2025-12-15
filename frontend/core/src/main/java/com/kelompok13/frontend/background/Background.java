package com.kelompok13.frontend.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Background {
    protected Texture backgroundTexture;
    protected TextureRegion backgroundRegion;
    protected float width;
    protected float height;
    protected boolean ownsTexture = true;
    protected ResizeMode mode = ResizeMode.COVER;
    protected boolean tileX = false;
    protected boolean tileY = false;
    protected float parallaxX = 1f; //moves with camera by default
    protected float parallaxY = 1f;

    // Direct-loading constructor (from file path)
    public Background(String path, ResizeMode mode, boolean tileX, boolean tileY) {
        this(new Texture(Gdx.files.internal(path)), mode, tileX, tileY, true);
    }

    // Texture-based constructor (for AssetManager - set ownsTexture=false if texture from AssetManager)
    public Background(Texture texture, ResizeMode mode, boolean tileX, boolean tileY, boolean ownsTexture) {
        this.backgroundTexture = texture;
        this.mode = mode;
        this.tileX = tileX;
        this.tileY = tileY;
        this.ownsTexture = ownsTexture;
        if (tileX || tileY) {
            backgroundTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        }
        // choose filtering: nearest for pixel-art, Linear for smooth. adjust as needed:
        backgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        this.backgroundRegion = new TextureRegion(backgroundTexture);
        this.width = backgroundTexture.getWidth();
        this.height = backgroundTexture.getHeight();
    }

    public void setParallax(float px, float py) {
        this.parallaxX = px;
        this.parallaxY = py;
    }

    // alpha used for crossfade
    public void render(SpriteBatch batch, OrthographicCamera camera, float vpW, float vpH, float alpha) {
        if (backgroundTexture == null) return;

        batch.setColor(1f, 1f, 1f, alpha);

        float texW = backgroundTexture.getWidth();
        float texH = backgroundTexture.getHeight();
        float camCenterX = camera.position.x;
        float camCenterY = camera.position.y;

        if (mode == ResizeMode.COVER || mode == ResizeMode.CONTAIN) {
            float scale = (mode == ResizeMode.COVER)
                ? Math.max(vpW / texW, vpH / texH)
                : Math.min(vpW / texW, vpH / texH);

            float drawW = texW * scale;
            float drawH = texH * scale;

            // center on camera, apply parallax as small offset
            float drawX = camCenterX - drawW / 2f + (1f - parallaxX) * (camCenterX);
            float drawY = camCenterY - drawH / 2f + (1f - parallaxY) * (camCenterY);

            batch.draw(backgroundRegion, drawX, drawY, drawW, drawH);

        } else if (mode == ResizeMode.TILE) {
            // tile to cover viewport:
            // compute how many tiles needed and draw in loops
            // align tiles with camera and parallax
            float px = (camCenterX) * (1f - parallaxX);
            float py = (camCenterY) * (1f - parallaxY);

            // start at viewport left-bottom in world coords
            float viewLeft = camCenterX - vpW / 2f;
            float viewBottom = camCenterY - vpH / 2f;

            // compute first tile coordinates (aligned to texture)
            float startX = viewLeft - (px % texW) - texW;
            float startY = viewBottom - (py % texH) - texH;

            int cols = (int) Math.ceil(vpW / texW) + 2;
            int rows = (int) Math.ceil(vpH / texH) + 2;

            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    float x = startX + i * texW + (1f - parallaxX) * px;
                    float y = startY + j * texH + (1f - parallaxY) * py;
                    batch.draw(backgroundRegion, x, y, texW, texH);
                }
            }

        } else { // STRETCH
            float drawX = camCenterX - vpW / 2f + (1f - parallaxX) * (camCenterX);
            float drawY = camCenterY - vpH / 2f + (1f - parallaxY) * (camCenterY);
            batch.draw(backgroundRegion, drawX, drawY, vpW, vpH);
        }

        batch.setColor(1f, 1f, 1f, 1f); // reset color
    }

    public void dispose() {
        if (ownsTexture && backgroundTexture != null) {
            backgroundTexture.dispose();
            backgroundTexture = null;
        }
    }
}
