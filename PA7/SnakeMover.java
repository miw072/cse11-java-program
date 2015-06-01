/*SnakeMover.java*/

/***************************                                                   
  * NAME: Mingxuan Wang
  * EMAIL: miw072@ucsd.edu                                                            
  * LOGIN: cs11winter2015                                                              
  * ID: A53077257                                                               
  * ************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * Provide a class to performe move of the snake
 * This class implements KeyListener, Runnable
 * @author Mingxuan Wang
 * @version CSE11-Winter2015-PR7
 */
public class SnakeMover implements KeyListener, Runnable
{
	private static final int STEP = 50;
	private static final int MINSTEP = 1;
	private static final int MAXSTEP = 20;

	private int timeStep = 20;
	private int currentTime;
	private Coord dir;
	private boolean validMove = true;
	private GameGrid grid;
	private SnakeGame thisGame;
  private int numMoves;

  /** Constructor 
   * 3 - argument constructor: build the graphic part of the gamegrid
   * @param game - current snake game object
   * @param fr - current graphic grid object
   * @param init - initial moving direction
   */
	public SnakeMover(SnakeGame game, GameGrid gr, Coord init){
		dir = init;
		grid = gr;
		thisGame = game;
    validMove = true;
	}

  /** stop the thread moving the snake forward
   */ 
	public void stopM(){
		validMove = false;
	}

  /** keep moving the snake forward as long as valid
   */ 
	public void run(){

		while (validMove){
			if (currentTime++ >= timeStep){
				validMove = grid.moveSnake(dir.getX(), dir.getY());
				if (validMove && numMoves++ >= 20){
					grid.addOBS();
					numMoves = 0;
				}
				currentTime = 0;
			}
			try { TimeUnit.MILLISECONDS.sleep(STEP);}                       
                catch (InterruptedException e){};
		}
		thisGame.error();
	}

  /** set the speed of the snake
   * @param speed - the speed to be set
   */ 
	public void setSpeed(int speed){
    if (speed < MINSTEP) {
        speed = MINSTEP;
    }
    if (speed > MAXSTEP) {
        speed = MAXSTEP;
    }
		timeStep = speed;
	}

  public void keyPressed(KeyEvent e)
	{
	}
	public void keyReleased(KeyEvent e)
	{
	}

  /** perform turning to the key type action
   * @param e - the key type event
   */ 
	public void keyTyped(KeyEvent e){
		if (!validMove){
			return;
		}

		char c = e.getKeyChar();

    Coord newdir = new Coord(0, 0);
		if (c == 'l'){
			if (dir.getX() == 0){
				newdir = new Coord(dir.getY() * (-1), 0);
			}else{
				newdir = new Coord(0, dir.getX());	
			}
		}else if (c == 'j'){
			if (dir.getX() == 0){
				newdir = new Coord(dir.getY(), 0);
			}else{
			  newdir = new Coord(0, dir.getX() * (-1));	
			}	
		}else {
      return;
    }
   
		dir = newdir;
		validMove = grid.growSnake(newdir.getX(), newdir.getY());
		currentTime = 0;
		if (validMove){
			thisGame.updateScore();
		}
	}
 
}


