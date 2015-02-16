/***************
	The 3rd Program Assignment of CSE 11, winter 2015
	Written by Mingxuan Wang
***************/
/**************
	Name : Mingxuan Wang
	Email: miw072@eng.ucsd.edu
	ID: A53077257
**************/
/**
 * Provide a set of Array functions on an Array of ints
 * @author Mingxuan Wang
 * @version CSE11-Winter2015-PR3
 */
public class IntArray11
{
	private int[] intArray11;
	/** 
 	 * 0-argument constructor. Valid instance of an Array of int,
 	 * no ints are stored in the array.
	*/
	public IntArray11()
	{
		intArray11 = new int[0];
	}

	/** 
 	 * Store an array of size n. Initialize contents of the array to 
 	 * be 1..n 
 	 * @param size the number of elements to store in the array
	*/
	public IntArray11(int size)
	{
		intArray11 = new int[size];
		for (int i = 0; i < size; i++){
			intArray11[i] = i + 1;
		}
	}

	/** 
 	 * Create an array of size n and store a copy of the contents of the
 	 * input argument
 	 * @param intArray array of elements to copy 
	*/
	public IntArray11(int[] intArray)
	{
		intArray11 = new int[intArray.length];
		for (int i = 0; i < intArray.length; i++){
			intArray11[i] = intArray[i];
		}
	}

	/* Make a string representation */
	/**
	 * Pretty Print  -- Empty String "[]"
	 *                  else "[e1, e2, ..., en]"
	 */
	@Override
	public String toString()
	{
		String string = new String("[");
		String conc = ", ";
		for (int i = 0; i < intArray11.length; i++){
			string += String.valueOf(intArray11[i]);
			if (i != intArray11.length - 1){
				string += conc;
			}
		}
		string += "]";
		return string;
	}

	/* Getters and Setters */

	/** get the number of elements stored in the array  
	 * @return number of elements in the array
	*/
	public int getNelem()
	{
		return intArray11.length; 
	}
	/** get the Element at index  
	 * @param index of data to retrieve 
	 * @return element if index is valid else  return 
	 * 		Integer.MIN_VALUE
	*/
	public int getElement(int index)
	{
		if (index < intArray11.length && index >= 0){
			return intArray11[index];
		}else{
			return Integer.MIN_VALUE;
		}	
	}
	
	/** retrieve a copy of the stored Array
	 * @return a deep copy of the Array. A new int array should be
	 * 		constructed of the correct size and values should
	 * 		copied into it.  
	*/
	public int[] getArray()
	{
		int[] arrayCopy = new int[intArray11.length];
		for (int i = 0; i < intArray11.length; i++){
			arrayCopy[i] = intArray11[i];
		}
		return arrayCopy;
	}

	/** set the value of an element in the stored array
	 * @param index of element to store. Must be a valid index 
	 * @param element the data to insert in the array
	 * @return true if element set was successful
	*/
	public boolean setElement(int index, int element)
	{
		if (index < intArray11.length && index >= 0){
			intArray11[index] = element;
			return true;
		}
		return false;
	}

	/** Insert an element at index in the array
	 * @param index where to insert. Must be between 0 and number of
	 *              elements in the array
	 * @param element the data to insert in the array
	 * @return true if element insertion was successful
	*/
	public boolean insert(int index, int element)
	{  
    if (intArray11.length == 0 && index == 0){
      intArray11 = new int[1];
      intArray11[index] = element;
      return true;
    }
     
       
		if (index > intArray11.length || index < 0){
			return false;
		}else{
			int[] newArray = new int[intArray11.length + 1];
			for (int i = 0; i < newArray.length - 1; i++){
				newArray[i < index? i : i + 1] = intArray11[i];
			}
			newArray[index] = element;
			intArray11 = new int[newArray.length];
			for (int i = 0; i < newArray.length; i++){
				intArray11[i] = newArray[i];
			}
			return true;
		}
	}

	/** Delete and element at index
	 * @param index of element to delete 
	 * @return true if element deletion was successful, false otherwise
	*/
	public boolean delete(int index)
	{
		if (index >= intArray11.length || index < 0){
			return false;
		}else{
			int[] newArray = new int[intArray11.length - 1];
			for (int i = 0; i < index; i++){
				newArray[i] = intArray11[i];
			}
			for (int i = index; i < newArray.length; i++){
				newArray[i] = intArray11[i + 1];
			}
			intArray11 = new int[newArray.length];
			for (int i = 0; i < newArray.length; i++){
				intArray11[i] = newArray[i];
			}
			return true;
		}
	}

	/** reverse the order of the elements in the array 
	*/
	public void reverse()
	{
		for (int left = 0, right = intArray11.length - 1; left < right; left++, right--) {
			int temp = intArray11[left];
			intArray11[left]  = intArray11[right];
			intArray11[right] = temp;
		}
		return;
	}

	/** reverse the order of the elements in the array from start to
 	*   end index 
	*   @param start beginning index of to start the reverse
	*   @param end	ending index to end the reverse
	*   @return true if start and end index are valid, false otherwise
	*
	*/
	public boolean reverse(int start, int end)
	{
		if (start <= end && start >=0 && end >= 0 && start < intArray11.length && end < intArray11.length){
			for (int left = start, right = end; left < right; left++, right--) {
				int temp = intArray11[left];
				intArray11[left]  = intArray11[right];
				intArray11[right] = temp;
			}
			return true;
		}
		return false;
	}

	/** swap two elements in the array 
	*   @param index1 index of first element 
	*   @param index2 index of second element
	*   @return true if index1 and index2 are valid, false otherwise
	*
	*/
	public boolean swap(int index1, int index2)
	{
		if (index1 >=0 && index2 >= 0 && index1 < intArray11.length && index2 < intArray11.length){
			int temp = intArray11[index1];
			intArray11[index1] = intArray11[index2];
			intArray11[index2] = temp;
			return true;
		}
		return false;
	}
 
//  public static void main(String[] args){
//    IntArray11 test = new IntArray11(7);
//    test.swap(0,6);
//    for (int i = 0; i < test.getNelem(); i++){
//      System.out.println(test.getElement(i));
//    }
//  }
}
// vim: ts=4:sw=4:tw=78:
