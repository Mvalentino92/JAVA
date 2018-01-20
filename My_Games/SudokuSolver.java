import java.util.*;
//I'll use this class to keep track of important information about each space. Mainly the avaiable moves.
class space
{
	boolean alreadyExists = false;
	ArrayList<Integer> goodNumbers = new ArrayList<>();
	int placedNumber = 0;

	public space(){}
}

public class SudokuSolver
{
	//Keeps track of how many iterations it takes to solve.
	public static int count = 0;
	public static int totalCount = 0;

	//Method to count if the board is complete.
	public static boolean completeBoard(space[][] spaceBoard)
	{
		for(int i = 0; i < spaceBoard.length; i++)
		{
			for(int j = 0; j < spaceBoard[0].length; j++)
			{
				if(spaceBoard[i][j].placedNumber == 0) return false;
			}
		}
		return true;
	}

	//Checks to make sure the final board is correct.
	public static boolean isCorrect(space[][] spaceBoard)
	{
		for(int i = 0; i < spaceBoard.length; i++)
		{
			for(int j = 0; j < spaceBoard[0].length; j++)
			{
				//Begin checking the row for this space
				for(int jj = 0; jj < spaceBoard[0].length; jj++)
				{
					if(j == jj) continue;
					if(spaceBoard[i][j].placedNumber == spaceBoard[i][jj].placedNumber)
					{
						return false;
					}
				}
				//Begin checking the column
				for(int ii = 0; ii < spaceBoard.length; ii++)
				{
					if(i == ii) continue;
					if(spaceBoard[i][j].placedNumber == spaceBoard[ii][j].placedNumber)
					{
						return false;
					}
				}
				//Begin checking the square for this space
				int startRow = i - (i % 3);
				int startCol = j - (j % 3);
				for(int ii = startRow; ii < startRow + 3; ii++)
				{
					for(int jj = startCol; jj < startCol + 3; jj++)
					{
						if(i == ii && j == jj) continue;
					       if(spaceBoard[i][j].placedNumber == spaceBoard[ii][jj].placedNumber)
						{
							return false;
						}
					}
				}
			}
		}
		return true;
	}


	//Method for creating the space board.
	public static space[][] createSpaceBoard(int[][] integerBoard)
	{
		space[][] spaceBoard = new space[9][9];
		for(int i = 0; i < integerBoard.length; i++)
		{
			for(int j = 0; j < integerBoard[0].length; j++)
			{
				int currentNumber = integerBoard[i][j];
				spaceBoard[i][j] = new space();
				if(currentNumber != 0)
				{
					spaceBoard[i][j].placedNumber = currentNumber;
					spaceBoard[i][j].alreadyExists = true;
				}
			}
		}
		return spaceBoard;
	}

	//Method for getting the first avaiable spot you can place move (checked from left to right, up to down)
	public static int[] firstSpot(space[][] spaceBoard)
	{
		int row = 0;
		int col = 0;
		for(int i = 0; i < spaceBoard.length; i++)
		{
			for(int j = 0; j < spaceBoard[0].length; j++)
			{
				if(spaceBoard[i][j].placedNumber == 0)
				{
					row = i;
					col = j;
					int[] first = {row,col};
					return first;
				}
			}
		}
		return null;
	}

	//Method for getting the last available spot you can place move (checked from left to right, up to down)
	public static int[] lastSpot(space[][] spaceBoard)
	{
		int row = 0;
		int col = 0;
		for(int i = spaceBoard.length - 1; i > -1; i--)
		{
			for(int j = spaceBoard[0].length - 1; j > -1; j--)
			{
				if(spaceBoard[i][j].placedNumber == 0)
				{
					row = i;
					col = j;
					int[] last = {row,col};
					return last;
				}
			}
		}
		return null;
	}

	//Method for printing the board.
	public static void printBoard(space[][] spaceBoard)
	{
		for(int i = 0; i < spaceBoard.length; i++)
		{
			if(i == 3 || i == 6) System.out.println();
			for(int j = 0; j < spaceBoard[0].length; j++)
			{
				if(j == 3 || j == 6) System.out.print(" ");
				System.out.print(spaceBoard[i][j].placedNumber+" ");
			}
			System.out.println();
		}
	}

