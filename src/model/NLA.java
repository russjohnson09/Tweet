package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class NLA {

	static ArrayList<Word> modifiers;
	static ArrayList<Word> posWords;
	static ArrayList<Word> negWords;

	public NLA(){
		loadWordLists();
	}
	
	public int evaluate(String s) {
		// Deliminate text into an arraylist of words(String)
		ArrayList<Word> words = deliminateText(s);

		// Check for known words and update their type and polarity
		words = lookupText(words);

		// Print the Words and their Type and Polarity
		System.out.println("NL Array: (Before resolve)");
		System.out.println("******************");
		for (int x = 0; x < words.size(); x++) {
			System.out.println(x + ", " + words.get(x).toString());
		}
		System.out.println("");

		// Resolve modifiers
		words = resolveModifiers(words);

		// Determine total text polarity
		int polarity = findPolarity(words);

		// Print the Words and their Type and Polarity
		System.out.println("NL Array: (After resolve)");
		System.out.println("******************");
		for (int x = 0; x < words.size(); x++) {
			System.out.println(x + ", " + words.get(x).toString());
		}
		System.out.println("");

		System.out.println("Final Polarity Value: " + polarity);
		return polarity;
	}
	
	public static void main(String[] args) {

		// Load word lists into arraylists
		loadWordLists();

		// Input dialog with a text field
		String input = JOptionPane.showInputDialog("Enter in some text:");
		System.out.println("NL Tweet: " + input + "\n");

		// Deliminate text into an arraylist of words(String)
		ArrayList<Word> words = deliminateText(input);

		// Check for known words and update their type and polarity
		words = lookupText(words);

		// Print the Words and their Type and Polarity
		System.out.println("NL Array: (Before resolve)");
		System.out.println("******************");
		for (int x = 0; x < words.size(); x++) {
			System.out.println(x + ", " + words.get(x).toString());
		}
		System.out.println("");

		// Resolve modifiers
		words = resolveModifiers(words);

		// Determine total text polarity
		int polarity = findPolarity(words);

		// Print the Words and their Type and Polarity
		System.out.println("NL Array: (After resolve)");
		System.out.println("******************");
		for (int x = 0; x < words.size(); x++) {
			System.out.println(x + ", " + words.get(x).toString());
		}
		System.out.println("");

		System.out.println("Final Polarity Value: " + polarity);

	}

	/*******************************************************************
	 * Deliminates text into an arraylist of Words
	 ******************************************************************/
	public static ArrayList<Word> deliminateText(String s) {

		// Break text down into words and punctuation
		Pattern stuff = Pattern.compile("[\\w']+|[\\s.,!?;:-]");
		Matcher matcher = stuff.matcher(s);
		ArrayList<Word> words = new ArrayList<Word>();
		while (matcher.find()) {
			Word w = new Word(matcher.group(0), WordType.UNKNOWN, 0);
			words.add(w);
		}

		// get rid of space characters
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).getWord().equals(" ")) {
				words.remove(i--);
			}
		}

		return words;
	}

	/*******************************************************************
	 * Looks up words and updates their WORDTYPE and POLARITY if found
	 ******************************************************************/
	public static ArrayList<Word> lookupText(ArrayList<Word> words) {

		// cycle through every word in the sentence array
		for (int tweetIndex = 0; tweetIndex < words.size(); tweetIndex++) {
			// if word is a modifier, update WORDTYPE and POLARITY
			for (int modindex = 0; modindex < modifiers.size(); modindex++) {
				if (words.get(tweetIndex).getWord()
						.equalsIgnoreCase(modifiers.get(modindex).getWord())) {
					words.get(tweetIndex).setType(
							modifiers.get(modindex).getType());
					words.get(tweetIndex).setPolarity(
							modifiers.get(modindex).getPolarity());
				}
			}

			// if word is positive, update WORDTYPE and POLARITY
			for (int posIndex = 0; posIndex < posWords.size(); posIndex++) {
				if (words.get(tweetIndex).getWord()
						.equalsIgnoreCase(posWords.get(posIndex).getWord())) {
					words.get(tweetIndex).setType(
							posWords.get(posIndex).getType());
					words.get(tweetIndex).setPolarity(
							posWords.get(posIndex).getPolarity());
				}
			}

			// if word is negative, update WORDTYPE and POLARITY
			for (int negIndex = 0; negIndex < negWords.size(); negIndex++) {
				if (words.get(tweetIndex).getWord()
						.equalsIgnoreCase(negWords.get(negIndex).getWord())) {
					words.get(tweetIndex).setType(
							negWords.get(negIndex).getType());
					words.get(tweetIndex).setPolarity(
							negWords.get(negIndex).getPolarity() * -1);
				}
			}
			
			//Double the value of words in all caps
			if (isAllCaps(words.get(tweetIndex).getWord())){
				words.get(tweetIndex).setPolarity(words.get(tweetIndex).getPolarity() * 2);
			}
		}

		return words;
	}

	/*******************************************************************
	 * Helper method for lookupText Method
	 * Determines if a string is composed of only upper case letters
	 ******************************************************************/
	public static boolean isAllCaps(String str){
		if(str.length() <= 1) //Take care of punctuation
			return false;
		for(int i=0; i<str.length(); i++){
			Character c = str.charAt(i);
			if(c.isLowerCase(c)) {
				return false;
			}
		}
		return true;
	}
	
	
	/*******************************************************************
	 * Resolves modifiers and mult. their value to neighbor words
	 ******************************************************************/
	public static ArrayList<Word> resolveModifiers(ArrayList<Word> words) {

		// resolve '!' by multiplying them to the next word of known type
		// loop though every word to find '!s'
		for (int tweetIndex = words.size() - 1; tweetIndex >= 0; tweetIndex--) {
			// if word is an '!'
			if (words.get(tweetIndex).getWord().equals("!")) {
				// find next known word and mult.
				for (int x = tweetIndex - 1; x >= 0; x--) {
					//if word i known
					if (!(words.get(x).getType() == WordType.UNKNOWN)) {
						words.get(x).setPolarity(
								words.get(tweetIndex).getPolarity()
								* words.get(x).getPolarity());
						break; // do just the first known word, not the rest
					}
				}
				words.get(tweetIndex).setPolarity(0); // set '!' polarity to
				// zero
			}
		}

		// resolve modifiers by multiplying them to the next word of known type
		// loop though every word to find other modifiers
		for (int tweetIndex = 0; tweetIndex < words.size(); tweetIndex++) {
			// if word is a modifier
			if (words.get(tweetIndex).getType() == WordType.MODIFIER
					&& !(words.get(tweetIndex).getWord().equals("!"))) {
				// find next known word and mult
				for (int x = tweetIndex + 1; x < words.size(); x++) {
					// if word is known and is not an '!'
					if (!(words.get(x).getType() == WordType.UNKNOWN)
							&& !(words.get(x).getWord().equals("!"))) {
						words.get(x).setPolarity(
								words.get(tweetIndex).getPolarity()
								* words.get(x).getPolarity());
						break; // do just the first known word, not the rest
					}
				}
				words.get(tweetIndex).setPolarity(0); // set mod to zero
			}
		}

		return words;
	}

	/*******************************************************************
	 * Determines the final polarity of the text
	 ******************************************************************/
	public static int findPolarity(ArrayList<Word> words) {

		int textPolarity = 0;
		
		// Find Average Positive value
		int avgPosValue = 0;
		for (int tweetIndex = 0; tweetIndex < words.size(); tweetIndex++) {
			if (words.get(tweetIndex).getPolarity() > 0) {
				avgPosValue += words.get(tweetIndex).getPolarity();
			}
		}
		// Find Average Negative Value
		int avgNegValue = 0;
		for (int tweetIndex = 0; tweetIndex < words.size(); tweetIndex++) {
			if (words.get(tweetIndex).getPolarity() < 0) {
				avgNegValue += words.get(tweetIndex).getPolarity();
			}
		}
		// Find the difference
		textPolarity = (avgPosValue + avgNegValue);

		return textPolarity;
	}

	/*******************************************************************
	 * Loads pos/neg/mod word lists into an arraylist of Words
	 ******************************************************************/
	public static void loadWordLists() {

		// *************** Make modifiers arraylist *******************
		Scanner scanMod = null;
		try {
			scanMod = new Scanner(new File("src/modifiers.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Bad File Path for modifiers.txt");
			e.printStackTrace();
		}

		modifiers = new ArrayList<Word>();
		while (scanMod.hasNext()) {
			Word mod = new Word(scanMod.next(), WordType.MODIFIER,
					scanMod.nextInt());
			modifiers.add(mod);
		}
		scanMod.close();

		System.out.println("*** Modifier Word List Created! ***\n");

		// ****************** Make pos_words arraylist **************
		// List derived from:
		// http://www.kisd.org/khs/english/help%20page/Descriptive%20Words.htm

		Scanner scanPos = null;
		try {
			scanPos = new Scanner(new File("src/pos_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Bad File Path for pos_words.txt");
			e.printStackTrace();
		}

		posWords = new ArrayList<Word>();

		while (scanPos.hasNext()) {
			Word pos = new Word(scanPos.next(), WordType.POSITIVE,
					scanPos.nextInt());
			posWords.add(pos);
		}
		scanPos.close();

		System.out.println("*** Positive Word List Created! ***\n");

		// ********************* Make neg_words arraylist *********************
		// List derived from:
		// http://www.kisd.org/khs/english/help%20page/Descriptive%20Words.htm
		Scanner scanNeg = null;
		try {
			scanNeg = new Scanner(new File("src/neg_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Bad File Path for neg_words.txt");
			e.printStackTrace();
		}

		negWords = new ArrayList<Word>();

		while (scanNeg.hasNext()) {
			Word neg = new Word(scanNeg.next(), WordType.NEGATIVE,
					scanNeg.nextInt());
			negWords.add(neg);
		}
		scanNeg.close();

		System.out.println("*** Negitive Word List Created! ***\n");
	}
}
