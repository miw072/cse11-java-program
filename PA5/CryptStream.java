/** A class that encrypts/decrypts
 * @author Mingxuan Wang
 * @version 12/Feb/2015
 */

import java.io.*;
import java.util.*;

public abstract class CryptStream
{
	protected StreamPair streams;
	/** Constructor 
	 * @param theStreams a constructed StreamPair Instance
	 */
	public CryptStream(StreamPair theStreams)
	{
		streams = theStreams;
	}
	/** Encrypt data in the byte array
	 * @param data - the data to encrypt
	 * @param len - how many bytes in the array to encrypt
	 * @return a byte array with data encrypted. length may not be equal to
	 * input
	 */
	abstract protected byte [] cryptData( byte [] data, int len); 
	/** Decrypt data in the byte array
	 * @param data - the data to decrypt
	 * @param len - how many bytes in the array to decrypt
	 * @return a byte array with data decrypted. length may not be equal to
	 * input
	 */
	abstract protected byte [] decryptData( byte [] data,int len);

	
	/** encrypt the input stream, and write to the output stream of 
     * of the StreamPair 
	*  @return number of bytes in output stream
	*/
	public final int encrypt() throws IOException
	{
            int i;
            int len = 0;
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = streams.getInput().read()) != -1){
     	        byte c = (byte) i;
                baos.write(c);
                len ++;			
	    }
 	    byte[] arr = baos.toByteArray();
	    byte[] code = cryptData(arr, len);
	    streams.getOutput().write(code);
	    return code.length;
	}
	/** decrypt the input stream, and write to the output stream of 
     * of the StreamPair 
	*  @return number of bytes in output stream
	*/
	public final int decrypt() throws IOException
	{
	    int i;
	    int len = 0;
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = streams.getInput().read()) != -1){
		 byte c = (byte) i;
                 baos.write(c);
                 len ++;			
	    }
    	    byte[] arr = baos.toByteArray();
	    byte[] origin = decryptData(arr, len);
	    streams.getOutput().write(origin);
	    return origin.length;
	}

}
// vim: ts=4:sw=4:tw=78
