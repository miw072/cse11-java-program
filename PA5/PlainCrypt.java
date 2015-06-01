/** A class that encrypts/decrypts by plain algorithm
 * @author Mingxuan Wang
 * @version 12/Feb/2015
 */
 
import java.io.*;
import java.util.*;

public class PlainCrypt extends CryptStream{

  /** Constructor
   * @param theStream Name of the the streampair. 
   */
	public PlainCrypt(StreamPair theStreams){
		super(theStreams);
	}
	
  /** Encrypt data in the byte array
	 * @param data - the data to encrypt
	 * @param len - how many bytes in the array to encrypt
	 * @return a byte array with data encrypted. length may not be equal to
	 * input
	 */
	public byte [] cryptData( byte [] data, int len){
		byte[] code = new byte[len];
		for (int i = 0; i < len; i++){
			code[i] = data[i];
		}
		return code;
	}
	
  /** Decrypt data in the byte array
	 * @param data - the data to decrypt
	 * @param len - how many bytes in the array to decrypt
	 * @return a byte array with data decrypted. length may not be equal to
	 * input
	 */
	public byte [] decryptData( byte [] data, int len){
		byte[] code = new byte[len];
		for (int i = 0; i < len; i++){
			code[i] = data[i];
		}
		return code;
	}
 }