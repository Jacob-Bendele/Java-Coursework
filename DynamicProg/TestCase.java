import java.io.*;
import java.util.*;

public class TestCase
{
	private static void fail()
	{
		System.out.println("Fail");
		System.exit(0);
	}

	public static void main(String [] args)
	{
		int [] blocks = new int[] {15, 3, 6, 17, 2, 1, 20};
		int ans = 52;

		if (DynamicProg.maxGain(blocks) != ans) fail();

		System.out.println("Pass");
	}
}
