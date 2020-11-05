package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

import java.awt.*;

// This is the class for the main menu screen
public class MenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected SpriteFont playGame;
    protected SpriteFont credits;
    protected Map background;
    protected Stopwatch keyTimer = new Stopwatch();
    protected int pointerLocationX, pointerLocationY;
    protected KeyLocker keyLocker = new KeyLocker();
    
    protected SpriteFont howTo;
    protected SpriteFont chooseAvatar;
    protected SpriteFont getInstructions;

    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
    	playGame = new SpriteFont("PLAY GAME", 200, 120, "Comic Sans", 30, new Color(49, 207, 240));
        playGame.setOutlineColor(Color.black);
        playGame.setOutlineThickness(3);
        credits = new SpriteFont("CREDITS", 200, 200, "Comic Sans", 30, new Color(49, 207, 240));
        credits.setOutlineColor(Color.black);
        credits.setOutlineThickness(3);
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        keyTimer.setWaitTime(200);
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);
        
        howTo = new SpriteFont("HOW TO PLAY", 200, 280, "Comic Sans", 30, new Color(49, 207, 240));
        howTo.setOutlineColor(Color.black);
        howTo.setOutlineThickness(3);
        
        chooseAvatar = new SpriteFont("CHOOSE AN AVATAR", 200, 360, "Comic Sans", 30, new Color(49, 207, 240));
        chooseAvatar.setOutlineColor(Color.black);
        chooseAvatar.setOutlineThickness(3);
        
        getInstructions = new SpriteFont("Press Space on any selected item.", 20, 560, "Times New Roman", 30, Color.white);
       
        Audio.playMusic("BGM.wav");
    }

    public void update() {
        // update background map (to play tile animations)
        background.update(null);

        // if down or up is pressed, change menu item "hovered" over (blue square in front of text will move along with currentMenuItemHovered changing)
        if ((Keyboard.isKeyDown(Key.DOWN) || Keyboard.isKeyDown(Key.S)) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            currentMenuItemHovered++;
        } else if ((Keyboard.isKeyDown(Key.UP) || Keyboard.isKeyDown(Key.W))&& keyTimer.isTimeUp()) {
            keyTimer.reset();
            currentMenuItemHovered--;
        }

        // if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
        if (currentMenuItemHovered > 3) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered == 1) {
            currentMenuItemHovered = 1;
        } else if (currentMenuItemHovered == 2) {
        	currentMenuItemHovered = 2;
        } else if (currentMenuItemHovered == 3) {
        	currentMenuItemHovered = 3;
        }

        // sets location for blue square in front of text (pointerLocation) and also sets color of spritefont text based on which menu item is being hovered
        if (currentMenuItemHovered == 0) {
            playGame.setColor(new Color(255, 215, 0));
            credits.setColor(new Color(49, 207, 240));
            howTo.setColor(new Color(49, 207, 240));
            chooseAvatar.setColor(new Color(49, 207, 240));
            pointerLocationX = 170;
            pointerLocationY = 100;
        } else if (currentMenuItemHovered == 1) {
            playGame.setColor(new Color(49, 207, 240));
            credits.setColor(new Color(255, 215, 0));
            howTo.setColor(new Color(49, 207, 240));
            chooseAvatar.setColor(new Color(49, 207, 240));
            pointerLocationX = 170;
            pointerLocationY = 180;
        } else if (currentMenuItemHovered == 2) {
        	playGame.setColor(new Color (49, 207, 240));
        	credits.setColor(new Color(49, 207, 240));
        	howTo.setColor(new Color(255, 215, 0));
        	chooseAvatar.setColor(new Color(49, 207, 240));
        	pointerLocationX = 170;
        	pointerLocationY = 260;	
        }	else if (currentMenuItemHovered == 3) {
        	playGame.setColor(new Color (49, 207, 240));
        	credits.setColor(new Color(49, 207, 240));
        	howTo.setColor(new Color(49, 207, 240));
        	chooseAvatar.setColor(new Color(255, 215, 0));
        	pointerLocationX = 170;
        	pointerLocationY = 340;
        	
        }

        // if space is pressed on menu item, change to appropriate screen based on which menu item was chosen
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) {
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 1) {
                screenCoordinator.setGameState(GameState.CREDITS);
            } else if (menuItemSelected == 2) {
            	screenCoordinator.setGameState(GameState.HOWTO);
            } else if (menuItemSelected == 3) {
            	screenCoordinator.setGameState(GameState.CHOOSEAVATAR);
            }
          
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        playGame.draw(graphicsHandler);
        credits.draw(graphicsHandler);
        howTo.draw(graphicsHandler);
        chooseAvatar.draw(graphicsHandler);
        getInstructions.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(49, 207, 240), Color.black, 2);
    }

    public int getMenuItemSelected() {
        return menuItemSelected;
    }
}
