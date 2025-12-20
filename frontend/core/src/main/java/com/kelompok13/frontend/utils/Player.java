package com.kelompok13.frontend.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.kelompok13.frontend.card.PlayingCard;
import com.kelompok13.frontend.models.Currency;

import java.util.List;

public class Player {
    private Vector2 position;
    private Rectangle collider;

    private float width = 64f;
    private float height = 64f;
    private Currency money;

    private Texture walkTexture;
    private Animation<TextureRegion> walkUp;
    private Animation<TextureRegion> walkDown;
    private Animation<TextureRegion> walkLeft;
    private Animation<TextureRegion> walkRight;
    private Animation<TextureRegion> currentAnimation;

    // Animation
    private Texture playerTexture;
    private Animation<TextureRegion> walkAnimation;
    private TextureRegion[] walkFrames;
    private TextureRegion currentFrame;
    private float stateTime;

    private float rotation = 0f;

    // Movement
    private float speed = 200f;
    private Vector2 velocity = new Vector2();
    private boolean isMoving = false;


    private enum Direction { UP, DOWN, LEFT, RIGHT }
    private Direction currentDirection = Direction.DOWN;


    public Player(Vector2 startPosition){
        this.position = new Vector2(startPosition);
        collider = new Rectangle(position.x, position.y, width, height);
        this.money = new Currency();
        playerTexture = new Texture(Gdx.files.internal("player.png"));
        //for now
        walkTexture = playerTexture;
        initializeAnimations();
    }

    private void initializeAnimations(){
        // load texture
        //walkTexture = new Texture(Gdx.files.internal("mc/player_walk.png"));

        // split into frames sized width x height
        TextureRegion[][] tmp = TextureRegion.split(walkTexture, (int)width, (int)height);

        int rows = tmp.length;
        int cols = tmp[0].length;

        Array<TextureRegion> downFrames = new Array<>();
        Array<TextureRegion> leftFrames = new Array<>();
        Array<TextureRegion> rightFrames = new Array<>();
        Array<TextureRegion> upFrames = new Array<>();

        // Fill arrays per-row if available.
        // If fewer rows are present, leave arrays empty
        if (rows >= 1) {
            for (int c = 0; c < cols; c++) downFrames.add(tmp[0][c]);
        }
        if (rows >= 2) {
            for (int c = 0; c < cols; c++) leftFrames.add(tmp[1][c]);
        }
        if (rows >= 3) {
            for (int c = 0; c < cols; c++) rightFrames.add(tmp[2][c]);
        }
        if (rows >= 4) {
            for (int c = 0; c < cols; c++) upFrames.add(tmp[3][c]);
        }

        float frameDuration = 0.1f; // seconds per frame

        // Create animations, with fallbacks if some rows are missing
        if (downFrames.size > 0) walkDown = new Animation<>(frameDuration, downFrames, Animation.PlayMode.LOOP);
        if (rightFrames.size > 0) walkRight = new Animation<>(frameDuration, rightFrames, Animation.PlayMode.LOOP);
        if (leftFrames.size > 0) walkLeft = new Animation<>(frameDuration, leftFrames, Animation.PlayMode.LOOP);
        if (upFrames.size > 0) walkUp = new Animation<>(frameDuration, upFrames, Animation.PlayMode.LOOP);

        // Fallback strategies
        if (walkRight == null) {
            if (walkLeft != null) {
                // flip left frames to get right
                Array<TextureRegion> flipped = new Array<>();
                for (TextureRegion r : leftFrames) {
                    TextureRegion copy = new TextureRegion(r);
                    copy.flip(true, false);
                    flipped.add(copy);
                }
                walkRight = new Animation<>(frameDuration, flipped, Animation.PlayMode.LOOP);
            } else if (downFrames.size > 0) {
                walkRight = new Animation<>(frameDuration, downFrames, Animation.PlayMode.LOOP);
            }
        }

        if (walkLeft == null) {
            if (walkRight != null) {
                Array<TextureRegion> flipped = new Array<>();
                for (TextureRegion r : walkRight.getKeyFrames()) {
                    TextureRegion copy = new TextureRegion(r);
                    copy.flip(true, false);
                    flipped.add(copy);
                }
                walkLeft = new Animation<>(frameDuration, flipped, Animation.PlayMode.LOOP);
            } else if (downFrames.size > 0) {
                // use down as fallback
                walkLeft = new Animation<>(frameDuration, downFrames, Animation.PlayMode.LOOP);
            }
        }

        if (walkUp == null) {
            if (upFrames.size == 0 && downFrames.size > 0) {
                // If no explicit up row, reuse down (or another) as fallback
                walkUp = new Animation<>(frameDuration, downFrames, Animation.PlayMode.LOOP);
            }
        }

        // Default to down if something is still null
        if (walkDown == null) walkDown = walkRight != null ? walkRight : walkLeft;
        if (walkRight == null) walkRight = walkDown;
        if (walkLeft == null) walkLeft = walkRight;
        if (walkUp == null) walkUp = walkDown;

        currentAnimation = walkDown;
        stateTime = 0f;
        currentFrame = currentAnimation.getKeyFrame(0f);
    }

    // Update player position and animation.
    // (-1,0) = left, (1,0) = right, (0,1) = up, (0,-1) = down. (0,0) idle.
    public void update(float delta, Vector2 inputDirection){
        boolean moving = inputDirection != null && (inputDirection.x != 0 || inputDirection.y != 0);
        stateTime += delta;
        if (moving){
            Vector2 movement = new Vector2(inputDirection).nor().scl(speed * delta);
            position.add(movement);
            collider.setPosition(position.x, position.y);

            // choose animation based on primary axis of movement
            if (Math.abs(inputDirection.x) > Math.abs(inputDirection.y)){
                // horizontal movement
                if (inputDirection.x > 0) currentAnimation = walkRight;
                else currentAnimation = walkLeft;
            } else {
                // vertical movement
                if (inputDirection.y > 0) currentAnimation = walkUp;
                else currentAnimation = walkDown;
            }
            currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        } else {
            // idle, first frame
            currentFrame = currentAnimation.getKeyFrame(0);
        }
    }


    public void updateCollider(){
        collider.setPosition(position.x+5, position.y+5);
        collider.setSize(width-20, height-10);
    }

    public void checkBoundaries(float minX, float minY, float maxX, float maxY){
        if (position.x < minX) position.x = minX;
        if (position.x + width > maxX) position.x = maxX - width;
        if (position.y < minY) position.y = minY;
        if (position.y + height > maxY) position.y = maxY - height;

        collider.setPosition(position.x, position.y);

        //bisa ditambah collider check sama objek lain tp ntar dl
    }

    public void render(SpriteBatch spriteBatch){
        if (currentFrame != null){
            spriteBatch.draw(currentFrame,
                position.x, position.y,
                width / 2, height / 2,
                width, height,
                1f, 1f,
                rotation
            );
        }
    }



    public void renderShape(ShapeRenderer shapeRenderer){
        shapeRenderer.setColor(0f,1f,0f,0.5f);
        shapeRenderer.rect(collider.x, collider.y, collider.width, collider.height);
    }

    public TextureRegion getCurrentFrame() {return currentFrame;}

    public Vector2 getPosition(){return position;}

    public Rectangle getCollider(){return collider;}

    public float getWidth() {return width;}

    public float getHeight() {return height;}

    public Currency getMoney() {
        return money;
    }

    public void dispose() {
        if (playerTexture != null) {
            playerTexture.dispose();
        }
    }
}
