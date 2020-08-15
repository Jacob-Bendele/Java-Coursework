

import java.io.*;
import java.util.*;

public class Driver
{
	public static void main(String [] args)
	{
		// Create a BSTGen.
		BSTGen<Integer> myTree = new BSTGen<Integer>();

		int [] array = {90, 54, 74, 52, 99, 6, 3, 43, 55, 67};

		for (int i = 0; i < array.length; i++)
			myTree.insert(array[i]);

		myTree.inorder();
		myTree.preorder();
		myTree.postorder();
	}
}
