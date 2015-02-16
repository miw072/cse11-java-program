/*************************************************************************
 *  Compilation:  javac -classpath '*':'.' Diagonal.java
 *  Execution:    java -classpath '*':'.' Diagonal.java 
 *  Requires: objectdraw.jar
 *
 *  prints filled circles down the diagonal of the canvas
 *  - runs as  java program
 *************************************************************************/
import objectdraw.*;
import java.awt.*;

public class Diagonal extends WindowController 
{
	private static final int WIN_SIZE = 400;
	private static final int DIAMETER = 25;
	private static final int FX=4, FY=54;
	private Text instructions;
	public void begin()
	{
		instructions = new Text("Click mouse to draw Circles", 50,WIN_SIZE-100, canvas);
	}
    	public void onMouseClick(Location point) {
		instructions.hide();
		new FilledOval(0,0,DIAMETER,DIAMETER, canvas);
		new FilledOval(25,25,DIAMETER,DIAMETER, canvas);
		new FilledOval(50,50,DIAMETER,DIAMETER, canvas);
		new FilledOval(75,75,DIAMETER,DIAMETER, canvas);

		new FilledOval(100,100,DIAMETER,DIAMETER, canvas);
		new FilledOval(125,125,DIAMETER,DIAMETER, canvas);
		new FilledOval(150,150,DIAMETER,DIAMETER, canvas);
		new FilledOval(175,175,DIAMETER,DIAMETER, canvas);

		new FilledOval(200,200,DIAMETER,DIAMETER, canvas);
		new FilledOval(225,225,DIAMETER,DIAMETER, canvas);
		new FilledOval(250,250,DIAMETER,DIAMETER, canvas);
		new FilledOval(275,275,DIAMETER,DIAMETER, canvas);

		new FilledOval(300,300,DIAMETER,DIAMETER, canvas);
		new FilledOval(325,325,DIAMETER,DIAMETER, canvas);
		new FilledOval(350,350,DIAMETER,DIAMETER, canvas);
		new FilledOval(375,375,DIAMETER,DIAMETER, canvas);
	}

	public static void main(String[] args) {	 
 		new Diagonal().startController(WIN_SIZE+FX,WIN_SIZE+FY); 
	} 
}
