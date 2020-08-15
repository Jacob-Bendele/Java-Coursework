import java.io.*;
import java.util.*;

public class TestCase04
{
	private static void checkTest(boolean success)
	{
		System.out.println(success ? "Pass" : "Fail");
	}

	public static void main(String [] args) throws Exception
	{
		Scanner in = new Scanner(new File("input/TestCase-input.txt"));
		ArrayList<String> list = new ArrayList<String>();

		// Read each line from the input file into the ArrayList.
		while (in.hasNext())
			list.add(in.next());

		checkTest(QueensAttack.allTheQueensAreSafe(list, 20000) == false);
	}
}
