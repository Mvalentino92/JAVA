import java.util.*;
public class ConnectFourGame
{
	//Navigates all directions
	static int[][] directions = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
	static int[] columns = {0,1,2,3,4,5,6};
	static Scanner input = new Scanner(System.in);

	//Randomize directions
	public static void randomizeDirections(int[][] directions)
	{
		ArrayList<int[]> holdShuffle = new ArrayList<>();
		for(int i = 0; i < directions.length; i++)
		{
			holdShuffle.add(directions[i]);
		}
		for(int i = 0; i < directions.length; i++)
		{
			int randomIndex = (int)(Math.random()*holdShuffle.size());
			directions[i] = holdShuffle.get(randomIndex);
			holdShuffle.remove(randomIndex);
		}
	}

	//Randomize columns
	public static void randomizeColumns(int[] columns)
	{
		ArrayList<Integer> shuffle = new ArrayList<>();
		for(int i = 0; i < columns.length; i++)
		{
			shuffle.add(columns[i]);
		}
		for(int i = 0; i < columns.length; i++)
		{
			int randomIndex = (int)(Math.random()*shuffle.size());
			columns[i] = shuffle.get(randomIndex);
			shuffle.remove(randomIndex);
		}
	}

	//Method that prints the board, (and also generates an initial board).
	public static void printBoard(char[][] board, boolean generate)
	{
		System.out.println();
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				if(generate) board[i][j] = 'O';
				else System.out.print(board[i][j]+" ");
			}
			if(!generate) System.out.println();
		}
		if(!generate)
		{
			System.out.println("-------------");
			System.out.println("0 1 2 3 4 5 6");
		}
	}

	/*Method to check if the player or the computer won.
	 * Returns a boolean, and will either check for the player or the computer depending on whats requested*/
	public static boolean checkWinner(char[][] board, boolean checkPlayer, int targetMatches)
	{
		//Checking for computer or player?
		char chip;
       		if(checkPlayer) chip = 'P';
		else chip = 'C';

		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				if(board[i][j] != chip) continue; //If initial space isnt a match, keep it moving
				else
				{
					randomizeDirections(directions);
					for(int k = 0; k < directions.length; k++)
					{
						int tracker = 1;
						int iCount = i;
						int jCount = j;
						while(((iCount = travelBoard(iCount,directions[k][0])) > -1 && iCount < board.length)&&((jCount = travelBoard(jCount,directions[k][1])) > -1 && jCount < board[0].length))
						{
							if(board[iCount][jCount] == chip) tracker++;
							else break;
							if(tracker == targetMatches) return true;
						}
					}
				}
			}
		}
		return false;
	}

	//Returns the traveled index of the current direction
	public static int travelBoard(int count, int directionValue)
	{
		if(directionValue == -1) return --count;
		else if(directionValue == 1) return ++count;
		else return count;
	}

	/*Test function, will prompt for if your moving the computer or the player
	 * Will then ask for an integer, (corresponding to the column), and put a chip there.
	 * Recursive, to keep asking for an integer within the bounds of the board. */
	public static boolean playerTurn(char[][] board,boolean playerMove)
	{
		//Print the board out for the player first
		printBoard(board,false);
		int columnChoice = -1;
		System.out.print("Choose your column: ");
		boolean choseCorrectly = false;
		while(!choseCorrectly)
		{
			while(input.hasNextInt())
			{
				columnChoice = input.nextInt();
				if(columnChoice >= 0 && columnChoice < board[0].length)
				{
					choseCorrectly = true;
					break;
				}
				else System.out.print("Please enter an integer within the boards size: ");
			}
			if(choseCorrectly) continue;
			else
			{
				System.out.print("Please enter an integer: ");
				input.next();
			}
		}
		char chip;
		if(playerMove) chip = 'P';
		else chip = 'C';
		int row = board.length - 1;

		while(row >= 0)
		{
			if(board[row][columnChoice] == 'O')
			{
				board[row][columnChoice] = chip;
				return true;
			}
			row--;
		}
		System.out.println("Cannot put a chip there, column is full!");
		return playerTurn(board,playerMove);
	}	

	/*Method that will both check if the player can win next turn, or the computer can win this turn.
	 * The highest precedence will be if the computer can win this turn.
	 * The second highest precedence will if the player can win their next turn.*/
	public static boolean smartMove(char[][] board,boolean victory,int targetMatches)
	{
		//Specify if trying to win or defend against loss
		char chip;
		if(victory) chip = 'C';
		else chip = 'P';

		randomizeColumns(columns);
		for(int w = 0; w < board[0].length; w++)
		{
			int i = columns[w];
			int row = board.length - 1;
			boolean check = false;
			while(row >= 0)
			{
				if(board[row][i] == 'O')
				{
					board[row][i] = chip;
					check = true;
					break;
				}
				row--;
			}
			if(checkWinner(board,!victory,targetMatches) && check)
			{
				if(chip == 'P' && row >= 0)
				{
					board[row][i] = 'C';
					if(row-1 >= 0)
					{
						board[row-1][i] = 'P';
						if(checkWinner(board,victory,4))
						{
							board[row][i] = 'O';
							board[row-1][i] = 'O';
							if(targetMatches - 1 < 2) break;
							else smartMove(board,victory,targetMatches - 1);
						}
						else
						{
							board[row-1][i] = 'O';
							return true;
						}
					}
					else return true;
				}
				else
				{
					if(row-1 >= 0)
					{
						board[row-1][i] = 'P';
						if(checkWinner(board,victory,4))
						{
							board[row][i] = 'O';
							board[row-1][i] = 'O';
							if(targetMatches - 1 < 2) break;
							else smartMove(board,victory,targetMatches - 1);
						}
						else
						{
							board[row-1][i] = 'O';
							return true;
						}
					}
					else return true;
				}
			}
			else 
			{
				if(row >= 0) board[row][i] = 'O';
			}
		}
		return false;
	}

	//Dummy way for the computer to make a move
	public static boolean dummyMove(char[][] board)
	{
		int column = (int)(Math.random()*board[0].length);
		int row = board.length - 1;
		while(row >= 0)
		{
			if(board[row][column] == 'O')
			{
				board[row][column] = 'C';
				return true;
			}
			row--;
		}
		return dummyMove(board);
	}

	public static void main(String[] args)
	{
		/*Intro for the game
		System.out.print("Welcome to Connect Four.~");
		input.nextLine();
		System.out.print("As a courtesy, we always let the player go first.~");
		input.nextLine();
		System.out.print("Have fun, and good luck.~:");
		input.nextLine();
		System.out.print("");*/

		//Setting up the board and essential variables
		char[][] board = new char[6][7];
		printBoard(board,true);
		boolean playerWon = false;
		boolean computerWon = false;

		while(!playerWon && !computerWon)
		{
			playerTurn(board,true);
			if(checkWinner(board,true,4)) playerWon = true;
			
			boolean changed = false;
			if(!changed) changed = smartMove(board,true,4);
			if(!changed) changed = smartMove(board,false,4);
			if(!changed) changed = smartMove(board,true,3);
			if(!changed) changed = smartMove(board,false,3);
			if(!changed) changed = smartMove(board,true,2);
			if(!changed) dummyMove(board);

			if(checkWinner(board,false,4)) computerWon = true;
		}

		if(playerWon) System.out.println("You won!");
		else
		{
			printBoard(board,false);
			System.out.println("The computer won!");
		}
	}
}
		
