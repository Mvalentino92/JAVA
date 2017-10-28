//Finds all occurences of a specified pattern in a string, and replaces them with another pattern. 
//Recursively calls itself until all the occurences have been replaced.
public class changeString
{
	public static String replace(String s,String t, String r)
	{
		String holdS = s;
		if(s.indexOf(t) != -1)
		{
			String newS = new String();
			int stop = s.indexOf(t);
			for(int i = 0; i < stop; i++)
			{
				newS += s.charAt(i);
			}

			for(int i = 0; i < r.length(); i++)
			{
				newS += r.charAt(i);
			}

			for(int i = stop + t.length(); i < s.length(); i++)
			{
				newS += s.charAt(i);
			}
			holdS = newS;
			return replace(holdS,t,r);
		}
		else return holdS;
	}

	//Small demonstration of how it works. "begging", turning to "bwatermeloning" is by design. 
	public static void main(String[] args)
	{
		String sentence1 = "\nDude begging eggs are just amazing. Have you ever had a fried egg?\nI like eating one egg in the morning,"+
		       		  " and the other egg in the evening.\nIf you go to the store, can you get two dozens eggs? Thanks man.\n"+
				  "egg egg egg! I cant get enough eggs!";
		System.out.println(sentence1);
		System.out.println("\nNow with the \"egg\" changed to \"watermelon\"");
		String sentence2 = replace(sentence1,"egg","watermelon");
		System.out.println(sentence2);
	}
}
		
