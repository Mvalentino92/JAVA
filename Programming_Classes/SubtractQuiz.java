import java.util.Scanner;
public class SubtractQuiz {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int correct = 0;
		for(int i = 0; i < 10; ++i)
		{
			int a = (int)(Math.random()*21);
			int b = (int)(Math.random()*21);
			if(a < b) 
			{
				int temp = a;
				a = b;
				b = temp;
			}
			int solution = a - b;
			System.out.print("What is "+a+" - "+b+"?: ");
			int answer = 0;
			while(!(input.hasNextInt()))        //So this is going to return true or false. it checks ahead of time if the input satisfies the type we want.
			{                                   //So if it is FALSE (aka was NOT an Int), then we want the while loop to run. So we switch false to true with "!"
				System.out.println("Retry, give me an interger");   //Prompt the user to try again and return an Int
				input.next();        //This apparently, just sort of "refreshes" the user input. It discards the old value, so "hasNextInt" can accept a new value
			}
			answer = input.nextInt();   //COOL! you put in an integer since you got of the viscious while loop. Save the value now.
			if(solution == answer) correct += 1;
		}
		System.out.println("Sorry, you got "+(10-correct)+" wrong!");
	}
}



