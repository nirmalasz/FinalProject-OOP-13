package com.kelompok13.frontend.states.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

import java.util.ArrayList;
import java.util.List;

//yayy
public class BattleState implements GameState {
    private PlayingState playingState; // Reference to the main playing state
    private GameStateManager gsm;
    private Enemy enemy;
    private DeckManager deckManager;

    // Battle state
    private boolean battleStarted;
    private boolean battleEnded;
    private boolean playerWon;
    private float resultDisplayTime;

    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    private int playerScore;

    private List<PlayingCard> fullDeck = new Deck().getCardList();
    private List<PlayingCard> currentHand;
    private List<PlayingCard> playedCards;
    private List<Rectangle> cardColliders;
    private List<Boolean> selectedCards;
    private Texture cardFrontTexture;

    private HandEvaluator handEvaluator;
    private CombatResolver combatResolver;
    private int targetScore;

    private Rectangle playButton;
    private Rectangle discardButton;


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

        currentHand = new ArrayList<>();
        playedCards = new ArrayList<>();
        cardColliders = new ArrayList<>();
        selectedCards = new ArrayList<>();

        this.font = new BitmapFont();
        this.shapeRenderer = new ShapeRenderer();

        this.playerScore = 0;

        // Initialize UI
        this.font = new BitmapFont();
        this.shapeRenderer = new ShapeRenderer();

        initButtons();

        System.out.println("Battle against " + enemy.getName());
    }

    private void initButtons(){
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();


        playButton = new Rectangle(
            screenWidth / 2 - 100,
            50, 80, 40);

        discardButton = new Rectangle(
            screenWidth /2 + 20,
            50, 80, 40);
    }

    private void startBattle(){
        this.battleStarted = true;
        System.out.println("Battle started!");

        drawHand();

        //need to draw cards, setuphand, and start combaat resolver
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
        int cardWidth = 80;
        int cardHeight = 120;
        int spacing = 20;

        int totalWidth = currentHand.size() * (cardWidth + spacing) - spacing;
        int startX = (screenWidth - totalWidth) / 2;
        int yPosition = 150;
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

        //check if player play card
        //evaluate hand
        //determine win
        handleInput();

    }

    @Override
    public void render(SpriteBatch batch) {
        //batch.begin();

        float centerX = Gdx.graphics.getWidth() / 2;
        float centerY = Gdx.graphics.getHeight() / 2;

        if(!battleEnded){
            // Draw battle info
            font.draw(batch, "BATTLE: " + enemy.getName(), centerX - 50, centerY + 100);
            font.draw(batch, "Score: " + playerScore + " / " + targetScore,
                centerX - 50, centerY + 70);
            batch.end();

            // Draw cards and buttons
            renderCards(batch);
            renderButtons(batch);
            // Restart batch for text
            batch.begin();

            renderCardText(batch);
            renderButtonText(batch);

        } else {
            // Draw result
            if (playerWon) {
                font.draw(batch, "VICTORY!", centerX - 40, centerY + 50);
                font.draw(batch, "Reward: $" + enemy.getRewardMoney(),
                    centerX - 50, centerY);
            } else {
                font.draw(batch, "DEFEAT!", centerX - 30, centerY + 50);
            }
            font.draw(batch, "Returning in " +
                    String.format("%.1f", 3.0f - resultDisplayTime) + "s...",
                centerX - 70, centerY - 50);
        }
        //batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
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

            //check buttons clicked
            if(playButton.contains(mouseX, mouseY)){
                playSelectedCards();
            } else if (discardButton.contains(mouseX, mouseY)){
                discardSelectedCards();
        }
    }
    }
    private void playSelectedCards(){
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

            updateCardPositions();
            System.out.println("Discarded " + toDiscard.size() + " cards");
        }
    }

    private void renderCards(SpriteBatch batch){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < currentHand.size(); i++){
            Rectangle cardRect = cardColliders.get(i);
            boolean selected = selectedCards.get(i);

            // Draw card background
            if (selected){
                shapeRenderer.setColor(selectedCardColor);
            } else {
                shapeRenderer.setColor(cardColor);
            }
            shapeRenderer.rect(cardRect.x, cardRect.y, cardRect.width, cardRect.height);

            // Draw card border
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(cardRect.x, cardRect.y, cardRect.width, 2);
            shapeRenderer.rect(cardRect.x, cardRect.y + cardRect.height - 2, cardRect.width, 2);
            shapeRenderer.rect(cardRect.x, cardRect.y, 2, cardRect.height);
            shapeRenderer.rect(cardRect.x + cardRect.width - 2, cardRect.y, 2, cardRect.height);
        }

        shapeRenderer.end();
    }

    private void renderCardText(SpriteBatch batch){
        // Draw card rank and suit
        for (int i = 0; i < currentHand.size(); i++){
            Rectangle cardRect = cardColliders.get(i);
            PlayingCard card = currentHand.get(i);

            String rankText = card.getRank().toString();
            String suitText = card.getSuit().toString().substring(0, 1); // First letter

            font.setColor(Color.BLACK);
            font.draw(batch, rankText, cardRect.x + 10, cardRect.y + cardRect.height - 10);
            font.draw(batch, suitText, cardRect.x + 10, cardRect.y + cardRect.height - 30);
            font.setColor(Color.WHITE);
        }
    }

    private void renderButtons(SpriteBatch batch){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw Play Button
        shapeRenderer.setColor(0.2f, 0.8f, 0.2f, 1f); // Green for Play
        shapeRenderer.rect(playButton.x, playButton.y, playButton.width, playButton.height);

        // Draw Discard Button
        shapeRenderer.setColor(0.8f, 0.2f, 0.2f, 1f); // Red for Discard
        shapeRenderer.rect(discardButton.x, discardButton.y, discardButton.width, discardButton.height);

        shapeRenderer.end();
    }

    private void renderButtonText(SpriteBatch batch){
        font.setColor(Color.BLACK);
        font.draw(batch, "PLAY",
            playButton.x + 10, playButton.y + 25);
        font.draw(batch, "DISCARD",
            discardButton.x + 5, discardButton.y + 25);
    }

}
