/**
 * This is a JUnit tester designed to test the functionality of the
 * IntArray11 class for Professor Papadopoulos' CSE 11 class, Winter 15.
 * You can use this tester to test your implementation of the IntArray11
 * methods before proceeding to write ArrayPlay.java.
 *
 * The easiest way to run this tester is on the ieng6 servers - to do this,
 * either use a computer from the CSE basement or SSH into the ieng6 servers
 * from your personal computer.
 *
 * Then, follow the instructions below. The '$' precedes a command that you
 * should enter into the terminal, but make sure to NOT type the '$'.
 *
 *    1) Place this file in the same folder as your IntArray11.java
 *    2) Compile this tester
 * 
 *       $ javac -cp /usr/share/java/junit.jar:. IntArrayTest.java
 * 
 *    3) Run this tester
 * 
 *       $ java -cp /usr/share/java/junit.jar:. junit.textui.TestRunner IntArrayTest
 * 
 *    4) Check the results of your test from the output
 */

import junit.framework.*;
import java.util.Arrays;

public class IntArrayTest extends TestCase {
   public static final int SIZE = 10;

   // initially [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
   private IntArray11 array;

   // initially []
   private IntArray11 empty;

   // IntArray11 copy of [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
   private IntArray11 copy;

   // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
   private int[] expected;

   /**
    * Initializes the IntArray11's and arrays to their expected values.
    * This is run once (before) for each test case below.
    * USES: all IntArray11 constructors
    */
   protected void setUp() {
      array = new IntArray11(SIZE);
      empty = new IntArray11();
      expected = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
      copy = new IntArray11(expected);
   }

   /** 
    * Check if initial sizes are correct.
    * USES: getNelem()
    */
   public void testInitialSizes() {
      assertEquals("Initial array size is not 10.", SIZE, array.getNelem());
      assertEquals("Empty array size is not 0.", 0, empty.getNelem());
      assertEquals("Copy array size is not 10.", SIZE, copy.getNelem());
   }

   /**
    * Check if the correct element is returned.
    * USES: getElement()
    */
   public void testGetElement() {
      // Invalid indices for getElement() should return Integer.MIN_VALUE
      assertEquals("Invalid index did not return MIN_VALUE.",
         Integer.MIN_VALUE, array.getElement(-1));
      assertEquals("Invalid index did not return MIN_VALUE.",
         Integer.MIN_VALUE, array.getElement(SIZE));
      assertEquals("Invalid index did not return MIN_VALUE.",
         Integer.MIN_VALUE, empty.getElement(-1));
      assertEquals("Invalid index did not return MIN_VALUE.",
         Integer.MIN_VALUE, empty.getElement(0));

      // Check getElement() for all valid indices
      for (int i = 0; i < SIZE; i++)
         assertEquals("Element at index " + i + " is incorrect: " +
            "expected " + expected[i] + ", got " + array.getElement(i) + ".", 
            expected[i], array.getElement(i));
   }

   /**
    * Check if the correct array (with correct values in correct order) 
    * is returned.
    * USES: getArray()
    */
   public void testGetArray() {
      assertTrue("Array returned is not correct.", 
         Arrays.equals(expected, array.getArray()));

      assertTrue("Array returned is not empty.",
         Arrays.equals(new int[] {}, empty.getArray()));

      assertTrue("Copy array is not equal to original.",
         Arrays.equals(expected, copy.getArray()));
   }

   /**
    * Check if the correct string representation of the array is returned.
    * USES: toString()
    */
   public void testToString() {
      assertEquals("toString returns incorrect string.",
         "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", array.toString());

      assertEquals("toString on empty array incorrect.",
         "[]", empty.toString());
   }

   /**
    * Check if invalid inserts (passing in invalid indices) return false
    * and leave the array unmodified.
    * USES: insert(), getNelem()
    */
   public void testInvalidInserts() {
      assertFalse("Invalid insert returned true.", array.insert(-1, 42));
      assertFalse("Invalid insert returned true.", array.insert(SIZE + 1, 42));

      assertEquals("Array size has changed after invalid inserts.",
         SIZE, array.getNelem());

      assertFalse("Invalid insert into empty array returned true.", empty.insert(-1, 42));
      assertFalse("Invalid insert into empty array returned true.", empty.insert(1, 42));

      assertEquals("Empty array has changed after invalid inserts.",
         0, empty.getNelem());
   }

