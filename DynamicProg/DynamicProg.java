// Jacob Bendele

import java.lang.*;
import java.io.*;

public class DynamicProg
{
	// Takes an input of 'blocks' array that contains ints. Imagine Mario jumping to hit blocks, but he can not hit two block consecutively.
	// The goal is to instead collect the most amount of points (highest ints) while following this constraint of not being able to hit the immediate
	// block after our first choice.
	public static int maxGain(int [] blocks)
	{
		int length = blocks.length;
		// Dynamic programming array.
		int dp[] = new int [Math.max(2, length + 1)];
		
		dp[0] = 0;
		dp[1] = blocks[0];
		
		for (int i = 2; i <= length; i++)
		{
			// dp array is filled in a way that mimics recursion relation below.
			dp[i] = Math.max(dp[i-1], (dp[i-2] + blocks[i-1]));
		}
		
		return dp[length];
	}
	
	// Recursive solution that takes blocks array and length as inputs.
	private static int maxGainRecursive(int []  blocks, int length)
	{
		if (length <= 0)
			return 0;

		if (length == 1)
			return blocks[0]; 
	
		return Math.max(maxGainRecursive(blocks, length - 1), (maxGainRecursive(blocks, length - 2) + blocks[length - 1]));
	}
}