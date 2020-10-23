package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

public class HowToScreen extends Screen{
	protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont instructionsLabel;
    protected SpriteFont how1;
    protected SpriteFont how2;
    protected SpriteFont how3;
    protected SpriteFont how4;
    protected SpriteFont how5;
    protected SpriteFont returnInstructionsLabel;


    public HowToScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }
    
    @Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        instructionsLabel = new SpriteFont("How To Play", 15, 35, "Times New Roman", 30, Color.red);
        how1 = new SpriteFont("Move towards the goal! Hit gold box to complete level.", 150, 100, "Times New Roman", 22, Color.orange);
        how2 = new SpriteFont("Use the left and right arrows or 'A' and 'D' keys to move.", 150, 130, "Times New Roman", 22, Color.yellow);
        how3 = new SpriteFont("Jump with the up arrow or 'W' key.", 150, 160, "Times New Roman", 22, Color.green);
        how4 = new SpriteFont("Crouch with the down arrow or 'S' key.", 150, 190, "Times New Roman", 22, Color.blue);
        how5 = new SpriteFont("Avoid the enemies! Don't let them kill you!", 150, 220, "Times New Roman", 22, Color.magenta);
        returnInstructionsLabel = new SpriteFont("Press Space to return to the menu.", 20, 560, "Times New Roman", 30, Color.white);
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        // if space is pressed, go back to main menu
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        instructionsLabel.draw(graphicsHandler);
        how1.draw(graphicsHandler);
        how2.draw(graphicsHandler);
        how3.draw(graphicsHandler);
        how4.draw(graphicsHandler);
        how5.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
      
    }
}

