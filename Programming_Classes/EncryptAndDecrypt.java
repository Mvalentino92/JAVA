import java.io.*;
import java.util.*;
public class EncryptAndDecrypt
{
	//Handles the encryption of lines with capital letters
	public static String handleFileEncryption(String lineToEncrypt)
	{
			ArrayList<Integer> indexes = new ArrayList<>();
			for(int i = 0; i < lineToEncrypt.length(); i++)
			{
				if((int)lineToEncrypt.charAt(i) > 64 && (int)lineToEncrypt.charAt(i) < 91)
				{
					indexes.add(i);
				}
			}
			String lowerCase = lineToEncrypt.toLowerCase();
			String encryptedLine = EncodeAndDecodeJavaFiles.encode(lowerCase);
			char[] charEncryptedLine = encryptedLine.toCharArray();
			for(int i = 0; i < indexes.size(); i++)
			{
				charEncryptedLine[indexes.get(i)] = (char)((int)charEncryptedLine[indexes.get(i)]
					       								- 32);
			}
			String finalEncryptedLine = new String(charEncryptedLine);
			return finalEncryptedLine;
	}
	
	//Creates an encrypted version of a java file
	public static void encryptFile(File fileToEncode) throws Exception
	{
		Scanner input = new Scanner(fileToEncode);
		File encryptedFile = new File(handleFileEncryption(fileToEncode.toString()));
		PrintWriter output = new PrintWriter(encryptedFile);

		while(input.hasNextLine())
		{
			String nextLine = handleFileEncryption(input.nextLine());
			output.println(nextLine);
		}
		input.close();
		output.close();
	}

	//Handles the decryption of lines with capital letters
	public static String handleFileDecryption(String lineToDecrpyt)
	{
			ArrayList<Integer> indexes = new ArrayList<>();
			for(int i = 0; i < lineToDecrpyt.length(); i++)
			{
				if((int)lineToDecrpyt.charAt(i) > 64 && (int)lineToDecrpyt.charAt(i) < 91)
				{
					indexes.add(i);
				}
			}
			String lowerCase = lineToDecrpyt.toLowerCase();
			String decryptedLine = EncodeAndDecodeJavaFiles.decode(lowerCase);
			char[] charDecryptedLine = decryptedLine.toCharArray();
			for(int i = 0; i < indexes.size(); i++)
			{
				charDecryptedLine[indexes.get(i)] = (char)((int)charDecryptedLine[indexes.get(i)]
					       								- 32);
			}
			String finalDecryptedLine = new String(charDecryptedLine);
			return finalDecryptedLine;
	}

	//Creates a decrypted version of a java file
	public static void decryptFile(File fileToDecode) throws Exception
	{
		Scanner input = new Scanner(fileToDecode);
		File decryptedFile = new File(handleFileDecryption(fileToDecode.toString()));
		PrintWriter output = new PrintWriter(decryptedFile);

		while(input.hasNextLine())
		{
			String nextLine = handleFileDecryption(input.nextLine());
			output.println(nextLine);
		}
		input.close();
		output.close();
	}
}
