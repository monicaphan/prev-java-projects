// Name: Bao-Tran Phan
// Class: CS 3345
// Section: 003
// Semester: Spring 2018
// Project 5: Write a java program to sort a list of integers using ‘in place’ 
//            Quicksort algorithm. Generate the list randomly every time using 
//            the java.util.Random class.

// import necessary libraries:
import java.time.Duration;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class Quicksort {
	
	// method swap(): switches the location of two elements with each other 
	public static void swap (int a, int b, int [] array) {
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
		
	} // end swap method
	
	// method printArray(): prints the sorted array out TO THE SYSTEM
	static void printArray(int [] sortedArr) {
		System.out.println("The sorted array is: ");
		for (int i = 0; i < sortedArr.length; i ++)
		{
			System.out.print(sortedArr[i]+" ");
		}
	} // end method: printArray()
	
	
	/* method QSFE(): Quicksort using the first element as the pivot point
	   Parameters: int array, int left (start of array) and int right (end of array)*/
	public static void QSFE (int [] array, int left, int right)
	{
		int i = left;
		int k = right;
		int pivot = array[left]; 	// set the pivot as the first element
		
		if (right - left >= 1)
		{
			while (k>i) {
				while (array[i] <= pivot && i <= right && k >i) 
					i++;
				while (array[k] > pivot && k >= left && k >=i) 
					k--;
				if (k>1)
					swap(i,k,array);
			} // end while
			swap(left, k, array);
			QSFE(array, left, k-1);
			QSFE (array,k+1,right);
		} // end if
		else
			return; 
	} // end method QSFE
	
	/* method QSRE(): quicksort choosing a random element to be a point
	 * Parameters: int array, int left (start of array) and int right 
	 * (end of array)*/
	public static void QSRE(int [] array, int left, int right) {
		// return if empty
		if(left-right <=0)
			return;
		else {
			Random rPivot = new Random(); // choose a random element
			int pivotLim = left + rPivot.nextInt(right - left + 1);
			swap(pivotLim,right, array);
			
			int pivot = array[right];
			int position = adjustPosition (array,left,right, pivot);
			// sort recursively
			QSRE(array,left, position-1);
			QSRE(array, position+1 , right);
		} // end else 
	} // end method QSRE
	
	/* method: adjustPosition()
	 * helps method QSRE() to choose a random element to be used as a pivot point
	 * Paramters: int [], int left (left bound, )int right (right bound)
	 * long pivot 
	 */
	public static int adjustPosition(int[] array, int left, int right, long pivot) {
		int lPointer = left-1;
		int rPointer = right;
		while (true) {
			while (array[++lPointer] < pivot);
			while (rPointer > 0 && array[--rPointer] > pivot);
			if (lPointer >- rPointer)
				break;
			else
				swap(lPointer,rPointer,array);
		} // end while
		swap (lPointer, right, array);
		return lPointer;
	} // end method adjustPosition
	
	
	/* method QSMedREInit: first part of QSMedRE quicksort method to 
	 * copy the array to choose random elements of the array
	 * Parameters: int array, int left (start of array) and int 
	 * right (end of array)*/
	public static void QSMedREInit(int [] array, int left, int right) {
		// make a copy of the unsorted array
		ArrayList<Integer> sol = new ArrayList<Integer>();
		int length = array.length;
		for (int i = 0; i < length; i++) {
			sol.add(array[i]);
		} // end for
		Collections.shuffle(sol);
		for (int i = 0; i < sol.size();i++) {
			array[i] = sol.get(i).intValue();
		} // end for 
		QSMedRE(array,left, right);
	} // end method QSRE
	
	/* method QSMedRE: QuickSort Median Random Elements... quicksort method
	 *  that Chooses the median of 3 randomly chosen elements as the pivot
	 */
	public static void QSMedRE(int[] array, int left, int right) {
		// if the array is sorted, return
		if (left >= right) {
			return;
		} // end if 
		int i = left; 
		int k = right; 
		
		// choosing the 3 elements and determine the median
		int first = left;
		int second, third;
		if (left < right)	// choosing the second element
			second = left + 1;
		else
			second = right;
		if (left+1 < right)
			third = left + 1;
		else
			third = right;
		int pivot = Math.max(Math.min(array[first], array[second]), 
				             Math.min(Math.max(array[first],array[second]), 
				             array[third]));
		// swapping to sort
		while (i <= k) {
			while (array[i] < pivot)
				i++;
			while (array[k] > pivot)
				k --;
			if (i<=k ) {
				swap ( i, k,array);
				i++;
				k--;
			} // end if
		} // end while
		// recursively sort
		if (left < right) 
			QSMedRE(array, left, right-1);
		
		if (right < left) 
			QSMedRE (array, left+1,right);
	} // end method QSMedRE()
	
	/* method: QSBT(): QuickSortBookTechnique
	 * quicksort using the book technique for choosing the pivot
	 * in which the pivot is the Median of first center and last element 
	 * Parameters: int array, int left (start of array) and int right (end of array)*/
	public static void QSBT(int [] array, int left, int right) {
		// return if the array is already sorted
		if (left >= right)
			return;
		
		int i = left; 
		int k = right;
		
		// establish pivot choice bounds
		int first = left;
		int second = (left + right )/2;
		int third;
		if (left+1 < right)
			third = left+2;
		else
			third = right;
		
		// establish the pivot
		int pivot = Math.max(Math.min(array[first], array[second]), 
				    Math.min(Math.max(array[first],array[second]),
				    	array[third]));
		
		// quick sort swap 
		while (i<=k) {
			while (array[i] < pivot)
				i++;
			while (array[k] > pivot)
				k--;
			if (i <= k) {
				swap (i,k,array);
				i++;
				k--;
			} // end if
		} // end while
		
		// sort recursively 
		if (left < right)
			QSBT (array, left, right-1);
		if (right < left)
			QSBT(array, left+1, right);
	} // end method QSBT
		
	
	public static void main (String[] args) throws FileNotFoundException{
		
		 // have user input the size of the array they would like
		Scanner reader = new Scanner (System.in);
		System.out.println("Enter array size: ");
		int arraySize = reader.nextInt();
		
		// create output file to print unsorted array to
	    PrintWriter outputUnsorted = new PrintWriter("unsorted.txt");

		// generate array of randomized numbers 
		System.out.println("Unsorted Array: ");
		int unsortedArr[] = new int[arraySize];
		Random rand = new Random(); 		
		 for (int i = 0; i < arraySize; ++i) {
			 unsortedArr[i] = rand.nextInt();
			 outputUnsorted.print(unsortedArr[i] + "  " );	// print list to output file 
	      } // end for 
		 
		 outputUnsorted.close();	// closes out the writer 
		 
		 // prints out unsorted array to console for testing
		  System.out.println(Arrays.toString(unsortedArr));
		
		 // create an output file for the sorted arrays to go to
		 PrintWriter outputSorted = new PrintWriter("sorted.txt");
		 
		 System.out.println("Enter which quicksort method you would like to perform.");
		 System.out.print("1. Quick sort with the first element as the pivot"+"\n"+ 
				 		  "2. Quick sort with randomly choosing pivot element:"+"\n"+
				 		  "3. Quick sort with choosing the median of 3 randomly chosen elements as the pivot:"+"\n"+
				 		  "4. Quick sort with choosing the median of first center and last element:"+"\n");
		 int choice = reader.nextInt();
		 reader.close(); 
		 
		 switch(choice) {
		 case 1:{
			 System.out.println("Performing Quicksort method 1");
			 long startTime = System.nanoTime();
			 
			 // sort
			 QSFE(unsortedArr, 0 , arraySize-1);
			 
			 // display time to system
			 long finishTime = System.nanoTime();
			 Duration elapsedTime = Duration.ofNanos(finishTime - startTime);
		     System.out.println("Timing for this sort is: " + elapsedTime);
			 
		     // print sorted array to system
		     System.out.println("The sorted array is: " + Arrays.toString(unsortedArr));
			
		     // print to output file
		     outputSorted.print(Arrays.toString(unsortedArr));
		     outputSorted.close();	// closes out the writer 
			 break;
		 } // end case 1
		
		 case 2: {
			 System.out.println("Performing Quicksort method 2");
			 long startTime = System.nanoTime();
			 // sort
			 QSRE(unsortedArr,0, arraySize-1);
			 // display time
			 long finishTime = System.nanoTime();
			 Duration elapsedTime = Duration.ofNanos(finishTime - startTime);
		     
			 System.out.println("Timing for this sort is: " + elapsedTime);
			// print sorted array to system
		     System.out.println("The sorted array is: " + Arrays.toString(unsortedArr));
		     
		  // print to output file
		     outputSorted.print(Arrays.toString(unsortedArr));
		     outputSorted.close();	// closes out the writer 
			 break;
		 } // end case 2
		 case 3: {
			 System.out.println("Performing Quicksort method 3");
			 long startTime = System.nanoTime();
			 QSMedRE(unsortedArr,0, arraySize-1);
			 long finishTime = System.nanoTime();
			 Duration elapsedTime = Duration.ofNanos(finishTime - startTime);
		     
			 System.out.println("Timing for this sort is: " + elapsedTime);
		
			 // print sorted array to system
		     System.out.println("The sorted array is: " + Arrays.toString(unsortedArr));
		 
		     // print to output file
		     outputSorted.print(Arrays.toString(unsortedArr));
		     outputSorted.close();	// closes out the writer 
		     
			 break;
		 } // end case 3
		
		 case 4:{
			 System.out.println("Performing Quicksort method 4");
			 long startTime = System.nanoTime();
			 QSBT(unsortedArr,0, arraySize-1);
			 long finishTime = System.nanoTime();
			 Duration elapsedTime = Duration.ofNanos(finishTime - startTime);
		     System.out.println("Timing for this sort is: " + elapsedTime);
		  // print sorted array to system
		     System.out.println("The sorted array is: " + Arrays.toString(unsortedArr));
		    
		  // print to output file
		     outputSorted.print(Arrays.toString(unsortedArr));
		     outputSorted.close();	// closes out the writer 
		     
		     break;
		 } // end case 4
		 
		 default: System.out.println("Enter either 1, 2, 3 or 4.");
	} // end switch ()
	} // end main
} // end class QuickSort
