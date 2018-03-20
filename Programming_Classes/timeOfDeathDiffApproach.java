import java.util.Scanner;
public class timeOfDeathDiffApproach
{
	public static double euler(double bodyTemperature, double ambientTemperature, double k, double h)
	{
		double rateOfChange = -k*(bodyTemperature - ambientTemperature);
		double midPoint = bodyTemperature + rateOfChange*h/2;
		double midPointRateOfChange = -k*(midPoint - ambientTemperature);
		
		return bodyTemperature + midPointRateOfChange*h;
	}

	public static void main(String[] args)
	{
		//Initialize Scanner and prompt user for a guess of timeOfDeath and ambient temp at that tme
		Scanner input = new Scanner(System.in);
		System.out.print("When do you think the time of death was? Please answer is military time: ");
		double proposedTimeOfDeath = input.nextDouble();
		double proposedCopy = proposedTimeOfDeath;
		double timePlaceHolder = proposedTimeOfDeath;

		//Setting information that we know about the temperatures of both the environment and body
		double ambientAtDiscovery = 32.0;
		double bodyTempAtDiscovery = 45.0;
		double bodyTempOneHourLater = 40.0;

		//Using this information to solve for constant of cooling, k
		double k = (-1)*Math.log((bodyTempOneHourLater - ambientAtDiscovery)/(bodyTempAtDiscovery - ambientAtDiscovery));
		//Setting up variables for the euler method
		double h = 1e-3;
		System.out.print("What was the temperature of the body when the person died?: ");
		double initialBodyTemperature = input.nextDouble();
		double bodyTempCopy = initialBodyTemperature;
		double initialAmbientTemperature = 0;

		//Beginning the euler steps
		while(initialBodyTemperature > bodyTempAtDiscovery)
		{
			//Changing the ambient temperature based on what time is it
			if((int)proposedTimeOfDeath == 10) initialAmbientTemperature = 50.0;
			else if((int)proposedTimeOfDeath == 11) initialAmbientTemperature = 50.0;
			else if((int)proposedTimeOfDeath == 12) initialAmbientTemperature = 45.0;
			else if((int)proposedTimeOfDeath == 13) initialAmbientTemperature = 30.0;
			else if((int)proposedTimeOfDeath == 14) initialAmbientTemperature = 20.0;
			else if((int)proposedTimeOfDeath == 15) initialAmbientTemperature = 25.0;
			else if((int)proposedTimeOfDeath == 16) initialAmbientTemperature = 32.0;
			else if((int)proposedTimeOfDeath == 17) initialAmbientTemperature = 32.0;
			
			//Calling euler method to get the new temperature
			initialBodyTemperature = euler(initialBodyTemperature, initialAmbientTemperature, k, h);
			
			//Updating the time
			proposedTimeOfDeath += h;
		}
		double EPSILON = 0.1;
		boolean matchesFirstTime = Math.abs(proposedTimeOfDeath - 16.0) < EPSILON;
		if(matchesFirstTime)
		{
			while(bodyTempCopy > bodyTempOneHourLater)
			{
				//Changing the ambient temperature based on what time is it
				if((int)proposedCopy == 10) initialAmbientTemperature = 50.0;
				else if((int)proposedCopy == 11) initialAmbientTemperature = 50.0;
				else if((int)proposedCopy == 12) initialAmbientTemperature = 45.0;
				else if((int)proposedCopy == 13) initialAmbientTemperature = 30.0;
				else if((int)proposedCopy == 14) initialAmbientTemperature = 20.0;
				else if((int)proposedCopy == 15) initialAmbientTemperature = 25.0;
				else if((int)proposedCopy == 16) initialAmbientTemperature = 32.0;
				else if((int)proposedCopy == 17) initialAmbientTemperature = 32.0;
				
				//Calling euler method to get the new temperature
				bodyTempCopy = euler(bodyTempCopy, initialAmbientTemperature, k, h);
				
				//Updating the time
				proposedCopy += h;
			}
			boolean matchesSecondTime = Math.abs(proposedCopy - 17.0) < EPSILON;
			if(matchesSecondTime)
			{
				int minutes = (int)((timePlaceHolder - (int)timePlaceHolder)*60);
				int hour = (int)timePlaceHolder;
				System.out.println("The person died at "+hour+":"+minutes);
			}
			else
			{
				System.out.println("Sorry, your guess was not accurate");
				System.out.println("The time when the body hit 40.0 degrees was "
						+proposedCopy+" and you need it to be 17.0");
			}
		}
		else
		{	
			System.out.println("Sorry, your guess was not accurate");
			System.out.println("The time when the body hit 45 degrees was "
					+proposedTimeOfDeath+" and you need it to be 16.0");
		}
	}
}
