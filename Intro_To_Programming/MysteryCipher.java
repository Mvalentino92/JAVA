public class MysteryCipher
{
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
			int j = i + 1;
			int letterCount = j;
			//Iterating through until whitespace, and only counting letters
			for(j = j; j < message.length(); j++)
			{
				if((int)message.charAt(j) > 96 && (int)message.charAt(j) < 123) letterCount++;
				if(message.charAt(j) == ' ') break;
			}
			int length = letterCount - i;
			//Iterating through the word, and shifting all the letters by the words length 
			for(i = i; i < j; i++)
			{
				//If the character is NOT a letter, just copy it over as it is
				if((int)message.charAt(i) < 97 || (int)message.charAt(i) > 122)
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
			if(i != message.length()) encodedMessage += ' ';
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
			int j = i + 1;
			int letterCount = j;
			for(j = j; j < message.length(); j++)
			{
				if((int)message.charAt(j) > 96 && (int)message.charAt(j) < 123) letterCount++;
				if(message.charAt(j) == ' ') break;
			}
			int length = letterCount - i;
			for(i = i; i < j; i++)
			{
				if((int)message.charAt(i) < 97 || (int)message.charAt(i) > 122)
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
			if(i != message.length()) decodedMessage += ' ';
		}
		return decodedMessage;
	}

	public static void main(String[] args)
	{
		String message = "hey will it's your lab partner for chem, mike";
		System.out.println("THe length of the message is :"+message.length());
		String encodedMessage = encode(message);
		System.out.println("The length of the encoded message is: "+encodedMessage.length());
		System.out.println(message);
		System.out.println(encodedMessage);
		String decodedMessage = decode(encodedMessage);
		System.out.println(decodedMessage);
		System.out.println("The length of the decoded Message is: "+decodedMessage.length());

	}
}
