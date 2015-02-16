/** 
 * This is a very simple class that displays an Image in a window
 * A user can update the Image contents themselves and then redisplay
 * the results.
 *
 * Supports closing the window (it can't be reopened)
*
* @author Philip Papadopoulos
* @version 29 Jan 2015
*/
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DisplayImage {

	private BufferedImage myImage;
	private JFrame frame;
	private JLabel label;
	private boolean isOpen = true;
	/**
	 * Constructor builds an empty JFrame 
	*/
	public DisplayImage()
	{
		buildFrame();
	}

	/**
	 * Constructor for image buffer that should be displayed
	 * @param img the image to display
	*/
	public DisplayImage(BufferedImage img)
	{
		myImage = img;
		buildFrame();
	}

	private void buildFrame()
	{
		frame=new JFrame();
		frame.setLayout(new FlowLayout());
		label=new JLabel();
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	/** Display the most recent image. Sets the width and height
	 *  to be that of the image. Should be called explictly after an
	 *  image has been updated
	 */
	public void display()
	{
		if (myImage != null && isOpen)
		{
			final int offX=2,offY=35;
			frame.setSize(myImage.getWidth()+offX,
					myImage.getHeight() + offY);
			ImageIcon icon=new ImageIcon(myImage);
			label.setIcon(icon);
			label.setSize(myImage.getWidth(),myImage.getHeight());
			frame.setFocusableWindowState(false);
			frame.setVisible(true);
		}
	}

	/** Set the image that is to be displayed
	 * @param img  image to be displayed
	 */
	public void setImage(BufferedImage img)
	{
		myImage = img;
	}

	/** close the window. It cannot be reopened
	 */
	public void closeWindow()
	{
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		frame.dispose();
		isOpen = false;
	}

}
// vim: ts=4:sw=4:tw=78
