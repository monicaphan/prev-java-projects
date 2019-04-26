package snippet;

public class HeapSort 
{
    private static int[] someArray;
    private static int n;
    private static int left;
    private static int right;
    private static int largest;

    
    public static void buildheap(int []someArray)
    {
        n=someArray.length-1;
        for(int i=n/2 ; i>=0 ; i--)
        {
            maxheap(someArray,i);
        } // end for 
    } // end buildheap(int[])
    
    public static void maxheap(int[] someArray, int i)
    { 
        left=2*i;
        right=2*i+1;
       
        if(left <= n && someArray[left] > someArray[i])
        {
            largest=left;
        } // end if
        else
        {
            largest=i;
        } // end else 
        
        if(right <= n && someArray[right] > someArray[largest])
        {
            largest=right;
        } // end if 
        if(largest!=i)
        {
            exchange(i,largest);
            maxheap(someArray, largest);
        } // end if 
    } // end maxheap(int[], int)
    
    public static void exchange(int i, int j)
    {
        int temp = someArray[i];
        someArray[i] = someArray[j];
        someArray[j] = temp ; 
     } // end exchange 
    
    public static void sort(int []otherArray)
    {
    	someArray = otherArray;
        buildheap(someArray);
        
        for(int i=n;i>0;i--)
        {
            exchange(0, i);
            n=n-1;
            maxheap(someArray, 0);
        } // end for 
    } // end sort 
  
    public static void main(String[] args)
    {
    	int []intArray={2,3,4,2,5,2,4,5,7,4,3}; // initialize array 
        
    	long startTime = System.nanoTime(); // start time (ns)
        sort(intArray);						// sort array 
        long endTime   = System.nanoTime(); // end time (ns)
        
        // display sorted array 
        for(int i=0;i<intArray.length;i++)
        {
            System.out.print(intArray[i] + " ");
        } // end for 
        
        System.out.println("Done with heap sort.");
        
        long totalTime = endTime - startTime; // calculate time to sort 
        System.out.println("Time to Sort:" +  totalTime + " ns"); // display time to sort in ns 
        
    } // end main(Stirng[])
} // end class HeapSort
