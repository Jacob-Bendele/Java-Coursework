// Jacob Bendele

// Determine whether or not all knights are safe from attacking one another on a chess board.
// The chess board size bounds are int.Max x int.Max in Java. 
// The runtime goal of this solution is O(nk) where n is the number of knights and k is there location string length.

import java.util.*;
import java.lang.Character;
import java.awt.Point;

class Piece
{
	// Variables that dictate the column and row of a single given chess piece.
	static int row;
	static int column;
}

public class KnightsAttack
{
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
	// a given knight. 
	public static void stringProcessor(String coordinateString)
	{
		// Declares the String that will hold the column data substring.
		String columnCoordinate = ""; 
	
		// Runs through the length of a knight position encoded string checking for the first digit.
		for (int i = 0; i < coordinateString.length(); i++)
		{
			char ch = coordinateString.charAt(i);
			
			// Once the first digit is found, the string is parsed into substrings for the 
			// row and column respectively.
			if (Character.isDigit(ch))
			{
				Piece.row = Integer.parseInt(coordinateString.substring(i));
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
		
		// Calls horners method to retrieve a numerical representation for the knight piece column.
		Piece.column = horners(baseConValue, columnCoordinate.length());
	}
	
	public static boolean allTheKnightsAreSafe(ArrayList<String> coordinateStrings, int boardSize)
	{
		// Instantiation of a HashSet to hold knight positions.
		HashSet<Point> attackPossibility = new HashSet<>(2 * coordinateStrings.size());
		
		// Possible knight moves stored in x and y offsets. (Specific order of moves from online
		// resource on knight attacks)
		int [] x = {2, 1, -1, -2, -2, -1, 1, 2};
		int [] y = {1, 2, 2, 1, -1, -2, -2, -1};
		
		// If boardSize is zero then there are no knights to attack each other so return true.
		if (boardSize <= 0)
		{
			return true;
		}
		
		// Loops through the ArrayList of n knights checking for possible attacks.
		for (int i = 0; i < coordinateStrings.size(); i++)
		{
			String coordinate = coordinateStrings.get(i);
			stringProcessor(coordinate);
			
			// Puts parsed knight coordinates into a point and adds knight to hashset.
			Point knightPoint = new Point(Piece.column, Piece.row);
			attackPossibility.add(knightPoint);
			
			for (int j = 0; j < 8; j++)
			{
				// If piece is at boundary avoid o(n) hash search for nonexistant coordinates.
				if ((Piece.column + x[j]) > boardSize || (Piece.row + y[j]) > boardSize)
					continue;
				
				// If coord overflows or at bound avoid o(n) hash search for nonexistant coordinate.
				else if ((Piece.column + x[j]) < 0 || (Piece.row + y[j]) < 0)
					continue;
			
				// Adds offset to current knights point to check attackable positions.
				Point attack = new Point(Piece.column + x[j], Piece.row + y[j]);
				
				// Checks for if an attackable piece is on the board.
				if (attackPossibility.contains(attack))
					return false;
				
			}
		}
		
		// No knights could attack return true.
		return true;
	}
}
