public class EncodeAndDecodeJavaFiles
{
	//Setting any array of the characters that seperate what I consider words
        static char[] javaSeperators = {' ','.','(',')','[',']','{','}'};

	//A function to check to see if the current character is a word seperator
	public static boolean checkSeperator(char character)
	{
		for(int i = 0; i < javaSeperators.length; i++)
		{
			if(character == javaSeperators[i]) return true;
		}
		return false;
	}

	/*Creating the function that will encode a message
	 * This encryption method works by counting the number of letters (letters, not characters), in word
	 * and shifting each letter in that word forward by that number. 
	 * It will do this individually for each word.
	 * words are anything seperated by whitespace.*/
	public static String encode(String message)
	{
		String encodedMessage = new String();
		for(int i = 0; i < message.length(); i++)
		{
			char stopper = '\u0000';
			int j = i + 1;
			int letterCount = 0;
			int nonLetterCount = 0;
			if((int)message.charAt(i) < 97 || (int)message.charAt(i) > 122) nonLetterCount++;
			else letterCount++;
			//Iterating through until seperator character
			//And counting both letters and non letters
			for(j = j; j < message.length(); j++)
			{
				if((int)message.charAt(j) > 96 && (int)message.charAt(j) < 123) letterCount++;
				else if(checkSeperator(message.charAt(j)))
				{
					stopper = message.charAt(j);
					nonLetterCount++;
					break;
				}		
				else nonLetterCount++;
			}
			//Setting the length to shift each character by
			int length = (letterCount + nonLetterCount) -  nonLetterCount;
			//Iterating through the word, and shifting all the letters by the words length 
			for(i = i; i < j; i++)
			{
				//If the character is NOT a letter, just copy it over as it is
				if(((int)message.charAt(i) < 97 || (int)message.charAt(i) > 122)
								&& (message.charAt(i) != stopper))
				{
					encodedMessage += message.charAt(i);
				}
				//Otherwise, convert to the encrypted letter via the shift.
				else
				{	
					//Statements to handle if the encrypted letter value will overflow 26
					int shift = (int)(message.charAt(i)) - 97 + length;
					if(shift > 25)
					{
						shift = shift % 26;
						encodedMessage += (char)(shift + 97);
					}
					else
					{
						encodedMessage += (char)(shift + 97);
					}
				}
			}
			//Finally, add the space that seperates this word iteration from the next
			if(i != message.length()) encodedMessage += stopper;
		}
		return encodedMessage;
	}

	/*This function will decode a message
	 * The decryption works by again counting the number of letter in a word,
	 * but instead shifts each letter in the word backwards by that number.
	 * The framework is exactly the same, except for the difference of shifting backwards.*/
	public static String decode(String message)
	{
		String decodedMessage = new String();

		for(int i = 0; i < message.length(); i++)
		{
			char stopper = '\u0000';
			int j = i + 1;
			int letterCount = 0;
			int nonLetterCount = 0;
			if((int)message.charAt(i) < 97 || (int)message.charAt(i) > 122) nonLetterCount++;
			else letterCount++;
			//Iterating through until seperator character
			//And counting both letters and non letters
			for(j = j; j < message.length(); j++)
			{
				if((int)message.charAt(j) > 96 && (int)message.charAt(j) < 123) letterCount++;
				else if(checkSeperator(message.charAt(j)))
				{
					stopper = message.charAt(j);
					nonLetterCount++;
					break;
				}		
				else nonLetterCount++;
			}
			int length = (letterCount + nonLetterCount) - nonLetterCount;
			for(i = i; i < j; i++)
			{
				if(((int)message.charAt(i) < 97 || (int)message.charAt(i) > 122)
								&& (message.charAt(i) != stopper))
				{
					decodedMessage += message.charAt(i);
				}
				else
				{	
					//Instead of shifting forward, the shift is backwards
					int shift = (int)(message.charAt(i)) - 97 - length;
					if(shift < 0)
					{
						shift = 26 + shift;
						decodedMessage += (char)(shift + 97);
					}
					else
					{
						decodedMessage += (char)(shift + 97);
					}
				}
			}
			if(i != message.length()) decodedMessage += stopper;
		}
		return decodedMessage;
	}
}
