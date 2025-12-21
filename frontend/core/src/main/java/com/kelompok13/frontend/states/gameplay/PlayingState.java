package com.kelompok13.frontend.states.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kelompok13.frontend.characters.Bartender;
import com.kelompok13.frontend.characters.BaseCharacter;
import com.kelompok13.frontend.characters.Enemy;
import com.kelompok13.frontend.factories.CharacterFactory;
import com.kelompok13.frontend.models.Inventory;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;
import com.kelompok13.frontend.states.interaction.InventoryState;
import com.kelompok13.frontend.states.menu.EndingState;
import com.kelompok13.frontend.utils.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//yayy
//when player roaming around the bar
public class PlayingState  implements GameState {
    private GameStateManager gsm;

    private Player player;
    private List<BaseCharacter> characters;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private Texture closeButtonTexture;
    private Texture closeButtonPressedTexture;
    private ImageButton closeButton;
    private Stage uiStage;

    private CharacterFactory characterFactory;
    private Enemy jackEnemy;
    private Enemy queenEnemy;
    private Enemy kingEnemy;
    private Enemy jokerEnemy;
    private Bartender bartender;

    private BaseCharacter currentInteractable;
    private float interactionCooldown = 0.5f;
    private float currentCooldown = 0f;

    private Inventory inventory;
    private InputMultiplexer inputMultiplexer;

    private boolean allEnemiesDefeated = false;
    private float victoryDelayTimer = 0f;
    private static final float VICTORY_DELAY = 3.0f;

    public PlayingState(GameStateManager gsm){
        this.gsm = gsm;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 5120/1.2f, 5120/1.2f);

        this.uiStage = new Stage(new ScreenViewport());

        this.characters = new ArrayList<>();
        this.characterFactory = new CharacterFactory();
        this.inventory = new Inventory(5, 3);
        initializeBarScene();
        buildUI();
        setupInput();
    }

    @Override
    public void resume(){
        setupInput();
        System.out.println("Resumed PlayingState");
    }

    private void buildUI(){
        closeButtonTexture = new Texture(Gdx.files.internal("button/closegame_button.png"));
        closeButtonPressedTexture = new Texture(Gdx.files.internal("button" +
            "/closegame_button_pressed.png"));

        //close button
        TextureRegionDrawable closeUpDrawable =
            new TextureRegionDrawable(new TextureRegion(closeButtonTexture));
        TextureRegionDrawable closeDownDrawable =
            new TextureRegionDrawable(new TextureRegion(closeButtonPressedTexture));
        closeButton = new ImageButton(closeUpDrawable, closeDownDrawable);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        closeButton.setSize(100, 100);
        closeButton.setPosition(screenWidth-120, screenHeight-120);

        closeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                System.out.println("Close button clicked!");
            }
        });
        uiStage.addActor(closeButton);
    }

    private void setupInput() {
        inputMultiplexer = new InputMultiplexer();

        // UI stage processes input first (for button clicks)
        inputMultiplexer.addProcessor(uiStage);

        // Set the multiplexer as the input processor
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public Inventory getInventory() {
        return inventory;
    }

    private void initializeBarScene(){
        player = new Player(new Vector2(5120/2 - 320,5120/2-320));
        createBarCharacters();
    }

    private void createBarCharacters(){
        jackEnemy = (Enemy) characterFactory.createCharacter("jack", new Vector2(150, 150));
        queenEnemy = (Enemy) characterFactory.createCharacter("queen", new Vector2(650, 150));
        kingEnemy = (Enemy) characterFactory.createCharacter("king", new Vector2(150, 350));
        jokerEnemy = (Enemy) characterFactory.createCharacter("joker", new Vector2(650, 350));
        bartender = (Bartender) characterFactory.createCharacter("bartender", new Vector2(400, 400));

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
            allEnemiesDefeated = true;
            victoryDelayTimer = 0f;
            // gsm.push(new EndingState(gsm));
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
        if (allEnemiesDefeated) {
            victoryDelayTimer += delta;

            if (victoryDelayTimer >= VICTORY_DELAY) {
                gsm.push(new EndingState(gsm));
                allEnemiesDefeated = false;
            }
            return;
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
            InventoryState inventoryState = new InventoryState(gsm, this);
            gsm.push(inventoryState);
        }

        uiStage.act(delta);

    }

    @Override
    public void render(SpriteBatch batch) {
        // Set camera projection
        batch.setProjectionMatrix(camera.combined);

        //batch.begin();

        // Render all characters
        for (BaseCharacter character : characters) {
            character.render(batch);
        }

        // Render player
        player.render(batch);
        batch.setProjectionMatrix(uiStage.getCamera().combined);
        uiStage.draw();

        //batch.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        player.dispose();
        uiStage.dispose();
        closeButtonTexture.dispose();
        closeButtonPressedTexture.dispose();

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
        player.checkBoundaries(0, 0, 5120, 8640);
    }

    private void updateCamera(float delta) {
        // Camera follows player
        camera.position.lerp(new Vector3(
            player.getPosition().x + player.getWidth() / 2,
            player.getPosition().y + player.getHeight() / 2,
            0
        ), 0.1f);
        float halfVPW = camera.viewportWidth / 2f;
        float halfVPH = camera.viewportHeight / 2f;

        camera.position.x = Math.max(halfVPW, Math.min(camera.position.x, 5120 - halfVPW));
        camera.position.y = Math.max(halfVPH, Math.min(camera.position.y, 8640 - halfVPH));
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

    public OrthographicCamera getCamera() {
        return camera; // or however you access your game camera
    }
}
