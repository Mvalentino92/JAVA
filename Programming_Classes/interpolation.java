public class interpolation
{
	/* Method to interpolate data.
	 * Returns a double value */
	public static double interpolate(double[] x, double[] y, double xNew)
	{
		//If the dimensions of the arrays dont match, return an error message
		if(x.length != y.length)
		{
			System.out.println("ERROR: Dimensions of the supplied arrays do match");
			return 0; //You have to return a double, since that is what the return type is. The default is 0.
		}
		//Check to see if the specified x value is within the bounds of the data set
		else if(xNew < x[0] || xNew > x[x.length-1])
		{
			System.out.println("ERROR: Attempting to interpolate a data point not within the bounds of the data set."
					+"\nUse extrapolation instead.");
			return 0;
		}
		else
		{
			//Iterating the x array, to find the flank of the xNew value
			//Note the "x.length - 1", because I want to check "i+1" each iteration. 
			for(int i = 0; i < x.length - 1; i++)
			{
				//If xNew already exists as a data point, notify the user and return the corresponding y value
				if(xNew == x[i])
				{
					System.out.println("Data point already exists. Returning corresponding y value");
					return y[i];
				}
				//Checking to find if xNew is greater then the current x value, and less than the next one
				if(xNew > x[i] && xNew < x[i+1])
				{
					//Once found, i is now our left flank, and i + 1 is now our right flank
					double x1 = x[i];
					double x2 = x[i+1];
					double y1 = y[i];
					double y2 = y[i+1];
					//Using the equation to interpolate and return value
					double yNew = y1 + (xNew - x1)*((y2 - y1)/(x2 - x1));
					return yNew;
				}
			}
			//Purposely structured like this. If the code reaches this point, it means something went wrong that I didn't anticipate
			System.out.println("UNKNOWN ERROR");
			return 0;
		}
	}

	public static void main(String[] args)
	{
		double[] x = new double[25];
		double[] y = new double[25];

		double start = -7.0;
		for(int i = 0; i < x.length; i++)
		{
			x[i] = start;
			y[i] = 2.0*(start*start) - (1.5*(start*start*start));
			start++;
		}

		double test = 13.11;
		double trueVal = 2.0*(test*test) - (1.5*(test*test*test));
		System.out.println("The true value is: "+trueVal+" and the interpolated value is: "+interpolate(x,y,test)); //This will work
		System.out.println();

		interpolate(x,y,-100); //This will throw out of range error
		System.out.println();

		System.out.println(interpolate(x,y,8)); //This will notify this point existed and return the corresponding value
		System.out.println();

		double[] xx = {1,2,3,3};
		double[] yy = {1,2,3,4,5,56,6};
		interpolate(xx,yy,5);  //This will print an error that he dimensions do not match
	}
}