	//Method to change the current coordinates. Depending on if your going forward or back.
	public static int[] changeSpaces(space[][] spaceBoard, int row, int col, boolean forward,int[] last)
	{
		int[] nextMove = new int[2];
		//If your on moving forward from last space, you've done it!. Dont overstep the array.
		if(forward)
		{
			if(row == last[0] && col == last[1]) return nextMove;
			do
			{
				if(col == 8)
				{
					row++;
					col = 0;
				}
				else col++;
			} while(spaceBoard[row][col].alreadyExists);
			nextMove[0] = row;
			nextMove[1] = col;
			return nextMove;
		}
		else
		{
			do
			{
				if(col == 0)
				{
					row--;
					col = 8;
				}
				else col--;
			} while(spaceBoard[row][col].alreadyExists);
			nextMove[0] = row;
			nextMove[1] = col;
			return nextMove;
		}
	}
	


	//**********************Methods for ultimately generating the list of possible moves********************
	
	//Generate squares, all 9 of them.
	public static int[][] generateSquares(int iStart, int jStart)
	{
		int[][] square = new int[9][2];
		int count = 0;
		for(int i = iStart; i < iStart + 3; i++)
		{
			for(int j = jStart; j < jStart + 3; j++)
			{
				square[count][0] = i;
			       	square[count++][1] = j;
			}
		}
		return square;
	}	

	//Find which square the current coordinates belong to, and then return a list of good numbers
	public static ArrayList<Integer> goodSquareNumbers(ArrayList<int[][]> squareList,space[][] spaceBoard, int row, int col)
	{
		ArrayList<Integer> goodNumbers = new ArrayList<>();
		for(int i = 0; i < squareList.size(); i++)
		{
			for(int j = 0; j < squareList.get(i).length; j++)
			{
				if(squareList.get(i)[j][0] == row && squareList.get(i)[j][1] == col)
				{
					//Once you find which one your in, generate the good numbers
					for(int k = 1; k <= 9; k++) goodNumbers.add(k);

					for(int ii = 0; ii < 9; ii++)
					{
						int indexNumber = goodNumbers.indexOf(spaceBoard[squareList.get(i)[ii][0]][squareList.get(i)[ii][1]].placedNumber);
						if(indexNumber > -1) goodNumbers.remove(indexNumber);
					}
					return goodNumbers;
				}
				else continue;
			}
		}
		return null;
	}

	//Returns a list of the current coordinates good numbers for that row.
	public static ArrayList<Integer> goodRowNumbers(space[][] spaceBoard, int row, int col)
	{
		ArrayList<Integer> goodNumbers = new ArrayList<>();
		for(int i = 1; i <= 9; i++) goodNumbers.add(i);

		for(int j = 0; j < spaceBoard[row].length; j++)
		{
			int indexNumber = goodNumbers.indexOf(spaceBoard[row][j].placedNumber);
			if(indexNumber > -1) goodNumbers.remove(indexNumber);
		}
		return goodNumbers;
	}

	//Returns a list of the current coordinates good numbers for that column.
	public static ArrayList<Integer> goodColNumbers(space[][] spaceBoard, int row, int col)
	{
		ArrayList<Integer> goodNumbers = new ArrayList<>();
		for(int i = 1; i <= 9; i++) goodNumbers.add(i);

		for(int j = 0; j < spaceBoard.length; j++)
		{
			int indexNumber = goodNumbers.indexOf(spaceBoard[j][col].placedNumber);
			if(indexNumber > -1) goodNumbers.remove(indexNumber);
		}
		return goodNumbers;
	}

