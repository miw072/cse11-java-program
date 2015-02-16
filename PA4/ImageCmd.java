/***************
	The 4th Program Assignment of CSE 11, winter 2015
	Written by Mingxuan Wang
***************/
/**************
	Name : Mingxuan Wang
	Email: miw072@eng.ucsd.edu
	ID: A53077257
**************/

/* This is a class used for interacte between Image11 class and DisplayImage class.
 * including some manupilations of a Bufferd Image.
 */
 
import java.util.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;

public class ImageCmd{

  // A helper class to keep track of each image 
	public class ImageRecord{
		public String filename;
		public DisplayImage window;

		public Image11 image;
		public boolean isOpen = false;                           //keep track of each image whether it is open or not
		
		public ImageRecord(BufferedImage img){
			window = new DisplayImage(img);
			image = new Image11(img);
		}
	}
	
	private static int length;
	private boolean isLoaded = false;
	private ArrayList<ImageRecord> lists = new ArrayList<ImageRecord>();
	private ImageRecord currentImageRec;
 
	public static void main(String[] args){
		Scanner scnr = new Scanner(System.in);
		ImageCmd cur = new ImageCmd();
    
		while (true){
			System.out.print("> ");
                        String input = scnr.nextLine();
			input = input.toLowerCase();
			String[] argv = input.split("\\s+");
			length = argv.length;
			
			//For different input, execute different helper method
			if (argv[0].equals("exit")){
                                cur.closeAll();            //close all window
				System.out.println("OK");
				return;
			}
			else if (argv[0].equals("load")){
				cur.loadHandler(argv);
				continue;
			}
			else if (argv[0].equals("list")){
				cur.listHandler(argv);
				continue;
			}
			else if (argv[0].equals("switch")){
				cur.switchHandler(argv);
				continue;
			}
			else if (argv[0].equals("current")){
				cur.currentHandler(argv);
				continue;
			}
			else if (argv[0].equals("size")){
				cur.sizeHandler(argv);
				continue;
			}
			else if (argv[0].equals("display")){
				cur.displayHandler(argv);
				continue;
			}
			else if (argv[0].equals("close")){
				cur.closeHandler(argv);
				continue;
			}
			else if (argv[0].equals("negative")){
				cur.negativeHandler(argv);
				continue;
			}
			else if (argv[0].equals("restore")){
				cur.restoreHandler(argv);
				continue;
			}
			else{
				System.out.println("BAD INPUT");
				continue;
			}
		}
	}
	
  // A helper method to close all window
	private void closeAll(){
        	for (int i = 0; i < lists.size(); i++){
       			if (lists.get(i).isOpen){
         			lists.get(i).window.closeWindow();
         			lists.get(i).isOpen = false;
       			}
     		}
        	return;
  	}

  // handler for restore command
	private void restoreHandler(String[] argv){
		if (!isLoaded){
      			System.out.println("BAD INPUT");
      			return;
    		}
    		if (length == 1){
			currentImageRec.image.restoreOriginal();
      			System.out.println("OK");
		}else{
			System.out.println("BAD INPUT");
			return;
		}
		return;
	}
	
  // handler for negative command
	private void negativeHandler(String[] argv){
    		if (!isLoaded){
      			System.out.println("BAD INPUT");
      			return;
    		}
		if (length == 1){
			boolean succ = currentImageRec.image.negative();
      			if (succ){
				System.out.println("OK");
			}else{
				System.out.println("BAD INPUT");
				return;
			}
		}else if (length == 5){
			boolean succ = currentImageRec.image.negative(Integer.parseInt(argv[1]), Integer.parseInt(argv[2]), 
                                                    Integer.parseInt(argv[3]), Integer.parseInt(argv[4]));
			if (succ){
				System.out.println("OK");
			}else{
				System.out.println("BAD INPUT");
				return;
			}
		}else{
			System.out.println("BAD INPUT");
		}
		return;
	}
	
