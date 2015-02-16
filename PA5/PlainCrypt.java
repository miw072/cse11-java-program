/** A class that encrypts/decrypts by plain algorithm
 * @author YOUR NAME HERE
 * @version 12/Feb/2015
 */
 
import java.io.*;
import java.util.*;

public class PlainCrypt extends CryptStream{

	public PlainCrypt(StreamPair theStreams){
		super(theStreams);
	}
	
	public byte [] cryptData( byte [] data, int len){
		byte[] code = new byte[len];
		for (int i = 0; i < len; i++){
			code[i] = data[i];
		}
		return code;
	}
	
	public byte [] decryptData( byte [] data, int len){
		byte[] code = new byte[len];
		for (int i = 0; i < len; i++){
			code[i] = data[i];
		}
		return code;
	}
 }