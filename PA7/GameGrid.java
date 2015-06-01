/*GameGrid.java*/

/***************************                                                   
  * NAME: Mingxuan Wang
  * EMAIL: miw072@ucsd.edu                                                            
  * LOGIN: cs11winter2015                                                              
  * ID: A53077257                                                               
  * ************************/

import java.awt.*;
import java.util.Random;

  
/**
 * Provide a class of the logic back-end part of the gamegrid
 * @author Mingxuan Wang
 * @version CSE11-Winter2015-PR7
 */
public class GameGrid
{
	  private static final Color BODY = Color.GREEN;
	  private static final Color HEAD = Color.RED;
	  private static final Color OBS= Color.BLUE;
	  private static final Color EMPTY = Color.WHITE;

    private Snake snake;
    private int maxX;
    private int maxY;
    private int pixels;
    private GraphicsGrid graphics;
    public boolean first = true;;

    /** Constructor 
     * 5 - argument constructor: build the logic part of the gamegrid
	   * @param initi - the initial coordinate of the snake
     * @param x - logical max x coordinate
     * @param y - logical max y coordinate
     * @param g - the corresponding graphic part of the gamegrid
     * @param p - the pixel
	   */
    public GameGrid(Coord init, int x, int y, GraphicsGrid g, int p){
    	  maxX = x;
    	  maxY = y;
        pixels = p;
    	  snake = new Snake(init, x * y);
        graphics = g;
        graphics.updateSnake(snake.getSnake());
    }

    /** move the snake along a centain direction
     * @param x - the x direction
     * @param x - the y direction
	   * @return true if succeed, false otherwise
	   */
    public boolean moveSnake(int x, int y){
        
        Coord[] oldSnake = snake.getSnake();

        snake.move(x, y);

        if (!isValid()){
            return false;
        }

        Coord[] newSnake = snake.getSnake();
        graphics.clearSnake();
        graphics.updateSnake(newSnake);
        return true;
    }

    /** grow the snake along a centain direction
     * @param x - the x direction
     * @param x - the y direction
	   * @return true if succeed, false otherwise
	   */
    public boolean growSnake(int x, int y){
        
        Coord[] oldSnake = snake.getSnake();

        snake.grow(x, y);

        if (!isValid()){
            return false;
        }

        Coord[] newSnake = snake.getSnake();
        graphics.clearSnake();
        graphics.updateSnake(newSnake);
        return true;
    }

    /** add an obstable to the graphic game grid
	   */
    public void addOBS(){
        graphics.addObstacle();
    }

    /** judge if the current state violate the game rules
	   * @return true if current state is valid, false otherwise
	   */
    private boolean isValid(){
        Coord head = snake.getHead();
        
        // the first time game
        if (first){
           
          if (head.getX()*pixels < graphics.xoff || head.getX()*pixels >= maxX*pixels + graphics.xoff ||
              head.getY()*pixels < graphics.yoff || head.getY()*pixels >= maxY*pixels + graphics.yoff){
              return false;
          }
        
        // not the first time game
        }else{
          if (head.getX()*pixels < graphics.xoff || head.getX()*pixels >= maxX*pixels + graphics.xoff ||
              head.getY()*pixels < graphics.yoff || head.getY()*pixels >= maxY*pixels + graphics.yoff){
              return false;
          }
        }

        for (int i = 0; i < graphics.obsCells.size(); i++){
            if (head.getX() == graphics.obsCells.get(i).getX() && head.getY() == graphics.obsCells.get(i).getY()){
                return false;
            }
        }

        if (snake.isIntersect()){
            return false;
        }
        return true;
    }

}