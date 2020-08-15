// Jacob Bendele

// Given a list of coordinate strings for queens on an arbitrarily large square chess board, 
// and you need to determine whether any of the queens can attack one another in the given configuration.

import java.util.*;
import java.lang.Character;


public class QueensAttack
{
	// Variables that dictate the column and row of a single given queen.
	static int queenRow;
	static int queenColumn;
	
	// A helper method that utililizes Horner's rule for the efficient polynomial computation
	// of a base 26 number, based on the int value of each char from a given string.
	public static int horners(int[] baseConValue, int stringLen)
	{
		int total = baseConValue[0];
		
		for (int i = 1; i < stringLen; i++)
		{
			total = total * 26 + baseConValue[i];
		}
		
		return total;
	}
	
	// A helper method that processes the strings that encode the column and row data for a
	// a given queen. 
	public static void stringProcessor(String coordinateString)
	{
		// Declares the String that will hold the column data substring.
		String columnCoordinate = ""; 
	
		
		// Runs through the length of a queen position encoded string checking for the first digit.
		for (int i = 0; i < coordinateString.length(); i++)
		{
			char ch = coordinateString.charAt(i);
			
			// Once the first digit is found, the string is parsed into substrings for the 
			// row and column respectively.
			if (Character.isDigit(ch))
			{
				queenRow = Integer.parseInt(coordinateString.substring(i));
				columnCoordinate = coordinateString.substring(0, i);

				break;
			}
		}
		
		// Declares an array that will hold int values parsed from the column substring.
		int[] baseConValue = new int[columnCoordinate.length()];
		
		// Converts chars into usable ints ranging from 1 to 26.
		for (int i = 0; i < columnCoordinate.length(); i++)
		{
			baseConValue[i] = columnCoordinate.charAt(i) - 'a' + 1;
		}
		
		// Calls the horners method to retrieve a numerical representation for queenColumn.
		queenColumn = horners(baseConValue, columnCoordinate.length());
	}
	
	public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings, int boardSize)
	{
		// Instantiation of integer arrays for frequency counting.
		int rowFreq[] = new int[boardSize + 1];
		int columnFreq[] = new int[boardSize + 1];
		int diagonalFreq1[] = new int[boardSize * 2];
		int diagonalFreq2[] = new int[boardSize * 2];
		
		// If boardSize is zero then there are no queens to attack each other so return true.
		if (boardSize <= 0)
		{
			return true;
		}
		
		// Loops throught the ArrayList of n queens checking for possible attacks.
		for (String coordinate : coordinateStrings)
		{
			stringProcessor(coordinate);
			
			// Defensive coding for good measure.
			if (queenRow > boardSize || queenColumn > boardSize)
			{
				// False is returned because in this case we are dealing with a rogue queen.
				// She can be anywhere on the board at any given time. Very dangerous.
				return false;
			}
			
			// If there are more than 1 queen in a given row then an attack can be made.
			if (++(rowFreq[queenRow]) > 1)
			{
				return false;
			}
			
			// If there are more than 1 queen in a given column then an attack can be made.
			else if (++(columnFreq[queenColumn]) > 1)
			{
				return false;
			}
			
			// If there is more than 1 queen in a "top left to bottom right" diagonal then an 
			// attack can be made.
			// NOTE: Each diagonal has the same of row + column, therefore the same index.
			else if (++(diagonalFreq1[queenRow + queenColumn]) > 1)
			{
				return false;
			}
			
			// This utilizes the same logic as the if condition above, but has to flip the rows, such
			// that the same sum for any piece within a "top right to bottom left" diagonal is achieved.
			// NOTE: boardSize - queenRow + 1 is what flips the numerical value of a row.
			else if (++(diagonalFreq2[(boardSize - queenRow + 1) + queenColumn]) > 1)
			{
				return false; 
			}
		}
		
		return true;
	}
	// Returns a double based on the perceived difficulty rating out of 5.0.
	public static double difficultyRating()
	{
		return 2.5;
	}
	
	// Returns a double based on the amount of hours spent on this assignmnet.
	public static double hoursSpent()
	{
		return 7.0;
	}
}
