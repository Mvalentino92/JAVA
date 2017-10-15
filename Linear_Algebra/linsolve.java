public class linsolve
{
	//Generating the matrix from the input entered from the command line. 
	public static void main(String[] args)
	{
		int rowN = Integer.parseInt(args[0]);
		int colN = Integer.parseInt(args[1]);
		if(args.length -2 != rowN*colN)
		{
			System.out.println("Matrix elements do not agree with specified dimensions");
			System.exit(1);
		}
		double matrix[][] = new double[rowN][colN-1];
		double b[][] = new double[rowN][1];
		int k = 2;
		for(int i = 0; i < rowN; ++i)
		{
			for(int j = 0; j < colN; ++j,++k)
			{
				if(j == colN-1) b[i][0] = Double.parseDouble(args[k]);
				else matrix[i][j] = Double.parseDouble(args[k]);
			}
		}
		matrix = linsolve(matrix,b);
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
		if(countZeroRows(matrix) == colNumber) return matrix;
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
	//Returns the inverse of a matrix if possible
	public static double[][] Inverse(double[][] matrix)
	{
		int rowNumber = matrix.length;
		int colNumber = matrix[0].length;
		double newMatrix[][] = new double[rowNumber][colNumber*2];
		double identity[][] = new double[rowNumber][colNumber];
		double inverse[][] = new double[rowNumber][colNumber];
		for(int i = 0; i < rowNumber; ++i)
		{
			for(int j = colNumber,k = 0; j < colNumber*2; ++j, ++k)
			{
				if((j-colNumber) == i)
				{
				       	newMatrix[i][j] = 1;
					identity[i][j-colNumber] = 1;
				}
				newMatrix[i][k] = matrix[i][k];
			}
		}
		newMatrix = gauss(newMatrix);
		boolean verdict = true;
		for(int i = 0; i < rowNumber; ++i)
		{
			for(int j = 0; j < colNumber; ++j)
			{
				if(newMatrix[i][j] != identity[i][j])
				{	
					verdict = false; 
					break;
				}
			}
			if(verdict == false) break;
		}
		if(verdict) 
		{
			for(int i = 0; i < rowNumber; ++i)
			{
				for(int j = 0; j < colNumber; ++j)
				{
					inverse[i][j] = newMatrix[i][j+colNumber];
				}
			}
			return inverse;
		}
		else
		{
			System.out.println("Matrix not invertible");
			double invalid[][] = {{Math.PI}};
			System.exit(1);
			return invalid;
		}
	}

	//Multiplies two matrices
	public static double[][] matmult(double[][] matone, double[][] mattwo)
	{
		int matoneRows = matone.length;
		int matoneColumns = matone[0].length;
		int mattwoRows = mattwo.length;
		int mattwoColumns = mattwo[0].length;
		if(matoneColumns == mattwoRows)
		{
			int update = 0;
			double matrix[][] = new double[matoneRows][mattwoColumns];
			for(int k = 0; k < mattwoColumns; ++k)
			{
				for(int i = 0; i < matoneRows; ++i)
				{
					double total = 0;
					for(int j = 0; j < mattwoRows; ++j)
					{
						total += matone[i][j]*mattwo[j][update];
					}
					matrix[i][update] = total;
				}
				update += 1;
			}
			return matrix;
		}
		else 
		{
			System.out.println("Matrix multiplication not possible"); 
			double invalid[][] = {{Math.PI}}; 
			System.exit(1);
			return invalid; 
		}
	}

	//Method that returns the coefficient vector "x" of a system of equations with a unique solution
	public static double[][] linsolve(double[][] A, double[][] b)
	{
		A = Inverse(A);
		//If either the inverse method or multiplication method arent successful, PI is returned as a tracker
		/*if(A[0][0] == Math.PI) 
		{
			System.out.println("System is not unique");
		       	System.exit(1); 
		}*/
		double solution[][] = matmult(A,b);
		/*if(solution[0][0] == Math.PI)
		{
			System.out.println("System is not unique");
			System.exit(1);
		}*/
		return solution;
	}
}
