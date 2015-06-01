/** A class that encrypts/decrypts by key algorithm
 * @author Mingxuan Wang
 * @version 12/Feb/2015
 */
 
import java.io.*;
import java.util.*;

public class KeyCrypt extends CryptStream{

	private String key;
	private byte numKey;
  /** Constructor
   * @param theStream Name of the the streampair. 
   * @param key The string represents the key. 
   */
	public KeyCrypt(StreamPair theStreams, String key){
		super(theStreams);
		this.key = key;
	}
	
  /** calculate the key using the string
   * @param stringKey the string represents the key
	 * @return  int key
	 */
	private int calKey(String stringKey){
		byte[] tmp = stringKey.getBytes();
		int result = 0;
               //sum up all charactors in the string
		for (int i = 0; i < tmp.length; i++){
			result += (int) tmp[i];
		}
		return result;
	}
 
	/** Encrypt data in the byte array
	 * @param data - the data to encrypt
	 * @param len - how many bytes in the array to encrypt
	 * @return a byte array with data encrypted. length may not be equal to
	 * input
	 */
	public byte [] cryptData( byte [] data, int len){
		numKey = (byte)(calKey(key) % 256);
		
                //XOR with the key
		byte[] code = new byte[len];
		for (int i = 0; i < len; i++){
	        code[i] = (byte)(numKey ^ data[i]);
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
		numKey = (byte)(calKey(key) % 256);
		
		byte[] code = new byte[len];
		for (int i = 0; i < len; i++){
		    code[i] = (byte)(numKey ^ data[i]);	
		}
		return code;
	}
}