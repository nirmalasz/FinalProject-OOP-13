package com.kelompok13.frontend.states.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kelompok13.frontend.card.CardTextureManager;
import com.kelompok13.frontend.card.PlayingCard;
import com.kelompok13.frontend.characters.Enemy;
import com.kelompok13.frontend.gameplay.CombatResolver;
import com.kelompok13.frontend.gameplay.DeckManager;
import com.kelompok13.frontend.gameplay.HandEvaluator;
import com.kelompok13.frontend.models.Deck;
import com.kelompok13.frontend.states.GameState;
import com.kelompok13.frontend.states.GameStateManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.util.ArrayList;
import java.util.List;

//yayy
public class BattleState implements GameState {
    private PlayingState playingState; // Reference to the main playing state
    private GameStateManager gsm;
    private Enemy enemy;
    private DeckManager deckManager;
    private CardTextureManager cardTextureManager;

    // Battle state
    private boolean battleStarted;
    private boolean battleEnded;
    private boolean playerWon;
    private float resultDisplayTime;

    private Texture playButtonTexture;
    private Texture playButtonPressedTexture;
    private Texture discardButtonTexture;
    private Texture discardButtonPressedTexture;
    private Texture closeButtonTexture;
    private Texture closeButtonPressedTexture;

    private ImageButton playButton;
    private ImageButton discardButton;
    private ImageButton closeButton;

    private BitmapFont titleFont;
    private BitmapFont font;
    private BitmapFont scoreFont;
    private BitmapFont discardFont;
    private BitmapFont playFont;

    private ShapeRenderer shapeRenderer;
    private Stage stage;

    private int playerScore;

    private List<PlayingCard> fullDeck = new Deck().getCardList();
    private List<PlayingCard> currentHand;
    private List<PlayingCard> playedCards;
    private List<Rectangle> cardColliders;
    private List<Boolean> selectedCards;

    // limit for play and discard: for now will be fixed bcs we haven't implement special item
    private static final int MAX_PLAY_CARDS = 5;
    private static final int MAX_DISCARD_CARDS = 5;

    private int playRemaining;
    private int discardRemaining;

    private HandEvaluator handEvaluator;
    private CombatResolver combatResolver;
    private int targetScore;

    private Color cardColor = new Color (0.9f, 0.9f, 0.95f, 1f);
    private Color selectedCardColor = new Color (0.7f, 0.9f, 1f, 1f);

    public BattleState(GameStateManager gsm, Enemy enemy, PlayingState playingState){
        this.gsm = gsm;
        this.enemy = enemy;
        this.playingState = playingState;
        this.deckManager = new DeckManager(fullDeck);

        this.battleStarted = false;
        this.battleEnded = false;
        this.playerWon = false;
        this.resultDisplayTime = 0;

        this.handEvaluator = new HandEvaluator();
        this.combatResolver = new CombatResolver(handEvaluator);
        this.targetScore = enemy.getNeededScoreToWin();

        this.cardTextureManager = CardTextureManager.getInstance();

        currentHand = new ArrayList<>();
        playedCards = new ArrayList<>();
        cardColliders = new ArrayList<>();
        selectedCards = new ArrayList<>();

        this.playRemaining = MAX_PLAY_CARDS;
        this.discardRemaining = MAX_DISCARD_CARDS;

        this.playerScore = 0;

        // Initialize UI
        this.titleFont = new BitmapFont();
        this.titleFont.getData().setScale(3f);
        this.font = new BitmapFont();
        this.font.getData().setScale(3f);
        this.discardFont = new BitmapFont();
        this.playFont = new BitmapFont();
        this.scoreFont = new BitmapFont();
        discardFont.getData().setScale(3f);
        playFont.getData().setScale(3f);
        scoreFont.getData().setScale(3f);

        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer = new ShapeRenderer();
        this.stage = new Stage();

        loadTexture();
        buildUI();
        Gdx.input.setInputProcessor(stage);

        System.out.println("Battle against " + enemy.getName());
    }

