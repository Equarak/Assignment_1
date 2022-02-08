import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Scanner;

public class PasswordCheckerUtility {
	
	public static boolean isValidLength (String str) throws LengthException {
		if(str.length() < 6) throw new LengthException("The password must be at least 6 characters long");
		return true;
	}

	/**
	 * Checks a String and returns true if a digit is found within it.
	 * @param str the password
	 * @return true if there is a digit within the String, false otherwise
	 */
	public static boolean hasDigit (String str) throws NoDigitException {
		for(int i = 0; i < str.length(); i++)
			if(Character.isDigit(str.charAt(i))) return true;
		throw new NoDigitException("The password must contain at least one digit");
	}

	/**
	 * Checks a String and returns true if it contains an upper case letter.
	 * @param str the password
	 * @return true if the String contains an upper case letter, false otherwise
	 */
	public static boolean hasUpperAlpha(String str) throws NoUpperAlphaException {

		//Uses a regular expression to check for upper case letters
		Pattern pattern = Pattern.compile(".*[A-Z].*"); 
		Matcher matcher = pattern.matcher(str); 
		if (matcher.matches())
			return true;
		throw new NoUpperAlphaException("The password must contain "
				+ "at least one uppercase alphabetic character");

	}

	/**
	 * Checks a String and returns true if it contains a lower case letter.
	 * @param str the password
	 * @return true if the String contains a lower case letter, false otherwise
	 */
	public static boolean hasLowerAlpha(String str) throws NoLowerAlphaException {

		//Uses a regular expression to check for upper case letters
		Pattern pattern = Pattern.compile(".*[a-z].*"); 
		Matcher matcher = pattern.matcher(str); 
		if (matcher.matches())
			return true;
		throw new NoLowerAlphaException("The password must contain "
				+ "at least one lowercase alphabetic character");

	}

	/**
	 * Checks a String and returns false if there is more 
	 * than three of the same character in a row.
	 * @param str the password
	 * @return false if there is more than three of the
	 * same character in a row, true otherwise
	 */
	public static boolean NoSameCharInSequence(String str) throws InvalidSequenceException {

		/*
		 * Uses loops to manually check for sequences of more than 2 characters.
		 * If the length of the array is two or less, then there are no checks.
		 */
		
		for(int i = 0; i <= str.length()-3; i++) {
			if(str.charAt(i) == str.charAt(i+1)) 
				throw new InvalidSequenceException("The password cannot contain more"
						+ " than two of the same character in sequence");
		}
		return true;
	}

	/**
	 * Checks a String and returns true if 
	 * it has a special symbol 
	 * (a character that is not a letter or a number)
	 * @param str the password
	 * @return true if there is a special symbol, false otherwise
	 */
	public static boolean hasSpecialChar (String str) throws NoSpecialCharacterException {
		for (int i=0; i < str.length(); i++) {
			String letter = Character.toString(str.charAt(i));
			if ("!@#$%^&*()_+=-{}[]:;\\\"'?/>.<,\\\\|`~".contains(letter))
				return true;
		}
		throw new NoSpecialCharacterException("The password must"  +
		" contain at least one special character");
	}

	/**
	 * Returns true if the password (the String parameter) 
	 * is considered valid and throws one of the
	 * following exceptions if it is not.
	 * 
	 * To be considered valid, the password must have at least 6 characters, one digit,
	 * one upper case letter, one lower case letter, no more than two of the same character
	 * in a row, and at least one special character (the exceptions will be thrown in that order).
	 * @param password the password
	 * @return true if the password is valid, throws an exception otherwise
	 */
	public static boolean isValidPassword(String password) throws LengthException,
	NoDigitException, NoUpperAlphaException, NoLowerAlphaException, InvalidSequenceException,
	NoSpecialCharacterException{

		/*
		 * Checks length of password and throws a 
		 * LengthException if it is less than 6

		 */
		if (isValidLength(password)) {
			if(hasDigit(password)) {
				if (hasUpperAlpha(password)) {
					if(hasLowerAlpha(password)) {
						if(hasSpecialChar(password)) {
							if(NoSameCharInSequence(password))
								return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	/**
	 * Checks a password and returns true if its length is >= 6 and <= 9.
	 * @param password the password
	 * @return true if the String's length is >= 6 and <= 9, false otherwise.
	 */
	public static boolean isWeakPassword(String password) throws WeakPasswordException {
		if(password.length() >= 6 && password.length() <= 9) 
			throw new WeakPasswordException("The password is OK but weak - it contains fewer than 10 characters.");
		return false;
	}

	/**
	 * Reads a file of passwords and returns an ArrayList of Strings containing them
	 * @param name the file name
	 * @return an ArrayList of Strings containing the passwords from said file
	 */
	public static ArrayList<String> readFile(String name){

		//This method uses the Scanner class to read the file
		ArrayList<String> passwords = new ArrayList<String>();
		Scanner inFile;

		//Reads the file and adds each password in it to the ArrayList
		try {

			if(!name.contains(".txt"))
				inFile = new Scanner(new File(name+".txt"));
			else inFile = new Scanner(new File(name));

			while(inFile.hasNext())
				passwords.add(inFile.nextLine());
		}catch(Exception e) {
			e.printStackTrace();
		}

		return passwords;
	}

	/**
	 * Scans an ArrayList of passwords and returns an ArrayList of all of the 
	 * invalid passwords from said list
	 * @param passwords the ArrayList of passwords being scanned
	 * @return an ArrayList of all the invalid passwords from the ArrayList
	 */
	public static ArrayList<String> getInvalidPasswords(ArrayList<String> passwords){
		ArrayList<String> invalids = new ArrayList<String>();
		
		/*
		 * Runs through the ArrayList and add all passwords that return false in
		 * isValidPassword(password)
		 */
		for(int i = 0; i < passwords.size(); i++) {
			try {
				isValidPassword(passwords.get(i));
			}catch(Exception e) {
				//System.out.println(passwords.get(i));
				invalids.add(passwords.get(i) + " - " + e.getMessage());
			}
		}
		return invalids;
	}

	public static void comparePasswords(String password, String passwordConfirm) throws UnmatchedException {
		if (!password.equals(passwordConfirm))
			throw new UnmatchedException("The passwords do not match");
	}
	
	public static boolean comparePasswordsWithReturn(String password, String passwordConfirm) {
		return password.equals(passwordConfirm);
	}
}