	//Returns the final list of good numbers for that current position.
	public static ArrayList<Integer> finalGoodNumbers(ArrayList<Integer> A, ArrayList<Integer> B, ArrayList<Integer> C)
	{
		ArrayList<Integer> AB = new ArrayList<>();
		for(int i = 0; i < A.size(); i++)
		{
			for(int j = 0; j < B.size(); j++)
			{
				if(A.get(i) == B.get(j)) AB.add(A.get(i));
			}
		}

		//Check if the list is empty. If it is, then return the empty list. No 3 way similarities exist.
		if(AB.isEmpty()) return AB;
		
		//If it's not empty, cross check it new list ABC with C.
		ArrayList<Integer> ABC = new ArrayList<>();
		for(int i = 0; i < AB.size(); i++)
		{
			for(int j = 0; j < C.size(); j++)
			{
				if(AB.get(i) == C.get(j)) ABC.add(AB.get(i));
			}
		}

		return ABC;
	}

	//Generates calls previous methods to get the final good move list, and returns true if its not empty.
	public static boolean goodMove(ArrayList<int[][]> squareList, space[][] spaceBoard, int row, int col)
	{
		//Generate the three lists of numbers.
		ArrayList<Integer> A = goodSquareNumbers(squareList, spaceBoard, row, col);
		ArrayList<Integer> B = goodRowNumbers(spaceBoard, row, col);
		ArrayList<Integer> C = goodColNumbers(spaceBoard, row, col);

		/*Use the three lists to assign the final list to the current spots "good numbers" data field
		 * If it's not empty, return true. Otherise, return false*/
		spaceBoard[row][col].goodNumbers = finalGoodNumbers(A, B, C);
		if(!spaceBoard[row][col].goodNumbers.isEmpty()) return true;
		else return false;
	}

	//************************Main recursive method**********************************************//
	
	//Method that recursively searches for the solution, using backtracking.
	public static boolean findSolution(ArrayList<int[][]> squareList, space[][] spaceBoard, int row, int col,boolean forward, int[] last)
	{
		count++;
		//If your going to run out of stacks soon, return to free, and pick up where you left off.
		if(count > 3000 && forward) return true;
		//If the last space was filled (since it's empty, return true).
		if(spaceBoard[last[0]][last[1]].placedNumber != 0) return true;

		boolean hasMoves = false;
		if(forward) hasMoves = goodMove(squareList,spaceBoard,row,col);
		if(hasMoves || !spaceBoard[row][col].goodNumbers.isEmpty())
		{
			int numberToPlace = spaceBoard[row][col].goodNumbers.get(0);
			spaceBoard[row][col].placedNumber = numberToPlace;
			spaceBoard[row][col].goodNumbers.remove(0);
			
			int[] newCoordinates = changeSpaces(spaceBoard,row,col,true,last);
			row = newCoordinates[0];
			col = newCoordinates[1];
			return findSolution(squareList,spaceBoard,row,col,true,last);
		}
		else
		{
			int[] newCoordinates = changeSpaces(spaceBoard,row,col,false,last);
			row = newCoordinates[0];
			col = newCoordinates[1];
			spaceBoard[row][col].placedNumber = 0;
			return findSolution(squareList,spaceBoard,row,col,false,last);
		}
	}

