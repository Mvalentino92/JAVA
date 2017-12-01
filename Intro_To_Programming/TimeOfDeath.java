import java.util.*;
public class TimeOfDeath
{
	public static void main(String[] args)
	{
		//Initializing the scanner, and the ArrayList to initialize take an unknown amount of values.
		Scanner input = new Scanner(System.in);
		int dimensionCount = 0;
		ArrayList<Double> xValues = new ArrayList<>();
		ArrayList<Double> yValues = new ArrayList<>();
		
		//Getting the input from the user
		System.out.println("Oh no! You've found a dead body."
				+"\nLooks like we need to figure out when this person died."
				+"\nFirst things first, we need times and temperatures."
				+"\nMake sure you enter the times in military time\n");
		while(true)
		{
			System.out.print("Enter a time: ");
			if(input.hasNextDouble()) xValues.add(input.nextDouble());
			else break;
			System.out.print("Enter corresponding temperature: ");
			if(input.hasNextDouble()) yValues.add(input.nextDouble());
			else break;
		}
		input.next();

		//Converting xValues to normal array
		double[] xVals = new double[xValues.size()];
		for(int i = 0; i < xVals.length; i++)
		{
			xVals[i] = xValues.get(i); 
		}

		//Converting yValues to a column vector
		double[][] yVals = new double[xVals.length][1];
		for(int i = 0; i < xVals.length; i++)
		{
			yVals[i][0] = yValues.get(i);
		}

		//Creating the systems of equations matrix for linspace
		double[][] mat = new double[xVals.length][xVals.length];

		//Adding the initial columns of all 1's
		for(int i = 0; i < xVals.length; i++)
		{
			mat[i][0] = 1;
		}

		//Iterating through and calculating the other values with Crammers Rule
		for(int i = 0; i < xVals.length; i++)
		{
			for(int j = 1; j < xVals.length; j++)
			{
				mat[i][j] = Math.pow(xVals[i],j);
			}
		}	

		//Solving the system of equations
		double[][] sol = linsolve.linsolve(mat,yVals);
		
		//Converting the column vector sol, into an array
		double[] coef = new double[xVals.length];
		for(int i = 0; i < xVals.length; i++)
		{
			coef[i] = sol[i][0];
		}

		//Gathering information to solve for k
		System.out.print("What was the ambient temperature"
			       +"during a period where you knew the bodies temperature?: ");
		double ambientTemp = input.nextDouble();
		System.out.print("What was the temperature of the body in the first half of that interval?: ");
		double tempInitial = input.nextDouble();
		System.out.print("What time was it at this half of the interval?"
				+"\n(Remeber to answer in military time. Look back at your previous inputs): ");
		double timeBodyWasFound = input.nextDouble();
		System.out.print("What was the temperature of the body in the second half of that interval?: ");
		double tempFinal = input.nextDouble();
		System.out.print("How must time passed during that intervel?: ");
		double timeInterval = input.nextDouble();

		//Solving for k
		double k = (-1/timeInterval)*Math.log((tempFinal - ambientTemp)/(tempInitial - ambientTemp));

		//Getting additional information before the euler steps
		System.out.print("Finally, what was the initial temperature of the person before they died?: ");
		double bodyTemp = input.nextDouble();
		
		//Starting the euler steps (doing midPoint)
		double h = 1e-5;
		while(tempInitial < bodyTemp)
		{
			double ambient = coefCompute(coef,timeBodyWasFound);
			double rateOfChange = k*(tempInitial - ambient);
			double mPoint = tempInitial + rateOfChange*h/2;
			double mRateOfChange = k*(mPoint - ambient); 
			tempInitial += mRateOfChange*h;
			timeBodyWasFound -= h;
		}

		//Printing out when the person died
		int truncate = (int)(timeBodyWasFound);
		int timeConvert = (int)((timeBodyWasFound*60) - (truncate*60));
		if(timeConvert >= 10)
		{
			System.out.println("According to the departments calculations,"
				       +"the person died at around "+truncate+":"+timeConvert);
		}
		else
		{
			System.out.println("According to the departments calcualtions,"
				       +"the person died at around "+truncate+":0"+timeConvert);
		}
	}

	//Setting up a function to compute the rate of change with coef obtained from linsolve function.
	static double coefCompute(double[] coefs, double xVal)
	{
		double retval = coefs[0];
		for(int i = 1; i < coefs.length; i++)
		{
			retval += coefs[i]*Math.pow(xVal,i);
		}
		return retval;
	}
}
	
		

