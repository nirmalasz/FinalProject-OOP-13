//manage and switch between
//different background types based on game state

package com.kelompok13.frontend.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;


// simple manager that handle a current background and a next background for transitions
// crossfade transition: renders current with alpha (1 - t) and next with t while t goes 0..1.
public class BackgroundManager {
    private Background current;
    private Background next;

    private float transitionProgress = 0f;
    private float transitionDuration = 0.6f;
    private boolean transitioning = false;

    public enum TransitionType { CROSSFADE }
    private TransitionType transitionType = TransitionType.CROSSFADE;

    private final AssetManager assets;

    public BackgroundManager(AssetManager assets) {
        this.assets = assets;
    }

    public BackgroundManager() { this(null); }

    // Immediate swap (no transition)
    public void setBackgroundImmediate(Background bg) {
        if (current != null) current.dispose();
        current = bg;
        next = null;
        transitioning = false;
        transitionProgress = 0f;
    }

    // Start crossfade to a prepared Background instance
    public void transitionTo(Background bg, float durationSeconds) {
        if (bg == null) return;
        if (bg == current) return; // no transition if same object

        this.next = bg;
        this.transitionDuration = Math.max(0.001f, durationSeconds);
        this.transitionProgress = 0f;
        this.transitioning = true;
        this.transitionType = TransitionType.CROSSFADE;
    }

    // Update transition state. Call each frame w delta
    public void update(float delta) {
        if (!transitioning) return;
        transitionProgress += delta / transitionDuration;
        if (transitionProgress >= 1f) {
            // finish transition
            transitionProgress = 1f;
            // dispose old background
            if (current != null) current.dispose();
            current = next;
            next = null;
            transitioning = false;
            transitionProgress = 0f;
        }
    }

    // called inside batch.begin()/end() with camera matrix already set
    public void render(SpriteBatch batch, OrthographicCamera camera, float vpW, float vpH) {
        if (current == null && next == null) return;

        if (!transitioning) {
            if (current != null) current.render(batch, camera, vpW, vpH, 1f);
        } else {
            float t = transitionProgress;
            // crossfade: current (1 - t), next t
            if (current != null) current.render(batch, camera, vpW, vpH, 1f - t);
            if (next != null) next.render(batch, camera, vpW, vpH, t);
        }
    }


    // Dispose owned backgrouns.
    public void dispose() {
        if (current != null) current.dispose();
        if (next != null) next.dispose();
    }
}

