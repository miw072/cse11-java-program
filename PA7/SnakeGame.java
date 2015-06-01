/*SnakeGame.java*/

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
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import java.awt.Dimension;
import java.lang.Thread;
import java.lang.Runnable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Provide a class of the top level controller of the game
 * This class implements ActionListener, ChangeListener
 * @author Mingxuan Wang
 * @version CSE11-Winter2015-PR7
 */
public class SnakeGame implements ActionListener, ChangeListener
{
	private static int width = 400;
	private static int height = 400;
	private static final int SLOW = 10, FAST=100;
	private static final int SPDINC = 100;
	private static int segmentsize = 10;
	private static final String FORMAT="% 6d";

	private Coord direction = new Coord(0,1);
	private GameGrid grid; 
	public SnakeMover mover;
	public GraphicsGrid graph;

	private int score = 0, highScore = 0;
	private boolean good = true;
	
	private JButton resetButton, newButton;
	private JLabel  scoreLabel, highScoreLabel; 
	private JSlider speedControl;
	private JLabel	goLabel;
	public JFrame  f;
  private boolean firsttime = true;


	private static void usage(){
		System.out.println("Usage: SnakeGame [width height segmentsize]");
	}

  /** Perform a command line program
	 * @param a - the input argument of command line 
	 */
	public static void main (String [] a)
	{
		if (a.length != 3 && a.length != 0){
      		usage();
      		return;
    	}
    
   	if (a.length == 3 ){
      		try{
        		width = Integer.parseInt(a[0]);
        		height = Integer.parseInt(a[1]);
        		segmentsize = Integer.parseInt(a[2]);
      		}catch(NumberFormatException e){
        		usage();
        		return;
      		}
    	}
    width = width - width % segmentsize;
    height = height - height % segmentsize;
   	
    SnakeGame thisGame = new SnakeGame();
		thisGame.initUI(width, height, segmentsize);
   
    try 
		{
			System.out.format("Hit Return to exit program");
			System.in.read();
		  
    }
		catch (IOException e){}
    
		thisGame.f.dispatchEvent(new WindowEvent(thisGame.f, 
			WindowEvent.WINDOW_CLOSING));
        thisGame.f.dispose();	
	  thisGame.mover.stopM();

	}

  /** initialize the UI part of the game
   * @param x - the max x 
   * @param x - the max y
   * @param p - pixels
   */ 
	public void initUI(int x, int y, int p){
		
    //first time game
    if (firsttime){
        f = new JFrame("Snake Game");
		    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		    f.setSize(600, 600);

		    JPanel upPart = new JPanel();
		    upPart.setLayout(new GridLayout(2,3));
        upPart.setPreferredSize(new Dimension(0, 30));

		    JLabel  stLabel = new JLabel("Score: ");
	    	scoreLabel = new JLabel("".format(FORMAT,score));
		    goLabel = new JLabel("Welcome to Snake Game~");

		    JLabel hsLabel = new JLabel("High Score: ");
		    highScoreLabel = new JLabel("".format(FORMAT,highScore));
		    upPart.add(stLabel);
		    upPart.add(scoreLabel);
		    upPart.add(goLabel);
		    upPart.add(hsLabel);
		    upPart.add(highScoreLabel);

	
		    JPanel downPart = new JPanel();
		    downPart.setLayout(new FlowLayout());
		    speedControl = new JSlider(SLOW,FAST,SLOW);
		    resetButton = new JButton("Reset!");
		    newButton = new JButton("New Game");
		    downPart.add(newButton);
		    downPart.add(resetButton);
		    downPart.add(speedControl);
        downPart.setPreferredSize(new Dimension(0, 30));

        newButton.addActionListener(this);
        resetButton.addActionListener(this);
		    speedControl.addChangeListener(this);

		
		    f.getContentPane().add(upPart, BorderLayout.NORTH);
		    f.getContentPane().add(downPart, BorderLayout.SOUTH);
		    
        graph = new GraphicsGrid(x, y, p, f);
    
		    f.add(graph, BorderLayout.CENTER);
    
        f.getContentPane().validate();
        
		    f.setVisible(true);
   //not the first time game
   }else{
        int fx = (int)f.getSize().getWidth();
        int fy = (int)f.getSize().getHeight();
      
        graph.set(fx, fy);
   }
   
	}

