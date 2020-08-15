import java.io.*;
import java.util.*;

public class TestCase
{
	private static void checkTest(boolean success)
	{
		System.out.println(success ? "Pass" : "Fail");
	}

	public static void main(String [] args) throws Exception
	{
		Scanner in = new Scanner(new File("input/TestCaseinput.txt"));
		ArrayList<String> list = new ArrayList<String>();

		// Read each line from the input file into the ArrayList.
		while (in.hasNext())
			list.add(in.next());

		checkTest(KnightsAttack.allTheKnightsAreSafe(list, Integer.MAX_VALUE) == true);
	}
}
