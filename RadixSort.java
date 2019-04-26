package snippet;

import java.util.ArrayList;
import java.util.List;

public class RadixSort 
{
		public static void sort(int[] num)
		{
			final int RADIX = 10; // SET LENGTH OF RADIX ARRAY change as necessary 
			List<Integer>[] bucket = new ArrayList[RADIX];
			 
			  for (int i = 0; i < bucket.length; i++) 
			  {
			    bucket[i] = new ArrayList<Integer>();
			  } // end for
			 
			  boolean maxLength = false;
			  int tmp = -1, placement = 1;
			  
			  while (!maxLength) 
			  {
			    maxLength = true;
			    for (Integer i : num) 
			    {
			      tmp = i / placement;
			      bucket[tmp % RADIX].add(i);
			      if (maxLength && tmp > 0) 
			      {
			        maxLength = false;
			      } // end if 
			    } // end for
			    int a = 0;
			    for (int b = 0; b < RADIX; b++) 
			    {
			      for (Integer i : bucket[b]) 
			      {
			        num[a++] = i;
			      } // end for 
			      bucket[b].clear();
			    } // end for 
			    placement *= RADIX;
			  } // end while 
			} // end sort(int[])
  
		public static void main(String[] args) 
		{
	        int []intArray={67,63,93,61,57,70,34,50,43,86}; // initialize array to sort
	        
	        long startTime = System.nanoTime(); // start time
	        sort(intArray);						// sort array
	        long endTime   = System.nanoTime(); // end time
	        
	        System.out.println("Done with radix sort."); // tell user sorting is done
	        
	        // print the sorted array
	        for(int i=0;i<intArray.length;i++)
	        {
	            System.out.print(intArray[i] + " ");
	        } // end for
	        System.out.println(" "); 
	        
	        long totalTime = endTime - startTime; // calculate time it took to sort
	        System.out.println("Time to sort:" + totalTime + " ns"); 
	        
	    } // end main(String[])
} // end class RadixSort
