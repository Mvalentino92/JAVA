import java.util.Scanner;
public class FinalTax
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		double tax = 0;
		int bracket = 0;
		//Percents
		double percent1 = 0.10;
		double percent2 = 0.15;
		double percent3 = 0.25;
		double percent4 = 0.28;
		double percent5 = 0.33;
		double percent6 = 0.35;
		double percent7 = 0.396;
		//Single
		double single1 = 9275;
		double single2 = 37650;
		double single3 = 91150;
		double single4 = 190150;
		double single5 = 413350;
		double single6 = 415050;
		//Married Widower
		double widow1 = 18550;
		double widow2 = 75300;
		double widow3 = 151900;
		double widow4 = 231450;
		double widow5 = 413350;
		double widow6 = 466950;
		//Seperate
		double seperate1 = 9275;
		double seperate2 = 37650;
		double seperate3 = 75905;
		double seperate4 = 115725;
		double seperate5 = 206675;
		double seperate6 = 233475;
		//head
		double head1 = 13250;
		double head2 = 50400;
		double head3 = 130150;
		double head4 = 210800;
		double head5 = 413350;
		double head6 = 441000;
		//Computing compound interests
		double singleCompoundInterest1 = single1*percent1;
		double singleCompoundInterest2 = singleCompoundInterest1 + (single2 - single1)*percent2;
		double singleCompoundInterest3 = singleCompoundInterest2 + (single3 - single2)*percent3;
		double singleCompoundInterest4 = singleCompoundInterest3 + (single4 - single3)*percent4;
		double singleCompoundInterest5 = singleCompoundInterest4 + (single5 - single4)*percent5;
		double singleCompoundInterest6 = singleCompoundInterest5 + (single6 - single5)*percent6;
		
		double compoundInterest1 = widow1*percent1;
		double compoundInterest2 = compoundInterest1 + (widow2 - widow1)*percent2;
		double compoundInterest3 = compoundInterest2 + (widow3 - widow2)*percent3;
		double compoundInterest4 = compoundInterest3 + (widow4 - widow3)*percent4;
		double compoundInterest5 = compoundInterest4 + (widow5 - widow4)*percent5;
		double compoundInterest6 = compoundInterest5 + (widow6 - widow5)*percent6;

		double seperateCompoundInterest1 = seperate1*percent1;
		double seperateCompoundInterest2 = seperateCompoundInterest1 + (seperate2 - seperate1)*percent2;
		double seperateCompoundInterest3 = seperateCompoundInterest2 + (seperate3 - seperate2)*percent3;
		double seperateCompoundInterest4 = seperateCompoundInterest3 + (seperate4 - seperate3)*percent4;
		double seperateCompoundInterest5 = seperateCompoundInterest4 + (seperate5 - seperate4)*percent5;
		double seperateCompoundInterest6 = seperateCompoundInterest5 + (seperate6 - seperate5)*percent6;

		double headCompoundInterest1 = head1*percent1;
		double headCompoundInterest2 = headCompoundInterest1 + (head2 - head1)*percent2;
		double headCompoundInterest3 = headCompoundInterest2 + (head3 - head2)*percent3;
		double headCompoundInterest4 = headCompoundInterest3 + (head4 - head3)*percent4;
		double headCompoundInterest5 = headCompoundInterest4 + (head5 - head4)*percent5;
		double headCompoundInterest6 = headCompoundInterest5 + (head6 - head5)*percent6;

		while(input.hasNextLine()&&input.hasNextInt())
		{
			int clientid  = input.nextInt();
			//System.out.println("[0 - Single]\n[1 - Married Jointly or Qualified Window(er)]\n[2 - Married Seperately]\n[3 - Head of Household]");
			//System.out.print("Please provide an interger representing the filing status you identify with: ");
			int status = input.nextInt();
			double income = input.nextDouble();
			switch(status) {
				case 0:
					if(income <= single1)
					{
						tax = income * percent1;
						bracket = 1;
					}
					else if(income <= single2)
					{
						tax = singleCompoundInterest1 + (income - single1)*percent2;
						bracket = 2;
					}
					else if(income <= single3)
					{
						tax = singleCompoundInterest2 + (income - single2)*percent3;
						bracket = 3;
					}
					else if(income <= single4)
					{
						tax = singleCompoundInterest3 + (income - single3)*percent4;
						bracket = 4;
					}
					else if(income <= single5)
					{
						tax = singleCompoundInterest4 + (income - single4)*percent5;
						bracket = 5;
					}
					else if(income <= single6)
					{
						tax = singleCompoundInterest5 + (income - single5)*percent6;
						bracket = 6;
					}
					else
					{
						tax = singleCompoundInterest6 + (income - single6)*percent7;
						bracket = 7;
					}
					break;
				case 1:
					if(income <= widow1)
					{
						tax = income * percent1;
						bracket = 1;
					}
					else if(income <= widow2)
					{
						tax = compoundInterest1 + (income - widow1)*percent2;
						bracket = 2;
					}
					else if(income <= widow3)
					{
						tax = compoundInterest2 + (income - widow2)*percent3;
						bracket = 3;
					}
					else if(income <= widow4)
					{
						tax = compoundInterest3 + (income - widow3)*percent4;
						bracket = 4;
					}
					else if(income <= widow5)
					{
						tax = compoundInterest4 + (income - widow4)*percent5;
						bracket = 5;
					}
					else if(income <= widow6)
					{
						tax = compoundInterest5 + (income - widow5)*percent6;
						bracket = 6;
					}
					else
					{
						tax = compoundInterest6 + (income - widow6)*percent7;
						bracket = 7;
					}
					break;
				case 2:
					if(income <= seperate1)
					{
						tax = income * percent1;
						bracket = 1;
					}
					else if(income <= seperate2)
					{
						tax = seperateCompoundInterest1 + (income - seperate1)*percent2;
						bracket = 2;
					}
					else if(income <= seperate3)
					{
						tax = seperateCompoundInterest2 + (income - seperate2)*percent3;
						bracket = 3;
					}
					else if(income <= seperate4)
					{
						tax = seperateCompoundInterest3 + (income - seperate3)*percent4;
						bracket = 4;
					}
					else if(income <= seperate5)
					{
						tax = seperateCompoundInterest4 + (income - seperate4)*percent5;
						bracket = 5;
					}
					else if(income <= seperate6)
					{
						tax = seperateCompoundInterest5 + (income - seperate5)*percent6;
						bracket = 6;
					}
					else
					{
						tax = seperateCompoundInterest6 + (income - seperate6)*percent7;
						bracket = 7;
					}
					break;
				case 3:
					if(income <= head1)
					{
						tax = income * percent1;
						bracket = 1;
					}
					else if(income <= head2)
					{
						tax = headCompoundInterest1 + (income - head1)*percent2;
						bracket = 2;
					}
					else if(income <= head3)
					{
						tax = headCompoundInterest2 + (income - head2)*percent3;
						bracket = 3;
					}
					else if(income <= head4)
					{
						tax = headCompoundInterest3 + (income - head3)*percent4;
						bracket = 4;
					}
					else if(income <= head5)
					{
						tax = headCompoundInterest4 + (income - head4)*percent5;
						bracket = 5;
					}
					else if(income <= head6)
					{
						tax = headCompoundInterest5 + (income - head5)*percent6;
						bracket = 6;
					}
					else
					{
						tax = headCompoundInterest6 + (income - head6)*percent7;
						bracket = 7;
					}
					break;
				default:
					System.out.println("Sorry, entered an invalid status");
					System.exit(1);
			}
			tax = (int)(tax * 100)/100;
			System.out.printf("ID Number: %d, in bracket %d, yields a tax of %10.2f\n",clientid,bracket,tax);
			//System.out.println(tax);
		}
	}
}
