import java.util.*; 
import java.math.*; 
import java.io.*; 

/* This is the message class. It will store a secret message and something known as a shift value
 * to encrypt and decrypt a message. It uses a strategy known as the Caesar Cipher.
 * All of the letters of alphabet can be assigned a value. 0 through 25.
 * "a" being 0, "b" being 1, "c" being 3, all the way to "z" being 25.
 * The shift, will be an integer that can be greater than 26. Using it,
 * you will shift all your values up by that amount, and change that letter to the new letter its
 * value corresponds to. EXAMPLE: "hey" with a shift value of 5 would become "mjd".
 * Notice the "y" doubled back to the beginning. A shift value of 31 would yield the same result.
 * Because shifting 26 times, will bring you back to same number! With 5 left over.*/
class Message
{
	//Field variables
	private String message;
	private int shift;

	//No ARG constructor
	public Message(){}

	//Parametized constructor, that will accept the message, and the shift.
	public Message(String message, int shift)
	{
		this.message = message;
		this.shift = shift % 26;
		/*There are only 26 letters in the alphabet.
		 * Shifting more than 26 times will revert back to the original letter.
		 * So, if the shift was 45. 45 % 26 = 19. The shift is really only 19. */
	}

	//Setters for both message and shift.
	public void setMessage(String message) {this.message = message;}
	public void setShift(int shift) {this.shift = shift % 26;}

	//Getters for both methods.
	public String getMessage() {return message;}
	public int getShift() {return shift;}

	//Return an array of booleans representing uppercase letters
	public boolean[] areUppercase()
	{
		boolean[] uppercaseIndexes = new boolean[message.length()];
		
		for(int i = 0; i < message.length(); i++)
		{
			int currentValue = (int)message.charAt(i);
			if(currentValue >= 65 && currentValue <= 90) uppercaseIndexes[i] = true;
		}
		return uppercaseIndexes;
	}

	//Method to encrypt the message.
	public void encrypt()
	{
		/*Some variables I will need. It is easier to work with all one case
		 * I chose to work with lowercase. Which is why I need an array that holds all the indexes
		 * of the uppercase characters. Because I'm going to convert them to lowercase to work
		 * with them first.*/
		String encryptedMessage = new String();
		int lowercaseMin = (int)'a';
		int lowercaseMax = (int)'z';
		boolean[] uppercaseIndexes = this.areUppercase();
		message = message.toLowerCase();

		for(int i = 0; i < message.length(); i++)
		{
			//Get the current letter, and its corresponding integer value.
			char currentLetter = message.charAt(i);
			int currentValue = (int)currentLetter;
			int uppercaseAdjust = 0;

			/*If I'm not working with a letter, just print whatever character it is.
			Maybe it's a space, or an exclamation point etc..*/
			if(currentValue < lowercaseMin || currentValue > lowercaseMax)
			{
				encryptedMessage += currentLetter;
			}
			//Otherwise, it is a letter. So it's shift time!
			else
			{
				/*If we are working on a previously uppercase letter, we have to account for
				that at the end.*/
				if(uppercaseIndexes[i] == true) uppercaseAdjust = 32;

				currentValue -= 97; //I want to work with 0-25, remember? 'a' == 97.
				int shiftedValue = (currentValue + shift) % 26;
				char encryptedLetter = (char)(shiftedValue + 97 - uppercaseAdjust);
				encryptedMessage += encryptedLetter;
			}
		}
		message = encryptedMessage;
	}

	//The method to call private decrypt method
	public void requestDecryption(int key)
	{
		if(this.getShift()*2 == key)
		{
			System.out.println("Message decrypted!");
			this.decrypt();
		}
		else System.out.println("Key is wrong. Message not decrypted.");
	}

	//Decrypts the message.
	private void decrypt()
	{
		String decryptedMessage = new String();
		int lowercaseMin = (int)'a';
		int lowercaseMax = (int)'z';
		boolean[] uppercaseIndexes = this.areUppercase();
		message = message.toLowerCase();

		for(int i = 0; i < message.length(); i++)
		{
			char currentLetter = message.charAt(i);
			int currentValue = (int)currentLetter;
			int uppercaseAdjust = 0;

			if(currentValue < lowercaseMin || currentValue > lowercaseMax) 
			{
				decryptedMessage += currentLetter;
			}
			//Cycling back isn't as easy in reverse.
			else
			{
				if(uppercaseIndexes[i] == true) uppercaseAdjust = 32;
				currentValue -= 97;

				if(currentValue - shift < 0)
				{
					int circleAround = shift - currentValue;
					int shiftedValue = 26 - circleAround;
					decryptedMessage += (char)(shiftedValue + 97 -  uppercaseAdjust);
				}
				else
				{
					int shiftedValue = currentValue - shift;
					decryptedMessage += (char)(shiftedValue + 97 - uppercaseAdjust);
				}
			}
		}
		message = decryptedMessage;
	}
}

public class SecretMessages
{
	public static void main(String[] args)
	{
		Message sentence = new Message("So what are classes REALLY good for? "+
				"Doing top secret encryption and decryption of messages!!",101);
		sentence.encrypt();
		System.out.println(sentence.getMessage());
		sentence.requestDecryption(sentence.getShift()*2);
		System.out.println(sentence.getMessage());
	}
}