    private void loadTexture(){
        playButtonTexture = new Texture(Gdx.files.internal("button/play_button.png"));
        playButtonPressedTexture = new Texture(Gdx.files.internal("button/play_button_pressed.png"));
        discardButtonTexture = new Texture(Gdx.files.internal("button/discard_button.png"));
        discardButtonPressedTexture = new Texture(Gdx.files.internal("button/discard_button_pressed.png"));
        closeButtonTexture = new Texture(Gdx.files.internal("button/close_button.png"));
        closeButtonPressedTexture = new Texture(Gdx.files.internal("button/close_button_pressed.png"));

    }

    private void buildUI(){
        //play button
        TextureRegionDrawable playUpDrawable =
            new TextureRegionDrawable(new TextureRegion(playButtonTexture));
        TextureRegionDrawable playDownDrawable =
            new TextureRegionDrawable(new TextureRegion(playButtonPressedTexture));
        playButton = new ImageButton(playUpDrawable, playDownDrawable);
        //discard button
        TextureRegionDrawable discardUpDrawable =
            new TextureRegionDrawable(new TextureRegion(discardButtonTexture));
        TextureRegionDrawable discardDownDrawable =
            new TextureRegionDrawable(new TextureRegion(discardButtonPressedTexture));
        discardButton = new ImageButton(discardUpDrawable, discardDownDrawable);
        //close button
        TextureRegionDrawable closeUpDrawable =
            new TextureRegionDrawable(new TextureRegion(closeButtonTexture));
        TextureRegionDrawable closeDownDrawable =
            new TextureRegionDrawable(new TextureRegion(closeButtonPressedTexture));
        closeButton = new ImageButton(closeUpDrawable, closeDownDrawable);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int buttonWidth = 200;
        int buttonHeight = 80;
        int spacing = 30;

        playButton.setSize(buttonWidth, buttonHeight);
        playButton.setPosition(
            screenWidth / 2 - buttonWidth - spacing / 2, 25);

        discardButton.setSize(buttonWidth, buttonHeight);
        discardButton.setPosition(
            screenWidth / 2 + spacing / 2, 25);

        closeButton.setSize(100, 100);
        closeButton.setPosition(screenWidth-120, 5);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!battleEnded) {
                    playSelectedCards();
                }
            }
        });

        discardButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!battleEnded) {
                    discardSelectedCards();
                }
            }
        });

        closeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                battleEnded = true;
                playerWon = false;
                gsm.pop();
            }
        });

        stage.addActor(playButton);
        stage.addActor(discardButton);
        stage.addActor(closeButton);

        updateButtonStates();
    }

    private void startBattle(){
        this.battleStarted = true;
        System.out.println("Battle started!");
        drawHand();
    }

    private void drawHand(){
        deckManager.drawCards();
        currentHand = deckManager.getCurrentHand();
        selectedCards.clear();
        for (int i = 0; i < currentHand.size(); i++){
            selectedCards.add(false);
        }
        updateCardPositions();
    }

    private void updateCardPositions(){
        cardColliders.clear();

        int screenWidth = Gdx.graphics.getWidth();
        int cardWidth = 100;
        int cardHeight = 150;
        int spacing = 20;

        int totalWidth = currentHand.size() * (cardWidth + spacing) - spacing;
        int startX = (screenWidth - totalWidth) / 2;
        int yPosition = 155;
        for(int i =0; i <currentHand.size(); i++){
            Rectangle bounds = new Rectangle(
                startX + i * (cardWidth + spacing),
                yPosition,
                cardWidth,
                cardHeight
            );
            cardColliders.add(bounds);
        }
    }

    private void endBattle(boolean playerWon){
        this.battleEnded = true;
        this.playerWon = playerWon;

        if(playerWon){
            System.out.println("Player won battle");
            if(playingState.getPlayer() != null){
                playingState.getPlayer().getMoney().addMoney(enemy.getRewardMoney());
            }
            playingState.onEnemyDefeated(enemy);
        } else{
            System.out.println("Better luck next time!");
        }

        resultDisplayTime = 0;
    }

    @Override
    public void update(float delta) {
        if (!battleStarted) {
            startBattle();
            return;
        }

        if (battleEnded) {
            resultDisplayTime += delta;

            // Return to playing state after 3 seconds
            if (resultDisplayTime > 3.0f) {
                gsm.pop();
            }
            return;
        }

        if (playRemaining <= 0 && discardRemaining <= 0) {
            // No more actions left, end battle
            if (playerScore >= targetScore) {
                endBattle(true);
            } else {
                endBattle(false);
            }
            return;
        }
        handleInput();
        stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        //batch.begin();

        float centerX = Gdx.graphics.getWidth() / 2;
        float centerY = Gdx.graphics.getHeight() / 2;

        renderEnemyPortrait(batch);

        if(!battleEnded){
            // Draw battle info
            batch.begin();
            //target Score
            font.draw(batch, "" + playerScore, centerX - 100, centerY + 99);
            scoreFont.draw(batch, "" + targetScore, centerX + 50, centerY + 99);
            playFont.draw(batch, "" + playRemaining, playButton.getX() + 90,
                Gdx.graphics.getHeight()-60);
            discardFont.draw(batch, "" + discardRemaining, discardButton.getX() + 80,
                Gdx.graphics.getHeight()-60);
            batch.end();

            // Draw cards and buttons
            renderCards(batch);
            batch.begin();
            renderCardText(batch);
            batch.end();
            stage.draw();

        } else {
            // Draw result
            batch.begin();
            if (playerWon) {
                font.draw(batch, "VICTORY!", centerX-50, centerY);
                font.draw(batch, "Reward: $" + enemy.getRewardMoney(),
                    centerX-100, centerY-50);
            } else {
                font.draw(batch, "DEFEAT!", centerX-50, centerY);
            }
            font.draw(batch, "Returning in " +
                    String.format("%.1f", 3.0f - resultDisplayTime) + "s...",
                centerX -150, centerY-100);
            batch.end();
        }
    }

    @Override
    public void dispose() {
        font.dispose();
        titleFont.dispose();
        scoreFont.dispose();
        playFont.dispose();
        discardFont.dispose();
        shapeRenderer.dispose();
        stage.dispose();
        playButtonTexture.dispose();
        playButtonPressedTexture.dispose();
        discardButtonTexture.dispose();
        discardButtonPressedTexture.dispose();
    }

    private void renderEnemyPortrait(SpriteBatch batch){
        Texture enemyTexture = enemy.getPortraitTexture();
        if (enemyTexture != null){
            float portraitWidth = 200f;
            float portraitHeight = 200f;
            float portraitX = 100;
            float portraitY = Gdx.graphics.getHeight() - 300;
            batch.begin();
            batch.draw(enemyTexture, portraitX, portraitY, portraitWidth, portraitHeight);
            batch.end();
        }
    }

    private void updateButtonStates(){
        playButton.setDisabled(playRemaining <= 0);
        discardButton.setDisabled(discardRemaining <= 0);
    }

    private void handleInput(){
        if(battleEnded ) return;

        if (Gdx.input.justTouched()){
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            //check card clicked
            for(int i =0; i < cardColliders.size(); i++){
                Rectangle cardRect = cardColliders.get(i);
                if(cardRect.contains(mouseX, mouseY)){
                    selectedCards.set(i, !selectedCards.get(i));
                    System.out.println("Card " + i + " selected: " + selectedCards.get(i));

                    return;
                }
            }
        }
    }
    private void playSelectedCards(){
        if (playRemaining <= 0){
            System.out.println("No remaining plays this turn.");
            return;
        }

        List<PlayingCard> toPlay = new ArrayList<>();
        for(int i = selectedCards.size() - 1; i >= 0; i--){
            if(selectedCards.get(i)){
                toPlay.add(currentHand.get(i));
            }
        }
        if (!toPlay.isEmpty()){
            int score = combatResolver.calculateScore(toPlay);
            playerScore += score;

            playedCards.addAll(toPlay);
            deckManager.playCards(toPlay);

            currentHand = deckManager.getCurrentHand();
            String handScored = handEvaluator.getCurrentHandType();

            selectedCards.clear();
            for (int i = 0; i < currentHand.size(); i++) {
                selectedCards.add(false);
            }

            playRemaining--;
            updateButtonStates();

            System.out.println("Played hand score: " + score + " | Total: " + playerScore + "| " +
                "Hand Played: " + handScored);

            //check if player won
            if (playerScore >= targetScore) {
                endBattle(true);
            } else if (currentHand.isEmpty() && deckManager.getDeckCards().isEmpty()) {
                // no more cards to draw
                endBattle(false);
            }

            updateCardPositions();
        }
    }

    private void discardSelectedCards(){
        if (discardRemaining <= 0){
            System.out.println("No remaining discards this turn.");
            return;
        }

        List<PlayingCard> toDiscard = new ArrayList<>();
        for(int i = selectedCards.size() - 1; i >= 0; i--){
            if(selectedCards.get(i)){
                toDiscard.add(currentHand.get(i));
                currentHand.remove(i);
                selectedCards.remove(i);
            }
        }
        if (!toDiscard.isEmpty()) {
            deckManager.discardCards(toDiscard);
            currentHand = deckManager.getCurrentHand();

            selectedCards.clear();
            for (int i = 0; i < currentHand.size(); i++) {
                selectedCards.add(false);
            }

            discardRemaining--;
            updateButtonStates();

            updateCardPositions();
            System.out.println("Discarded " + toDiscard.size() + " cards");
        }
    }

    private void renderCards(SpriteBatch batch){
        //shadow
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < currentHand.size(); i++) {
            Rectangle cardRect = cardColliders.get(i);
            shapeRenderer.setColor(0, 0, 0, 0.1f);
            shapeRenderer.rect(cardRect.x + 4, cardRect.y - 4, cardRect.width, cardRect.height);
        }
        shapeRenderer.end();

        batch.begin();

        for (int i = 0; i < currentHand.size(); i++){
            Rectangle cardRect = cardColliders.get(i);
            boolean selected = selectedCards.get(i);
            PlayingCard card = currentHand.get(i);

            float yOffset = selected ? 10f : 0f;

            Texture cardTexture = cardTextureManager.getCardTexture(card);

            if (cardTexture != null){
                batch.draw(cardTexture,
                    cardRect.x, cardRect.y +yOffset,
                    cardRect.width, cardRect.height);
            } else {
                //fallback if texture not found
                batch.end();
                shapeRenderer.begin();
                // Draw card background
                if (selected){
                    shapeRenderer.setColor(selectedCardColor);
                } else {
                    shapeRenderer.setColor(cardColor);
                }
                shapeRenderer.rect(cardRect.x, cardRect.y + yOffset, cardRect.width, cardRect.height);
                shapeRenderer.end();
                batch.begin();
            }
            if (selected) {
                batch.end();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                Gdx.gl.glLineWidth(2);
                shapeRenderer.setColor(Color.GOLD);
                shapeRenderer.rect(cardRect.x, cardRect.y + yOffset, cardRect.width, cardRect.height);
                shapeRenderer.end();
                Gdx.gl.glLineWidth(1);
                batch.begin();
            }
        }

        batch.end();
    }

    private void renderCardText(SpriteBatch batch){
        // Draw card rank and suit
        for (int i = 0; i < currentHand.size(); i++){
            PlayingCard card = currentHand.get(i);
            // Only draw text if no texture
            if (cardTextureManager.getCardTexture(card) == null){
                Rectangle cardRect = cardColliders.get(i);
                String rankText = card.getRank().toString();
                String suitText = card.getSuit().toString().substring(0, 1); // First letter

                font.setColor(Color.BLACK);
                font.draw(batch, rankText, cardRect.x + 10, cardRect.y + cardRect.height - 10);
                font.draw(batch, suitText, cardRect.x + 10, cardRect.y + cardRect.height - 30);
                font.setColor(Color.WHITE);
            }
        }
    }

}
