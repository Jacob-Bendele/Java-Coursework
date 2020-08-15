// Jacob Bendele

// Implementation of a probabilistic data structure called a skiplist. The skiplist operates like a linked list where every
// node points to a noide further along in the list, but with a a key difference. This difference comes in the form of height.
// The skiplist has a heigh element that allows searching runtimes to be reduced. This veritcality will allow a search operation to 
// skip more than one node ahead at a time, thus imporving search times. Imagine skyscrapers, some are taller than others and have line of sight
// while others are shorter and cannot be seen throught the taller ones.
// The height of these skiplist towers are evened out by the probabilistic side of the datasturcuture. When the height of the skiplist grows
// an operation occurs with a 50% chance to grow the remaining towers. The growth of tower is adding a null reference on top for future use.
// Deletion works similarily.

import java.util.*;
import java.lang.Math;

class Node<AnyType extends Comparable<AnyType>>
{
	// Instance variabls specific to each node.
	private int height;
	private AnyType data;
	private ArrayList<Node<AnyType>> next;
	private Random rand = new Random();	

	// Node constructor that only uses height and leaves the data field blank. Used for head creation.
	Node(int height)
	{	
		this.height = height;
		
		next = new ArrayList<>(10 * this.height);
		
		for (int i = 0; i < this.height; i++)
		{
			next.add(null);
		}
	}
	
	/// Node constructor used to create standard nodes that hold data.
	Node(AnyType data, int height)
	{
		this(height);
		this.data = data;
	}
	
	// Returns node's data
	public AnyType value()
	{
		return this.data; 
	}
	
	// Returns node's height
	public int height()
	{
		return this.height;
	}
	
	// Retrieves reference of specified level from node's next arraylist.
	public Node<AnyType> next(int level)
	{
		if ((level < 0) || (level > (this.height - 1)))
		{
			return null;
		}

		return next.get(level);
	}
	
	// Adds passed node to the specified level of the node's next arraylist.
	public void setNext(int level, Node<AnyType> node)
	{
		next.set(level, node);
	}
	
	// Grows the this node by 1 level.
	public void grow()
	{
		next.add(null);
		height++;
	}
	
	// 50% percent chance of growing current node.
	public boolean maybeGrow()
	{
		boolean i = rand.nextBoolean();
		
		if (i)
		{
			grow();
			return true;
		}	
		
		return false;
	}
	
	// Trims one level from this node.
	public void trim()
	{
		next.remove(this.height - 1);
		this.height--;
	}
	
	// Trims level down to specified height for this node.
	public void trim(int height)
	{
		for (int i = this.height; i > height; i--)
		{
			next.remove(i - 1);
		}
		
		this.height = height;
	}
}

public class SkipList<AnyType extends Comparable<AnyType>>
{
	// Instance and Class variables
	private int numNodes;
	private Node<AnyType> head;
	static final double log2 = Math.log(2);
	private static Random rand = new Random();

	// Constructs a SkipList of default heigh (which is 1).	
	SkipList()
	{
		head = new Node<>(1);
		numNodes = 0;
	}
	
	// Constructor that initializes the height of the SkipList.
	SkipList(int height)
	{
		if (height <= 1)
		{
			head = new Node<>(1);
			numNodes = 0;
		}
		
		else
		{	
			head =  new Node<>(height);
			numNodes = 0;
		}
	}
	
	// Generates a random height for a node upon insertion.
	private static int generateRandomHeight(int maxHeight)
	{
		int heightCounter = 0;
		boolean i = false;
		
		while (i != true && heightCounter < maxHeight)
		{
			i = rand.nextBoolean();
			heightCounter++;
		}
		
		return heightCounter;
	}
	
	// Trims the SkipList upon deletion of a node.
	private void trimSkipList()
	{
		int nodeHeightMax = getMaxHeight(numNodes);
		Node<AnyType> temp, temp2;
		
		for (int i = head.height() - 1; i > nodeHeightMax - 1; i--)
		{	
				temp = head;
				
				while (temp != null)
				{
					temp2 = temp.next(height() - 1);
					temp.trim();
					temp = temp2;
				}
		}
	}
	
	// Grows the skiplist based upon an insertion triggering a height increase.
	private void growSkipList()
	{
		int headHeight = height();
		int nodeHeightMax = getMaxHeight(numNodes);
		Node<AnyType> temp;
		boolean flag;
		
		if (headHeight < nodeHeightMax)
		{
			head.grow();
			
			temp = head;
			
			while (temp.next(height() - 2) != null)
			{	
				flag = false; 
				
				flag = temp.next(height() - 2).maybeGrow();
				
				if (flag)
				{
					temp.setNext(temp.height() - 1, temp.next(height() - 2));
				}
				
				temp = temp.next(head.height() - 2);
			}
		}		
	}
	
	// Returns height of the head node of the skiplist.
	public int height()
	{
		return head.height();
	}
	
	// Returns size of the skiplist.
	public int size()
	{
		return numNodes;
	}
	
	// Determines the max height that the skiplist will support.
	private static int getMaxHeight(int n)
	{
		if (n == 0)
			return 1;
		
		else if (n == 1)
			return 1; 
		
		else
			return (int)Math.ceil(Math.log(n)/log2);
	}
	