  /** initialize the the logical part of the game
   */ 
	public void initGame(){
		
    
    graph.clearSnake();
    graph.obsCells.clear();
    graph.bodyCells.clear();
    
    score = 0;
		scoreLabel.setText("".format(FORMAT,score));
		good = true;
		direction = new Coord(0,1);

    int cols = 0;
    int rows = 0;
    int inita = 0;
    
    if (!firsttime){
		  cols = (int)f.getSize().getWidth()/segmentsize;
		  rows = ((int)f.getSize().getHeight()-60)/segmentsize;	
    
      int fx = (int)f.getSize().getWidth();
      int fy = (int)f.getSize().getHeight();
      
      graph.set(fx, fy);
    
      grid = new GameGrid(new Coord(cols/2, 0), cols, rows, graph, segmentsize);
      grid.first = false;
    }else{
    
      cols = width/segmentsize;
		  rows = height/segmentsize;
      inita = (int)f.getSize().getWidth()/segmentsize;
    
      grid = new GameGrid(new Coord(inita/2, ((int)f.getSize().getHeight() - height - 60)/(segmentsize*2)), cols, rows, graph, segmentsize);
      firsttime = false;
    }
    
		mover = new SnakeMover(this, grid, direction);

    newButton.addKeyListener(mover);
		resetButton.addKeyListener(mover);
		speedControl.addKeyListener(mover);
		
    setSpeed();	
    
		goLabel.setText("");
    Thread t = new Thread(this.mover);
    t.start();
	}

  /** perform new or reset according to the pressing button action
   * @param evt - the pressing button action event
   */ 
	public void actionPerformed (ActionEvent evt)
	{
		if (evt.getSource() == resetButton)
		{
			highScoreLabel.setText("".format(FORMAT,highScore=0));

			scoreLabel.setText("".format(FORMAT,score=0));
			if (mover != null ) mover.stopM();
			good = false;
      goLabel.setText("GAME OVER!");
			speedControl.setValue(SLOW);
		}

		if (evt.getSource() == newButton)
		{
      if (mover != null ) mover.stopM();
      if (!firsttime){
          initUI((int)f.getSize().getWidth(), (int)f.getSize().getHeight() - 60, segmentsize);
      }
      
			initGame();
		}
	}
		
	private void setSpeed()
	{
		int speed = speedControl.getValue();
		if (mover != null)
		{
			speed = ((FAST - speed)/SLOW) + 1;
			mover.setSpeed ( 2 * speed);
		}
	}

  /** perform changing speed when the sliding action
   * @param evt - the sliding action event
   */ 
	public void stateChanged (ChangeEvent evt)
	{
		setSpeed();
	}

  /** update the score part and increase speed after a while
   */ 
	public void updateScore()
	{
		if (!good) return;
		score += 10;
		
		if (score >= highScore){
			highScore = score;
		}

		scoreLabel.setText("".format(FORMAT,score));
		highScoreLabel.setText("".format(FORMAT,highScore));
		increaseSpeed();	
	}

	private void increaseSpeed()
	{
		int speed = speedControl.getValue();
		int newSpeed = (score/SPDINC+ 1)*SLOW; 
		newSpeed = Math.min(newSpeed,FAST);
		speedControl.setValue(Math.max(newSpeed,speed));
	}

  /** when an error happen, stop the mover and game over
   */ 
  public void error()
	{
		good = false;
		goLabel.setText("GAME OVER!");
    if (mover != null ) mover.stopM();
	}
 
}