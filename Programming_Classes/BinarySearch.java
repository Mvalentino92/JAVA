import java.util.*; 
import java.math.*; 
import java.io.*; 

public class BinarySearch
{
	public static int bs(int[] list, int target)
	{
		/*Not actually going to physically split the list up input smaller list
		 * Just going to track what would be the left and right bounds of the new list*/
		int l = 0;
		int r = list.length - 1;
		int index = (r-l)/2;
		int number = list[index];

		while(number != target)
		{
			if(target > number) l = index + 1;
			else r = index - 1;
			index = l + (r-l)/2;
			number = list[index];
		}
		return index;
	}	

	public static void main(String[] args)
	{
		/*Creating a list of 1 million random intergers.
		 * Sorted it, and perform a binary search on each element in order
		 * Indicates if binary search was unsucessful via printout.*/
		int[] list = new int[1000000];
		int min = -100000000;
		int max = 100000000;
		for(int i = 0; i < list.length; i++) list[i] = (int)(Math.random()*(max - min + 1)) + min;
		Arrays.sort(list);

		for(int i = 0; i < list.length; i++)
		{
			if(list[bs(list,list[i])] != list[i]) System.out.println("WRONG!!");
		}
	}
}
