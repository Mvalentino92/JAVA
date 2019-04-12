import java.util.*;
public class coinFlips
{
	/*Prints all possibilities of flipping a coin n amount of times.
	 * 0 is heads, 1 is tails.
	 * This is a recursive function.*/
	public static int[][] flips(int n)
	{
		//Base case. Flip one coin. Results are heads or tails.
		if(n == 1)
		{
			int[][] retval = new int[2][1];
			retval[1][0] = 1;
			return retval;
		}

		/*Otherwise, do the following.
		 * 1) Grab all possible solutions for flipping a coin (n-1) times.
		 * 2) If you were to flip a coin one more time (the nth time) for each of those instances,
		 * -- there are two posssible outcomes. Heads or tails.
		 * -- So, take each of those instances (from n-1), and create 2 instances from it.
		 * -- One, where you flipped heads at the end (nth flip), which is prepending a 0.
		 * -- Another, where you flipped tails at the end (nth flip), which is prepending a 1.*/
		else
		{
			//Grab all the instances of flipping (n-1) coins.
			int[][] next = flips(n-1);
			int half = next.length;

			//Create return array
			int[][] retval = new int[half*2][n];

			//Prepend 0 and 1 onto all the instances from next (n-1)
			for(int i = 0; i < half; i++)
			{
				retval[i][0] = 0; //First spot for first half gets 0
				retval[i+half][0] = 1; //First spot for second half gets 1
				for(int j = 1; j < retval[0].length; j++)
				{
					//Clone the rest of the spots the entire thing.
					retval[i][j] = next[i][j-1];
					retval[i+half][j] = next[i][j-1];
				}
			}
			return retval;
		}
	}

	/*This function is going to abuse the fact that all the possible instances
	 * of flipping the coins can be represented as the binary representation (with padded zeros) 
	 * of the numbers 0 through (total number of instances - 1)
	 * Works performing the following steps:
	 * 1) Calculate how many instances are possible (calculated by doing 2^n) call this size.
	 * 2) Create a matrix with size as the number of rows, and n as number of columns.
	 * 3) By default, this matrix is all 0's. The only elements we want to change,
	 * -- are the one we will change to 1. Which is achieved through representing this number in binary.*/
	public static int[][] flips2(int n)
	{
		//Create return matrix.
		int size = (int)(Math.pow(2,n));
		int[][] retval = new int[size][n];

		//Start at second index, because it corresponds to the number 1 (0, is just all 0's).
		for(int i = 1; i < size; i++)
		{
			int num = i; //Clone i, so we can update the number, but not effect the for loop.
			for(int j = 0; j < n; j++)
			{
				/*Grab the current power of 2, and see if it's less than our number.
				 * If it is, change the element at this index in our matix to 1.
				 * And minus power from num*/
				int power = (int)(Math.pow(2,n - j - 1));
				if(power <= num)
				{
					retval[i][j] = 1;
					num -= power;
				}
			}
		}
		
		return retval;
	}

	public static void main(String[] args)
	{
		/*Lets see which method is faster for calculation! (Not printing)
		 * Change n here for how many coins you want to flip!*/
		int n = 10;

		//*******************Using flips (recursive)***********************
		//Grab the time is takes to compute
		double start = System.nanoTime();
		int[][] test = flips(n);
		double time = (System.nanoTime() - start)/1e6;

		//Gather results
		for(int i = 0; i < test.length; i++)
		{
			for(int j = 0; j < test[i].length; j++) System.out.print(test[i][j]+" ");
			System.out.println();
		}
		String results = "Using flips, there are "+test.length+" possibilities,"+
				  " and took "+time+" milliseconds to compute.";
		System.out.println("\n********************************************\n");

		//*******************Using flips2 (binary)*************************
		//Grab the time is takes to compute
		start = System.nanoTime();
		int[][] test2 = flips2(n);
		time = (System.nanoTime() - start)/1e6;

		//Gather results
		for(int i = 0; i < test2.length; i++)
		{
			for(int j = 0; j < test2[i].length; j++) System.out.print(test2[i][j]+" ");
			System.out.println();
		}
		String results2 = "Using flips2, there are "+test2.length+" possibilities,"+
				  " and took "+time+" milliseconds to compute.";

		//Print results
		System.out.println(results);
		System.out.println(results2);
	}
}
