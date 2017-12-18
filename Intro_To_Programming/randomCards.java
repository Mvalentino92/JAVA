import java.io.*;
import java.util.*;
public class randomCards
{
	//Method to randomly shuffle the elements of an ArrayList and return an array.
	public static String[] generateCards(ArrayList<String> cards, String[] shuffled, int count)
	{
		if(cards.isEmpty()) return shuffled; 
		else
		{
			int max = cards.size();
			int min = 0;
			int randomCard = (int)(Math.random()*max);
			if(randomCard % 2 != 0) return generateCards(cards,shuffled,count);
			
			//Adding the randomly generated card to shuffled deck
			shuffled[count++] = cards.get(randomCard);
			shuffled[count++] = cards.get(randomCard + 1);

			//Deleting it from the list
			cards.remove(randomCard);
			cards.remove(randomCard);

			//Recursive call to the function
			return generateCards(cards,shuffled,count);
		}
	}

	public static void main(String[] args) throws Exception
	{
		//Initializing the ArrayList and the index count
		ArrayList<String> myCards = new ArrayList<>();
		int count = 0;

		//Getting the name of the file from the user
		Scanner input = new Scanner(System.in);
		System.out.print("What is the name of the file that contains the questions?: ");
		String rawFileName = input.nextLine();
		File file = new File(rawFileName);
		Scanner fileInput = new Scanner(file);

		//Adding questions from the file to ArrayList
		while(fileInput.hasNextLine())
		{
			myCards.add(fileInput.nextLine());
		}

		//Calling the method to shuffle the cards and return a new array with the shuffled questions
		String[] shuffled = new String[myCards.size()];
		shuffled = generateCards(myCards,shuffled,count);

		System.out.println();
		for(int i = 0, j = 1; i < shuffled.length; i += 2, j++)
		{
			System.out.print("QUESTION "+j+": ");
			System.out.println(shuffled[i]);
			System.out.print("\nPress ENTER to view answer: ");
			input.nextLine();
			System.out.print("ANSWER: ");
			System.out.println(shuffled[i+1]);
			if(i != shuffled.length - 2) 
			{
				System.out.print("\nPress ENTER to view next question: ");
				input.nextLine();
			}
			else System.out.println("\nCongrats, you've finished your flash cards!");
		}
	}
}	
