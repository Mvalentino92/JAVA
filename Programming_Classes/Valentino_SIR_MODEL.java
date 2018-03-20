import java.util.*;
/*Making a class for population to store all the values I will need
 * Didn't need to, just wanted to for fun*/
class Population
{
	double[] susceptible;
       	double[] infected;
	double[] recovered;
	double[] time;
	double infectionRate;
	double recoveryRate;
	double days;
	double h;
	boolean quarantine = false;
	double infectedThreshhold;
	double infectedIsolated;
	double limit;
	int qCount = 0;

	public Population(double infectionRate, double recoveryRate, double days, double h)
	{
		this.infectionRate = infectionRate;
		this.recoveryRate = recoveryRate;
		this.days = days;
		this.h = h;
	}
	
	public void setTimeSpan()
	{
		time = new double[(int)(days/h)];
		susceptible = new double[time.length];
		infected = new double[time.length];
		recovered = new double[time.length];
	}

	public void setNaughts(double s0, double i0, double r0)
	{
		time[0] = 0;
		susceptible[0] = s0;
		infected[0] = i0;
		recovered[0] = r0;
	}

	public void setQuarantine(double infectedThreshhold,double infectedIsolated,int limit)
	{
		quarantine = true;
		this.infectedThreshhold = infectedThreshhold;
		this.infectedIsolated = infectedIsolated;
		this.limit = limit;
	}
}

public class Valentino_SIR_MODEL
{
	/*I'm making a function for each rate of change, I was originally going to do RungeKutta
	 * but I was having difficulty because theres 3 rates of chance, I tried updating each rate of change
	 * involved for each variable in the function, but I must've messed it up.
	 * The total population was slowly increasing. So I defaulted to euler, but just left it like this
	 * No harm done for this problem*/
	public static double SrateOfChange(double S, double I, double infectionRate)
	{
		return -infectionRate*S*I;
	}

	public static double IrateOfChange(double S, double I, double infectionRate, double recoveryRate)
	{
		return infectionRate*S*I - recoveryRate*I;
	}

	public static double RrateofChange(double I, double recoveryRate)
	{
		return recoveryRate*I;
	}

	/*If the user opted for the quarantine, that is addressed int he for loop.
	 * I was toying with the idea of trying to make it so it's not like the infected 
	 * are instantaneously rounded up and quarantined, but I decided against it
	 * So while it's not accurate, its still a cool feature*/
	public static void euler(Population pop)
	{
		for(int i = 0; i < pop.time.length - 1; i++)
		{
			if(pop.quarantine && pop.qCount < pop.limit)
			{
				if(pop.infected[i] >= pop.infectedThreshhold)
				{
					double toBeMoved = ((pop.infectedIsolated)/100)*pop.infected[i];
					pop.infected[i] -= toBeMoved;
					pop.recovered[i] += toBeMoved;
					pop.qCount++;
					System.out.println("QUARANTINE "+pop.qCount+" in effect");
				}
			}
			double sRate = SrateOfChange(pop.susceptible[i],pop.infected[i],pop.infectionRate);
			double iRate =IrateOfChange(pop.susceptible[i],pop.infected[i],pop.infectionRate,pop.recoveryRate);
			double rRate = RrateofChange(pop.infected[i],pop.recoveryRate);

			pop.susceptible[i+1] = pop.susceptible[i] + sRate*pop.h;
			pop.infected[i+1] = pop.infected[i] + iRate*pop.h;
			pop.recovered[i+1] = pop.recovered[i] + rRate*pop.h;
			pop.time[i+1] = (i+1)*pop.h;
		}
	}

	/*If the user opts to look at any of the data points from a span of days
	 * this functin is called*/
	public static void displayData(Population pop, double start, double end)
	{
		int startIndex = (int)(start/pop.h);
		int stopIndex = (int)(end/pop.h);

		for(int i = startIndex; i < stopIndex; i++)
		{
			System.out.printf("S: %.2f \t I: %.2f \t R: %.2f\n",pop.susceptible[i],pop.infected[i],pop.recovered[i]);
		}
	}

