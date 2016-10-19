import java.util.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;




class morseTranscription
{
// -----------------------------------------
// Attrbibuts
// -----------------------------------------
	private static String	resourceDir = "../resource/";
	private static String[]	lettersMorse= new String['Z'-'A'+1];


// -----------------------------------------
// Local Methode
// -----------------------------------------
	private static void buildMorseLetters()
	{
		FileReader fic;
		Scanner	sc;

		try
		{
			fic	= new FileReader(resourceDir + "morse-code.txt");
			sc	= new Scanner(fic);
			for (int i=0; i<('Z'-'A'+1); i++)
			{
				sc.next();
				lettersMorse[i] = sc.next();
			}
			fic.close();
			sc.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	private static void translateDictionary()
	{
		FileReader ficIn;
		FileWriter ficOut;
		Scanner	sc;
		String wordEng, wordMor;
		int morseIndex;

		try
		{
			ficIn	= new FileReader(resourceDir + "dict-english-clean.txt");
			ficOut	= new FileWriter(new File(resourceDir + "dict-english-morse.txt"));
			sc	= new Scanner(ficIn);
			while(sc.hasNext())
			{
				wordEng	= sc.next();
				wordMor	= "";
				for (int i=0; i<wordEng.length(); i++)
				{
					morseIndex = wordEng.charAt(i) - 'A';
					wordMor += lettersMorse[morseIndex];
				}
				ficOut.write(wordEng + "\t" + wordMor + "\n");
			}
			ficIn.close();
			ficOut.close();
			sc.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void cleanEngDictionary()
	{
		FileReader ficIn;
		FileWriter ficOut;
		Scanner	sc;
		String wordEng, wordMor;
		int morseIndex, i;
		char c;

		try
		{
			ficIn	= new FileReader(resourceDir + "dict-english.txt");
			ficOut	= new FileWriter(new File(resourceDir + "dict-english-clean.txt"));
			sc	= new Scanner(ficIn);
			while(sc.hasNext())
			{
				wordEng	= sc.next();
				for (i=0; i<wordEng.length(); i++)
				{
					c = wordEng.charAt(i);
					if ((c < 'A') || (c > 'Z')) break;
				}
				if (i < wordEng.length()) continue;
				ficOut.write(wordEng + "\n");
			}
			ficIn.close();
			ficOut.close();
			sc.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
			
	}

// -----------------------------------------
// Main Methode
// -----------------------------------------
	public static void main(String args[])
	{
		//cleanEngDictionary();
		buildMorseLetters();
		translateDictionary();
	}
}
