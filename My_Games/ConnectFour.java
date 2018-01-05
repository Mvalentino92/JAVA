import java.util.*;
public class ConnectFour
{
	//Some static arrays I'll need, that will be randomized every turn.
	static int[][] directions = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
	static int[] columns = {0,1,2,3,4,5,6};
	static Scanner input = new Scanner(System.in);

	//Checks if the board is full
	public static boolean fullBoard(char[][] board)
	{
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				if(board[i][j] == 'O') return false;
			}
		}
		return true;
	}

	/*Randomize the directions array. So the computer doesnt always just begin checking for moves in the same
	 * fashion. Called everytime the computer moves*/
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

	/*Randomize the columns array. Similar to the above method. It ensures that the computer does always
	 * begin checking for moves from left to right, as the board would most likely begin to fill up there.*/
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
			System.out.println("1 2 3 4 5 6 7");
		}
	}

	/*Method that can checks if either either player won. It can also be used to check if certain criteria
	 * are satisfied. Such as either player having 3 in a row somewhere. The number of matches to check for
	 * is denoted by targetMatches. This method will be used in conjunction with smartMove. It serves as the 
	 * "foresight" to see where the computer should place its next chip, based on it's current chips, as
	 * well as the players.*/
	public static boolean checkWinner(char[][] board, boolean checkPlayer, int targetMatches,int row, int col)
	{
		//Checking for computer or player?
		char chip;
       		if(checkPlayer) chip = 'P';
		else chip = 'X';

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
						boolean sameSpot;
						if(iCount == row && jCount == col) sameSpot = true;
						else sameSpot = false;
						while(((iCount = travelBoard(iCount,directions[k][0])) > -1 && iCount < board.length)&&((jCount = travelBoard(jCount,directions[k][1])) > -1 && jCount < board[0].length))
						{
							if((board[iCount][jCount] == chip)&&(iCount == row && jCount == col))
							{
								sameSpot = true;
								tracker++;
							}
							else if(board[iCount][jCount] == chip) tracker++;
							else break;
							if(tracker == targetMatches && sameSpot) return true;
						}
					}
				}
			}
		}
		return false;
	}

	/*Checks if it's possible to get 4 in a row anywhere using this spot
	 * This is an exact duplicate of check winner with one small change.
	 * Didn't want to add yet another boolean parameter, so I just copied the code*/
	public static boolean possible(char[][] board, boolean checkPlayer, int targetMatches,int row, int col)
	{
		//Checking for computer or player?
		char chip;
       		if(checkPlayer) chip = 'P';
		else chip = 'X';

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
						boolean sameSpot;
						if(iCount == row && jCount == col) sameSpot = true;
						else sameSpot = false;
						int oCount = 0;
						int oMax = 4 - targetMatches;
						while(((iCount = travelBoard(iCount,directions[k][0])) > -1 && iCount < board.length)&&((jCount = travelBoard(jCount,directions[k][1])) > -1 && jCount < board[0].length))
						{
							if((board[iCount][jCount] == chip || board[iCount][jCount] == 'O')&&(iCount == row && jCount == col))
							{
								sameSpot = true;
								tracker++;
								if(board[iCount][jCount] == 'O') oCount++;
							}
							else if(board[iCount][jCount] == chip || board[iCount][jCount] == 'O')
							{
								tracker++;
								if(board[iCount][jCount] == 'O') oCount++;
							}
							else break;
							if((tracker == 4 && sameSpot)&&(oCount <= oMax)) return true;
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
				if(columnChoice >= 1 && columnChoice <= board[0].length)
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
		else chip = 'X';
		int row = board.length - 1;

		while(row >= 0)
		{
			if(board[row][columnChoice-1] == 'O')
			{
				board[row][columnChoice-1] = chip;
				return true;
			}
			row--;
		}
		System.out.println("Cannot put a chip there, column is full!");
		return playerTurn(board,playerMove);
	}	

	/*The main method the computer uses to move. It will be called in a hierarchy of precedence.
	 * For instance, if the computer can win this turn it will do so.
	 * If not, it will check to see if the player can win, and will block that move.
	 * Next, it will use this method to check if it can get 3 in a row etc...
	 * Has some recursive element to it, as it tries to predict if it's next move will set the player up
	 * for a winning move next turn. It will also check to see if the following space has the potential
	 * to ultimate be involved in four in a row. Whether for the current conditions/move being satisfied
	 * or not. Last resort is to call the dummy method*/
	public static boolean smartMove(char[][] board,boolean victory,int targetMatches)
	{
		//Specify if trying to win or defend against loss
		char chip;
		if(victory) chip = 'X';
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
			boolean setUp = false;
			if(row - 1 >= 0)
			{
				board[row-1][i] = 'X';
				if(checkWinner(board,false,4,row-1,i)) setUp = true;
				board[row-1][i] = 'O';
			}
			if((checkWinner(board,!victory,targetMatches,row,i) && check)&&(possible(board,!victory,targetMatches,row,i) || setUp))
			{
				if(chip == 'P' && row >= 0)
				{
					board[row][i] = 'X';
					if(row-1 >= 0)
					{
						board[row-1][i] = 'P';
						if(checkWinner(board,!victory,4,row-1,i)&&targetMatches != 4)
						{
							board[row][i] = 'O';
							board[row-1][i] = 'O';
							if(targetMatches - 1 < 2) continue; //Changed from break
							else 
							{
								if(w == board[0].length - 1) return smartMove(board,!victory,targetMatches - 1);
								else continue;
							}
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
						if(checkWinner(board,victory,4,row-1,i)&&targetMatches != 4)
						{
							board[row][i] = 'O';
							board[row-1][i] = 'O';
							if(targetMatches - 1 < 2) continue; //Changed from break
							else 
							{
								if(w == board[0].length) return smartMove(board,victory,targetMatches - 1);
								else continue;
							}
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
				if(row >= 0 && check) board[row][i] = 'O';
			}
		}
		return false;
	}

	//If no conditions for smartMove to called are met, then this method will pick a spot at random.
	public static boolean dummyMove(char[][] board,boolean firstAttempt)
	{
		if(firstAttempt)
		{
			ArrayList<Integer> acceptableSpots = dummyCheck(board);
			if(acceptableSpots.isEmpty()) return dummyMove(board,false);
			else
			{

				int getGoodColumn = (int)(Math.random()*acceptableSpots.size());
				int column = acceptableSpots.get(getGoodColumn);
				int row = board.length - 1;
				while(row >= 0)
				{
					if(board[row][column] == 'O')
					{
						board[row][column] = 'X';
						break;
					}
					row--;
				}
				return true;

			}
		}
		else
		{
			int column = (int)(Math.random()*board[0].length);
			int row = board.length - 1;
			while(row >= 0)
			{
				if(board[row][column] == 'O')
				{
					board[row][column] = 'X';
					return true;
				}
				row--;
			}
			return dummyMove(board,false);
		}
	}

	public static ArrayList<Integer> dummyCheck(char[][] board)
	{
		ArrayList<Integer> acceptableColumns = new ArrayList<>();
		for(int i = 0; i < board[0].length; i++)
		{
			if(board[1][i] != 'O') continue;
			else
			{
				int row = board.length - 1;
				while(row >= 0)
				{
					if(board[row][i] == 'O') break;
					row--;
				}
				board[row-1][i] = 'P';
				if(checkWinner(board,true,4,row-1,i))
				{
					board[row-1][i] = 'O';
					continue;
				}
				else
				{
					board[row-1][i] = 'O';
					acceptableColumns.add(i);
				}
			}
		}
		return acceptableColumns;
	}



	//Copies the board for the dummy method just to be safe. May or may not use
	public static char[][] copyBoard(char[][] board)
	{
		char[][] boardCopy = new char[6][7];
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				boardCopy[i][j] = board[i][j];
			}
		}
		return boardCopy;
	}

	//Calls checkWinner a bunch of times to check the entire board to see if anyone won
	public static boolean winner(char[][] board, boolean checkPlayer, int targetMatches)
	{
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				if(checkWinner(board,checkPlayer,4,i,j)) return true;
			}
		}
		return false;
	}
		

	public static void main(String[] args)
	{
		//Setting up the board and essential variables
		char[][] board = new char[6][7];
		printBoard(board,true);
		boolean playerWon = false;
		boolean computerWon = false;
		//int turns = 0;
		while(!playerWon && !computerWon)
		{
			//Player turn, and checking to see if the player won.
			if(fullBoard(board)) break;		
			playerTurn(board,true);

			if(winner(board,true,4))
			{
				playerWon = true;
				break;
			}
			
			if(fullBoard(board)) break;		

			/*The computers turn. Will descend down the heirarchy of commands until a move is made.
			 * Once it is, and the computer has gone, the changed variable will be set to true.
			 * Afterwards checks to see if the computer won*/
			boolean changed = false;
			if(!changed) changed = smartMove(board,true,4); //If you can win, do it.
			if(!changed) changed = smartMove(board,false,4); //If player can win, block
			if(Math.random() < 0.55)
			{
				if(!changed) changed = smartMove(board,false,3); //If you can get 3 in a row,doit
				if(!changed) changed = smartMove(board,true,3); //If player can get 3 in row blok
			}
			else
			{
				if(!changed) changed = smartMove(board,true,3); //If you can get 3 in a row,doit
				if(!changed) changed = smartMove(board,false,3); //If player can get 3 in row blok
			}
			if(!changed) changed = smartMove(board,true,2); //If you can get 2 in a row, do it.
			if(!changed) dummyMove(board,true); //No moves smart moves avaiable? Randomly place one.

			if(winner(board,false,4)) computerWon = true;
		}

		//Check to see end status of the game, and print out results.
		printBoard(board,false);
		if(playerWon) System.out.println("You won!");
		else if(computerWon) System.out.println("The computer won!");
		else System.out.println("It\'s a tie! Ran out of spaces!");
	}
}
