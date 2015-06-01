/** A command line program
 * @author Mingxuan Wang
 * @version 12/Feb/2015
 */
 
import java.io.*;
import java.util.*;

public class Cryptor
{
    
    // a error helper method, print the usage
    private static void errorHelper(){
        System.out.format("Crytor [-d algorithm] [-e algorithm] [-k key] [-i infile] [-o outfile]%n");
        System.out.format("     algorithm: plain rot13 key%n");
    }
    
    private static String deAlgorithm = "plain";
    private static String enAlgorithm = "plain";
    private static String key = "";
    private static String infile = "-";
    private static String outfile = "-";
	
 
    //main method of command line program
    public static void main(String[] args){
        boolean isEncode = true;
	StreamPair pair = null;
      
        //use two hash set to keep strings of command
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
		
	//check error input--odd number of parameters
	if (args.length % 2 != 0){
            errorHelper();
            return;
        }
		
        //check unknown flags
        for (int i = 0; i < args.length; i += 2){
           if (!commandSet.contains(args[i])){
               System.out.println("Unknown flag: " + args[i]);
	       errorHelper();
	       return;
	   }
        }
		 
        //store the imporatant parameters
	for (int i = 0; i < args.length - 1; i++){
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
		
         //check unknown algorithm
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
		
         //create streampair, deal with exception if necessary
        try{
	    pair = new StreamPair(infile, outfile);
            cryptor(pair, isEncode);
        }catch(IOException e){
	    System.err.println(e);
	    errorHelper();
	    return;
	}finally{
            closeStreamPair(pair);
        }
        return;
    }
    
    /** void closeStreamPair(StreamPair pair): helper method of close stream pair
     *  input: pair: the name of the StreamPair
     */
    private static void closeStreamPair(StreamPair pair){
        try{
            if (pair != null){
                pair.close();
            }
        }catch(IOException e){
            System.err.println(e);
	    errorHelper();
        }
    }
    
    /** void cryptor(StreamPair pair, boolean isEncode): helper method of cryptor to create
     *  certain type of CryptStream according to the algorithm
     *  input: pair: the name of the StreamPair
     *         isEncode: boolean variable indicates that encode or decode
     */
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
	
    /** cryptHelper(CryptStream stream, boolean isEncode): helper method to do crypt or decrypt
     *  input: stream: the name of the CryptStream
     *         isEncode: boolean variable indicates that encode or decode
     */
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
