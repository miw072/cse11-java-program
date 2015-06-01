/** Create a pair of InputStream/OutputStream  objects
 * based what was asked for in the constructor
 * @author Mingxuan Wang
 * @version 15 Feb 2015
 */
import java.io.*;
import java.util.*;
public class StreamPair 
{
	private InputStream in;
	private OutputStream out;
	/** Constructor
	 * @param infile  Name of the input file. "-" means standard input
	 * @param outfile  Name of the output file. "-" means standard output
	 * @throws IOException  file related exceptions
	 */
	public StreamPair(String infile, String outfile) throws IOException
	{
		if (infile.equals("-")){
			in = System.in;
		}else{
			FileInputStream fileInput = new FileInputStream(infile);
			in = fileInput;
		}
		
		if (outfile.equals("-")){
			out = System.out;
		}else{
			FileOutputStream fileOutput = new FileOutputStream(outfile);
			out = fileOutput;
		}
		
	}

	/** get a reference to this stream pair's input stream
	 * @return  reference to the input stream
	 */
	public InputStream getInput()
	{
		return in;
	}

	/** get a reference to this stream pair's output stream
	 * @return  reference to the output stream
	 */
	public OutputStream getOutput()
	{
		return out;
	}

	/** close both streams. Should not cause an error if invoked multiple times
	 */
	public void close() throws IOException
	{
            if (in != null) {
               in.close();
            }
            if (out != null) {
               out.close();
            }
	}
}
// vim: ts=4:sw=4:tw=78
