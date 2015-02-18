/** A class that encrypts/decrypts by rotate 13 algorithm
 * @author YOUR NAME HERE
 * @version 12/Feb/2015
 */
 
import java.io.*;
import java.util.*;

public class KeyCrypt extends CryptStream{

	private String key;
	private byte numKey;
	public KeyCrypt(StreamPair theStreams, String key){
		super(theStreams);
		this.key = key;
	}
	
	private int calKey(String stringKey){
		byte[] tmp = stringKey.getBytes();
		int result = 0;
		for (int i = 0; i < tmp.length; i++){
			result += (int) tmp[i];
		}
		return result;
	}
	
	public byte [] cryptData( byte [] data, int len){
		numKey = (byte)(calKey(key) % 256);
		
		byte[] code = new byte[len];
		for (int i = 0; i < len; i++){
	        code[i] = (byte)(numKey ^ data[i]);
		}
		return code;
	}
	
	public byte [] decryptData( byte [] data, int len){
		numKey = (byte)(calKey(key) % 256);
		
		byte[] code = new byte[len];
		for (int i = 0; i < len; i++){
		    code[i] = (byte)(numKey ^ data[i]);	
		}
		return code;
	}
}