package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

public class AvatarOptionsScreen extends Screen{
	protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont chooseAvatar;
    protected SpriteFont returnInstructionsLabel;

    public AvatarOptionsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }
    
    @Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        chooseAvatar = new SpriteFont("Choose an avatar!", 15, 35, "Times New Roman", 30, Color.red);
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
        chooseAvatar.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
       
    }
}

