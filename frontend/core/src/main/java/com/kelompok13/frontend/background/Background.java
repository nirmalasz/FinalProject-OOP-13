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

    // constructor lgsg
    public Background(String path, ResizeMode mode, boolean tileX, boolean tileY) {
        this(new Texture(Gdx.files.internal(path)), mode, tileX, tileY, true);
    }

    // constructor using asset manager
    public Background(Texture texture, ResizeMode mode, boolean tileX, boolean tileY, boolean ownsTexture) {
        this.backgroundTexture = texture;
        this.mode = mode;
        this.tileX = tileX;
        this.tileY = tileY;
        this.ownsTexture = ownsTexture;
        if (tileX || tileY) {
            backgroundTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        }
        // filter nanti lihat dl gambarnya pixel art apa ga
        // kalau backgroundnya pixel pakai nearest
        // kalau smooth pakai linear
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

            // SIMPLIFIED: Center on camera with parallax effect
            float parallaxOffsetX = (camCenterX - vpW / 2f) * (1f - parallaxX);
            float parallaxOffsetY = (camCenterY - vpH / 2f) * (1f - parallaxY);

            // Calculate position to center the background
            float centerX = camCenterX - drawW / 2f;
            float centerY = camCenterY - drawH / 2f;

            // Apply parallax offset
            float drawX = centerX + parallaxOffsetX;
            float drawY = centerY + parallaxOffsetY;

            batch.draw(backgroundRegion, drawX, drawY, drawW, drawH);

        } else if (mode == ResizeMode.TILE) {
            //adjust this value to make tiles smaller/larger
            float tileScale = 0.3f;
            float scaledTexW = texW * tileScale;
            float scaledTexH = texH * tileScale;

            float viewLeft = camCenterX - vpW / 2f;
            float viewRight = camCenterX + vpW / 2f;
            float viewBottom = camCenterY - vpH / 2f;
            float viewTop = camCenterY + vpH / 2f;

            float offsetX = camCenterX * (1f - parallaxX);
            float offsetY = camCenterY * (1f - parallaxY);

            int startTileX = (int)Math.floor((viewLeft - offsetX) / scaledTexW);
            int endTileX = (int)Math.ceil((viewRight - offsetX) / scaledTexW);
            int startTileY = (int)Math.floor((viewBottom - offsetY) / scaledTexH);
            int endTileY = (int)Math.ceil((viewTop - offsetY) / scaledTexH);

            for (int x = startTileX; x <= endTileX; x++) {
                for (int y = startTileY; y <= endTileY; y++) {
                    float drawX = x * scaledTexW + offsetX;
                    float drawY = y * scaledTexH + offsetY;
                    batch.draw(backgroundRegion, drawX, drawY, scaledTexW, scaledTexH);
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
