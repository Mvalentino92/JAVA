import java.util.*;
public class Program4_Commented
{
//******************************These are static variables. Any method can access them. The main method, any method you create.******************************

	//Creating a String array of the states.
	public static String[] states = {"New Jersey","New York","Pennsylvania"};
	
	//Create an array of Strings containing the months.
	public static String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

//**********************************************************************************************************************************************************

	//Gets user input and creates a 2D array of rainfall data.
	public static int[][] createArray()
	{
		//Initialize Scanner and create 2D array.
		Scanner input = new Scanner(System.in);
		int[][] rainfall = new int[3][12];

		//Start nested for loops to accept input from the user.
		for(int i = 0; i < rainfall.length; i++) //Iterates the rows (the states).
		{
			System.out.print("Enter 12 months of rainfall in inches for "+states[i]+": "); //Prints the current state.
			for(int j = 0; j < rainfall[0].length; j++) rainfall[i][j] = input.nextInt(); //Iterates the columns (assigns rainfall for each month).
		}
		System.out.println();

		//Return the 2D array containing the values.
		return rainfall;
	}

	//Prints the rainfall data entered by the user in a formatted table. (Must be passed a 2D array as an arguement)
	public static void printRainfall(int[][] rainfall)
	{
		//Printing the months of year.
		System.out.print("State\t\t");
		for(int i = 0; i < months.length; i++) System.out.printf("%7s",months[i]); //Iterating my static array months.
		System.out.println();

		//Printing the individual rainfall for each state in every month.
		for(int i = 0; i < rainfall.length; i++) //Iterates the rows (states).
		{
			System.out.print(states[i]+"\t"); //Prints the current state.
			for(int j = 0; j < rainfall[0].length; j++) System.out.printf("%7d",rainfall[i][j]); //Iterates the columns (printing rainfall for each month).
			System.out.println(); //Print a line after each states rainfall data, to seperate it nicely.
		}
		System.out.println();
	}

	//Prints the total rainfall of each state by traversing the 2D array.
	public static void printTotalRainfall(int[][] rainfall)
	{
		//Go through the array, print the state, total its rainfall, and print the total.
		for(int i = 0; i < rainfall.length; i++)
		{
			System.out.print(states[i]+" Total Rainfall: "); //Print the current state.
			int totalRainfall = 0; //Set totalRainfall equal to zero. This will reset to zero every time the first loop goes back for the next states data.
			for(int j = 0; j < rainfall[0].length; j++) totalRainfall += rainfall[i][j]; //Add up all the rainfall data for that state.
			System.out.print(totalRainfall+"\n"); //Print the total rainfall we just calculated above.
		}
		System.out.println();
	}

	//Finds the maximum rainfall from any state in any month and prints the value.
	public static void printHighest(int[][] rainfall)
	{
		//Initialize 3 variables to track the: Highest rainfall, what state it occurs in, and what month it was in.
		int maxRainfall = rainfall[0][0]; //Default the maxRainfall to the very first value.
		int stateIndex = 0; //Default both index's to 0;
		int monthIndex = 0;

		/*Iterate through the 2D array, and check every value against the current max.
		 * If any value is higher, that value is set as the new maxRainfall value.*/
		for(int i = 0; i < rainfall.length; i++)
		{
			for(int j = 0; j < rainfall[0].length; j++)
			{
				if(rainfall[i][j] > maxRainfall) //If the current rainfall data is highter than the current max....
				{
					maxRainfall = rainfall[i][j]; //Set max equal to this new value.
					stateIndex = i; //Set the stateIndex to i (i iterates the states).
					monthIndex = j; //Set the monthIndex to j (j iterates the months).
				}
			}
		}

		//Prints the max rainfall, along with the state and month it occured in.
		System.out.println("The highest rainfall of "+maxRainfall+" inches, occured on "+months[monthIndex]+" in "+states[stateIndex]+".");
	}

	public static void main(String[] args)
	{
		//Creating the array by calling the method created.
		int[][] rain = createArray();

		//Calling every other method on that array to print the desired information.
		printRainfall(rain);
		printTotalRainfall(rain);
		printHighest(rain);
	}
}
