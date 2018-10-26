import java.util.*; 
import java.math.*; 
import java.io.*; 

public class peapTest
{
	public static void main(String[] args)
	{
		//Test for the following inputs
		int[] testers = {0,1,10,100,1000,10000,100000,1000000,2500000};
		for(int k = 0; k < testers.length; k++)
		{
			//Init vars
			int n = testers[k];
			Peap<Integer> test = new Peap<>();
			int[] exTrue = new int[n];

			//Start the timer and begin adding values to peap.
			double start = System.nanoTime();
			for(int i = 0; i < n; i++) 
			{
				exTrue[i] = (int)(Math.random()*(n/3) + 1);
				test.add(exTrue[i]);
			}

			//Begin removing values and building the array, then stop the time.
			int[] ex = new int[n];
			int size = test.getSize();
			for(int i = 0; i < size; i++) 
			{
				ex[size-i-1] = test.remove();
			}
			double time = (System.nanoTime() - start)/1e9;

			//Sort using java's method, and get the true sorted array. Compare mine to it.
			Arrays.sort(exTrue);
			for(int i = 0; i < ex.length; i++)
			{
				if(ex[i] != exTrue[i])
				{
					System.out.println("WRONG!");
					System.exit(1);
				}
			}

			//If everything was good, print out the time and how many elements it sorted
			System.out.println("Took "+time+" seconds to sort "+ex.length+" elements correctly");

			//Add and remove elements in a staggered fashion to/from the now empty peap
			//Just to prove it won't get messed up if depleted first, 
			//or things are added and removed before total removal
			for(int i = 0; i < 50; i++)
			{
				if(i % 5 == 4) test.remove();
				else test.add((int)(Math.random()*50 + 1));
			}

			//Print out those 30 elements, that can easily be seen as sorted by user.
			System.out.print("Sorted again, with 30 new elements: ");
			int s = test.getSize();
			for(int i = 0; i < s; i++) System.out.print(test.remove()+" ");
			System.out.println("\n");
		}
	}
}
