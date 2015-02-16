/*************************************************************************
 *  Compilation:  javac -classpath '*':'.' DiagonalLoopGray.java
 *  Execution:    java -classpath '*':'.' DiagonalLoopGray.java 
 *  Requires: objectdraw.jar
 *
 *  prints filled circles down the diagonals of the canvas. Varies gray color
 *  - runs as  java program
 *
 * NOTE: initial window is not guaranteed to be size WIN_SIZE x WIN_SIZE
 *************************************************************************/
import objectdraw.*;
import java.awt.*;

public class DiagonalLoopGray extends WindowController 
{
	private static final int WIN_SIZE = 300;
	private static final int DIAMETER = 30;
	private static final int FX=25, FY=25;
	private static final int GRAY = 20, COLORCHANGE=10;
	private Text instructions;
	
	public void begin()
	{
		instructions = new Text("Click mouse to draw Circles", 50,WIN_SIZE-100, canvas);
	}

	public void onMouseClick(Location point) 
	{
		instructions.hide();
		int xcoord = 0; 		// where to draw on \-diagonal
		int ycoord = 0; 		// ycoord same for both diagonals 
		int x2coord = WIN_SIZE; // where to draw on /-diagonal 
	
		int hue = GRAY;
		FilledOval circle;
	
		// Draw circles down the diagonals. Start at upper right, upper left of canvas
		// and move downwards
		while ( xcoord <= WIN_SIZE) 	// Repeat until we run out of window
		{
			circle = new FilledOval(xcoord,ycoord,DIAMETER,DIAMETER, canvas);
			circle.setColor(new Color(hue,hue,hue));
	
			circle = new FilledOval(x2coord,ycoord,DIAMETER,DIAMETER, canvas);
			circle.setColor(new Color(hue,hue,hue));
	
			xcoord += DIAMETER; ycoord=xcoord;    // Move X-coord to right, Y-coord down
			x2coord -= DIAMETER;
			hue += COLORCHANGE;
		}
		System.out.println("Panel Size - " + this.getSize());
	}

	public static void main(String[] args) {	 
		WindowController W = new DiagonalLoopGray();
		W.startController(WIN_SIZE+FX,WIN_SIZE+FY); 
	} 
}
 // vim:ts=4:sw=4:tw=78:
