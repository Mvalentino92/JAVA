import java.util.*;
import java.math.*;
public class RecursiveSum
{
	//Static variable to track the amount of recursive calls
	public static int count = 0;

	/*The recursive method to find all available combos. It works as follows:
	 * Take the list {1,2,3} for instance. We know that the combos are {1,2,3,(1+2),(1+3),(2+3),(1+2+3).
	 * There's 7 possible sums. Which is 2^n - 1. Where n, is the length of our list.
	 * So 2^3 - 1 = 7!
	 * Now how can we tell the computer to find them all? 
	 * Well, when scanning through this list of numbers and adding them to a sum,
	 * there's only two things you could possible do at every instance (each new number you get to).
	 * Either add it to your sum, or dont. 
	 * Those are the two cases.
	 * Case 1: Add the current number were looking at to our sum, and then head to the next number.
	 * Case 2: Dont add the current number, then head to the next number.
	 *
	 * So how's this work in code?
	 * Well, everytime you recursively call this function it creates another stack.
	 * Which you can think of as "another universe", completely seperate from the one you were just in.
	 * If our sum was 2, and then we called the function again with (sum + 3),
	 * we would then have our previous universe where the sum was still 2, and in our current universe,
	 * the sum is now 5. And if we ever go back to the previous universe at any point, guess what?
	 * The sum there is still 2!
	 *
	 * All of this is represented in the code below*/
	public static void getCombos(int[] arr, Set<Integer> hs, int sum, int index)
	{
		//Add 1 to count, and add the current sum to the set.
		count++;
		hs.add(sum); //A Set will only add an element if it doesn't already contain that element.

		//Check to see if were at the end of the list, if we are, return (go back to previous universe!)
		if(index == arr.length) return;

		                          /*TWO CASES*/
		
		//CASE 1: We add the current element, and increment the index
		getCombos(arr,hs,sum + arr[index],index + 1); //Going to the NEXT UNIVERSE!
		//WOAH! If the code reached this line, that means we came back from the above universe!)

		//CASE 2: We dont change the sum, and increment the index
		getCombos(arr,hs,sum,index+1); //Going to the NEXT UNIVERSE!
		//WOAH! If the code reached this line, that means we came back from the above universe!)
	}

	public static void main(String[] args)
	{
		//Create array and HashSet to store the values
		int[] arr = {1,5,12,19,23,37,59,67,101,123,125,135,181,213,
		             224,225,336,341,456,467,469,501,513,567,601,883,
			     1011,1456,1556,3245};
		Set<Integer> hs = new HashSet<>();

		//Get the combos and store the time of computation.
		double start = System.nanoTime();
		getCombos(arr,hs,0,0);
		double time = (System.nanoTime() - start)/1e6;

		//Remove the accidental 0 form the set, and grab a sorted array from the set.
		hs.remove(0);
		Integer[] combos = new Integer[hs.size()];
		combos = hs.toArray(combos);
		Arrays.sort(combos);

		//Print results
		System.out.println("Amount of possible combos to look through is: "+
				   ((int)Math.pow(2,arr.length) - 1));
		System.out.println("Actual amount of combos looked through recursively: "+
				   count);
		System.out.println("It took "+time+" milliseconds to find the "+hs.size()+
				   " unique sums.");
		System.out.print("\nThe sums are: ");
		for(int i = 0; i < combos.length; i++) System.out.print(combos[i]+" ");
		System.out.println();
	}

	/*Thoughts: Why is the amount of possible combo's 63? (2^6 - 1)
	 * But the actual amount of combos we checked (recursive calls) was 127? (2^7 - 1).
	 * Why did I remove a 0 from the set?*/
}
