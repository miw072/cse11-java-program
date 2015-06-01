/*Coord.java*/

/***************************                                                   
  * NAME: Mingxuan Wang
  * EMAIL: miw072@ucsd.edu                                                            
  * LOGIN: cs11winter2015                                                              
  * ID: A53077257                                                               
  * ************************/

/**
 * Provide a class contains the coordinate of a point
 * @author Mingxuan Wang
 * @version CSE11-Winter2015-PR7
 */
public final class Coord
{
	private int x;
	private int y;

  /** Constructor 
   * 2 - argument constructor
	 * @param i - the x coordinate of the point
   * @param j - the y coordinate of the point
	 */
	public Coord(int i, int j){
		x = i;
		y = j;
	}

  /** Constructor 
   * 1 - argument constructor
	 * @param initial - the initial Coord of a point
	 */
	public Coord(Coord initial){
		x = initial.getX();
		y = initial.getY();
	}

  /** get the x coordinate of the point
	 * @return the x coordinate of the point
	 */
	public int getX(){
		return x;
	}

  /** get the y coordinate of the point
	 * @return the y coordinate of the point
	 */
	public int getY(){
		return y;
	}

}