	/*Largely dominated by prompts and user input. Gathering all the information necessary to 
	 * generate the data and the graph*/
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Hello, welcome to SIR ModelsTechXX");
		input.nextLine();
		System.out.print("We pride ourselves in our ability to generate accurate and well thought"
				+" out SIR Models\n");
		input.nextLine();
		System.out.print("First things first, how many days (as an integer) would you like the"+
			       " run the model for?\nIf you choose longer than 45 days"+
			       ", please change the limits in SimpleGraphComponents.java: ");
		double days = input.nextInt();
		double hourRateMax = (days*24)/1e5;
		System.out.printf("\nHow many hours would you like to go by per time step?\n"
				+"Please be advised that choosing a value less than %.7f"
				+" will\nresult in more than 100 thousand data points"
				+" being generated: ",hourRateMax);
		double h = input.nextDouble()/24; 
		System.out.println("\nFantastic! Now what are the starting values?\n"
				+"Please keep the total population < 100, otherwise you will"
				+" have to change the limits on SimpleGraphComponenets");
		System.out.print("How many initial susceptible are there?: ");
		double s = input.nextDouble();
		System.out.print("How many initial infected are there?: ");
		double infec = input.nextDouble();
		System.out.print("And how many initial recovered?: ");
		double r = input.nextDouble();

		System.out.println("Now let\'s get the rates");
	        System.out.print("What is the infection rate?: ");
		double iRate = input.nextDouble();
		System.out.print("And what is the recovery rate?: ");
		double rRate = input.nextDouble();
		
		Population pop = new Population(iRate,rRate,days,h);
		pop.setTimeSpan();
		pop.setNaughts(s,infec,r);

		System.out.print("We\'ve recently implemented a quarantine feature\n"
				+" Would you like to implement it? [y/n] ");
		if(input.next().charAt(0) == 'y')
		{
			System.out.print("Great, how many infected does there have to be at any one"
					+" moment for the quarantine to be issued?: ");
			double qthresh = input.nextDouble();
			System.out.print("What percent of the current infected should be isolated?: ");
			double qmoved = input.nextDouble();
			System.out.print("By the way, do you want a limit on how many times"
					+" a quarantine can be put into effect? [y/n] ");
			if(input.next().charAt(0) == 'y')
			{
				System.out.print("What do you want the limit do be?: ");
				int limit = input.nextInt();
				pop.setQuarantine(qthresh,qmoved,limit);
			}
			else pop.setQuarantine(qthresh,qmoved,10000);
		}
		else System.out.println("Oh, okay. Maybe next time you will be interested");

		System.out.println("Generating your data points and displaying and plot");
		euler(pop);

		System.out.print("Would you like to look at any range of data points specifically? [y/n] ");
		if(input.next().charAt(0) == 'y')
		{
			System.out.print("How many ranges of data points would you like to look at?: ");
			int dataIters = input.nextInt();
			int[][] dataToCheck = new int[dataIters][2];
			for(int i = 0; i < dataIters; i++)
			{
				System.out.print("What day would you like to start on for data set "+(i+1)+"?: ");
				dataToCheck[i][0] = input.nextInt();
				System.out.print("What day would you like to end on for data set "+(i+1)+"?: ");
				dataToCheck[i][1] = input.nextInt();
			}
			for(int i = 0; i < dataIters; i++)
			{
				input.nextLine();
				System.out.println();
				displayData(pop,dataToCheck[i][0],dataToCheck[i][1]);
				System.out.print("Press ENTER to look at next data set");
			}

		}

		/*Finally, graphing the data calculated*/
		SimplePlot ss = new SimplePlot(800,800);
		ss.setData(pop.time,pop.susceptible);
		ss.addData(pop.time,pop.infected);
		ss.addData(pop.time,pop.recovered);
		ss.setTitle("SIR MODEL   S: Blue, I: Red, R: Green");
		ss.setYLabel("Population");
		ss.setXLabel("Time over "+pop.days+" days");
		ss.repaint();
	}
}
