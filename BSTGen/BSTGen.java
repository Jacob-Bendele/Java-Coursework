// Jacob Bendele

// Practice with generics in Java by converting an implementation of a Binary Search Tree to accept comparable objects.

import java.io.*;
import java.util.*;

// This is the class that defines the nodes of a Binary Search Tree (BST).
class Node<T>
{
	T data;
	Node<T> left, right;

	Node(T data)
	{
		this.data = data;
	}
}

public class BSTGen<AnyType extends Comparable<AnyType>>
{
	private Node<AnyType> root;
	
	// Public insert method that captures the root of the BST upon insertion of a node.
	public void insert(AnyType data)
	{
		root = insert(root, data);
	}

	// Insert method that takes a Node and piece of data to perform an insert on a BST. Returns a Node.
	private Node<AnyType> insert(Node<AnyType> root, AnyType data)
	{
		// If the root is null create one. Otherwise, check if the data within the passed parameter
		// node is greater than or less than the passed data. Recursively traverse the BST for the
		// appropriate insertion position.
		if (root == null)
		{
			return new Node<AnyType>(data);
		}
		
		else if (data.compareTo(root.data) == -1)
		{
			root.left = insert(root.left, data);
		}
		
		else if (data.compareTo(root.data) == 1)
		{
			root.right = insert(root.right, data);
		}

		return root;
	}

	// Public delete method that captures the root of the modified BST upon deletion of a node.
	public void delete(AnyType data)
	{
		root = delete(root, data);
	}

	// Recusively deletes a node from a BST and returns the root of the modified BST.
	private Node<AnyType> delete(Node<AnyType> root, AnyType data)
	{
		// Defense against dereferencing null roots.
		if (root == null)
		{
			return null;
		}
		
		// Checks to see which branch of the BST to recursively traverse.
		else if (data.compareTo(root.data) == -1)
		{
			root.left = delete(root.left, data);
		}
		
		else if (data.compareTo(root.data) == 1)
		{
			root.right = delete(root.right, data);
		}
		
		// Checks for the three cases of BST node deletion.
		else
		{
			// 1) A node has no children.
			if (root.left == null && root.right == null)
			{
				return null;
			}
			
			// 2) A node has one child, either the left or right.
			else if (root.left == null)
			{
				return root.right;
			}
			
			else if (root.right == null)
			{
				return root.left;
			}
			
			// 3) A node has two children. Find the max of the left subtree and swap it.
			else
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is not empty.
	private AnyType findMax(Node<AnyType> root)
	{
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}

	// Takes a piece of data as a parameter and calls the private contains method. Returns T/F.
	public boolean contains(AnyType data)
	{
		return contains(root, data);
	}

	// Called by public contains method. Takes data and root as parameters.
	// Checks if BST contains "data". Returns T/F.
	private boolean contains(Node<AnyType> root, AnyType data)
	{
		// Defense against dereferencing null root.
		if (root == null)
		{
			return false;
		}
		
		// Determines traversal path based on the data at each node.
		else if (data.compareTo(root.data) == -1)
		{
			return contains(root.left, data);
		}
		
		else if (data.compareTo(root.data) == 1)
		{
			return contains(root.right, data);
		}
		
		// If the data is not greater or less than the passed data it is equal to the passed data.
		else
		{
			return true;
		}
	}

	// Provides formatting for inorder traversal and calls the private inorder method.
	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	// Recursively traverses the BST's inorder and prints along the way.
	private void inorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	// Provides formatting for preorder traversal and calls the private preorder method.
	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	// Recursively traverses the BST's preorder and prints along the way.
	private void preorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	// Provides formatting for the postorder traversal and calls the private postorder method.
	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	// Recursively traverses the BST's postorder and prints along the way.
	private void postorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}
}
