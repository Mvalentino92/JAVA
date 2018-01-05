import java.util.*;
public class Othello
{
	//Some static variables I will need.
	static Scanner input = new Scanner(System.in);
	static char[] letters = {'A','B','C','D','E','F','G','H'};
	static char[] acceptableLetters = {'a','A','b','B','c','C','d','D','e','E','f','F','g','G','h','H'};
	static char[] acceptableNumbers = {'1','2','3','4','5','6','7','8'};
	static int[][] directions = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};

	//**********General methods for the game board****************//

	//Method that creates the board.
	public static char[][] createBoard(int rows, int columns)
	{
		char[][] board = new char[rows][columns];
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				board[i][j] = 'O';
			}
		}
		board[3][3] = 'W';
		board[4][4] = 'W';
		board[3][4] = 'B';
		board[4][3] = 'B';
		return board;
	}

	//Method that prints the board, along with the coordinate border for the user to specify input.
	public static void printBoard(char[][] board)
	{
		System.out.println();
		for(int i = 0; i < board.length; i++)
		{
			System.out.print(letters[i]+"| ");
			for(int j = 0; j < board[0].length; j++)
			{
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("   ---------------");
		System.out.print("   1 2 3 4 5 6 7 8");
		System.out.println();
	}

	//Method for counting who the winner is.
	public static void getWinner(char[][] board)
	{
		int player = 0;
		int computer = 0;
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				if(board[i][j] == 'B') player++;
				else if(board[i][j] == 'W') computer++;
			}
		}
		System.out.println("SCORE: Player: "+player+"\t"+"Computer: "+computer);
		if(player > computer) System.out.println("Congrats! You beat the computer!");
		else if(computer > player) System.out.println("Oh sorry! The computer beat you!");
		else System.out.println("Whoa! It was a tie!");
	}

	//************Methods for handling the lists of the information responsible for picking the best course of action each turn**************//

	//Method to get average from the ArrayLists int[]'s
	public static double getAverage(ArrayList<int[]> list, int indexToUse)
	{
		double average = 0;
		for(int i = 0; i < list.size(); i++) average += list.get(0)[indexToUse];

		return average/list.size();
	}

	//Method to get the minimum value from the ArrayLists int[]'s
	public static int getMin(ArrayList<int[]> list, int indexToUse)
	{
		int min = list.get(0)[indexToUse];
		for(int i = 0; i < list.size(); i++) 
		{
			if(list.get(i)[indexToUse] < min) min = list.get(i)[indexToUse];
		}

		return min;
	}

	//Method to get the maximum value from the ArrayLists int[]'s
	public static int getMax(ArrayList<int[]> list, int indexToUse)
	{
		int max = list.get(0)[indexToUse];
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i)[indexToUse] > max) max = list.get(i)[indexToUse];
		}

		return max;
	}

	//Method that returns a list of all the moves that are along the borders of the game board.
	public static ArrayList<int[]> borderMoves(ArrayList<int[]> list, int indexToUse)
	{
		ArrayList<int[]> borderList = new ArrayList<>();
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i)[indexToUse] == 1) borderList.add(list.get(i));
		}

		return borderList;
	}

	//Method that returns a list of all the moves that cover equal to above the direction threshhold.
	public static ArrayList<int[]> directionMoves(ArrayList<int[]> list, int indexToUse, int directionThreshold)
	{
		ArrayList<int[]> directionList = new ArrayList<>();
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i)[indexToUse] > directionThreshold) directionList.add(list.get(i));
		}

		return directionList;
	}

	//Method that returns a list off all the moves that appear in both the borderList and the directionList.
	public static ArrayList<int[]> combinedMoves(ArrayList<int[]> borderList, ArrayList<int[]> directionList)
	{
		ArrayList<int[]> combinedList = new ArrayList<>();
		if(borderList.isEmpty() || directionList.isEmpty()) return combinedList;
		else
		{
			for(int i = 0; i < borderList.size(); i++)
			{
				for(int j = 0; j < directionList.size(); j++)
				{
					if(borderList.get(i)[3] == directionList.get(j)[3] && borderList.get(i)[4] == directionList.get(j)[4])
					{
						combinedList.add(borderList.get(i));
					}
				}
			}
			return combinedList;
		}
	}

	//Method that removes all the elements of the list that are not equal to the max specified
	public static void maxFlipped(ArrayList<int[]> list)
	{
		if(!list.isEmpty())
		{
			int max = getMax(list,0);
			for(int i = 0; i < list.size(); i++)
			{
				if(list.get(i)[0] < max) list.remove(i);
			}
		}
	}

	//Method that randomly chooses a set of coordinates from the final list with identical number of flipped chips
	public static int[] randomChoice(ArrayList<int[]> list)
	{
		int randomIndex = (int)(Math.random()*list.size());
		int[] choice = {list.get(randomIndex)[3],list.get(randomIndex)[4]};

		return choice;
	}

	//**********************Methods for checking for interacting with the board*************************************//
	
	//Method that checks the board for the specified player, and returns true or false if it's possible to make a valid move.
	public static boolean hasValidMove(char[][] board, boolean isPlayer)
	{
		//Who are we checking for?
		char yourChip;
		char opponentChip;
		if(isPlayer)
		{
			yourChip = 'B';
			opponentChip = 'W';
		}
		else 
		{
			yourChip = 'W';
			opponentChip = 'B';
		}

		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				if(board[i][j] != 'O') continue;
				else
				{
					for(int k = 0; k < directions.length; k++)
					{
						int row = i + directions[k][0];
						int col = j + directions[k][1];
						if((row < 0 || row >= board.length)||(col < 0 || col >= board[0].length)) continue;
						else
						{
							if(board[row][col] != opponentChip) continue;
							else
							{
								while((row >= 0 && row < board.length)&&(col >= 0 && col < board[0].length))
								{
									if(board[row][col] == yourChip) return true;
									else if(board[row][col] == opponentChip)
									{
										row += directions[k][0];
										col += directions[k][1];
									}
									else break;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	//*********************Supporting methods for user input*******************************//
	
	//Method that checks to see if the first char of the input is within the specified letters
	public static boolean isGoodLetter(char letter)
	{
		for(int i = 0; i < acceptableLetters.length; i++)
		{
			if(letter == acceptableLetters[i]) return true;
		}
		return false;
	}	

	//Method that checks to see if the second char of the input is within the specified numebrs
	public static boolean isGoodNumber(char number)
	{
		for(int i = 0; i < acceptableNumbers.length; i++)
		{
			if(number == acceptableNumbers[i]) return true;
		}
		return false;
	}

	//Method that prompts the user for input. Will have boolean parameter to either place white or black chips. Intended for testing.
	public static int[] playerMove(char[][] board, boolean isPlayer)
	{
		String moveChoice = new String();
		int[] coordinates = {-1,-1};
		if(isPlayer) System.out.print("Player B: ");
		else System.out.print("Player W: ");
		while(input.hasNextLine())
		{
			moveChoice = input.nextLine();
			if(moveChoice.length() != 2) break;
			else 
			{
				char row = moveChoice.charAt(0);
				char col = moveChoice.charAt(1);
				if(!(isGoodLetter(row) && isGoodNumber(col))) break;
				else
				{
					if(row  == 'a' || row == 'A') coordinates[0] = 0;
					else if(row == 'b' || row == 'B') coordinates[0] = 1;
					else if(row == 'c' || row == 'C') coordinates[0] = 2;
					else if(row == 'd' || row == 'D') coordinates[0] = 3;
					else if(row == 'e' || row == 'E') coordinates[0] = 4;
					else if(row == 'f' || row == 'F') coordinates[0] = 5;
					else if(row == 'g' || row == 'G') coordinates[0] = 6;
					else if(row == 'h' || row == 'H') coordinates[0] = 7;

					coordinates[1] = (int)col - 49;
					break;
				}
			}
		}
		return coordinates;
	}

	//Method that plays the players and computers move
	public static boolean playMove(char[][] board, boolean isPlayer, int[] coordinates)
	{
		int row = coordinates[0];
		int col = coordinates[1];
		if(row == -1 && col == -1) return false;
		else if(board[row][col] != 'O') return false;
		boolean flipped = false;
		char yourChip;
		char opponentChip;
		if(isPlayer)
		{
			yourChip = 'B';
			opponentChip = 'W';
		}
		else 
		{
			yourChip = 'W';
			opponentChip = 'B';
		}

		for(int k = 0; k < directions.length; k++)
		{
			int tempRow = row + directions[k][0];
			int tempCol = col + directions[k][1];
			if((tempRow < 0 || tempRow >= board.length)||(tempCol < 0 || tempCol >= board[0].length)) continue;
			else
			{
				if(board[tempRow][tempCol] != opponentChip) continue;
				else
				{
					while((tempRow >= 0 && tempRow < board.length)&&(tempCol >= 0 && tempCol < board[0].length))
					{
						if(board[tempRow][tempCol] == yourChip)
						{
							int changeRow = row + directions[k][0];
							int changeCol = col + directions[k][1];
							while((changeRow >= 0 && changeRow < board.length)&&(changeCol >= 0 && changeCol < board[0].length))
							{
								if(board[changeRow][changeCol] == yourChip)
								{
									flipped = true;
									tempRow = -1; 
									break;
								}
								else if(board[changeRow][changeCol] == opponentChip)
								{
									board[changeRow][changeCol] = yourChip;
									changeRow += directions[k][0];
									changeCol += directions[k][1];
								}
							}	
						}		
						else if(board[tempRow][tempCol] == opponentChip)
						{
							tempRow += directions[k][0];
							tempCol += directions[k][1];
						}
						else break;
					}
				}
			}
		}
		if(flipped) board[row][col] = yourChip;
		return flipped;
	}


										




	public static void main(String[] args)
	{
		char[][] board = createBoard(8,8);
		while(true)
		{
			if(hasValidMove(board,true)) 
			{
				printBoard(board);
				while(!(playMove(board,true,playerMove(board,true))));
			}
			else break;
			if(hasValidMove(board,false)) 
			{
				printBoard(board);
				while(!(playMove(board,false,playerMove(board,false))));
			}
			else break;
		}
		printBoard(board);
		getWinner(board);
	}
}