   /**
    * Check if valid inserts correctly increase the array size by 1, inserts
    * the correct element in the correct location, and shifts the other values
    * if necessary.
    * USES: insert(), getNelem(), getElement(), getArray()
    */
   public void testValidInserts() {
      // Array initially empty
      assertEquals("Array not initially empty.", 0, empty.getNelem());
      // Array at this point: []

      // Test inserting "1"
      assertTrue("Insert of 1 not successful.", empty.insert(0, 1));
      assertEquals("Array size not updated after insert.", 1, empty.getNelem());
      assertEquals("1 is not inserted correctly.", 1, empty.getElement(0));
      assertTrue("Array does not have correct values in order.",
         Arrays.equals(new int[] {1}, empty.getArray()));
      // Array at this point: [1]

      // Test inserting "2" to the END
      assertTrue("Insert of 2 not successful.", empty.insert(1, 2));
      assertEquals("Array size not updated after insert.", 2, empty.getNelem());
      assertEquals("2 is not inserted correctly.", 2, empty.getElement(1));
      assertTrue("Array does not have correct values in order.",
         Arrays.equals(new int[] {1, 2}, empty.getArray()));
      // Array at this point: [1, 2]

      // Test inserting "3" to the BEGINNING
      assertTrue("Insert of 3 not successful.", empty.insert(0, 3));
      assertEquals("Array size not updated after insert.", 3, empty.getNelem());
      assertEquals("3 is not inserted correctly.", 3, empty.getElement(0));
      assertTrue("Array does not have correct values in order.",
         Arrays.equals(new int[] {3, 1, 2}, empty.getArray()));
      // Array at this point: [3, 1, 2]

      // Test inserting "4" to somewhere in the MIDDLE
      assertTrue("Insert of 4 not successful.", empty.insert(1, 4));
      assertEquals("Array size not updated after insert.", 4, empty.getNelem());
      assertEquals("4 is not inserted correctly.", 4, empty.getElement(1));
      assertTrue("Array does not have correct values in order.",
         Arrays.equals(new int[] {3, 4, 1, 2}, empty.getArray()));
      // Array at this point: [3, 4, 1, 2]
   }

   /**
    * Checks if setting an element at an invalid index leaves the array unchanged.
    * USES: setElement(), getArray()
    */
   public void testInvalidSetElement() {
      assertFalse("Setting at index -1 was successful.", array.setElement(-1, 123));
      assertTrue("Array changed after an invalid set",
         Arrays.equals(expected, array.getArray()));

      assertFalse("Setting at SIZE was successful.", array.setElement(SIZE, 123));
      assertTrue("Array changed after an invalid set.",
         Arrays.equals(expected, array.getArray()));

      assertFalse("Set element of empty array succeeded.", empty.setElement(0, 123));
      assertTrue("Empty array changed after an invalid set.",
         Arrays.equals(new int[] {}, empty.getArray()));
   }

   /**
    * Checks if setting an element at a valid index correctly alters the array.
    * USES: setElement(), getElement(), getNelem(), getArray()
    */
   public void testValidSetElement() {
      // Set element of BEGINNING
      assertTrue("setElement with valid index 0 returns false.",
         array.setElement(0, 11));
      assertEquals("Element at index 0 did not change.",
         11, array.getElement(0));
      assertEquals("Array size changed after setElement.",
         SIZE, array.getNelem());

      // Set element of MIDDLE
      assertTrue("setElement with valid index 4 returns false.",
         array.setElement(4, 55));
      assertEquals("Element at index 4 did not change.",
         55, array.getElement(4));
      assertEquals("Array size changed after setElement.",
         SIZE, array.getNelem());

      // Set element of END
      assertTrue("setElement with valid index (SIZE - 1) returns false.",
         array.setElement(SIZE - 1, 1010));
      assertEquals("Element at index (SIZE - 1) did not change.",
         1010, array.getElement(SIZE - 1));
      assertEquals("Array size changed after setElement.",
         SIZE, array.getNelem());

      // Check final array
      assertTrue("Array is incorrectly changed after multiple setElement calls.",
         Arrays.equals(
            new int[] {11, 2, 3, 4, 55, 6, 7, 8, 9, 1010}, array.getArray()));
   }

