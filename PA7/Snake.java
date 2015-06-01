/*Snake.java*/

/***************************                                                   
  * NAME: Mingxuan Wang
  * EMAIL: miw072@ucsd.edu                                                            
  * LOGIN: cs11winter2015                                                              
  * ID: A53077257                                                               
  * ************************/
  
/**
 * Provide a class of the logic back-end part of the snake
 * @author Mingxuan Wang
 * @version CSE11-Winter2015-PR7
 */ 
public class Snake
{
	private Coord[] body;
	private int bodyLength;

  /** Constructor 
   * 2 - argument constructor: build an array of coordinate
	 * @param initial - the initial start coordinate of the snake
   * @param max - maximum length of the snake
	 */
	public Snake(Coord initial, int max){
      body = new Coord[max];
      body[0] = new Coord(initial);
      bodyLength = 1;
	}

  /** move the snake along a centain direction
   * @param x - the x direction
   * @param x - the y direction
	 * @return true if succeed, false otherwise
	 */
	public boolean move(int x, int y){
		  if ((x != 0 && y != 0) || (x == 0 && y == 0)){
			  return false;
		  }
		  if (Math.abs(x) > 1 || Math.abs(x) > 1){
			  return false;
		  }

		  Coord newHead = new Coord(body[0].getX() + x, body[0].getY() + y);
    
		  if (bodyLength == 1){
			  body[0] = newHead;
		  }else{
			  for (int i = bodyLength - 1; i > 0; i--){
				  body[i] = body[i - 1];
			  }
			  body[0] = newHead;
		  }
		  return true;
  }

  /** grow the snake along a centain direction
   * @param x - the x direction
   * @param x - the y direction
	 * @return true if succeed, false otherwise
	 */
	public boolean grow(int x, int y){
		  Coord tail = getTail();
		  move(x, y);
		  if (bodyLength != body.length){
			  body[bodyLength++] = tail;
			  return true;	
		  }
		  return false;
  }

  /** get the head coordinate of the snake
	 * @return the head coordinate of the snake
	 */
	public Coord getHead(){
		  return body[0];
	}  

  /** get the tail coordinate of the snake
	 * @return the tail coordinate of the snake
	 */
	public Coord getTail(){
		  return body[bodyLength - 1];
	}

  /** get the length of current snake
	 * @return the length of the snake
	 */
	public int getLength(){
		  return bodyLength;
	}

  /** get the body part of current snake
	 * @return the the coordinate of the body as an array
	 */
	public Coord[] getSnake(){
		  if (body == null){
			  return null;
		  }
		  Coord[] tSnake = new Coord[bodyLength];
		  for (int i = 0; i < bodyLength; i++){
			  tSnake[i] = body[i];
		  }
		  return tSnake;
  }

  /** judge if the snake intersect itself
	 * @return true if the snake intersect itself, false otherwise
	 */
	public boolean isIntersect(){
		  Coord h = body[0];
		  for (int i = 1; i < body.length; i++){
			  if (body[i] != null && body[i].getX() == h.getX() && body[i].getY() == h.getY()){
				  return true;
			  }
		  }
		  return false;
  }
  
}