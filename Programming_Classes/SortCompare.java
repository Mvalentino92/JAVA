import java.math.*;
import java.util.*;
public class SortCompare
{
	/*Partition function. Will partition around the specified element in place
	 * Needs the starting and last index (to simulate splitting up the array),
	 * Will return the index where the element of question was finally places.
	 * uses the last element as the partitioning element*/
	public static int partition(Integer[] arr, int start, int end)
	{
		Integer temp;
		while(start < end - 1)
		{
			//If leftmost is larger do a double swap
			if(arr[start].compareTo(arr[end]) > 0)
			{
				temp = arr[start];
				arr[start] = arr[end-1];
				arr[end-1] = arr[end];
				arr[end] = temp;
				end--;
			}
			//else just move start forward
			else start++;
		}
		//Swap once if applicable for last case
		if(arr[start].compareTo(arr[end]) > 0)
		{
			temp = arr[start];
			arr[start] = arr[end];
			arr[end] = temp;
			end--;
		}
		return end;
	}

	//Actual quickSort algorithm
	public static void QS(Integer[] arr,int start, int end)
	{
		//If base case return
		if(start >= end) return;
		
		//Otherwise call partition with the bounds,
		//get new bound, and recursively call quickSort	
		int newEnd = partition(arr,start,end);
		QS(arr,start,newEnd-1);
		QS(arr,newEnd+1,end);
	}

	//Helper function to call it
	public static void quickSort(Integer[] arr)
	{
		QS(arr,0,arr.length-1);
	}

	//Merge function
	public static Integer[] merge(Integer[] l, Integer[] r)
	{
		//Init indexes and return new Student[]
		Integer[] retval = new Integer[l.length + r.length];
		int i = 0;
		int j = 0;
		int index = 0;
		
		//Loop through and add values
		while(i < l.length && j < r.length)
		{
			if(l[i].compareTo(r[j]) <= 0) retval[index++] = l[i++];
			else retval[index++] = r[j++];
		}

		//Check to see who finished first, then add the rest of the other person
		if(i == l.length)
		{
			for(int k = j; k < r.length; k++) retval[index++] = r[k];;
		}
		else
		{
			for(int k = i; k < l.length; k++) retval[index++] = l[k];
		}

		//Return the new, and sorted ArrayList
		return retval;
	}
	
	//Performs the mergeSort algorithm
	public static Integer[] MS(Integer[] arr)
	{
		//Base case
		if(arr.length <= 1) return arr;

		//Partition into two arrays, and recursively call it
		int halfWay = arr.length/2;
		Integer[] l = new Integer[halfWay];
		Integer[] r = new Integer[arr.length - halfWay];

		//Set arrays
		int lDex = 0;
		int rDex = 0;
		for(int i = 0; i < halfWay; i++) l[lDex++] = arr[i];;
		for(int i = halfWay; i < arr.length; i++) r[rDex++] = arr[i];

		//Recursively call mergesort
		return merge(MS(l),MS(r));
	}

	//Merges for Iterative MergeSort
	public static void iMerge(Integer[] l, Integer[] r, Integer[] arr, int index)
	{
		//Init indexes
		int i = 0;
		int j = 0;
		
		//Loop through and add values
		while(i < l.length && j < r.length)
		{
			if(l[i].compareTo(r[j]) <= 0) arr[index++] = l[i++];
			else arr[index++] = r[j++];
		}

		//Check to see who finished first, then add the rest of the other person
		if(i == l.length)
		{
			for(int k = j; k < r.length; k++) arr[index++] = r[k];;
		}
		else
		{
			for(int k = i; k < l.length; k++) arr[index++] = l[k];
		}
		return;
	}

	//Iterative mergesort function
	public static void iMergeSort(Integer[] arr)
	{
		//Power of 2 to iterate by, and length of array, and half + 1.
		int pow2 = 1;
		int length = arr.length;

		//Iterate in powers of 2 while those powers are less than the length/2 + 1
		while(pow2 < length/2 + 1)
		{
			//Calculate the remainder (r) and the bound
			int rem = length % (pow2*2);
			int bound = length - rem;

			//Begin to iterate the array in strides of the pow2*2
			int i;
			for(i = 0; i < bound; i += pow2*2)
			{
				//Left
				Integer[] l = new Integer[pow2];
				for(int j = 0; j < l.length; j++) l[j] = arr[i+j];

				//Right
				Integer[] r = new Integer[pow2];
				for(int k = 0; k < r.length; k++) r[k] = arr[i+k+pow2];

				//Get merged
				iMerge(l,r,arr,i);
			}
			/*Handle if there is remainders or not*/
			pow2 <<= 1;
			i -= pow2;
			if(rem == 0) continue;

			//Left
			Integer[] l = new Integer[pow2];
			for(int j = 0; j < l.length; j++) l[j] = arr[i+j];

			//Right
			Integer[] r = new Integer[rem];
			for(int k = 0; k < r.length; k++) r[k] = arr[i+k+pow2];

			//Get merged
			iMerge(l,r,arr,i);
		}
	}

