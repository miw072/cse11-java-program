/*GraphicsGrid.java*/

/***************************                                                   
  * NAME: Mingxuan Wang
  * EMAIL: miw072@ucsd.edu                                                            
  * LOGIN: cs11winter2015                                                              
  * ID: A53077257                                                               
  * ************************/

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import java.util.Random;
import java.awt.event.KeyEvent;  
import java.awt.event.KeyListener;

/**
 * Provide a class of the graphic part of the gamegrid
 * @author Mingxuan Wang
 * @version CSE11-Winter2015-PR7
 */
public class GraphicsGrid extends JPanel
{

	public ArrayList<Coord> bodyCells;
	public ArrayList<Coord> obsCells;
  private int maxX;
  private int maxY;
  private int pixels;
  private int fx;
  private int fy;
  public int xoff;
  public int yoff;
  private JFrame fr;
  private boolean first;
 
	private static final Color BODY = Color.GREEN;
	private static final Color HEAD = Color.RED;
	private static final Color OBS= Color.BLUE;
	private static final Color EMPTY = Color.WHITE;

  private Random random;

  /** Constructor 
   * 4 - argument constructor: build the graphic part of the gamegrid
   * @param x - max x coordinate
   * @param y - max y coordinate
   * @param p - the pixel
   * @param f - the JFrame of the game
   */
  public GraphicsGrid(int x, int y, int p, JFrame f){
      fr= f;
    	maxX = x;
    	maxY = y;
    	pixels = p;
    	random = new Random();
    	bodyCells = new ArrayList<Coord>();
    	obsCells = new ArrayList<Coord>();
      fx = (int)f.getSize().getWidth();
      fy = (int)f.getSize().getHeight() - 60;
      
      xoff = (fx - x) / 2;
      yoff = (fy - y) / 2;
      repaint();
      first = false;
  }
  
  /** set the x, y coordinate when resize the window by user
   * @param x - the x direction
   * @param x - the y direction
   */    
  public void set(int x, int y){

      maxX = x / pixels * pixels;
      maxY = (y - 60)/ pixels * pixels;
        
      xoff = 0;
      yoff = 0;
        
      repaint();
  }
    
  /** paint the grid line and blocks in Grid
	 * @param g - the graphics object to be paint
	 */
  public synchronized void paintComponent(Graphics g) {
    if (!first){
       fx = (int)fr.getSize().getWidth();
       fy = (int)fr.getSize().getHeight() - 60;
      
       xoff = (fx - maxX) / 2;
       yoff = (fy - maxY) / 2;
		super.paintComponent(g);
    for (Coord fillCell : bodyCells) {
			int cellX = (fillCell.getX() * pixels + xoff);
			int cellY = (fillCell.getY() * pixels + yoff);
			g.setColor(BODY);
			g.fillRect(cellX, cellY, pixels, pixels);
    }

		for (Coord fillCell : obsCells) {
			int cellX = (fillCell.getX() * pixels + xoff);
			int cellY = (fillCell.getY() * pixels + yoff);
			g.setColor(OBS);
			g.fillRect(cellX, cellY, pixels, pixels);
		}

		if (!bodyCells.isEmpty()){
      g.setColor(HEAD);
      g.fillRect(bodyCells.get(0).getX() * pixels + xoff, bodyCells.get(0).getY() * pixels + yoff, pixels, pixels);
    }
    
		g.setColor(Color.BLACK);
		g.drawRect(xoff, yoff, maxX, maxY);

		for (int i = xoff; i < maxX + xoff; i += pixels) {
      g.drawLine(i, yoff, i, maxY + yoff);
		}

		for (int i = yoff; i < maxY + yoff; i += pixels) {
      g.drawLine(xoff, i, maxX + xoff, i);
 	  }
    }else{
        super.paintComponent(g);
    for (Coord fillCell : bodyCells) {
			int cellX = (fillCell.getX() * pixels);
			int cellY = (fillCell.getY() * pixels);
			g.setColor(BODY);
			g.fillRect(cellX, cellY, pixels, pixels);
    }

		for (Coord fillCell : obsCells) {
			int cellX = (fillCell.getX() * pixels);
			int cellY = (fillCell.getY() * pixels);
			g.setColor(OBS);
			g.fillRect(cellX, cellY, pixels, pixels);
		}

		if (!bodyCells.isEmpty()){
      g.setColor(HEAD);
      g.fillRect(bodyCells.get(0).getX() * pixels, bodyCells.get(0).getY() * pixels, pixels, pixels);
    }
    
		g.setColor(Color.BLACK);
		g.drawRect(xoff, yoff, maxX, maxY);

		for (int i = xoff; i < maxX + xoff; i += pixels) {
      g.drawLine(i, yoff, i, maxY + yoff);
		}

		for (int i = yoff; i < maxY + yoff; i += pixels) {
      g.drawLine(xoff, i, maxX + xoff, i);
 	  }
    }
  }

  /** clear the snake and repaint the object
	 */
  public synchronized void clearSnake(){
      bodyCells.clear();
      repaint();
  }
    
  /** update the snake and repaint the object
	 */
  public synchronized void updateSnake(Coord[] news){
      bodyCells.clear();
      for (int i = 0; i < news.length; i++){
     	    bodyCells.add(news[i]);
      }
      repaint();
  }

  /** add an obstable to the graphic game grid
   *  by adding a Coord to the array represent all obstacles
   */
  public synchronized void addObstacle(){
      int rx = 0;
      int ry = 0;
      boolean isFree = true;
      int count = 0;

      do{
          rx = random.nextInt(maxX/pixels) + xoff/pixels;
          ry = random.nextInt(maxY/pixels) + yoff/pixels;
          
          for (int i = 0; i < obsCells.size(); i++){
		        if (rx == obsCells.get(i).getX() && ry == obsCells.get(i).getY()){
	              isFree = false;
                break;
		        }
            isFree = true;
          }
            if (isFree){
			         obsCells.add(new Coord(rx, ry));
               repaint(); 
          }
          count ++;
      }while (!isFree && count < 100);

      repaint();
    }

}