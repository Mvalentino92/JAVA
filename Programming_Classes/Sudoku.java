public class Sudoku
{
	public static void main(String[] args)
	{
		int[][] mat = {{0,0,0,9,0,2,0,0,0},
                               {0,4,0,0,0,0,0,5,0},
			       {0,0,2,0,0,0,3,0,0},
			       {2,0,0,0,0,0,0,0,7},
			       {0,0,0,4,5,6,0,0,0},
			       {6,0,0,0,0,0,0,0,9},
			       {0,0,7,0,0,0,8,0,0},
			       {0,3,0,0,0,0,0,4,0},
			       {0,0,0,2,0,7,0,0,0}};
		solveSudoku(mat);
	}

	//Check to see if placing this number violates the current row
	public static boolean checkRow(int[][] mat, int row, int col, int num)
	{
		//Check to the left of this spot (by changing the col)
		int left = col - 1;
		while(left > -1)
		{
			if(mat[row][left] == num) return false;
			left--;
		}

		//Check to the right of this spot
		int right = col + 1;
		while(right < mat[row].length)
		{
			if(mat[row][right] == num) return false;
			right++;
		}
		
		//If we reach here, there were no violations
		return true;
	}

	//Check to see if placing this number violates the current col
	public static boolean checkCol(int[][] mat, int row, int col, int num)
	{
		//Check up from this spot (by changing the row)
		int up = row - 1;
		while(up > -1)
		{	
			if(mat[up][col] == num) return false;
			up--;
		}
		
		//Check down from this spot
		int down = row + 1;
		while(down < mat.length)
		{	
			if(mat[down][col] == num) return false;
			down++;
		}

		//If we made it here, no violations
		return true;
	}

	//Check to see if placing this number violates the current square
	public static boolean checkSquare(int[][] mat, int row, int col, int num)
	{
		//Init where to start to check square (want to start top left corner)
		int startRow = 3;
		int startCol = 3;	

		//Get startRow
		if(row < 3) startRow = 0;
		else if(row > 5) startRow = 6;

		//Get startCol
		if(col < 3) startCol = 0;
		else if(col > 5) startCol = 6;

		//Iterate the square
		for(int i = startRow; i < startRow + 3; i++)
		{
			for(int j = startCol; j < startCol + 3; j++)
			{
				if(mat[i][j] == num) return false;
			}
		}

		//If we made it here, no violations
		return true;
	}

	//Checks all 3 instances (rows, cols, and squares)
	public static boolean isValid(int[][] mat, int row, int col, int num)
	{
		//Return all 3 checks
		return checkRow(mat,row,col,num) &&
                       checkCol(mat,row,col,num) &&
		       checkSquare(mat,row,col,num);
	}

	//The BackTracking Algorithm.
	public static boolean backTrack(int[][] mat, int row, int col)
	{
		//If we made it to the end, return true
		if(row == mat.length) return true;

		//Assume didn't find solution
		boolean foundSolution = false;
		int incrementRow = col == mat[row].length - 1 ? 1 : 0;

		//If we can't place anything here, go forward without change
		if(mat[row][col] != 0)
		{
			foundSolution = backTrack(mat,row + incrementRow,
                                                 (col + 1) % mat[row].length);
		}
		//Otherwise, attempt to populate this space
		else
		{
	
			//Begin to iterate all possible numbers, and check if we can add them
			for(int n = 1; n <= 9; n++)
			{
				//If it's not a valid move, keep going
				if(!isValid(mat,row,col,n)) continue;

				//Otherwise add it, and recur
				mat[row][col] = n;
				foundSolution = backTrack(mat,row + incrementRow,
                                                                 (col + 1) % mat[row].length);
				
				//If we found a solution, stop
				if(foundSolution) break;
				
				//Otherwise, keep checking and erase this entry
				mat[row][col] = 0;
			}
		}
		return foundSolution;
	}

	//Solves a Sudoku Board using BackTracking, and prints result
	public static void solveSudoku(int[][] mat)
	{
		double start = System.nanoTime();
		boolean solved = backTrack(mat,0,0);
		double time = (System.nanoTime() - start)/1e9;
		System.out.println("Solved?: "+solved+", and took "+time+" seconds:");
		for(int i = 0; i < mat.length; i++)
		{
			for(int j = 0; j < mat[i].length; j++) System.out.print(mat[i][j]+" ");
			System.out.println();
		}
	}
}
