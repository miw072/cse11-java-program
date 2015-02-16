/*
 * CSE 11 PR4
 * February 2015
 *
 * Test cases for Image11.java
 *
 * DEPENDENCIES: hamcrest-core-1.3.jar and junit-4.12.jar in the current directory
 *               (downloads here https://github.com/junit-team/junit/wiki/Download-and-Install)
 *
 * COMPILING:    javac -cp '.:*' TestImage11.java
 *
 * RUNNING:      java -cp '.:*' org.junit.runner.JUnitCore TestImage11
 *
 */

import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

import static org.junit.Assert.*;
import org.junit.*;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

/* Run tests in order (sorted lexicographically by name...), as later
 * tests depend on previous ones.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestImage11
{
	private BufferedImage image = null;
	private Image11 img11 = null;

	private BufferedImage readImage(String fname)
	{
		BufferedImage i = null;

		try {
			i = ImageIO.read(new File(fname));
		} catch (IOException e) {
			System.out.println("File not found: " + e.getMessage());
		}
		return i;
	}

	/* Make a BufferedImage to be manipulated by Image11, link it to our Image11
	 * object. Test images will be initialized as needed in test methods.
	 */
	@Before
	public void setUp()
	{
		image = readImage("owl_original.jpg");
		img11 = new Image11(image);
	}

	/* getSize() is used extensively in other test cases, but we should
	 * minimally verify that it meets its spec
	 */
	@Test
	public void a_testGetSize()
	{
		// This should really be a tuple, but java doesn't have them :(
		int[] size = img11.getSize();

		assertEquals("Wrong length of returned list",
					 size.length, 2);

		assertEquals("Width field differs from actual image width",
					 size[0], image.getWidth());

		assertEquals("Height field differs from actual image height",
					 size[1], image.getHeight());
	}

	@Test
	public void b_testGetInitialPixels()
	{
		int[] size = img11.getSize();
		int[][] pixels = img11.getInitialPixels();

		assertEquals("Width in returned array doesn't match width of image",
					 pixels.length, image.getWidth());

		assertEquals("Width in returned array doesn't match width field",
					 size[0], pixels.length);

		assertEquals("Height in returned array doesn't match height of image",
					 pixels[0].length, image.getHeight());

		assertEquals("Height in returned array doesn't match height field",
					 size[1], pixels[0].length);

		/* Compare pixels to unmodified image, should be the same as we haven't
		 * modified anything.
		 */
		BufferedImage the_image = readImage("owl_original.jpg");

		for (int i = 0; i < pixels.length; ++i)
			for (int j = 0; j < pixels[0].length; ++j)
				assertEquals("Found a pixel value different to original image!",
							 pixels[i][j], the_image.getRGB(i,j));
	}

	@Test
	public void c_testGetPixels()
	{
		int[][] initPix = img11.getInitialPixels();
		int[][] copyPix = img11.getPixels();

		assertEquals("Width different from picture width",
					initPix.length, copyPix.length);

		assertEquals("Height different from picture height",
					 initPix[0].length, copyPix[0].length);

		// No reference equality, getPixels makes a NEW array
		assertTrue("getPixels returned a shallow copy cf getInitialPixels",
				   initPix != copyPix);

		/* Every nested array has the same values, as we haven't changed
		 * anything.
		 */
		for (int i = 0; i < initPix.length; ++i)
			assertArrayEquals("Unmodified pixel array different from initial array",
							  initPix[i], copyPix[i]);
	}


	// negates whole image
	@Test
	public void d_testNegativeNoArgs()
	{
		//BufferedImage negated_image = readImage("owl_full_neg.jpg");

		// Negated
		img11.negative();

		int[][] negated = img11.getPixels();
		int[][] initPix  = img11.getInitialPixels();

		// Every pixel is different to original
		for (int i = 0; i < initPix.length; ++i)
			for (int j = 0; j < initPix[0].length; ++j)
				// Sadly there's no AssertArrayNotEquals... poor design
				assertTrue("Modified pixel same as pixel from initial array",
						   negated[i][j] != initPix[i][j]);

		WriteImage.write(image,"YOUR_FULL_NEG.jpg");
		System.out.println("Check generated full negative image with reference: YOUR_FULL_NEG.jpg");

		// Back to normal
		img11.negative();
		negated = img11.getPixels();

		// negated == initPixels
		for (int i = 0; i < initPix.length; ++i)
			assertArrayEquals("Modified pixel NOT same as pixel from initial array",
							  negated[i], initPix[i]);
	}

	@Test
	public void e_testNegativeWithArgs()
	{
		BufferedImage partially_negated_image = readImage("owl_partial_neg.jpg");
		BufferedImage original_image = readImage("owl_original.jpg");
		int[] size = img11.getSize();

		// Check bounds -- these should all exit and return false
		// starting pos outside frame
		assertFalse("Error: upper left x smaller than 0",
					img11.negative(-100, 10, 50, 50));

		assertFalse("Error: upper left y smaller than 0",
					img11.negative(10, -100, 50, 50));

		assertFalse("Error: upper left x > image width",
					img11.negative(size[0]+100, 10, 50, 50));

		assertFalse("Error: upper left y > image height",
					img11.negative(10, size[1]+100, 50, 50));

		// goes out of bounds horizontally
		assertFalse("Error: too wide for this starting position ",
					img11.negative(size[0]/2, 10, size[0], 10));

		// goes out of bounds vertically
		assertFalse("Error: too tall for this starting position",
					img11.negative(10, size[1]/2, 10, size[1]));

		// start at center, negate an area > quadrant 4
		assertFalse("Error: completely outside image",
					img11.negative(size[0]/2, size[1]/2, size[0], size[1]));

		// image should not be modified
		int[][] modifiedp = img11.getPixels();

		for (int i = 0; i < size[0]; ++i)
			for (int j = 0; j < size[1]; ++j)
				assertTrue("Found a different pixel to original image",
						   modifiedp[i][j] == original_image.getRGB(i,j));

		// Generate sample negation from writeup
		img11.negative(170,140,300,200);

		WriteImage.write(image,"YOUR_PARTIAL_NEG.jpg");
		System.out.println("Check generated partial negative image with reference: YOUR_PARTIAL_NEG.jpg");
	}

	/* Restores original image after some arbitrary negations
	 */
	@Test
	public void f_testRestoreOriginal()
	{
		BufferedImage original = readImage("owl_original.jpg");

		// Modify then restore
		img11.negative();
		img11.negative(170,140,300,200);
		img11.restoreOriginal();

		int[][] pixels = img11.getPixels();

		// Again, test for pixel equality with original image
		for (int i = 0; i < pixels.length; ++i)
			for (int j = 0; j < pixels[0].length; ++j)
				assertEquals("Different pixel values, did not restore properly",
							  pixels[i][j], original.getRGB(i,j));
	}
}
