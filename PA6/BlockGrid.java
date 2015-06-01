/***************
	The 6th Program Assignment of CSE 11, winter 2015
	Written by Mingxuan Wang
***************/
/**************
	Name : Mingxuan Wang
	Email: miw072@eng.ucsd.edu
	ID: A53077257
**************/

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import java.awt.Dimension;
import java.lang.Thread;
import java.lang.Runnable;

/**
 * Provide a class to perform BlockGrid and its move
 * @author Mingxuan Wang
 * @version CSE11-Winter2015-PR6
 */

public class BlockGrid {
  
  /** Print out the usage instruction
	 */
  
  private static void usage(){
      System.out.println("Usage: CoreControl [ width height pixels ]");
  }

  /** Perform a command line program
	 * @param a - the input argument of command line 
	 */
	public static void main(String[] a) {
    
    if (a.length != 3 && a.length != 0){
      usage();
      return;
    }
    
    int width = 800;
    int height = 400;
    int pixels = 20;
    
    if (a.length == 3  ){
      try{
        width = Integer.parseInt(a[0]);
        height = Integer.parseInt(a[1]);
        pixels = Integer.parseInt(a[2]);
      }catch(NumberFormatException e){
        usage();
        return;
      }
    }
    
    width = width - width % pixels;
    height = height - height % pixels;
    
    
    if (width < 0 || height < 0 || pixels < 0 || pixels > width || pixels > height){
      usage();
      return;
    }
    
    MyWindow window = new MyWindow(new Dimension(width, height), pixels);
    Thread t = new Thread(window);
    t.start();
    System.out.println("t start");
		try 
		{
			System.out.format("Hit Return to exit program");
			System.in.read();
		  
    }
		catch (IOException e){}
    
		window.dispatchEvent(new WindowEvent(window, 
			WindowEvent.WINDOW_CLOSING));
        window.dispose();	
	  window.stopM();
  }

}

/**
 * Provide a class create a JFrame with specific requiment
 * @author Mingxuan Wang
 * @version CSE11-Winter2015-PR6
 */

class MyWindow extends JFrame implements Runnable
{
	private	Grid grid;
  private int width = 800;
  private int height = 400;
  private int pixels = 20;
  private Mover mover;
  private static final int WIDTH=400;
	private static final int HEIGHT=400;
	private static final int SLOW = 10, FAST=100;
	private static final String SCOREFORMAT="% 8d";
	private static final int SCORE_INCR=10;
	private static final int FOOD_MULT=10;
	private static final int SPEEDDIV=100;
	private static int GRID=40;
	private static final int SEGMENTSIZE=10;
	private static final int M2O_RATIO=10;
  private int score = 0, highScore = 0;
  
  /** Constructor 
	 * 0-argument constructor: build a window in default size
	 */
	public MyWindow() {
		super();
		grid = new Grid();
		setSize(810, 540);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(grid);
		setVisible(true);
		grid.fillCell(0, 0);
		grid.fillCell(79, 0);
		grid.fillCell(0, 49);
		grid.fillCell(79, 49);
		grid.fillCell(39, 24);
    mover = new Mover(grid);
	}

  /** Constructor 
	 * @param dim - Dimension contains the information of width and height
   * @param pixels - how many pixels does a block have
	 */
  public MyWindow(Dimension dim, int pixels){
    super();
    this.pixels = pixels;
    this.width = (int)dim.getWidth();
    this.height = (int)dim.getHeight();
    
    JButton buttonReset, buttonNew;
 	  JLabel   scoreLabel, highScoreLabel; 
	  JSlider speedSlider;
	  JLabel	gameOver;

    
    
    JPanel northPanel, southPanel;
		northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(2,3));

		JLabel  sLabel = new JLabel("Score: ");
		scoreLabel = new JLabel("".format(SCOREFORMAT,score));
		gameOver = new JLabel("GAME OVER!");

		JLabel  hsLabel = new JLabel("High Score: ");
		highScoreLabel = new JLabel("".format(SCOREFORMAT,highScore));

		northPanel.add(sLabel);
		northPanel.add(scoreLabel);
		northPanel.add(gameOver);
		northPanel.add(hsLabel);
		northPanel.add(highScoreLabel);

	
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		speedSlider = new JSlider(SLOW,FAST,SLOW);
		buttonReset = new JButton("Reset!");
		buttonNew = new JButton("New Game");
		southPanel.add(buttonNew);
		southPanel.add(buttonReset);
		southPanel.add(speedSlider);

		Container pane = getContentPane();
		pane.add(northPanel, BorderLayout.NORTH);
		pane.add(southPanel, BorderLayout.SOUTH);
		pane.validate();
    
    
    
