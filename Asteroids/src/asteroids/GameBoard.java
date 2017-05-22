package asteroids;

//Layout used by the JPanel
import java.awt.BorderLayout;

//Define color of shapes
import java.awt.Color;
import java.awt.Font;
//Allows me to draw and render shapes on components
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;

//Will hold all of my Rock objects
import java.util.ArrayList;

//Runs commands after a given delay
import java.util.concurrent.ScheduledThreadPoolExecutor;

//Defines time units. In this case TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit;

// immport sound libraries

import javax.swing.*;
import sounds.SoundLoader;

public class GameBoard extends JFrame {

	// Height and width of the game board

	public static int boardWidth = 1000;
	public static int boardHeight = 800;

	public static Font font = new Font(null, Font.BOLD, 30);
	public static int score = 0;
	public static int lifes = 3;
	public static int nrOfRock = 15;

	public enum GameState {
		Menu, Running, End
	}

	static GameState state = GameState.Menu;

	// Used to check if a key is being held down

	public static boolean keyHeld = false;

	String thrustFile = "/thrust.au";
	String laserFile = "/laser.aiff";
	// Gets the keycode for the key being held down

	public static int keyHeldCode;

	// Holds every PhotonTorpedo I create

	public static ArrayList<PhotonTorpedo> torpedos = new ArrayList<PhotonTorpedo>();

	// Holds every Rock I create

	public static ArrayList<Rock> rocks = new ArrayList<Rock>();

	public static void main(String[] args) {
		new GameBoard();

	}

	public GameBoard() {

		// Define the defaults for the JFrame
		this.setSize(boardWidth, boardHeight);
		this.setTitle("Java Asteroids");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Handles executing code based on keys being pressed

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			// forward
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 87) {
					keyHeldCode = e.getKeyCode();
					keyHeld = true;
					// playSoundEffect(thrustFile);
				}
				// backward
				else if (e.getKeyCode() == 83) {
					keyHeldCode = e.getKeyCode();
					keyHeld = true;

				}

				// Id the d key is pressed set keyHeld as if it
				// was being held down. This will cause the ship to
				// constantly rotate. keyHeldCode stores the keyCode for d

				else if (e.getKeyCode() == 68) {
					keyHeldCode = e.getKeyCode();
					keyHeld = true;

				}

				// Same thing is done here as was done with the last
				// 65 is the keyCode for a

				else if (e.getKeyCode() == 65) {
					keyHeldCode = e.getKeyCode();
					keyHeld = true;
				}

				else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					
					if(GameBoard.state != GameBoard.GameState.End){
						GameBoard.state = GameBoard.GameState.Running;
					}
					
				}
				

				else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					GameBoard.state = GameBoard.GameState.Menu;
					GameBoard.rocks.clear();

					GameDrawingPanel2.MakeRocks(GameBoard.nrOfRock);

					GameBoard.lifes = 3;
					GameBoard.score = 0;
				}

				// NEW Checks if Enter key is pressed ---------------

				else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(GameBoard.state == GameBoard.GameState.Running){
					SoundLoader.playSoundEffect(laserFile);
					}
					// Creates a new torpedo and passes the ships nose position
					// so the torpedo can start there. Also passes the ships
					// rotation angle so the torpedo goes in the right direction

					torpedos.add(new PhotonTorpedo(GameDrawingPanel2.theShip.getShipNoseX(),
							GameDrawingPanel2.theShip.getShipNoseY(), GameDrawingPanel2.theShip.getRotationAngle()));

				}

			}

			// When the key is released this informs the code that
			// the key isn't being held down

			public void keyReleased(KeyEvent e) {

				keyHeld = false;

			}

		});

		GameDrawingPanel2 gamePanel = new GameDrawingPanel2();

		this.add(gamePanel, BorderLayout.CENTER);

		// Used to execute code after a given delay
		// The attribute is corePoolSize - the number of threads to keep in
		// the pool, even if they are idle

		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

		// Method to execute, initial delay, subsequent delay, time unit

		executor.scheduleAtFixedRate(new RepaintTheBoard2(this), 0L, 20L, TimeUnit.MILLISECONDS);

		// Show the frame

		this.setVisible(true);
	}

}

// Class implements the runnable interface
// By creating this thread we can continually redraw the screen
// while other code continues to execute

class RepaintTheBoard2 implements Runnable {

	GameBoard theBoard;

	public RepaintTheBoard2(GameBoard theBoard) {
		this.theBoard = theBoard;
	}

	@Override
	public void run() {

		// Redraws the game board

		theBoard.repaint();

	}

}

@SuppressWarnings("serial")

// GameDrawingPanel is what we are drawing on

class GameDrawingPanel2 extends JComponent {

	// Get the original x & y points for the polygon

	int[] polyXArray = Rock.sPolyXArray;
	int[] polyYArray = Rock.sPolyYArray;

	// Create a SpaceShip
	static SpaceShip theShip = new SpaceShip();

	// Gets the game board height and weight

	int width = GameBoard.boardWidth;
	int height = GameBoard.boardHeight;

	// Creates 50 Rock objects and stores them in the ArrayList
	// Suppress warnings when I clone the rocks array

	public GameDrawingPanel2() {

		MakeRocks(GameBoard.nrOfRock);
	}

