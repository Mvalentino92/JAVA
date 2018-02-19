import java.util.Scanner;
public class mastermind
{
	public static Scanner input = new Scanner(System.in);
	public static boolean misplacedMatch(int guess, int[] numbers)
	{
		for(int i = 0; i < numbers.length; i++)
		{
			if(guess == numbers[i]) return true;
		}
		return false;
	}

	public static int[] createNumbers(int elements)
	{
		int[] numbers = new int[elements];
		for(int i = 0; i < numbers.length; i++) numbers[i] = (int)(Math.random()*10);
		return numbers;
	}

	public static int[] getGuess(int elements,int tries, int numberOfGuesses)
	{
		int[] numberGuesses = new int[elements];
		System.out.print((numberOfGuesses - tries)+" guesses left: ");
		for(int i = 0; i < numberGuesses.length; i++) numberGuesses[i] = input.nextInt();
		return numberGuesses;
	}

	public static boolean isWinner(String verdict)
	{
		for(int i = 0; i < verdict.length(); i++)
		{
			if(verdict.charAt(i) != '$') return false;
		}
		return true;
	}

	public static void main(String[] args)
	{
		System.out.println("Welcome to Mastermind!");
		System.out.print("How many numbers would you like to have to try and guess?: ");
		int numberOfElements = input.nextInt();
		System.out.print("And how many guesses would you like to do so?: ");
		int numberOfGuesses = input.nextInt();
		int[] mysteryNumbers = createNumbers(numberOfElements);
		int tries = 0;
		while(tries < numberOfGuesses)
		{
			int[] guess = getGuess(numberOfElements,tries,numberOfGuesses);
			String verdict = new String();
			for(int i = 0; i < guess.length; i++)
			{
				if(guess[i] == mysteryNumbers[i]) verdict += "$";
				else if(misplacedMatch(guess[i],mysteryNumbers)) verdict += "O";
				else verdict += "X";
			}
			if(isWinner(verdict))
			{
				System.out.println("You win!!");
				break;
			}
			else
			{
				for(int i = 0; i < guess.length; i++) System.out.print(guess[i]+" ");
				System.out.println();
				for(int i = 0; i < verdict.length(); i++) System.out.print(verdict.charAt(i)+" ");
				System.out.println("\n---------------");
				tries++;
			}
			input.nextLine();
		}
		if(tries == numberOfGuesses) 
		{
			System.out.println("You lose!");
			System.out.print("The numbers were: ");
			for(int i = 0; i < mysteryNumbers.length; i++) System.out.print(mysteryNumbers[i]+" ");
			System.out.println();
		}
	}
}
