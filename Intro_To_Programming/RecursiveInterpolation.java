//A class for storing x and y data points
class DataPoints
{
	double[] xData;
	double[] yData;

	public DataPoints(int len)
	{
		this.xData = new double[len];
		this.yData = new double[len];
	}
}

public class RecursiveInterpolation
{
	/* A recursive method that linearly interpolates the middle of two data points.
	 * It does this interpolation for every 2 points, and returns new arrays.
	 * The user specifies how many elements they want the new array to be,
	 * and this method recursively calls itself until that number of elements is satisfied.
	 * Each method call will produce an array that is 2*n - 1 data points compared to the original. */
	public static DataPoints interpolate(double[] x, double[] y, int numberOfElements) throws Exception
	{
		//Throws an error if the method is used incorrectly
		if(x.length != y.length || x.length >= numberOfElements)
		{	
			throw new Exception("Dimensions must match, and requested dimensions must be greater "
					    +"then the original");
		}
		DataPoints data = new DataPoints(2*x.length - 1);

		for(int i = 0, j = 0; i < data.xData.length; i++)
		{
			//Add all the original data points where they are supposed to be. (every other value)
			if(i % 2 == 0)
			{
				data.xData[i] = x[j];
				data.yData[i] = y[j];
			}
			else
			{
				//Create the new data points inbetween two pre-existing ones
				data.xData[i] = x[j] + (x[j+1] - x[j])/2.0;
				data.yData[i] = y[j] + (data.xData[i] - x[j])*((y[j+1] - y[j])/(x[j+1] - x[j]));
				j++;
			}
		}
		//If the number of elements isn't yet satisfied, call them ethod again. Otherwise return new array
		if(data.xData.length<numberOfElements) return interpolate(data.xData,data.yData,numberOfElements);
		else return data;
	}

	public static void main(String[] args) throws Exception
	{
		double[] x = new double[200];
		double[]y = new double[200];
		for(int i = 0; i < x.length; i++)
		{
			x[i] = i;
			y[i] = Math.cos(i)*10;
		}

		DataPoints data = interpolate(x,y,300);
		for(int i = 0; i < data.xData.length; i++)
		{
			System.out.println("x is: "+data.xData[i]+"\ty is: "
					+data.yData[i]+" true value is: "+Math.cos(data.xData[i])*10);
		}
		System.out.println("Number of elements is: "+data.xData.length);
	}
}
