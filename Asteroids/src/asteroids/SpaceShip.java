package asteroids;

import java.awt.Polygon;

@SuppressWarnings("serial")
public class SpaceShip extends Polygon {

	// Upper left hand corner of space ship

	int uLeftXPos = 500, uLeftYPos = 400;

	// Determines the direction the ship moves

	int xDirection = 0;
	int yDirection = 0;

	// Get the board width and height

	int width = GameBoard.boardWidth;
	int height = GameBoard.boardHeight;

	// Will hold the x & y coordinates for the ship
	// Everything is based on coordinates from the center
	// It is done this way so that rotation works properly

	public static int[] polyXArray = { -13, 14, -13, -5, -13 };
	public static int[] polyYArray = { -15, 0, 15, 0, -15 };
	
	static int rotationAngle=0;

	// Creates a new space ship

	public SpaceShip() {

		// Creates a Polygon by calling the super or parent class of Rock
		// Polygon

		super(polyXArray, polyYArray, 5);

	}

	public void move() {
		/*
		// Get the upper left and top most point for the Polygon
		// This will be dynamic later on

		int uLeftXPos = super.xpoints[0];
		int uLeftYPos = super.ypoints[0];

		// If the ship hits a wall it will go in the opposite direction

		if (uLeftXPos < 0 || (uLeftXPos + 25) > width)
			xDirection = -xDirection;

		if (uLeftYPos < 0 || (uLeftYPos + 50) > height)
			yDirection = -yDirection;

		// Change the values of the points for the Polygon

		for (int i = 0; i < super.xpoints.length; i++) {

			super.xpoints[i] += xDirection;
			super.ypoints[i] += yDirection;

		}
		*/
		super.xpoints = SpaceShip.polyXArray;
		super.ypoints = SpaceShip.polyYArray;
		
	}

}