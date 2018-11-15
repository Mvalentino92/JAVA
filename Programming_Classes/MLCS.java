import java.util.*; 
import java.math.*; 
import java.io.*; 

public class MLCS
{
	//Will find the longest common substring between any arbitrary number of strings
	public static String LCS(LinkedList<String> list)
	{
		//Base case, if there's only 2 strings in the list, just grab the substring
		if(list.size() == 2) return lcs(list.get(0),list.get(1));

		//Otherwise, reduce this list and recursively call it again
		for(int i = 1; i < list.size(); i++) list.set(i,formSubString(list.get(0),list.get(i)));
		list.removeFirst();
		return LCS(list);
	}

	//Method that keeps reducing the substrings
	public static String formSubString(String A, String B)
	{
		//If either a or b are empty, immediately return an empty string
		if(A.length() == 0 || B.length() == 0) return "";

		//Get the matrix for A and B
		int matAB[][] = new int[A.length()+1][B.length()+1];

		//Populate the matrix
		for(int i = 0; i < A.length(); i++)
		{
			if(A.charAt(i) == '$') continue;
			for(int j = 0; j < B.length(); j++)
			{
				if(A.charAt(i) == B.charAt(j)) matAB[i+1][j+1] = matAB[i][j] + 1;
			}
		}

		/*Iterate the matrix from the diagonals, and search for successive sequences of numbers
		 * They represent substrings between A and B.
		 * Concatenate all these substrings togetwhere with a dummy character*/
		HashSet<String> present = new HashSet<>(); //Track repeats!
		String AB = "";
		int col;
		for(int colHold = matAB[0].length - 1; colHold > 0; colHold--)
		{
			int row = 1;
			col = colHold;
			while(row < matAB.length && col < matAB[0].length)
			{
				//If we hit a 1, begin to seek forward and form a word
				if(matAB[row][col] == 1)
				{
					//Change this to only add the string if it doesn't exist already
					String toAdd = "" + B.charAt(col-1);
					int num = 2;
					col++;
					row++;
					while(row < matAB.length && col < matAB[0].length)
					{
						if(matAB[row][col] == num++)
						{
							toAdd += B.charAt(col-1);
							row++;
							col++;
						}
						else break;
					}
					//Check if it's in the HashSet before adding it!
					if(!present.contains(toAdd))
					{
						present.add(toAdd);
						AB += toAdd;
						AB += "$";
					}
				}
				row++;
				col++;
			}
		}

		//Do the remoaining ones with col being 1, and row changing
		int row;
		for(int rowHold = 2; rowHold < matAB.length; rowHold++)
		{
			col = 1;
			row = rowHold;
			while(row < matAB.length && col < matAB[0].length)
			{
				//If we hit a 1, begin to seek forward and form a word
				if(matAB[row][col] == 1)
				{
					String toAdd = "" + B.charAt(col-1);
					int num = 2;
					col++;
					row++;
					while(row < matAB.length && col < matAB[0].length)
					{
						if(matAB[row][col] == num++)
						{
							toAdd += B.charAt(col-1);
							row++;
							col++;
						}
						else break;
					}
					//Check if it's in the HashSet before adding it!
					if(!present.contains(toAdd))
					{
						present.add(toAdd);
						AB += toAdd;
						AB += "$";
					}
				}
				row++;
				col++;
			}
		}
		//Return the new String which is a concatenation of all the substrings between A and B
		return AB;
	}

	//Method that gets the longest common substring between two strings
	public static String lcs(String A, String B)
	{
		//If either is an empty string, return an empty string
		if(A.length() == 0 || B.length() == 0) return "";

		//Make the matrix, with an extra row and column
		int[][] mat = new int[A.length()+1][B.length()+1];

		//Init max and maxDex (end of the word)
		int max = 0;
		int maxDex = 0;

		//Begin to iterate the words and fill out the matrix
		for(int i = 0; i < A.length(); i++)
		{
			if(A.charAt(i) == '$') continue; //Skip dummy
			for(int j = 0; j < B.length(); j++)
			{
				/*If they match, look up and to left in matrix.
				* Report this new value as upLeft + 1.
				* Remember i and j are looking up and left, the current is both + 1*/
				if(A.charAt(i) == B.charAt(j)) 
				{
					//Update, and update max and maxDex if applicable
					mat[i+1][j+1] = mat[i][j] + 1;
					if(mat[i+1][j+1] > max)
					{
						max = mat[i+1][j+1];
						maxDex = i;
					}
				}
			}
		}
		//Return the substring using A
		return A.substring(maxDex - (max - 1),maxDex + 1);
	}

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		LinkedList<String> list = new LinkedList<>();
	
		//Grab at least two strings
		System.out.print("Enter the first String: ");
		list.add(input.nextLine());
		System.out.print("Enter the second String: ");
		list.add(input.nextLine());
		System.out.print("Enter another String, or type \"stop\" to terminate: ");

		//Grab as many as the user wants
		while(input.hasNextLine())
		{
			String next = input.nextLine();
			if(next.compareTo("stop") == 0) break;
			else list.add(next);
			System.out.print("Enter another String, or type \"stop\" to terminate: ");
		} 

		//Track the time
		double start = System.nanoTime();
		String retval = LCS(list);
		double time = (System.nanoTime() - start)/1e9;

		//Print results
		if(retval.length() == 0) System.out.println("No common substring between them.");
		else System.out.print("The LCS is: "+retval+"\n");
		System.out.println("Computation time took "+time+" seconds.");
	}
}
