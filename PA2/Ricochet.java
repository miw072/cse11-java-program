/***************
	The second Program Assignment of CSE 11, winter 2015
	Written by Mingxuan Wang
***************/
/**************
	Name : Mingxuan Wang
	Email: miw072@eng.ucsd.edu
	ID: A53077257
**************/

import objectdraw.*;
import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit; 

public class Ricochet extends WindowController{
   private static final int CANVAS_HEIGHT = 600;                        //used for canvas height
   private static final int CANVAS_WIDTH = 400;                         //used for canvas height
   private static int boxSize, numCrossings, pixelSpeed;
   
   public static void main(String[] args){
      Scanner scnr = new Scanner(System.in);
      WindowController W = new Ricochet();
      
      //get user's input
      System.out.print("Enter size of box in pixel: ");
		  boxSize = scnr.nextInt();
      if ((boxSize <= 0) || (boxSize >= CANVAS_WIDTH / 2 - 1)){         //check for valid input
         System.out.println("Invalid boxsize!");
         return;
      }
      System.out.print("Enter pixel speed: ");
      pixelSpeed = scnr.nextInt();
      System.out.print("Enter number of crossings: ");
      numCrossings = scnr.nextInt();
      if (numCrossings <= 0){                                           
         pixelSpeed = 0;
      }
      W.startController(CANVAS_WIDTH, CANVAS_HEIGHT);                   //draw the canvas
   }
   
   //This method is used for all logical operation of this assignment
   public void begin(){
      FilledRect box;
      int count = 0;
      Text timesLeft = new Text(numCrossings - count, 0, 0, canvas);    //count how many times left to cross the center
      int xcoord = CANVAS_WIDTH / 2 - boxSize / 2;                      //initial box in the center of canvas
      int ycoord = CANVAS_HEIGHT / 2 - boxSize / 2;
      int dx = pixelSpeed;                                              //speed of box, positive: left to right, negative: right to left
      int dy = 0;
    
      box = new FilledRect(xcoord, ycoord, boxSize, boxSize, canvas);   //draw a box in the center of canvas
      box.setColor(new Color(0, 0, 255));                               //set color of the box to BLUE
      try { TimeUnit.MILLISECONDS.sleep(2000);}                         //pause for 2 seconds 
                catch (InterruptedException e){};
      
      while (count < numCrossings){
         try { TimeUnit.MILLISECONDS.sleep(100);}                       //used for the simulation of animation
                catch (InterruptedException e){};
         if (box.getX() + boxSize + dx >= CANVAS_WIDTH){                //left-most side 
            box.moveTo(CANVAS_WIDTH - boxSize, ycoord); 
            dx = -dx;                                                   //change direction
         }else if (box.getX() + dx <= 0){
            box.moveTo(0, ycoord);                                      //right-most side
            dx = -dx;                                                   //change direction
         }else{
            if ((box.getX() <= (CANVAS_WIDTH / 2) && (box.getX() + dx) > (CANVAS_WIDTH / 2)) || 
                (box.getX() >= (CANVAS_WIDTH / 2) && (box.getX() + dx) < (CANVAS_WIDTH / 2))){
               count ++;                                                //the box(upper-left point) pass the center, add 1 to count
               timesLeft.setText(numCrossings - count);                 //update the text
            }
            box.moveTo(box.getX() + dx, ycoord);                        //regular movement
         } 
      }
      return;
  }
}