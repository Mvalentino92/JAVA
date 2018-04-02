import java.io.*;
import java.util.*;
public class FlashCards
{
	//Method to limit the number of characters per line during printout
	public static void formatLines(String s, int lines, int indent)
	{
		int count = 0;
		for(int i = 0; i < s.length(); i++)
		{
			count++;
			//If the space between words equals the threshhold of characters per line, just start the next time
			if(s.charAt(i) == ' ' && count == lines) 
			{
				System.out.println();
				for(int k = 0; k < indent; k++)
				{
					System.out.print(' ');
				}
				count = 0;
			}
			//Otherwise, before we start printing the next word, we need to check if by printing that entire word, it will run over the threshold
			else if(s.charAt(i) == ' ' && i + 1 < s.length())
			{
				int j = i + 1;
				int countHold = count;
				while(s.charAt(j) != ' ')
				{
					j++;
					countHold++;
					if(j >= s.length()) break;
				}
				if(countHold > lines)
				{
					System.out.println();
					for(int k = 0; k < indent; k++)
					{
						System.out.print(' ');
					}
					count = 0;
				}
				else System.out.print(s.charAt(i));
			}
			//Otherwise, just continue printing the word your currently printing
			else
			{
				System.out.print(s.charAt(i));
			}
		}
	}

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
		int lineLimit = 75;
		//Initializing the ArrayList and the index count, and the count for how many questions are answered incorrectly.
		int wrongCount = 0;
		ArrayList<String> myCards = new ArrayList<>();
		int count = 0;

		//Getting the name of the file from the user
		Scanner input = new Scanner(System.in);
		System.out.print("What is the name of the file that contains the questions?: ");
		String rawFileName = input.nextLine();
		File file = new File("null");
		Scanner fileInput = new Scanner(System.in);

		//Checking to see if the file exists. If it doesn't, notify the user possible reasons why.
		try
		{
			file = new File(rawFileName);
			fileInput = new Scanner(file);
		}
		catch(Exception e)
		{
			System.out.println("ERROR: File does not exist. Please ensure the following"+
					"\n1) You have spelled the file name correctly."
					+"\n2) The file is in the same directory/folder as this script."
					+"\n3) You aren't including an file type extensions, like .txt."
					+"\n4) Dont be a giant maps.");
			System.exit(1);
		}
		
		//Creating PrintWriter for wrong answers file 
		File wrongAnswers = new File("WrongAnswers"+wrongCount+".txt");
		PrintWriter output = new PrintWriter(wrongAnswers);

		//Adding questions from the file to ArrayList
		while(fileInput.hasNextLine())
		{
			myCards.add(fileInput.nextLine());
		}

		//Calling the method to shuffle the cards and return a new array with the shuffled questions
		String[] shuffled = new String[myCards.size()];
		shuffled = generateCards(myCards,shuffled,count);

		/*Printing out the questions and answers, while asking the user if they got the current question correct or not.
		 * If they got the question incorrect, I'm writing that question and answer to a new document
		 * This document containing all the incorrectly answered questions, will be read again after this round finishes. 
		 */
		System.out.println();
		for(int i = 0, j = 1; i < shuffled.length; i += 2, j++)
		{
			String indent = "QUESTION "+j+": ";
			System.out.print(indent);
			formatLines(shuffled[i],lineLimit,indent.length());
			System.out.print("\n\nPress ENTER to view answer: ");
			input.nextLine();
			indent = "ANSWER: ";
			System.out.print(indent);
			formatLines(shuffled[i+1],lineLimit,indent.length());
			System.out.print("\n\nDid you get that question correct?: [y/n] ");
			String verdict = input.next();
			if(verdict.charAt(0) == 'n')
			{
				wrongCount++;
				output.println(shuffled[i]);
				output.println(shuffled[i+1]);
			}
			input.nextLine();
		}
		
		/*If answers were incorrect, reshuffle those wrong answers.
		 * Keep doing so with the wrong answers, until all questions are answered correctly.
		 * Keeps track of how many times this occurs, and always deletes the next file it writes the repeated questions to
		 */
		output.close();
		Scanner wrong = new Scanner(wrongAnswers);
		int questionRounds = 1;

		while(wrong.hasNextLine())
		{
			for(int i = 0; i < 50; i++)
			{
				System.out.println();
			}
			System.out.print("*************************************************************************");
			questionRounds++;
			System.out.println("\nROUND "+questionRounds);
			myCards = new ArrayList<>();
			count = 0;

			//Creating PrintWriter for wrong answers file 
			wrongAnswers.delete();
			wrongAnswers = new File("WrongAnswers"+wrongCount+".txt");
			output = new PrintWriter(wrongAnswers);

			//Adding questions from the file to ArrayList
			while(wrong.hasNextLine())
			{
				myCards.add(wrong.nextLine());
			}

			//Calling the method to shuffle the cards and return a new array with the shuffled questions
			shuffled = new String[myCards.size()];
			shuffled = generateCards(myCards,shuffled,count);


			System.out.println();
			for(int i = 0, j = 1; i < shuffled.length; i += 2, j++)
			{
				String indent = "QUESTION "+j+": ";
				System.out.print(indent);
				formatLines(shuffled[i],lineLimit,indent.length());
				System.out.print("\n\nPress ENTER to view answer: ");
				input.nextLine();
				indent = "ANSWER: ";
				System.out.print(indent);
				formatLines(shuffled[i+1],lineLimit,indent.length());
				System.out.print("\n\nDid you get that question correct?: [y/n] ");
				String verdict = input.next();
				if(verdict.charAt(0) == 'n')
				{
					wrongCount++;
					output.println(shuffled[i]);
					output.println(shuffled[i+1]);
				}
				input.nextLine();
			}
			output.close();
			wrong = new Scanner(wrongAnswers);
		}
		wrongAnswers.delete();
		System.out.print("*************************************************************************");
		System.out.println("\nCONGRATS!! You answered all the questions correctly after "+questionRounds+" rounds!");
	}
}	