  // handler for close command
	private void closeHandler(String[] argv){
    		if (!isLoaded){
      			System.out.println("BAD INPUT");
      			return;
    		}
		if (lists.isEmpty()){
			System.out.println("BAD INPUT");
			return;
		}
		if (length == 1){
			currentImageRec.window.closeWindow();
       			currentImageRec.isOpen = false;
			System.out.println("OK");
		}else if (length == 2){
			int index = Integer.parseInt(argv[1]);
			if (index >= lists.size() || index < 0){
				System.out.println("BAD INPUT");
				return;
			}else{
				lists.get(index).window.closeWindow();
          			currentImageRec.isOpen = false;
				System.out.println("OK");
			}
		}else{
			System.out.println("BAD INPUT");
			return;
		}
		return;
	}
	
	
  // handler for display command
	private void displayHandler(String[] argv){
    		if (!isLoaded){
      			System.out.println("BAD INPUT");
      			return;
    		}
		if (lists.isEmpty()){
			System.out.println("BAD INPUT");
			return;
		}
		if (length == 1){
			currentImageRec.window.display();
			currentImageRec.isOpen = true;
			System.out.println("OK");
		}else if (length == 2 ){
			int index = Integer.parseInt(argv[1]);
			if (index >= lists.size() || index < 0){
				System.out.println("BAD INPUT");
				return;
			}else{
				lists.get(index).window.display();
				lists.get(index).isOpen = true;
				System.out.println("OK");
			}
		}else{
			System.out.println("BAD INPUT");
			return;
		}
		return;
	}
	
  // handler for size command
	private void sizeHandler(String[] argv){
    		if (!isLoaded){
      			System.out.println("BAD INPUT");
      			return;
    		}
		if ((length != 1) || (lists.isEmpty())){
			System.out.println("BAD INPUT");
			return;
		}else{
			int[] size = currentImageRec.image.getSize();
			System.out.println(size[0] + " " + size[1]);
			System.out.println("OK");
		}
		return;
	}
 
  // handler for current command	
	private void currentHandler(String[] argv){
		if ((length != 1) || (lists.isEmpty())){
			System.out.println("BAD INPUT");
			return;
		}else{
			int index = lists.indexOf(currentImageRec);
			System.out.println(index);
			System.out.println("OK");
		}
		return;
	}
	
  // handler for swtich command
	private void switchHandler(String[] argv){
    		if (!isLoaded){
      			System.out.println("BAD INPUT");
     			return;
   		}
		if (length != 2 || (lists.isEmpty())){
			System.out.println("BAD INPUT");
			return;
		}else{
			int index = Integer.parseInt(argv[1]);
			if (index >= lists.size() || index < 0){
				System.out.println("BAD INPUT");
				return;
			}else{
				currentImageRec = lists.get(index);
				System.out.println("OK");
			}
		}
		return;
	}
	
  // handler for list command
	private void listHandler(String[] argv){
		if (length != 1){
			System.out.println("BAD INPUT");
			return;
		}else{
			int listSize = lists.size();
			for (int i = 0; i < listSize; i++){
				String name = lists.get(i).filename;
				System.out.println(i + ": " + name);
			}
			System.out.println("OK");
		}
		return;
	}
	
  // handler for load command
	private void loadHandler(String[] argv){
		if (length != 2){
			System.out.println("BAD INPUT");
			return;
		}else{
			String tFilename = argv[1];
			BufferedImage img;
			try { 
				img = ImageIO.read(new File(tFilename));
				ImageRecord myRecord = new ImageRecord(img);
				this.isLoaded = true;
				myRecord.filename = tFilename;
				lists.add(myRecord);
				currentImageRec = myRecord;
        System.out.println("OK");
			}
			catch (IOException e){
				System.out.println("BAD INPUT");
				return;
			} 
		}
		return;
	}
	
}
