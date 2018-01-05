/*The game is called Last chance.
 * RULES: A card with a possible 5 open spots is drawn.
 * The non zero digits represent numbers you need to fill by rolling the dice, while the zero digits represent free spaces.
 * The number of dice you get to roll each turn, corresponds to the amount of non zero digits you have left on your card.
 * Each turn, before rolling the dice, you are allotted a random amount of turns (from 2-5), to complete the card.
 * The catch is, you can either bet that you WILL fill out the card, or bet AGAINST yourself that you will not.
 * If you bet correctly (whether you did or did not fill out the card in the number of supplied turns), you will be awarded with the amount of money the card was worth.
 * Values are between 100 to 5000. The goal of the game is to reach 15000 dollars.*/


import java.util.Scanner;
public class LastChance
{
	//This function checks for the number of occurences of each value from 1 to 6 of the dice you rolled
	public static int countOccurences(int[] diceRolled,int value)
	{
		int numberOfOccurences = 0;
		for(int i = 0; i < diceRolled.length; ++i)
		{
			if(diceRolled[i] == value)
			{
				numberOfOccurences += 1;
			}
		}
		return numberOfOccurences;
	}



	//This function changes any of the values on the Played card if you had rolled those values.
	public static int[] changeCard(int[] playedCard ,int value, int occurences)
	{
		int count = 0;
		for(int j = 0; j < playedCard.length; ++j)
		{
			if(playedCard[j] == value)
			{
				count += 1;
			}
		}
		while(occurences > 0 && count > 0)
		{
			for(int i = 0; i < playedCard.length; ++i)
			{
				if(playedCard[i] == value)
				{
					playedCard[i] = 0;
					occurences -= 1;
					count -= 1;
					break;
				}
				
				
			}
		}
		return playedCard;
	}

	//Returns a valeu for the reward
	public static int Money()
	{
		int[] Prices = {50,100,250,500,750,1000,2000,2500,3750,5000};
		int indexOfPrices = (int)(Math.random()*10);
		int price = Prices[indexOfPrices];
		return price;
	}

	//Count the number of zeros in the card;
	public static int countZero(int[] Card)
	{
		int numberOfZeros = 0;
		for(int i = 0; i < Card.length; ++i)
		{
			if(Card[i] == 0)
			{
				numberOfZeros += 1;
			}
		}
		return numberOfZeros;
	}
	

	//Generate the card
	public static int[] generateCard()
	{
		int[] card = new int[5];
		for(int i = 0; i < card.length; ++i)
		{
			card[i] = (int)(Math.random()*7);
		}
		if(countZero(card) == 5)
		{
			card[1] = 6;
			card[2] = 6;
			card[3] = 6;
		}
		return card;
	}


	//Generates the rolled dice
	public static int[] Roll(int diceToRoll)
	{
		int[] rolledDice = new int[diceToRoll];
		for(int i = 0; i < diceToRoll; ++i)
		{
			rolledDice[i] = (int)(Math.random()*6 + 1);
		}
		return rolledDice;
	}

	//Gererates turns to roll
	public static int Turns()
	{
		int turnsToRoll = (int)(Math.random()*5 + 1);
		if(turnsToRoll == 1)
		{
			turnsToRoll = 4;
		}
		return turnsToRoll;
	}


	//Prints the values of the card or dice in a neat fashion
	public static void printValues(int[] cardOrDice)
	{
		for(int i = 0; i < cardOrDice.length; ++i)
		{
			System.out.print(" "+cardOrDice[i]);
		}
	}


	public static void main(String[] args)
	{
		int totalCash = 500;
		int occurences = 0;
		while(totalCash < 15000)
		{
			Scanner input = new Scanner(System.in); 
			int[] card = generateCard();
			int turns = Turns();
			int reward = Money();
			System.out.println("You have "+totalCash+" dollars!");
			System.out.print("\nThe card is:");
			printValues(card);
			System.out.println("\nAnd you have "+turns+" turns to roll it, for a reward of "+reward);
			System.out.print("\nWill you bet for or against? [1 - Yes I can do it!],[0 - No I do not think I will]: ");
			int verdict = 2;
			while(verdict != 1 && verdict != 0)
			{
				while(!(input.hasNextInt()))
				{
					System.out.print("ERROR: Please enter either 1 for betting with, or 0 for betting against: ");
					input.next();
				}
				verdict = input.nextInt();
				if(verdict != 1 && verdict != 0) System.out.print("ERROR: Please enter either 1 for betting with, or 0 for betting against: ");
			}
			input.nextLine();
			while(turns > 0 && countZero(card) != 5)
			{
				int numberOfDice = 5 - countZero(card);
				int[] dice = Roll(numberOfDice);
				System.out.println("\n\nTurns left: "+turns);
				System.out.print("Press ENTER to roll!: ");
				input.nextLine();
				System.out.println("This is what you rolled!");
				printValues(dice);
				for(int i = 1; i < 7; ++i)
				{
					
					occurences = countOccurences(dice,i);
					card = changeCard(card,i,occurences);
				}
				turns -= 1;
				System.out.println("\n\nHere is what you still need to roll: ");
				printValues(card);
			}
			if((countZero(card) == 5 && verdict == 1) || (countZero(card) != 5 && verdict == 0))
			{
				totalCash += reward;
				System.out.println("\n\nYou won!!\nYou gain "+reward+" dollars!");
				System.out.print("Press ENTER to get your next card: ");
				input.nextLine();
			}
			else
			{
				System.out.println("\n\nOh I'm sorry! You didn't place the correct bet!");
				System.out.print("Press ENTER to get your next card: ");
				input.nextLine();
			}
		}	
		System.out.println("Congrats you won!");
	}
}
