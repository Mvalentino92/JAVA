import java.io.*;
import java.util.*;
public class interview
{
	static void readInterview(File fileToRead) throws Exception
	{
		if(fileToRead.exists())
		{
			Scanner input = new Scanner(fileToRead);
			while(input.hasNextLine())
			{
				String line = input.nextLine();
				System.out.println(line);
			}
		}
		else throw new Exception("The file was not created properly, cannot be read");
	}	

	public static void main(String[] args) throws Exception
	{
		String[] questions = new String[5];
		questions[0] = "What made you want to pursue a major that had to do with education?";
		questions[1] = "What grade(s) would you want to teach and why?";
		questions[2] = "What would say is your favorite aspect of education?";
		questions[3] = "Do you see yourself staying in this major?";
		questions[4] = "Finally, what advice would you have for your future students?";

		Scanner input = new Scanner(System.in);
		System.out.print("What is your last name?: ");
		String name = input.nextLine();

		File results = new File("InterviewResults_"+name+".txt");
		if(results.exists())
		{
			System.out.println("Interview was already conducted for this person, file exists");
			System.exit(1);
		}

		PrintWriter output = new PrintWriter(results);
		output.println("Interview with "+name);

		for(int i = 0; i < questions.length; i++)
		{
			System.out.println(questions[i]);
			output.println("Q: "+questions[i]+" ");
			output.println("A: "+input.nextLine()+" \n");
		}

		input.close();
		output.close();
		System.out.println("\nAttempting to retrieve interview file for view...\n\n");

		try
		{
			readInterview(results);
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
}
