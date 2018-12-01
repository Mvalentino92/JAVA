import java.util.*;
public class KnapSack
{
	//Max of two values
	public static int max(int a, int b) {return a > b ? a : b;}

	//Gets the capacity of the knapsack from the user
	public static int getCapacity()
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Enter the capacity of your knapsack: ");
		return input.nextInt();
	}

	//Gets the value and weight of all the items from the user
	public static int[][]  getItems()
	{
		//Number of items
		Scanner input = new Scanner(System.in);
		System.out.print("Enter number of items: ");
		int[][] items = new int[2][input.nextInt()];

		//Populate the items
		for(int i = 0; i < items[0].length; i++)
		{
			System.out.print((i+1)+") Enter value and weight: ");
			items[0][i] = input.nextInt();
			items[1][i] = input.nextInt();
		}
		return items;
	}

	public static int solveKS(int capacity, int[][] items)
	{
		//Create the table of sub knapsack problems
		int[][] table = new int[items[0].length+1][capacity+1];

		//Begin to iterate smaller problems and populate solution matrix
		for(int i = 1; i < table.length; i++)
		{
			for(int j = 1; j < table[i].length; j++)
			{
				/*If we can fit this item, compare the following
				 * Case 1) Stuffing the previous items knapsack, matching the remaining weight
				 * after taking this current item, into our current knapsack.
				 * Case 2) Not taking this item, and taking the previous items knapsack
				 * for this current weight.*/
				if(items[1][i-1] <= j)
				{
					table[i][j] = max(items[0][i-1] + table[i-1][j - items[1][i-1]],
							  table[i-1][j]);
				}
				//Otherwise, take previous items knapsack for this weight anyway
				else table[i][j] = table[i-1][j];
			}
		}
		//Prompt the user if they want to print the matrix
		Scanner input = new Scanner(System.in);
		int printMatrix = 0;
		System.out.print("\n0) No\n1) Yes\nPrint matrix?: ");
		printMatrix = input.nextInt();

		//Print matrix if yes
		if(printMatrix > 0)
		{
			for(int i = 0; i < table.length; i++)
			{
				for(int j = 0; j < table[i].length; j++) System.out.printf("%4d ",table[i][j]);
				System.out.println();
			}
		}
		//Return max value!
		System.out.println();
		return table[table.length-1][table[0].length-1];
	}

	//Solves the knapsack problem
	public static void main(String[] args)
	{
		System.out.println("The max knapsack is: "+solveKS(getCapacity(),getItems()));
	}
}
