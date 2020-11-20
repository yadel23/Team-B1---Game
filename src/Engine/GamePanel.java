package Engine;

import GameObject.Rectangle;
import Screens.PlayLevelScreen;
import SpriteFont.SpriteFont;
import Utils.Colors;

import javax.swing.*;

import Game.GameState;
import Game.ScreenCoordinator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * This is where the game loop starts
 * The JPanel uses a timer to continually call cycles of update and draw
 */
public class GamePanel extends JPanel {
	// loads Screens on to the JPanel
	// each screen has its own update and draw methods defined to handle a "section"
	// of the game.
	private ScreenManager screenManager;
	
	//****
	private ScreenCoordinator screenCoordinator;

	// used to create the game loop and cycle between update and draw calls
	private Timer timer;
	
	private long startTime, millisPassed, secondsPassed, minutesPassed, pauseTime;
	private boolean wasStarted, timerStart;

	// used to draw graphics to the panel
	private GraphicsHandler graphicsHandler;

	private boolean doPaint = false;
	private boolean isGamePaused = false;
	private SpriteFont pauseLabel, timerLabel;
	private KeyLocker keyLocker = new KeyLocker();
	private final Key pauseKey = Key.P;

	/*
	 * The JPanel and various important class instances are setup here
	 */
	public GamePanel() {
		super();
		this.setDoubleBuffered(true);

		// attaches Keyboard class's keyListener to this JPanel
		this.addKeyListener(Keyboard.getKeyListener());

		graphicsHandler = new GraphicsHandler();

		screenManager = new ScreenManager();

		pauseLabel = new SpriteFont("PAUSE", 365, 280, "Comic Sans", 24, Color.white);
		pauseLabel.setOutlineColor(Color.black);
		pauseLabel.setOutlineThickness(2.0f);
		timerLabel = new SpriteFont(minutesPassed + ":" + secondsPassed, 720, 25, "Comic Sans", 24, Color.white);

		// Every timer "tick" will call the update method as well as tell the JPanel to
		// repaint
		// Remember that repaint "schedules" a paint rather than carries it out
		// immediately
		// If the game is really laggy/slow, I would consider upping the FPS in the
		// Config file.
		timer = new Timer(1000 / Config.FPS, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
				repaint();
			}
		});
		timer.setRepeats(true);
	}
	
	// sets the screencoordinator to the one being used
	public void setScreenCoordinator(ScreenCoordinator sc) {
		this.screenCoordinator = sc;
	}

	// this is called later after instantiation, and will initialize screenManager
	// this had to be done outside of the constructor because it needed to know the
	// JPanel's width and height, which aren't available in the constructor
	public void setupGame() {
		setBackground(Colors.CORNFLOWER_BLUE);
		screenManager.initialize(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		doPaint = true;
	}

	// this starts the timer (the game loop is started here
	public void startGame() {
		timer.start();
		wasStarted = true;
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	// only lets the game be paused if the game state is the level 
	public void update() {
		if(screenCoordinator.getGameState() == GameState.LEVEL) {
			if (Keyboard.isKeyDown(pauseKey) && !keyLocker.isKeyLocked(pauseKey)) {
				isGamePaused = !isGamePaused;
				keyLocker.lockKey(pauseKey);
		}
	}
		
		if (Keyboard.isKeyUp(pauseKey)) {
			keyLocker.unlockKey(pauseKey);
		}

		if (!isGamePaused) {
			screenManager.update();
		}
		gameTimer();
	}
	// runs the timer that counts how long the player has been playing to be used
		// for the score
		public void gameTimer() {
			if (PlayLevelScreen.playLevelScreenRunning()) {

				if (wasStarted) {
					startTime = System.currentTimeMillis();
					wasStarted = false;
					timerStart = true;
				}
				
				if (isGamePaused) {
					pauseTime = System.currentTimeMillis() - startTime - millisPassed;
				} else {
					millisPassed = System.currentTimeMillis() - startTime - pauseTime;
					secondsPassed = (millisPassed / 1000);

					if (secondsPassed >= 60) {
						minutesPassed = minutesPassed + 1;
						secondsPassed = 0;

						startTime = System.currentTimeMillis();

					}
				}

				// System.out.println("minutes: " + minutesPassed + " seconds: " +
				// secondsPassed);
				String timerString;

				if (secondsPassed < 10) {
					timerString = minutesPassed + ":0" + secondsPassed;
				} else {
					timerString = minutesPassed + ":" + secondsPassed;
				}
				timerLabel = new SpriteFont(timerString, 720, 25, "Comic Sans", 24, Color.white);
				timerLabel.setOutlineColor(Color.black);
				timerLabel.setOutlineThickness(2.0f);
			} else if (PlayLevelScreen.levelOver()) {
				startTime = System.currentTimeMillis();
				wasStarted = false;
				timerStart = true;
				pauseTime = 0;
			}
		}
		
		
	public void draw() {
		screenManager.draw(graphicsHandler);

		if (timerStart && PlayLevelScreen.playLevelScreenRunning()) {
			timerLabel.draw(graphicsHandler);
		}
		
		// if game is paused, draw pause gfx over Screen gfx
		if (isGamePaused) {
			pauseLabel.draw(graphicsHandler);
			graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(),
					new Color(0, 0, 0, 100));
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// every repaint call will schedule this method to be called
		// when called, it will setup the graphics handler and then call this class's
		// draw method
		graphicsHandler.setGraphics((Graphics2D) g);
		if (doPaint) {
			draw();
		}
	}
}