   /**
    * Checks that deleting an element at an invalid index does not change the array.
    * USES: delete(), getNelem()
    */
   public void testInvalidDelete() {
      assertFalse("Delete with index -1 returned true.", array.delete(-1));
      assertEquals("Invalid delete changed the array's size.", SIZE, array.getNelem());

      assertFalse("Delete with index SIZE returned true.", array.delete(SIZE));
      assertEquals("Invalid delete changed the array's size.", SIZE, array.getNelem());

      assertFalse("Deleting at index 0 of empty array returned true.", empty.delete(0));
      assertEquals("Delete on empty array changed its size.", 0, empty.getNelem());
   }

   /**
    * Check that deleting elements from the array changes the array correctly.
    * USES: delete(), getNelem(), getArray(), constructor IntArray(int)
    */
   public void testValidDelete() {
      // Delete from BEGINNING
      assertTrue("Valid delete of front returned false.", array.delete(0));
      assertEquals("Size did not change after delete.", SIZE - 1, array.getNelem());
      assertTrue("Array is not correctly changed after valid delete.",
         Arrays.equals(new int[] {2, 3, 4, 5, 6, 7, 8, 9, 10}, array.getArray()));
      // Array at this point: [2, 3, 4, 5, 6, 7, 8, 9, 10]

      // Delete from END
      assertTrue("Valid delete of end returned false.", array.delete(array.getNelem() - 1));
      assertEquals("Size did not change after delete.", SIZE - 2, array.getNelem());
      assertTrue("Array is not correctly changed after valid delete.",
         Arrays.equals(new int[] {2, 3, 4, 5, 6, 7, 8, 9}, array.getArray()));
      // Array at this point: [2, 3, 4, 5, 6, 7, 8, 9]

      // Delete from MIDDLE
      assertTrue("Valid delete of middle returned false.", array.delete(3));
      assertEquals("Size did not change after delete.", SIZE - 3, array.getNelem());
      assertTrue("Array is not correctly changed after valid delete.",
         Arrays.equals(new int[] {2, 3, 4, 6, 7, 8, 9}, array.getArray()));
      // Array at this point: [2, 3, 4, 6, 7, 8, 9]

      // Test delete from array of size 1: [1]
      array = new IntArray11(1);
      assertTrue("Valid delete of size 1 array.", array.delete(0));
      assertEquals("Size did not change after delete.", 0, array.getNelem());
      assertTrue("Array is not correctly changed after valid delete.",
         Arrays.equals(new int[] {}, array.getArray()));
      // Array at this point: [0]
   }

   /**
    * Checks that reversing an array with invalid ranges does not modify the array.
    * USES: reverse(int, int), getArray()
    */
   public void testInvalidReverse() {
      // Both start and end indices are invalid
      assertFalse("Reverse with invalid ranges returned true.", array.reverse(-1, SIZE));

      // Only start index is invalid
      assertFalse("Reverse with invalid ranges returned true.", array.reverse(-1, 0));

      // Only end index is invalid
      assertFalse("Reverse with invalid ranges returned true.", array.reverse(0, SIZE));

      // Ensure array is unchanged
      assertTrue("Array was changed after invalid reverse calls.",
         Arrays.equals(expected, array.getArray()));
   }

   /**
    * Reverses an empty array.
    * USES: reverse(), getNelem()
    */
   public void testReverseEmpty() {
      empty.reverse();
      assertEquals("Empty array was modified after another reverse.", 0, empty.getNelem());
   }

