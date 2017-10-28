//Finds the eigenvalues of a matrix. Cannot find complex or imaginary eigenvalues. 
//The eigenvalues must be "nice", in the sense that they are not VERY close to eachother in value (1e-5 type close).
//The minimum and maximum eigenvalues in absolute values cannot have negative of positive inverses.
//EXAMPLE: 15 and -15, or -2 and 2.
public class EigenValues
{

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
		double[] vals = eig(matrix);
		for(int i = 0; i < vals.length; i++)
		{
			System.out.print(vals[i]+" ");
			System.out.println();
		}
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

	//Returns the inverse of a matrix by appending the an identity matrix of equal dimensions,
	//and then putting the new matrix into reduced row and echelon form, and extraction the portion that is the inverse
	public static double[][] inv(double[][] matrix)
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
			System.out.println("Matrix not invertible, displaying result with all free variables set to 1");
			double invalid[][] = {{Math.PI}};
			//System.exit(1);
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


	public static double[][] normalize(double[][] vector)
	{
		double norm;
		double total = 0;
		double[][] newVector = new double[vector.length][vector[0].length];
		for(int i = 0; i < vector.length; i++)
		{
			total += vector[i][0]*vector[i][0];
		}
		norm = Math.sqrt(total);
		
		for(int i = 0; i < vector.length; i++)
		{
			newVector[i][0] = vector[i][0]/norm;
		}
		return newVector;
	}



	public static double[][] vectorCopy(double[][] vector)
	{
		double[][] newVector = new double[vector.length][vector[0].length];
		for(int i = 0; i < vector.length; i++)
		{
			newVector[i][0] = vector[i][0];
		}
		return newVector;
	}

	//Returns a matrix with mu subtracted from the diagonal.
	public static double[][] muMinus(double[][] matrix,double mu)
	{
		double[][] newMatrix = new double[matrix.length][matrix.length];
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix.length; j++)
			{
				if(j == i) newMatrix[i][j] = matrix[i][j] - mu;
				else newMatrix[i][j] = matrix[i][j];
			}
		}
		return newMatrix;
	}

	//Calculates the eigenvalues of a matrix
	public static double[] eig(double[][] matrix)
	{
		int numberOfEigs = matrix.length;
		int length;
		int totalEigen = 0;
		double[] eigenValues = new double[numberOfEigs];
		double EPSILON = 1e-14;
		double zeroTolerance = 1e-8;
		double eigenvalue;
		double[][] checkEigen = new double[matrix.length][1];

		double[][] oldb = new double[matrix.length][1];
		double[][] b = new double[matrix.length][1];

		for(int i = 0; i < matrix.length; i++)
		{
			oldb[i][0] = Math.random();
			b[i][0] = Math.random();
		}
		double[][] nextb = normalize(matmult(matrix,b));

		while(Math.abs(nextb[0][0] - b[0][0]) > EPSILON && Math.abs(nextb[0][0] - oldb[0][0]) > EPSILON)
		{
			oldb = vectorCopy(b);
			b = vectorCopy(nextb);
			nextb = normalize(matmult(matrix,b));
		}

		eigenvalue = 0;
		length = nextb.length;
		checkEigen = matmult(matrix,nextb);
		for(int i = 0; i < nextb.length; i++)
		{
			if(Math.abs(nextb[i][0]) < zeroTolerance)
			{
				length--;
				continue;
			}
			eigenvalue += checkEigen[i][0]/nextb[i][0];
		}
		eigenvalue /= length;
		eigenValues[0] = eigenvalue;
		totalEigen++;
		
		double[][] matrixInverse = inv(matrix);
		if(matrixInverse[0][0] == Math.PI)
		{
		       	eigenValues[1] = 0;
			System.out.println("HEY");
		}
		else
		{
			for(int i = 0; i < matrix.length; i++)
			{
				oldb[i][0] = Math.random();
				b[i][0] = Math.random();
			}
			nextb = normalize(matmult(matrixInverse,b));

			while(Math.abs(nextb[0][0] - b[0][0]) > EPSILON && Math.abs(nextb[0][0] - oldb[0][0]) > EPSILON)
			{
				oldb = vectorCopy(b);
				b = vectorCopy(nextb);
				nextb = normalize(matmult(matrixInverse,b));
			}

			eigenvalue = 0;
			length = nextb.length;
			checkEigen = matmult(matrix,nextb);
			for(int i = 0; i < nextb.length; i++)
			{
				if(Math.abs(nextb[i][0]) < zeroTolerance)
				{
					length--;
					continue;
				}
				eigenvalue += checkEigen[i][0]/nextb[i][0];
			}
			eigenvalue /= length;
			eigenValues[1] = eigenvalue;
			totalEigen++;
		}
		
		double max = Math.abs(eigenValues[0]);
		double min = Math.abs(eigenValues[1]);
		double difference = max - min;
		double muStep = (difference/(matrix.length*4))*0.997;
		double mu = max*(-1);

		while(totalEigen < matrix.length)
		{
			if(mu > min*(-1) && mu < min) mu = min;
			mu += muStep;
			double[][] muMatrix = inv(muMinus(matrix,mu));

			for(int i = 0; i < matrix.length; i++)
			{
				oldb[i][0] = Math.random();
				b[i][0] = Math.random();
			}
			nextb = normalize(matmult(muMatrix,b));

			while(Math.abs(nextb[0][0] - b[0][0]) > EPSILON && Math.abs(nextb[0][0] - oldb[0][0]) > EPSILON)
			{
				oldb = vectorCopy(b);
				b = vectorCopy(nextb);
				nextb = normalize(matmult(muMatrix,b));
			}

			eigenvalue = 0;
			length = nextb.length;
			checkEigen = matmult(matrix,nextb);
			for(int i = 0; i < nextb.length; i++)
			{
				if(Math.abs(nextb[i][0]) < 1e-2)
				{
					length--;
					continue;
				}
				eigenvalue += checkEigen[i][0]/nextb[i][0];
			}
			eigenvalue /= length;
			boolean verdict = true;
			for(int i = 0; i < totalEigen; i++)
			{
				if(Math.abs(eigenvalue - eigenValues[i]) < 1e-5)
				{
					verdict = false;
					break;
				}
			}
			if(verdict)
			{
				eigenValues[totalEigen] = eigenvalue;
				totalEigen++;
			}
		}
		return eigenValues;
	}
}
