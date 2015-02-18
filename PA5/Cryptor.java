/** A command line program
 * @author Mingxuan Wang
 * @version 12/Feb/2015
 */
 
import java.io.*;
import java.util.*;

public class Cryptor
{
    private static void errorHelper(){
        System.out.format("Crytor [-d algorithm] [-e algorithm] [-k key] [-i infile] [-o outfile]%n");
        System.out.format("     algorithm: plain rot13 key%n");
    }
	private static String deAlgorithm = "plain";
	private static String enAlgorithm = "plain";
	private static String key;
	private static String infile = "-";
	private static String outfile = "-";
	
    public static void main(String[] args){
	    boolean isEncode = true;
		StreamPair pair;
        HashSet<String> algoSet = new HashSet<String>();
		HashSet<String> commandSet = new HashSet<String>();
		
		algoSet.add("plain");
		algoSet.add("rot13");
		algoSet.add("key");
		
		commandSet.add("-d");
		commandSet.add("-e");
		commandSet.add("-i");
		commandSet.add("-k");
		commandSet.add("-o");
		
		//check error input
		if (args.length % 2 != 0){
            errorHelper();
            return;
        }
		
        for (int i = 0; i < args.length; i += 2){
            if (!commandSet.contains(args[i])){
                    System.out.println("Unknown flag: " + args[i]);
					errorHelper();
					return;
				}
        }
		 
		for (int i = 0; i < args.length; i++){
		    if (args[i].equals("-d")){
                deAlgorithm = args[i + 1];
				isEncode = false;
			}
            if (args[i].equals("-e")){
                enAlgorithm = args[i + 1];
				isEncode = true;
			}
            if (args[i].equals("-k")){
                key = args[i + 1];
			}
            if (args[i].equals("-i")){
                infile = args[i + 1];
			}
            if (args[i].equals("-o")){
                outfile = args[i + 1];
			}			
		}
		
		if (!algoSet.contains(deAlgorithm)){
		    System.out.println("Unknown algorithm: " + deAlgorithm);
			errorHelper();
			return;
		}
		
		if (!algoSet.contains(enAlgorithm)){
		    System.out.println("Unknown algorithm: " + enAlgorithm);
			errorHelper();
			return;
		}
		
		try{
			pair = new StreamPair(infile, outfile);
		}catch(IOException e){
		    System.err.println(e);
			errorHelper();
			return;
		}
		
		cryptor(pair, isEncode);
    }

	private static void cryptor(StreamPair pair, boolean isEncode){
		String algorithm;
		
		if(isEncode){
			algorithm = enAlgorithm;
		}else{
			algorithm = deAlgorithm;
		}
		
		switch(algorithm){
			case "plain":
				CryptStream plainStream = new PlainCrypt(pair);
				cryptHelper(plainStream, isEncode);
				break;
			case "rot13":
				CryptStream rot13Stream = new Rot13Crypt(pair);
				cryptHelper(rot13Stream, isEncode);
				break;
			case "key":
				CryptStream keyStream = new KeyCrypt(pair, key);
				cryptHelper(keyStream, isEncode);
				break;
			default:
			    break;
		}
	}
	
	private static void cryptHelper(CryptStream stream, boolean isEncode){
		if(isEncode){
			try{
				stream.encrypt();
			}catch(IOException e){
			    System.err.println(e);
				errorHelper();
				return;
			}
		}else{
			try{
				stream.decrypt();
			}catch(IOException e){
			    System.err.println(e);
				errorHelper();
				return;
			}
		}
	}
}
