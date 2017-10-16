public class rref
{
	//Generating the matrix from the input entered from the command line.
	//Enter the the dimensions of the matrix (rows then columns), followed by each row's elements.
	/*EXAMPLE for the matrix 1 2 3
	                         4 5 6
	  You would enter: java rref 2 3 1 2 3 4 5 6
	*/
	public static void main(String[] args)
	{
		int rowN = Integer.parseInt(args[0]);
		int colN = Integer.parseInt(args[1]);
		if(args.length - 2 != rowN*colN)
		{
			System.out.println("Matrix elements do not agree with specified dimensions");
			System.exit(1);
		}
		double matrix[][] = new double[rowN][colN];
		int k = 2;
		for(int i = 0; i < rowN; ++i)
		{
			for(int j = 0; j < colN; ++j,++k)
			{
				matrix[i][j] = Double.parseDouble(args[k]);
			}
		}
		matrix = gauss(matrix);
		matprint(matrix);
	}

	//Prints the contents of a matrix
	public static void matprint(double[][] matrix)
	{
		for(int i = 0; i < matrix.length; ++i)
		{
			for(int j = 0; j < matrix[0].length; ++j)
			{
				System.out.printf("%6.2f ",matrix[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	//Counts the number of zero rows from the bottom up after the matrix is reduced to echelon form
	public static int countZeroRows(double[][] matrix)
	{
		boolean check = true;
		int zCount = 0;
		int startRow = matrix.length-1;
		int numColumns = matrix[0].length;
		while(check)
		{
			int tracker = 0;
			for(int i = 0; i < numColumns ; ++i)
			{
				if(matrix[startRow][i] == 0) tracker += 1;
				
			}
			if(tracker == numColumns)
			{
				zCount += 1;
				if(startRow - 1 < 0) break;
				startRow -= 1;
			}
			else check = false;
		}
		return zCount;
	}

	//Switches a row with specified rows below it in a matrix
	public static double[][] switchRows(double[][] matrix, int rowNumber,int jump)
	{
		double[] temp =  new double[matrix[0].length];
		for(int i = 0; i < matrix[0].length; ++i)
		{
			temp[i] = matrix[rowNumber][i];
			matrix[rowNumber][i] = matrix[rowNumber + jump][i];
			matrix[rowNumber + jump][i] = temp[i];
		}
		return matrix;
	}

	/*Returns the matrix in reduced row echelon form*/
	public static double[][] gauss(double[][] matrix)
	{
		int rowNumber = matrix.length;
		int colNumber = matrix[0].length;
		int top = 0; //Repesents the row index of the matrix during descent to row echelon form
		int bottom = 0; //Represent the row index of the matrix during the ascent to reduced row echelon form
		int pivot  = 0; //Represents the column index of the used by the pivot element
		double kill = 0; //kill is the number to multiply the pivot element by so it can "zero out" the y elements
		if(countZeroRows(matrix) == rowNumber) return matrix;
		for(int k = 0; k < colNumber; ++k)
		{
			for(int j = 1; j < (rowNumber - top); j++)
			{
				//x is the actual pivot element
				if(pivot > colNumber -1) break;
				double x = matrix[top][pivot];
				int increment = 1;
				while((x == 0)&&((increment < (rowNumber - top))))
				{
					matrix = switchRows(matrix,top,increment);
					x = matrix[top][pivot];
					increment += 1;
				}
				//y is each element being altered by the pivot element and its row memebers
				double y = matrix[top+j][pivot];
				while(y == 0 && x == 0)  
				{
					for(int i = (top + j + 1); i < rowNumber; ++i)
					{
						if(matrix[i][pivot] != 0)
						{
							switchRows(matrix,top,i-top);
							x = matrix[top][pivot];
						}
					}
					if(x == 0)
					{
						pivot++;
						if(pivot > colNumber - 1) break;
						x = matrix[top][pivot];
						y = matrix[top+j][pivot];
					}
				}
				if(pivot > colNumber - 1) break;
				else if(y == 0 || x == 0) continue;
				kill = ((-1)*y)/x;
				for(int i = 0; i < matrix[0].length; ++i)
				{
					matrix[top + j][i] = matrix[top][i]*kill + matrix[top + j][i];
					//A tolerance of 1e-10 is used, testing will confirm if it is too low or too high
					if(Math.abs(matrix[top + j][i]) < 1e-10) matrix[top + j][i] = 0;
				}
			}
			pivot++;
			top++;
		}
		//Reduce the bottom row (division by the element);
		bottom = (matrix.length - 1) - countZeroRows(matrix);
		pivot  = (matrix.length - 1) - countZeroRows(matrix);
		int backtrack = pivot;
		double divisor = matrix[bottom][pivot];
		while(divisor == 0)
		{
			pivot += 1;
			divisor = matrix[bottom][pivot];
		}
		for(int i = 0; i < matrix[0].length; ++i)
		{
			matrix[bottom][i] = matrix[bottom][i]/divisor;
		}
		//Begin backtracking to put into reduced row echelon form, once it is in echelon form	
		for(int k = backtrack ; k > 0; k--)
		{
			for(int j = 1; j < bottom + 1; ++j)
			{
				double x = matrix[bottom][pivot];
				double y = matrix[bottom - j][pivot];
				if(y == 0) continue; 
				kill = ((-1)*y)/x;
				for(int i = pivot; i < matrix[0].length; i++)
				{
					matrix[bottom - j][i] = matrix[bottom][i]*kill + matrix[bottom - j][i];
					//A tolerance of 1e-10 is used, testing will confirm if it is too low or too high
					if(Math.abs(matrix[bottom - j][i]) < 1e-10) matrix[bottom -j][i] = 0;
				}
			}
			pivot--;
			bottom--;
			//Always get the next pivot point as 1 for next iteration
			if(pivot < 0) break;
			if(pivot == 0)
			{
				divisor = matrix[bottom][pivot];
			}
			else
			{
				
				boolean tracker = true;
				while(tracker)
				{
					if(pivot-1 >= 0)
					{
						if(matrix[bottom][pivot-1] != 0) pivot--;
						else tracker = false;
					}
					else tracker = false;
				}
				divisor = matrix[bottom][pivot];
			}
			while(divisor == 0)
			{
				pivot--;
				if(pivot < 0) break;
				divisor = matrix[bottom][pivot];
			}
			//If the pivot element has been exhausted all the way to the left during backtrack, stop.
			if(pivot < 0) break;
			if(bottom == 0 && divisor == 0)
			{
				while(pivot != 0)
				{
					pivot--;
					if(matrix[bottom][pivot] != 0) break;
				}
				divisor = matrix[bottom][pivot];
			}
			if(divisor == 0) break;
			for(int i = 0; i < matrix[0].length; ++i)
			{
				matrix[bottom][i] = matrix[bottom][i]/divisor;
			}
		}
		return matrix;
	}
}
