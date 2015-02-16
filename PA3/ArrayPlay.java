/***************
	The 3rd Program Assignment of CSE 11, winter 2015
	Written by Mingxuan Wang
***************/
/**************
	Name : Mingxuan Wang
	Email: miw072@eng.ucsd.edu
	ID: A53077257
**************/

import java.util.Scanner;

public class ArrayPlay
{
	private IntArray11 currentArray;
	
	public static void main(String[] args){
		Scanner scnr = new Scanner(System.in);
		boolean isNewed = false;
    ArrayPlay cur = new ArrayPlay();
    
    //infinite loop for user's input
		while(true){
			System.out.print("> ");
      String input = scnr.nextLine();
			input = input.toLowerCase();
			String[] argv = input.split("\\s+");                                           //split by white space
      
			if (!isNewed && !argv[0].equals("new") && !argv[0].equals("exit")){            //check for valid input before new
				System.out.println("BAD INPUT");
				continue;
			}
      
      //check for valid input command
			if (argv[0].equals("exit")){
				System.out.println("OK");
				return;
			}
			else if (argv[0].equals("new")){
				cur.newHandler(argv);
				isNewed = true;
				continue;
			}
			else if (argv[0].equals("print")){
				cur.printHandler(argv);
				continue;
			}
		  else if (argv[0].equals("delete")){
				cur.deleteHandler(argv);
				continue;
			}
			else if (argv[0].equals("insert")){
				cur.insertHandler(argv);
				continue;
			}
			else if (argv[0].equals("reverse")){
				cur.reverseHandler(argv);
				continue;
			}
			else if (argv[0].equals("size")){
				cur.sizeHandler(argv);
				continue;
			}
			else if (argv[0].equals("swap")){
				cur.swapHandler(argv);
				continue;
			}
      else if (argv[0].equals("set")){
				cur.setHandler(argv);
				continue;
			}
      else{
        System.out.println("BAD INPUT");
        continue;
      }
		}
	}
	
  //method used for handle "new" command
	private void newHandler(String[] argv){
		int length = argv.length;
		if (length == 1){
			currentArray = new IntArray11();
			System.out.println("OK");
			return;
		}else if (length == 2){
			currentArray = new IntArray11(Integer.parseInt(argv[1]));
			System.out.println("OK");
			return;
		}else{
			int[] argArray = new int[length - 1];
			for (int i = 0; i < argArray.length; i++){
				argArray[i] = Integer.parseInt(argv[i + 1]);
			}
			currentArray = new IntArray11(argArray);
			System.out.println("OK");
			return;
		}
	}
	
  //method used for handle "print" command
	private void printHandler(String[] argv){
		int length = argv.length;
		if (length == 1){
			String printStr = currentArray.toString();
			System.out.println(printStr);
			System.out.println("OK");
			return;
		}else if (length == 2){
			int index = Integer.parseInt(argv[1]);
			int num = currentArray.getElement(index);
			if (num == Integer.MIN_VALUE){
				System.out.println("BAD INPUT");
			}else{
				System.out.println(num);
			}
			return;
		}
	}
	
  //method used for handle "delete" command
	private void deleteHandler(String[] argv){
		int length = argv.length;
		if (length == 2){
			int index = Integer.parseInt(argv[1]);
			boolean ifSuccess = currentArray.delete(index);
			if (ifSuccess){
				System.out.println("OK");
			}else{
				System.out.println("BAD INPUT");
			}
			return;
		}else{
			System.out.println("BAD INPUT");
			return;
		}
	}
 
	//method used for handle "insert" command
	private void insertHandler(String[] argv){
		int length = argv.length;
		if (length == 3){
			int index = Integer.parseInt(argv[1]);
			int element = Integer.parseInt(argv[2]);
			boolean ifSuccess = currentArray.insert(index, element);
			if (ifSuccess){
				System.out.println("OK");
			}else{
				System.out.println("BAD INPUT");
			}
			return;
		}else{
			System.out.println("BAD INPUT");
			return;
		}
	}
	
  //method used for handle "reverse" command
	private void reverseHandler(String[] argv){
		int length = argv.length;
		if (length == 3){
			int start = Integer.parseInt(argv[1]);
			int end = Integer.parseInt(argv[2]);
			boolean ifSuccess = currentArray.reverse(start, end);
			if (ifSuccess){
				System.out.println("OK");
			}else{
				System.out.println("BAD INPUT");
			}
			return;
		}else if (length == 1){
      currentArray.reverse();
      System.out.println("OK");
			return;
		}
	}
	
  //method used for handle "size" command
	private void sizeHandler(String[] argv){
		int length = argv.length;
		if (length == 1){
			int size = currentArray.getNelem();
			System.out.println(size);
      System.out.println("OK");
		}else{
			System.out.println("BAD INPUT");
		}
		return;
	}
	
  //method used for handle "swap" command
	private void swapHandler(String[] argv){
		int length = argv.length;
		if (length == 3){
			int index1 = Integer.parseInt(argv[1]);
			int index2 = Integer.parseInt(argv[2]);
			boolean ifSuccess = currentArray.swap(index1, index2);
			if (ifSuccess){
				System.out.println("OK");
			}else{
				System.out.println("BAD INPUT");
			}
		}else{
			System.out.println("BAD INPUT");
		}
		return;
	}
 
  //method used for handle "set" command
  private void setHandler(String[] argv){
		int length = argv.length;
		if (length == 3){
			int index = Integer.parseInt(argv[1]);
			int element = Integer.parseInt(argv[2]);
			boolean ifSuccess = currentArray.setElement(index, element);
			if (ifSuccess){
				System.out.println("OK");
			}else{
				System.out.println("BAD INPUT");
			}
			return;
		}else{
			System.out.println("BAD INPUT");
			return;
		}
	}
	
}

