import java.io.*;
import java.util.*;

public class TestCase
{
	private static void fail(String params)
	{
		System.out.println("Test Case Failed: hasExactTopoSort(" + params + ")");
		System.exit(0);
	}

	public static void main(String [] args) throws IOException
	{
		exactTopologicalSort t = new exactTopologicalSort("input/g1.txt");

		if (t.hasExactTopoSort(3, 1) != false) fail("3, 1");
		if (t.hasExactTopoSort(3, 2) != false) fail("3, 2");
		if (t.hasExactTopoSort(3, 4) != true) fail("3, 4");
		if (t.hasExactTopoSort(2, 1) != true) fail("2, 1");
		if (t.hasExactTopoSort(2, 3) != true) fail("2, 3");
		if (t.hasExactTopoSort(2, 4) != true) fail("2, 4");
		if (t.hasExactTopoSort(4, 1) != false) fail("4, 1");
		if (t.hasExactTopoSort(4, 2) != false) fail("4, 2");
		if (t.hasExactTopoSort(4, 3) != false) fail("4, 3");
		if (t.hasExactTopoSort(1, 2) != true) fail("1, 2");
		if (t.hasExactTopoSort(1, 3) != true) fail("1, 3");
		if (t.hasExactTopoSort(1, 4) != true) fail("1, 4");

		System.out.println("Test Case Passed");
	}
}