	// Returns the head of the skiplist.
	public Node<AnyType> head()
	{
		return head;
	}
	
	// Takes a data parameter and creates a node for it.
	public void insert(AnyType data)
	{
		Node<AnyType> temp = head;
		int newHeight;
		// Holds the nodes at which the traversal drops levels initialized to 40 for large numbers of nodes.
		ArrayList<Node<AnyType>> dropNodes = new ArrayList<>(40);
		
		// Traverses the skiplist checking if the next value is less than our current data.
		// If it is then we hop to that node and search further.
		for (int i = height() - 1; i >= 0; i--)
		{
			while (temp.next(i) != null && (data.compareTo(temp.next(i).value()) > 0))
			{
				temp = temp.next(i);
			}
			
			// Every time the search drops a level we store that node for remapping pointers.
			dropNodes.add(temp);
		}
		
		// Determines max height allowed for the number of nodes.
		int maxH = getMaxHeight(numNodes);
		
		// If maxH is greater than our current height we pass it to generate a random height.
		if (height() < maxH)
			newHeight = generateRandomHeight(maxH);
		
		else
			newHeight = generateRandomHeight(height());

		// Creation of a new node containg data for insertion.
		Node<AnyType> newNode = new Node<>(data, newHeight);
		numNodes++;
		
		// Remaps the nodes to correct next(pointer) values after the insertion.
		for (int i = dropNodes.size() - 1; i >= 0; i--)
		{
			int index = (dropNodes.size() - 1 - i);
			
			if (index <= newNode.height() - 1 && index <= dropNodes.get(i).height() - 1)
			{	
				newNode.setNext(index, dropNodes.get(i).next(index));
				dropNodes.get(i).setNext(index, newNode);
			}
		}	
		
		// Calls GrowSkipList as it determines if it should grow the skiplist.
		growSkipList();
	}
	
	// Perfroms an insertion identical to the one above, but with a fixed height
	// insead of a randomly generated one.
	public void insert(AnyType data, int height)
	{
		Node<AnyType> temp = head;
		ArrayList<Node<AnyType>> dropNodes = new ArrayList<>(40);
		
		for (int i = height() - 1; i >= 0; i--)
		{
			while (temp.next(i) != null && (data.compareTo(temp.next(i).value()) > 0))
			{
				temp = temp.next(i);
			}
			
			dropNodes.add(temp);
		}
		
		Node<AnyType> newNode = new Node<>(data, height);
		numNodes++;
		
		for (int i = dropNodes.size() - 1; i >= 0; i--)
		{
			int index = (dropNodes.size() - 1 - i);
			
			if (index <= newNode.height() - 1 && index <= dropNodes.get(i).height() - 1)
			{	
				newNode.setNext(index, dropNodes.get(i).next(index));
				dropNodes.get(i).setNext(index, newNode);
			}
		}	
		
		growSkipList();		
	}

	// Performs a deletion of a node containing the parameter data in a skiplist.
	public void delete(AnyType data)
	{
		Node<AnyType> temp = head;
		Node<AnyType> temp2;
		ArrayList<Node<AnyType>> dropNodes = new ArrayList<>(40);
		
		// Traverses the skiplist checking if the next value is less than our current data.
		// If it is then we hop to that node and search further.
		for (int i = height() - 1; i >= 0; i--)
		{
			while (temp.next(i) != null && (data.compareTo(temp.next(i).value()) > 0))
			{
				temp = temp.next(i);
			}
			
			// Every time the search drops a level we store that node for remapping pointers.
			dropNodes.add(temp);
		}
		
		temp2 = temp.next(0);
		
		// If the value referenced by temp2 is the value we want to delete,
		// Remap the nodes next pointers and abandon it in memory for java to deal with.
		if (temp2 != null && (data.compareTo(temp2.value()) == 0))
		{
			for (int i = dropNodes.size() - 1; i >= 0; i--)
			{
				int index = (dropNodes.size() - 1 - i);
				
				if (index <= temp2.height() - 1 && index <= dropNodes.get(i).height() - 1)
				{	
					dropNodes.get(i).setNext(index, temp2.next(index));
				}
			}
			
			// Decrement the number of nodes and call on the trim function.
			numNodes--;
			trimSkipList();
		}
	}
	
	// Searches through skiplist and returns true if the skiplist contains a node
	// containg parameter data. False otherwise.
	public boolean contains(AnyType data)
	{
		Node<AnyType> temp = head;
		
		for (int i = height() - 1; i >= 0; i--)
		{
			while (temp.next(i) != null && (data.compareTo(temp.next(i).value()) > 0))
			{
				temp = temp.next(i);
			}
		}
		
		if (data.compareTo(temp.next(0).value()) == 0)
			return true;
		
		return false;
	}
	
	// Uses identical searching as the contains function above, but instead returns
	// a reference to the sought after node.
	public Node<AnyType> get(AnyType data)
	{
		Node<AnyType> temp = head;
		
		for (int i = height() - 1; i >= 0; i--)
		{
			while (temp.next(i) != null && (data.compareTo(temp.next(i).value()) > 0))
			{
				temp = temp.next(i);
			}
		}
		
		if (data.compareTo(temp.next(0).value()) == 0)
			return temp.next(0);
		
		else 
			return null;
	}
}

