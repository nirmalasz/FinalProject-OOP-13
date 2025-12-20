package com.kelompok13.frontend.states.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kelompok13.frontend.characters.Bartender;
import com.kelompok13.frontend.characters.BaseCharacter;
import com.kelompok13.frontend.characters.Enemy;
import com.kelompok13.frontend.factories.CharacterFactory;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;
import com.kelompok13.frontend.states.menu.EndingState;
import com.kelompok13.frontend.utils.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//when player roaming around the bar
public class PlayingState  implements GameState {
    private GameStateManager gsm;

    private Player player;
    private List<BaseCharacter> characters;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private CharacterFactory characterFactory;
    private Enemy jackEnemy;
    private Enemy queenEnemy;
    private Enemy kingEnemy;
    private Enemy jokerEnemy;
    private Bartender bartender;

    private BaseCharacter currentInteractable;
    private float interactionCooldown = 0.5f;
    private float currentCooldown = 0f;


    public PlayingState(GameStateManager gsm){
        this.gsm = gsm;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        // this.camera.setToOrtho(false, 800, 480);
        this.characters = new ArrayList<>();
        this.characterFactory = new CharacterFactory();
        initializeBarScene();
    }

    private void initializeBarScene(){
        player = new Player(new Vector2(10,10));
        createBarCharacters();
    }

    private void createBarCharacters(){
        jackEnemy = (Enemy) characterFactory.createCharacter("jack", new Vector2(100,100));
        queenEnemy = (Enemy) characterFactory.createCharacter("queen", new Vector2(200,100));
        kingEnemy = (Enemy) characterFactory.createCharacter("king", new Vector2(300,100));
        jokerEnemy = (Enemy) characterFactory.createCharacter("joker", new Vector2(400,100));
        bartender = (Bartender) characterFactory.createCharacter("bartender", new Vector2(500,100));

        characters.add(jackEnemy);
        characters.add(queenEnemy);
        characters.add(kingEnemy);
        characters.add(jokerEnemy);
        characters.add(bartender);
    }

    private void startBattle(Enemy enemy){
        System.out.println("Starting battle with " + enemy.getName());

        BattleState battleState = new BattleState(gsm, enemy, this);
        gsm.push(battleState);
    }

    public void onEnemyDefeated(Enemy enemy){
        enemy.markAsDefeated();
        System.out.println(enemy.getName() + " defeated!");
        characters.remove(enemy);
        if (areAllEnemiesDefeated()){
            System.out.println("All enemies defeated! You win!");
            //gsm.push(new EndingState(gsm));
        }
    }
    private boolean areAllEnemiesDefeated() {
        return jackEnemy.isDefeated() &&
            queenEnemy.isDefeated() &&
            kingEnemy.isDefeated() &&
            jokerEnemy.isDefeated();
    }


    @Override
    public void update(float delta) {
        if(currentCooldown > 0){
            currentCooldown -= delta;
        }

        handlePlayerInput(delta);
        updateCamera(delta);
        checkInteractions();

        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && currentInteractable != null && currentCooldown <= 0) {
            currentInteractable.onInteract();
            currentCooldown = interactionCooldown;

            // If interacting with enemy, start battle
            if (currentInteractable instanceof Enemy) {
                Enemy enemy = (Enemy) currentInteractable;
                startBattle(enemy);
            }
        }

        // Open inventory
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            // gsm.push(new InventoryState(gsm));
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        // Set camera projection
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Render all characters
        for (BaseCharacter character : characters) {
            character.render(batch);
        }

        // Render player
        player.render(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        player.dispose();

        for (BaseCharacter character : characters) {
            character.dispose();
        }
    }

    public Player getPlayer() {
        return player;
    }

    private void handlePlayerInput(float delta) {
        Vector2 inputDirection = new Vector2(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) inputDirection.y = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) inputDirection.y = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) inputDirection.x = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) inputDirection.x = 1;

        player.update(delta, inputDirection);

        // Keep player within bounds
        player.checkBoundaries(0, 0, 1280, 720);
    }

    private void updateCamera(float delta) {
        // Camera follows player
        camera.position.lerp(new Vector3(
            player.getPosition().x + player.getWidth() / 2,
            player.getPosition().y + player.getHeight() / 2,
            0
        ), 0.1f);
        camera.update();
    }

    private void checkInteractions() {
        currentInteractable = null;
        Rectangle playerBounds = player.getCollider();
        float closestDistance = Float.MAX_VALUE;

        for (BaseCharacter character : characters) {
            if (character.isPlayerinRange(playerBounds) && character.isInteractable()) {
                float distance = character.getPosition().dst(player.getPosition());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    currentInteractable = character;
                }
            }
        }
    }
}
