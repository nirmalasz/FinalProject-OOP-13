//abstract class for all characters

package com.kelompok13.frontend.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseCharacter {
    protected String name;
    protected Vector2 position;
    protected Rectangle bound;
    protected String[] dialogues;

    public BaseCharacter(String name, Vector2 position, float width, float height){
        this.name = name;
        this.position = position;
        this.bound = new Rectangle(position.x, position.y, width, height);
    }

    public String getInteractPrompt(){
        return "Press E to interact";
    }

    public boolean isPlayerinRange(Rectangle playerBound){
        return bound.overlaps(playerBound);
    }

    abstract void onInteract();
    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public Rectangle getBound() {
        return new Rectangle(bound);
    }

    public String getName() {
        return name;
    }
}