	public void paint(Graphics g) {

		// Allows me to make many settings changes in regards to graphics

		Graphics2D graphicSettings = (Graphics2D) g;
		AffineTransform identity = new AffineTransform();

		if (GameBoard.state == GameBoard.GameState.Menu) {

			g.setFont(GameBoard.font);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1000, 800);
			g.setColor(Color.WHITE);
			g.drawString("TAP SPACE TO START", 350, 350);

		}

		else if (GameBoard.state == GameBoard.GameState.Running) {

			// Draw a black background that is as big as the game board

			graphicSettings.setColor(Color.BLACK);
			graphicSettings.fillRect(0, 0, getWidth(), getHeight());

			// Set rendering rules

			graphicSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Set the drawing color to white

			graphicSettings.setPaint(Color.WHITE);

			// draw score
			g.setFont(GameBoard.font);
			g.drawString("SCORE:" + Integer.toString(GameBoard.score), 820, 40);
			g.drawString("LIFES:" + Integer.toString(GameBoard.lifes), 20, 40);

			// Cycle through all of the Rock objects

			for (Rock rock : GameBoard.rocks) {

				// Move the Rock polygon
				if (rock.onScreen) {
					rock.move(theShip, GameBoard.torpedos);

					// Stroke the polygon Rock on the screen

					graphicSettings.draw(rock);
				}

			}
			if ((GameBoard.lifes < 1) || (GameBoard.score == GameBoard.nrOfRock - (3 - GameBoard.lifes))) {
				GameBoard.state = GameBoard.GameState.End;
			}

			// Handles spinning the ship in the clockwise direction when the D
			// key is pressed and held

			if (GameBoard.keyHeld == true && GameBoard.keyHeldCode == 68) {

				theShip.increaseRotationAngle();

			} else

			// Continues to rotate the ship counter clockwise if the A key is
			// held

			if (GameBoard.keyHeld == true && GameBoard.keyHeldCode == 65) {

				theShip.decreaseRotationAngle();

			} else

			if (GameBoard.keyHeld == true && GameBoard.keyHeldCode == 87) {

				theShip.setMovingAngle(theShip.getRotationAngle());
				theShip.increaseXVelocity(theShip.shipXMoveAngle(theShip.getMovingAngle()) * 0.1);
				theShip.increaseYVelocity(theShip.shipYMoveAngle(theShip.getMovingAngle()) * 0.1);

			} else

			if (GameBoard.keyHeld == true && GameBoard.keyHeldCode == 83) {

				theShip.setMovingAngle(theShip.getRotationAngle());
				theShip.decreaseXVelocity(theShip.shipXMoveAngle(theShip.getMovingAngle()) * 0.1);
				theShip.decreaseYVelocity(theShip.shipYMoveAngle(theShip.getMovingAngle()) * 0.1);

			}

			theShip.move();

			// Sets the origin on the screen so rotation occurs properly

			graphicSettings.setTransform(identity);

			// Moves the ship to the center of the screen

			graphicSettings.translate(theShip.getXCenter(), theShip.getYCenter());

			// Rotates the ship

			graphicSettings.rotate(Math.toRadians(theShip.getRotationAngle()));

			graphicSettings.draw(theShip);

			// NEW Draw torpedos -------------------------

			for (PhotonTorpedo torpedo : GameBoard.torpedos) {

				// Move the Torpedo polygon

				torpedo.move();

				// Make sure the Torpedo is on the screen

				if (torpedo.onScreen) {

					// Stroke the polygon torpedo on the screen

					graphicSettings.setTransform(identity);

					// Changes the torpedos center x & y vectors

					graphicSettings.translate(torpedo.getXCenter(), torpedo.getYCenter());

					graphicSettings.draw(torpedo);

				}

			}

		} else if (GameBoard.state == GameBoard.GameState.End) {

			g.setFont(GameBoard.font);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1000, 800);
			g.setColor(Color.WHITE);
			if ((GameBoard.lifes < 1)) {
				g.drawString("YOU LOST", 409, 350);
				g.drawString("SCORE: "+GameBoard.score, 415, 400);
				g.drawString("BACSPACE TO RESTART", 310, 450);
			} else if (GameBoard.score == GameBoard.nrOfRock - (3 - GameBoard.lifes)) {
				g.drawString("YOU WON", 420, 350);
				g.drawString("SCORE: " + GameBoard.score, 415, 400);
				g.drawString("BACSPACE TO RESTART", 310, 450);
			}

		}

	}

	public static void MakeRocks(int NrOfRock) {
		for (int i = 0; i < NrOfRock; i++) {

			// Find a random x & y starting point
			// The -40 part is on there to keep the Rock on the screen

			int randomStartXPos = (int) (Math.random() * (GameBoard.boardWidth - 40) + 1);
			int randomStartYPos = (int) (Math.random() * (GameBoard.boardHeight - 40) + 1);

			// Add the Rock object to the ArrayList based on the attributes sent

			GameBoard.rocks.add(new Rock(Rock.getpolyXArray(randomStartXPos), Rock.getpolyYArray(randomStartYPos), 13,
					randomStartXPos, randomStartYPos));

			Rock.rocks = GameBoard.rocks;
		}
	}
}