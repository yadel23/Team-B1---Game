package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.AnimatedSprite;
import GameObject.ImageEffect;
import GameObject.Sprite;
import GameObject.SpriteSheet;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class AvatarOptionsScreen extends Screen{
	protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont chooseAvatar;
    protected SpriteFont returnInstructionsLabel;
      
    protected Sprite Avatar1;
    protected Sprite Avatar2;
    protected Sprite Avatar3;
    protected Sprite Avatar4;
    
    protected Stopwatch keyTimer = new Stopwatch();
    protected int pointerLocationX, pointerLocationY;
    
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected static int chosenavatar = 0;
    
    
    public AvatarOptionsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;   
    }
    
    
    @Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
    
    	  background = new TitleScreenMap();
          background.setAdjustCamera(false);
          keyTimer.setWaitTime(200);
          menuItemSelected = -1;
          
         
          Avatar1 = new Sprite(ImageLoader.loadSubImage("Cat.png", 0, 0, 24, 24), 200, 75, 3, ImageEffect.NONE);
          Avatar2 = new Sprite(ImageLoader.loadSubImage("Dog.png", 0, 0, 100, 65), 212, 170, .7f, ImageEffect.NONE);
//          Avatar3 = new SpriteFont(ImageLoader.load("Cat.png"), 200, 280);
//          Avatar4 = new SpriteFont(ImageLoader.load("Cat.png"), 200, 360);
          
          returnInstructionsLabel = new SpriteFont("Press Esc to return to the menu.", 20, 560, "Times New Roman", 30, Color.BLACK);
          keyLocker.lockKey(Key.SPACE);   
        
    }
    
   
    

    public void update() {
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
             pointerLocationX = 170;
             pointerLocationY = 100;
         } else if (currentMenuItemHovered == 1) {
             pointerLocationX = 170;
             pointerLocationY = 180;
         } else if (currentMenuItemHovered == 2) {
         	pointerLocationX = 170;
         	pointerLocationY = 260;	
         }	else if (currentMenuItemHovered == 3) {
         	pointerLocationX = 170;
         	pointerLocationY = 340;
         	
         }
        if (Keyboard.isKeyUp(Key.ESC)) {
            keyLocker.unlockKey(Key.ESC);
        }
    		
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if(!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE) && currentMenuItemHovered == 0){
            chosenavatar = 0;
            //System.out.println("Hi this is working");
        }
        else if(!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE) && currentMenuItemHovered == 1){
            chosenavatar = 1;
            //System.out.println("Hi this is working 2");
        }

        // if Esc is pressed, go back to main menu
        if (!keyLocker.isKeyLocked(Key.ESC) && Keyboard.isKeyDown(Key.ESC)) {
            screenCoordinator.setGameState(GameState.MENU);
        }



    }

    public void draw(GraphicsHandler graphicsHandler) {
       background.draw(graphicsHandler);
         Avatar1.draw(graphicsHandler);
         Avatar2.draw(graphicsHandler);
//       Avatar3.draw(graphicsHandler);
//       Avatar4.draw(graphicsHandler);
       returnInstructionsLabel.draw(graphicsHandler);
      graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(49, 207, 240), Color.black, 2);
    }
    

}

