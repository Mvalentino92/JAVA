import java.util.*; 
import java.math.*; 
import java.io.*; 

public class Project1_Checker
{
	//Eats white space until the next line which contains info, and returns it as a String
	public static String getNextInfo(Scanner scan)
	{
		String retval = "";
		while(scan.hasNextLine())
		{
			retval = scan.nextLine();
			if(retval.length() != 0) break;
		}
		return retval;
	}

	public static void main(String[] args) throws Exception
	{
		//Read in the birthdaySolution.txt file as a Scanner
		Scanner correctInput = new Scanner(new File("birthdaySolution.txt"));

		//Prompt the user to enter the name of the students file, and create Scanner for the file if it exists
		Scanner getFile = new Scanner(System.in);
		System.out.print("What is the name of the students file, including the extension?: ");
		Scanner studentInput = new Scanner(new File(getFile.nextLine()));
		
		//Init the count for various tracking variables
		int totalQueries = 1534;
		int queriesCorrect = 0;
		int totalQueriesChecked = 0;
		int totalClasses = 20;
		int classesCorrect = 0;
		int classNumber = 1; 
		int printClassNumbers = 0;
		boolean allCorrect = true;

		//Get the first line of output from both correct and student Scanner (SHOULD BE CLASS 1)
		String correctLine = getNextInfo(correctInput);
		String studentLine = getNextInfo(studentInput);

		//Begin to iterate both Scanners and grab the input	
		while(correctInput.hasNextLine() && studentInput.hasNextLine())
		{
			//Check if it's the start of a new Class
			String classString = "" + classNumber;
			if(correctLine.indexOf(classString) > -1)
			{
				//Check if the student printed the correct class number for this class.
				if(studentLine.indexOf(classString) > -1) printClassNumbers++;

				//Since it's a new class, init a boolean to track if this whole class was correct, and increment classNumber
				allCorrect = true;
				classNumber++;

				//Begin to iterate this class, and count matches til we get to a line that is a new class
				while(correctInput.hasNextLine() && studentInput.hasNextLine())
				{
					//Grab next input from both Scanners
					correctLine = getNextInfo(correctInput);
					studentLine = getNextInfo(studentInput);

					//Check if it's the end of the file for either
					if(correctLine.length() == 0 || studentLine.length() == 0) break;

					//If it's the next class, tally up if whole class was correct and break
					classString = "" + classNumber;
					if(correctLine.indexOf(classString) > -1)
					{
						if(allCorrect) classesCorrect++;
						break;
					}
					else //Otherwise check if the input is correct
					{
						//Increment total queries checked (know if last class was complete)
						totalQueriesChecked++;

						//Split Strings into arrays (we want to check 0,1 and n, n-1
						String[] correctArray = correctLine.split("\\s+");
						String[] studentArray = studentLine.split("\\s+");

						//Get sizes
						int cSize = correctArray.length;
						int sSize = studentArray.length;

						//Check to see if sSize is < 4, if so: set to false and continue (can't be right!)
						if(sSize < 4)
						{
							allCorrect = false;
							System.out.println(correctLine);
							System.out.println(studentLine+"\n");
							continue;
						}

						//Check first word matches
						String c = correctArray[0].toUpperCase();
						String s = studentArray[0].toUpperCase();
						if(c.compareTo(s) != 0)
						{
							allCorrect = false;
							System.out.println(correctLine);
							System.out.println(studentLine+"\n");
							continue;
						}

						//Check second word matches
						c = correctArray[1].toUpperCase();
						s = studentArray[1].toUpperCase();
						if(c.compareTo(s) != 0)
						{
							allCorrect = false;
							System.out.println(correctLine);
							System.out.println(studentLine+"\n");
							continue;
						}

						//Check third word matches
						c = correctArray[cSize-2].toUpperCase();
						s = studentArray[sSize-2].toUpperCase();
						if(c.compareTo(s) != 0)
						{
							allCorrect = false;
							System.out.println(correctLine);
							System.out.println(studentLine+"\n");
							continue;
						}

						//Check fourth (handle possible non-period)
						c = correctArray[cSize-1].toUpperCase();
						s = studentArray[sSize-1].toUpperCase();
						if(s.charAt(s.length()-1) != '.') s = s + ".";
						if(c.compareTo(s) != 0)
						{
							allCorrect = false;
							continue;
						}
						//If you made it here, everything was correct, add one to queriesCorrect
						queriesCorrect++;
					}
				}
			}
		}
		//See if last class was correct
		if(allCorrect) classesCorrect++;

		//If you didn't make it all the way through the last class,
		//but it was marked correct because you had the ones right you got to, take it back!
		if(totalQueriesChecked != totalQueries && allCorrect) classesCorrect--;

		//Print the results
		System.out.println(printClassNumbers+"/"+totalClasses+" class NUMBERS (literally the numbers) were printed correctly.");
		System.out.println(classesCorrect+"/"+totalClasses+" classes had completely correct queries.");
		System.out.println(queriesCorrect+"/"+totalQueries+" queries were correct.");

		//If a very low number of queries were matches, make a note (could be a missing query making everything else wrong case)
		if(queriesCorrect < 250) System.out.println("Less than 250 queries were matched correctly\n"+
				                            "Output may be formatted so poorly, that all possible matches were voided.");
	}
}