    grid = new Grid(dim, pixels);
    setSize(width + 10, height + 10);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(grid);
	  setVisible(true);
    grid.fillCell(0,0);
    grid.fillCell((width - pixels) / pixels, 0);
    grid.fillCell(0, (height - pixels) / pixels);
    grid.fillCell((width - pixels) / pixels, (height - pixels) / pixels);
    grid.fillCell((width / 2 - pixels) / pixels, (height / 2 - pixels) / pixels); 
    mover = new Mover(grid);
    
  }
  
  /** make the Grid move 
	 */
	public void run() {
       System.out.println("window run begin");
       mover.move();
	}
  
  /** make the Grid stop moving 
	 */
  public void stopM(){
    mover.stopMove();
  }
}


/**
 * Provide a class of a object to show in the window
 * @author Mingxuan Wang
 * @version CSE11-Winter2015-PR6
 */

class Grid extends JPanel {
	private ArrayList<Point> fillCells;
  private int width = 800;
  private int height = 400;
  private int pixels = 20;
  
  /** Constructor 
	 * 0-argument constructor: build a grid with the special points in
   * an arraylist
	 */
	public Grid() {
		fillCells = new ArrayList<Point>();
	}

  /** Constructor 
	 * @param dim - Dimension contains the information of width and height
   * @param pixels - how many pixels does a block have
	 */
  public Grid(Dimension dim, int pixels){
    fillCells = new ArrayList<Point>();
    width = (int)dim.getWidth();
    height = (int)dim.getHeight();
    this.pixels = pixels;
  }
  
  /** paint the grid line and blocks in Grid
	 * @param g - the graphics object to be paint
	 */
	@Override
	protected synchronized void paintComponent(Graphics g) {
		System.out.println("debug: paint");
    super.paintComponent(g);
		for (Point fillCell : fillCells) {
			int cellX = (fillCell.x * pixels);
			int cellY = (fillCell.y * pixels);
			g.setColor(Color.RED);
			g.fillRect(cellX, cellY, pixels, pixels);
		}
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width , height);

		for (int i = 0; i < width; i += pixels) {
		    g.drawLine(i, 0, i, height);
		}

		for (int i = 0; i < height ; i += pixels) {
        g.drawLine(0, i, width, i);
    }
	}
  
  /** put the certain cell to the arraylist
   * contains all cells to be paint in red
	 * @param x - x coordinate of the cell
	 * @param y - y coordinate of the cell
	 */
	public synchronized void fillCell(int x, int y) {
		fillCells.add(new Point(x, y));
		//System.out.println(fillCells.size());
    repaint();
	}
  
  /** reomeve the certain cell to the arraylist
   * contains all cells to be paint in red
	 * @param p - Point object contains the x, y coordinate of the cell
	 */
  public synchronized void clearCell(int index){
    fillCells.remove(index);
    //System.out.println(fillCells.size());
    repaint();
  }
  
  /** get the width of the window
	 * @return the width of the window
	 */
  public int getW(){
    return width;
  }
  
  /** get the height of the window
	 * @return the height of the window
	 */
  public int getH(){
    return height;
  }
  
  /** get the pixels of the each block
	 * @return the pixels of the each block
	 */
  public int getPixels(){
    return pixels;
  }
  
  /** get the ArrayList of the cells to be paint red
	 * @return ArrayList of the cells to be paint red
	 */
  public ArrayList<Point> getList(){
    return fillCells;
  }
  
}

/**
 * Provide a class to perform the move of the Grid
 * @author Mingxuan Wang
 * @version CSE11-Winter2015-PR6
 */
 
class Mover{

   private Grid grid;
   private int width;
   private int height;
   private int pixels;
   private final int DELAY = 150;
   private boolean stop = false;
   
   /** Constructor 
	 * @param grid - the Grid object to be moved
	 */
   public Mover(Grid grid){
     this.grid = grid;
     this.width = grid.getW();
     this.height = grid.getH();
     this.pixels = grid.getPixels();
   }
   
   /** move the Grid back and forth in the window
	 */
   public void move(){
     
     boolean toRight = true;
     
     while(!stop){
      
      try { TimeUnit.MILLISECONDS.sleep(DELAY);}                       
          catch (InterruptedException e){};
      
      if (toRight){
        
        Point current = grid.getList().get(grid.getList().size() - 1);
        
        if ((current.getX() + 1) * pixels > width - pixels){
          toRight = false;
          continue;
        }else{
          grid.clearCell(4);
          current.translate(1, 0);
          grid.fillCell((int)current.getX(), (int)current.getY());
        }
      
      }else{
        
        Point current = grid.getList().get(grid.getList().size() - 1);
        
        if ((current.getX() - 1) * pixels < 0){
          toRight = true;
          continue;
        }else{
          grid.clearCell(4);
          current.translate(-1, 0);
          grid.fillCell((int)current.getX(), (int)current.getY());
        } 
      }
    }
  }
  
  public void stopMove(){
    stop = true;
  }
  
}
// vim: ts=4:sw=4:tw=78