	public static void main(String[] args)
	{
		//Compare all 4 implementations of the sorting algorithms
		int[] inputs = {10,100,1000,10000,32768,100000,1000000,(int)(Math.pow(2,21)),
			       (int)(Math.pow(2,22)),(int)(Math.pow(2,23)),10000000};
		for(int i = 0; i < inputs.length; i++)
		{
			boolean doInsertion = i < 5 ? true : false;
			//Get array of numbers to be sorted
			Integer[] test = new Integer[inputs[i]];
			Integer[] TRUE = new Integer[test.length];
			Integer[] qs = new Integer[test.length];
			Integer[] rm = new Integer[test.length];
			Integer[] im = new Integer[test.length];
			MyLinkedList<Integer> li = new MyLinkedList<>();
			for(int j = 0; j < test.length; j++)
			{
				test[j] = Integer.valueOf((int)(Math.random()*(test.length/3)));
				TRUE[j] = test[j];
				qs[j] = test[j];
				rm[j] = test[j];
				im[j] = test[j];
				if(doInsertion) li.add(test[j]);
			}
			//Javas Arrays.sort()
			double javaStart = System.nanoTime();
			Arrays.sort(TRUE);
			double javaTime = (System.nanoTime() - javaStart)/1e9;

			//Recursive QuickSort
			double qsStart = System.nanoTime();
			quickSort(qs);
			double qsTime = (System.nanoTime() - qsStart)/1e9;

			//MergeSort Recursive
			double rmStart = System.nanoTime();
			rm = MS(rm);
			double rmTime = (System.nanoTime() - rmStart)/1e9;

			//MergeSort Iterative
			double imStart = System.nanoTime();
			iMergeSort(im);
			double imTime = (System.nanoTime() - imStart)/1e9;

			//Heap
			Heap<Integer> heap = new Heap<>();
			Integer[] sortedHeap = new Integer[test.length];
			double heapStart = System.nanoTime();
			for(int k = 0; k < test.length; k++) heap.add(test[k]);
			for(int k = 0; k < test.length; k++) sortedHeap[test.length - 1 - k] = heap.remove();
			double heapTime = (System.nanoTime() - heapStart)/1e9;

			//Peap
			Peap<Integer> peap = new Peap<>();
			Integer[] sortedPeap = new Integer[test.length];
			double peapStart = System.nanoTime();
			for(int k = 0; k < test.length; k++) peap.add(test[k]);
			for(int k = 0; k < test.length; k++) sortedPeap[test.length - 1 - k] = peap.remove();
			double peapTime = (System.nanoTime() - peapStart)/1e9;

			//Linked Insertion Sort
			double liTime = 0;
			if(doInsertion)
			{
				double liStart = System.nanoTime();
				li.iSort();
				liTime = (System.nanoTime() - liStart)/1e9;
			}

			//Check if all correct before printing
			for(int j = 0; j < TRUE.length; j++)
			{
				Integer x = doInsertion ? li.get(j) : TRUE[j];
				if(TRUE[j].compareTo(qs[j]) != 0 ||
			           TRUE[j].compareTo(rm[j]) != 0 ||
				   TRUE[j].compareTo(im[j]) != 0 ||
				   TRUE[j].compareTo(sortedHeap[j]) != 0 ||
				   TRUE[j].compareTo(sortedPeap[j]) != 0 ||
				   TRUE[j].compareTo(x) != 0)
				{
					System.out.println("SORTED INCORRECTLY!");
					System.exit(1);
				}
			}

			//Print the time for all if correct
			System.out.println("Sorting "+test.length+" elements: ");
			System.out.println("Java's Arrays.sort() took "+javaTime+" seconds.");
			System.out.println("Recursive QuickSort took "+qsTime+" seconds.");
			System.out.println("Recursive MergeSort took "+rmTime+" seconds.");
			System.out.println("Iterative MergeSort took "+imTime+" seconds.");
			System.out.println("HeapSort took "+heapTime+" seconds.");
			System.out.println("PeapSort took "+peapTime+" seconds.");
			if(doInsertion) 
			{
				System.out.println("Linked InsertionSort took "+liTime+" seconds.\n");
			}
			else System.out.println();
		}
	}
}