   /**
    * Checks if reverse correctly reverses some part of the array.
    * USES: reverse(int, int), getArray()
    */
   public void testValidReverse() {
      // Reverse all but start and end
      assertTrue("Valid reverse returned false.", array.reverse(1, 8));
      assertTrue("Array was not changed correctly after reverse(1, 8).",
         Arrays.equals(new int[] {1, 9, 8, 7, 6, 5, 4, 3, 2, 10}, array.getArray()));

      // Reverse entire array
      assertTrue("Valid reverse returned false.", array.reverse(0, 9));
      assertTrue("Array was not changed correctly after reverse(0, 9).",
         Arrays.equals(new int[] {10, 2, 3, 4, 5, 6, 7, 8, 9, 1}, array.getArray()));

      // Reverse segment containing start
      assertTrue("Valid reverse returned false.", array.reverse(0, 7));
      assertTrue("Array was not changed correctly after reverse(0, 7).",
         Arrays.equals(new int[] {8, 7, 6, 5, 4, 3, 2, 10, 9, 1}, array.getArray()));

      // Reverse segment containing end
      assertTrue("Valid reverse returned false.", array.reverse(4, 9));
      assertTrue("Array was not changed correctly after reverse(4, 9).",
         Arrays.equals(new int[] {8, 7, 6, 5, 1, 9, 10, 2, 3, 4}, array.getArray()));

      // Reverse only 1 index (does not change the array)
      assertTrue("Valid reverse returned false.", array.reverse(5, 5));
      assertTrue("Array was not changed correctly after reverse(5, 5).",
         Arrays.equals(new int[] {8, 7, 6, 5, 1, 9, 10, 2, 3, 4}, array.getArray()));
   }

   /**
    * Checks if reversing an array twice restores it to the original array.
    * USES: reverse(), getArray()
    */
   public void testDoubleReverse() {
      array.reverse();
      assertTrue("Reversing a size 10 array did not reverse it.", 
         Arrays.equals(new int[] {10, 9, 8, 7, 6, 5, 4, 3, 2, 1}, array.getArray()));

      array.reverse();
      assertTrue("Reversing a size 10 array (AGAIN) restored it to normal.",
         Arrays.equals(expected, array.getArray()));
   }

   /**
    * Ensures that swaps with invalid ranges return false and do not 
    * alter the array.
    * USES: swap(), getArray()
    */
   public void testInvalidSwap() {
      // Both start and end are invalid
      assertFalse("Invalid swap returned true.", array.swap(-1, SIZE));

      // Only start is invalid
      assertFalse("Invalid swap returned true.", array.swap(-1, 0));

      // Only end is invalid
      assertFalse("Invalid swap returned true.", array.swap(0, SIZE));

      // Ensure array is unchanged
      assertTrue("Array was changed after invalid swap calls.",
         Arrays.equals(expected, array.getArray()));
   }

   /**
    * Checks that valid swaps correctly change the array.
    * USES: swap(), getArray()
    */
   public void testValidSwap() {
      // Swap begin and end
      assertTrue("Valid swap returned false.", array.swap(0, 9));
      assertTrue("Array was not changed correctly after swap(0, 9).",
         Arrays.equals(new int[] {10, 2, 3, 4, 5, 6, 7, 8, 9, 1}, array.getArray()));

      // Swap begin and some other non-end index
      assertTrue("Valid swap returned false.", array.swap(0, 4));
      assertTrue("Array was not changed correctly after swap(0, 4).",
         Arrays.equals(new int[] {5, 2, 3, 4, 10, 6, 7, 8, 9, 1}, array.getArray()));

      // Swap end and some other non-begin index
      assertTrue("Valid swap returned false.", array.swap(7, 9));
      assertTrue("Array was not changed correctly after swap(7, 9).",
         Arrays.equals(new int[] {5, 2, 3, 4, 10, 6, 7, 1, 9, 8}, array.getArray()));

      // Swap two distinct indices (both are not start or end index)
      assertTrue("Valid swap returned false.", array.swap(2, 6));
      assertTrue("Array was not changed correctly after swap(2, 6).",
         Arrays.equals(new int[] {5, 2, 7, 4, 10, 6, 3, 1, 9, 8}, array.getArray()));

      // Swap same index (does not change the array)
      assertTrue("Valid swap returned false.", array.swap(8, 8));
      assertTrue("Array was not changed correctly after swap(8, 8).",
         Arrays.equals(new int[] {5, 2, 7, 4, 10, 6, 3, 1, 9, 8}, array.getArray()));
   }
}
