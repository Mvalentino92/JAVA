import java.util.*;
public class RadiixSort
{
	//Returns the min and max of the array
	public static int[] getMinMax(int[] arr)
	{
		int[] retval = new int[2];
		retval[0] = arr[0];
		retval[1] = arr[0];

		for(int i = 1; i < arr.length; i++)
		{
			retval[0] = arr[i] < retval[0] ? arr[i] : retval[0];
			retval[1] = arr[i] > retval[1] ? arr[i] : retval[1];
		}

		return retval;
	}

	//Stand alone coutning sort
	public static int[] countingSort(int[] arr)
	{
		int[] minmax = getMinMax(arr);
		int mod = Math.max(Math.abs(minmax[0]),Math.abs(minmax[1]));
		return cs(arr,minmax[0],minmax[1],1,mod + 1);
	}

	//Actual working counting sort
	public static int[] cs(int[] arr, int min, int max, int div, int mod)
	{
		int[] occur = new int[max - min + 1];
		int shift = min*-1;
		for(int i = 0; i < arr.length; i++) occur[(arr[i])/div % mod + shift]++;
		for(int i = 1; i < occur.length; i++) occur[i] += occur[i-1];

		int[] retval = new int[arr.length];
		for(int i = arr.length-1; i > -1; i--) retval[--(occur[(arr[i])/div % mod + shift])] = arr[i];
		return retval;
	}
	
	//Radiix Sort
	public static int[] radiixSort(int[] arr)
	{
		//Found how many digits the numbers are
		int bound = 10;
		int d = 1;
		int num = Math.abs(arr[0]);
		while(num - bound >= 0)
		{
			d++;
			bound *= 10;
		}

		//Call counting sort
		int div = 1;
		for(int i = 0; i < d; i++)
		{
			arr = cs(arr,-9,9,div,10);
			div *= 10;
		}
		return arr;
	}
		


	public static void main(String[] args)
	{
		//Init
		int n = 1000000;
		int[] test = new int[n];
		int s = 1000;
		int t = 9999;
		int d = 4;
		for(int i = 0; i < test.length; i++)
		{
			int val = (int)(Math.random()*(t - s + 1)) + s;
			val *= Math.random() < 0.5 ? -1 : 1;
			test[i] = val;
		}
		System.out.println("For "+n+" elements, each with "+d+" digits:");

		//Sort
		double start = System.nanoTime();
		int[] retval = radiixSort(test);
		double time = (System.nanoTime() - start)/1e9;
		System.out.println("Radiix Sort: "+time);

		start = System.nanoTime();
		Arrays.sort(test);
		time = (System.nanoTime() - start)/1e9;
		System.out.println("Java's Sort: "+time);

		//Compare
		for(int i = 0; i < test.length; i++) 
		{
			if(test[i] != retval[i]) 
			{
				System.out.println("WRONG");
				break;
			}
		}
	}
}
