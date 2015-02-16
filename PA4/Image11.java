/***************
	The 4th Program Assignment of CSE 11, winter 2015
	Written by Mingxuan Wang
***************/
/**************
	Name : Mingxuan Wang
	Email: miw072@eng.ucsd.edu
	ID: A53077257
**************/

/* Support some simple manipulation of a BufferedImage
 * Keep track of initial color values of an image
 * Compute color negatives of rectangular subregions
 * Restore image to its initial value
 * provide array of rgb values of the current state
 * return size of image
 */
 
 /**
 * Provide a set of Image functions on a BufferdImage
 * @author Mingxuan Wang
 * @version CSE11-Winter2015-PR4
 */
 
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Image11 
{
	private int[][] originPixels;                        //private int array to store the origin image pixel value
	private BufferedImage currentImage;
	private int imageWidth;
	private int imageHeight;
 
	/** Constructor 
	 * @param img Shallow copied reference 
	 */
	public Image11(BufferedImage img){
		currentImage = img;
		imageHeight = img.getHeight();
		imageWidth = img.getWidth();
    restoreHelper(imageWidth, imageHeight, img);
	}
 
  /** compute and store the origin image pixel value to an 2D int array
	 */
	private void restoreHelper(int imageWidth, int Height, BufferedImage img){
    	originPixels = new int[imageWidth][imageHeight];
		for (int i = 0; i < imageWidth; i++){
			for (int j = 0; j < imageHeight; j++){
				originPixels[i][j] = img.getRGB(i, j);
			}
		}
	}

	/** return a width x height array of current rgb values 
	 * @return array of rgb values 
	 */
	public int [][] getPixels()
	{
		int[][] currentPixels = new int[imageWidth][imageHeight];
		for (int i = 0; i < imageWidth; i++){
			for (int j = 0; j < imageHeight; j++){
				currentPixels[i][j] = currentImage.getRGB(i, j);
			}
		}
		return currentPixels;
	}

	/** return the array of pixel values of the image as it was
	 *  as at construction time  
	 * @return array of rgb values (width x height) 
	 */
	public int [][] getInitialPixels()
	{
		return originPixels;
	}
		
	/** Compute the color negative of a rectangular region
	 * This operates on the current state of the image
	 * @param x - x graphics coordinate of upper-left corner of region
	 * @param y - y graphics coordinate of upper-left corner of region
	 * @param width - width of region to negate
	 * @param height - height of region to negate
	 * @return true if negation was succesful, false otherwise
	 */ 
	public boolean negative(int x, int y, int width, int height)
	{
		if (x < 0 || y < 0 || width < 0|| height < 0 || (x + width >= imageWidth) || (y + height >= imageHeight)){
			return false;
		}
		for (int i = x; i <= x + width; i ++){
			for (int j = y; j <= y +height; j++){
				int rgb = currentImage.getRGB(i, j);
				int nRGB = (0x00FFFFFF - (rgb | 0xFF000000)) | (rgb & 0xFF000000);  // negative of rgb
				currentImage.setRGB(i, j, nRGB);
			}
		}
		return true;
	}

	/** compute the color negative of the current image state
	 * @return true if successful, false otherwise
	 */
	public boolean negative()
	{
		for (int i = 0; i < imageWidth; i ++){
			for (int j = 0; j < imageHeight; j++){
				int rgb = currentImage.getRGB(i, j);
				int nRGB = (0x00FFFFFF - (rgb | 0xFF000000)) | (rgb & 0xFF000000);  // negative of rgb
				currentImage.setRGB(i, j, nRGB);
			}
		}
		return true;
	}

	/** Restore the current state of the image back to its orginal 
	 *  state
	 */
	public void restoreOriginal()
	{
    for (int i = 0; i < imageWidth; i ++){
			for (int j = 0; j < imageHeight; j++){
				currentImage.setRGB(i, j, originPixels[i][j]);                      //use originPixels to set the current image
			}
		}
	}

	/** Return the width and height of the image 
	 *  @return array where index=0 is width, index=1 height 
	 */
	public int [] getSize()
	{
		int[] size = new int[2];
		size[0] = imageWidth;
		size[1] = imageHeight;
		return size;
	}
}
// vim: ts=4:sw=4:tw=78
