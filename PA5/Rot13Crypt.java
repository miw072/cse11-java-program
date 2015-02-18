/** A class that encrypts/decrypts by rotate 13 algorithm
 * @author YOUR NAME HERE
 * @version 12/Feb/2015
 */
 
import java.io.*;
import java.util.*;

public class Rot13Crypt extends CryptStream{

	public Rot13Crypt(StreamPair theStreams){
		super(theStreams);
	}
	
	public byte [] cryptData( byte [] data, int len){
		byte[] code = new byte[len];
		for (int i = 0; i < len; i++){
			code[i] = (byte)(((int)data[i] + 13) % 256);
		}
		return code;
	}
	
	public byte [] decryptData( byte [] data, int len){
		byte[] code = new byte[len];
		for (int i = 0; i < len; i++){
			int tmp = (int)data[i] - 13;
			if (tmp < 0){
				code[i] = (byte)(tmp + 256);
			}else{
				code[i] = (byte)tmp;
			}
		}
		return code;
	}
}