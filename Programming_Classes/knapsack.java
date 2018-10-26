import java.util.*;
import java.math.*;
import java.io.*;

public class knapsack
{
	//Weight,value
	public static int dSolve(int size, int[][] items)
	{
		//Create dynamic array to hold solutions to all sizes
		int[] table = new int[size+1];

		//Begin to iterate all instances of sizes, and build up the table.
		for(int i = 1; i < table.length; i++)
		{
			/*Set current max to 0, and then check for the best item
			 * that has a weight equal to the size (if it exists)*/
			int max = 0;
			for(int j = 0; j < items.length; j++)
			{
				//If the item weight matches current bag size, check if value is greater than max
				if(items[j][0] == i) max = items[j][1] > max ? items[j][1] : max;
			}

			//Now Begin to iterate the previous max values in table (halfway), and check for best possibility.
			for(int k = 1; k <= i/2; k++) max = table[k] + table[i-k] > max ? table[k] + table[i-k] : max;
			table[i] = max;
		}
		//Return the final value in table
		return table[size];
	}

	public static void main(String[] args)
	{
		int[][] items = {{4,9},{3,8},{1,2},{5,5},{11,6},{2,4},{4,5},{17,23}};
		System.out.println(dSolve(100,items));
	}
}