	public static void main(String[] args)
	{
		//*****************************Creating all the boards*****************************

		space[][] spaceBoard;
		//Create the first board.
		int[][] integerBoard1 = {{0,0,0,2,6,0,7,0,1},{6,8,0,0,7,0,0,9,0},{1,9,0,0,0,4,5,0,0},{8,2,0,1,0,0,0,4,0},{0,0,4,6,0,2,9,0,0},{0,5,0,0,0,3,0,2,8},{0,0,9,3,0,0,0,7,4},{0,4,0,0,5,0,0,3,6},{7,0,3,0,1,8,0,0,0}};

		//Create the second board.
		int[][] integerBoard2 = {{1,0,0,4,8,9,0,0,6},{7,3,0,0,0,0,0,4,0},{0,0,0,0,0,1,2,9,5},{0,0,7,1,2,0,6,0,0},{5,0,0,7,0,3,0,0,8},{0,0,6,0,9,5,7,0,0},{9,1,4,6,0,0,0,0,0},{0,2,0,0,0,0,0,3,7},{8,0,0,5,1,2,0,0,4}};

		//Create the third board
		int[][] integerBoard3 = {{0,2,0,6,0,8,0,0,0},{5,8,0,0,0,9,7,0,0},{0,0,0,0,4,0,0,0,0},{3,7,0,0,0,0,5,0,0},{6,0,0,0,0,0,0,0,4},{0,0,8,0,0,0,0,1,3},{0,0,0,0,2,0,0,0,0},{0,0,9,8,0,0,0,3,6},{0,0,0,3,0,6,0,9,0}};

		//Creating fourth board.
		int[][] integerBoard4 = {{0,0,0,6,0,0,4,0,0},{7,0,0,0,0,3,6,0,0},{0,0,0,0,9,1,0,8,0},{0,0,0,0,0,0,0,0,0},{0,5,0,1,8,0,0,0,3},{0,0,0,3,0,6,0,4,5},{0,4,0,2,0,0,0,6,0},{9,0,3,0,0,0,0,0,0},{0,2,0,0,0,0,1,0,0}};

		//Creating fifth board
		int[][] integerBoard5 = {{2,0,0,3,0,0,0,0,0},{8,0,4,0,6,2,0,0,3},{0,1,3,8,0,0,2,0,0},{0,0,0,0,2,0,3,9,0},{5,0,7,0,0,0,6,2,1},{0,3,2,0,0,6,0,0,0},{0,2,0,0,0,9,1,4,0},{6,0,1,2,5,0,8,0,9},{0,0,0,0,0,1,0,0,2}};

		//Creating six board
		int[][] integerBoard6 = {{0,2,0,0,0,0,0,0,0},{0,0,0,6,0,0,0,0,3},{0,7,4,0,8,0,0,0,0},{0,0,0,0,0,3,0,0,2},{0,8,0,0,4,0,0,1,0},{6,0,0,5,0,0,0,0,0},{0,0,0,0,1,0,7,8,0},{5,0,0,0,0,9,0,0,0},{0,0,0,0,0,0,0,4,0}};

		//Making a list of all the boards to iterate.
		ArrayList<int[][]> allBoards = new ArrayList<>();
		allBoards.add(integerBoard1);
		allBoards.add(integerBoard2);
		allBoards.add(integerBoard3);
		allBoards.add(integerBoard4);
		allBoards.add(integerBoard5);
		allBoards.add(integerBoard6);

		//Generate the arrays of coordinates for the squares
		int[][] leftUpper = generateSquares(0,0);
		int[][] middleUpper = generateSquares(0,3);
		int[][] rightUpper = generateSquares(0,6);
		int[][] leftMid = generateSquares(3,0);
		int[][] middleMid = generateSquares(3,3);
		int[][] rightMid = generateSquares(3,6);
		int[][] leftLower = generateSquares(6,0);
		int[][] middleLower = generateSquares(6,3);
		int[][] rightLower = generateSquares(6,6);
		
		//Make an ArrayList of all the squares so you can iterate and find which your in
		ArrayList<int[][]> squareList = new ArrayList<>();
		squareList.add(leftUpper);
		squareList.add(middleUpper);
		squareList.add(rightUpper);
		squareList.add(leftMid);
		squareList.add(middleMid);
		squareList.add(rightMid);
		squareList.add(leftLower);
		squareList.add(middleLower);
		squareList.add(rightLower);

		//*************************Solving the boards*********************************//
		for(int i = 0; i < allBoards.size(); i++)
		{
			spaceBoard = createSpaceBoard(allBoards.get(i));
			System.out.println("Original board "+(i+1)+":");
			printBoard(spaceBoard);
			totalCount = 0;
			while(!completeBoard(spaceBoard))
			{
				int[] firstMove = firstSpot(spaceBoard);
				int[] lastMove = lastSpot(spaceBoard);
				count = 0;
				findSolution(squareList,spaceBoard,firstMove[0],firstMove[1],true,lastMove);
				totalCount += count;
			}
			if(isCorrect(spaceBoard))
			{
				System.out.println("\nSolved board "+(i+1)+":");
				printBoard(spaceBoard);
				System.out.print("\nIt took "+totalCount+" steps to solve.\n");
			}
			else System.out.println("No solution found");
			System.out.println("*****************************************************");
			totalCount = 0;
		}
	}
}
