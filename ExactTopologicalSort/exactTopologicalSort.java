// Jacob Bendele

// Given a directed graph, G, and two integers, x and y, determine whether G has a valid topological sort in which vertex 
// x comes before vertex y. (Notice that the problem does not ask whether x comes directly before y.)

import java.util.*;
import java.io.*;

public class exactTopologicalSort
{
	// Instance variables for:
	// Adjacency Matrix; vertex degree; number of vertices; incoming edges of vertices
	private boolean [][] adjMatrix;
	private int [] vDegree;
	private int incoming[];
	private int numVert;
	
	// Constructor takes file formated to represent a directed graph and initializes above instance variables.
	public exactTopologicalSort(String filename) throws IOException
	{
		Scanner in = new Scanner(new File(filename));
		
		numVert = in.nextInt();
		
		// All arrays offset by 1 so there is no need to subtract 1 from inputs in later methods.
		adjMatrix = new boolean[numVert + 1][numVert + 1];
		vDegree = new int[numVert + 1];
		incoming = new int[numVert + 1];
		
		// For each vertex read its degree number and then iterate the degree (num outgoing edges)
		// to fill in the adjacency matrix for this vertex.
		for (int i = 1; i <= numVert; i++)
		{
			vDegree[i] = in.nextInt();
			
			for (int j = 1; j <= vDegree[i]; j++)
			{
				// f briefly holds the last read int
				int f;
				adjMatrix[i][f = in.nextInt()] = true;
				incoming[f] += (adjMatrix[i][f] ? 1 : 0);
			}
		}
	}
	
	// A recursive DFS function that takes a starting vertex, a key vertex 'x', and a visited array.
	// If the key is found return true else return false.
	// DFS search O(|V|^2) w/ (adjMatrix).
	private boolean xDependyDFS(int x, int vertex, boolean [] visited)
	{
		visited[vertex] = true;
		boolean lastKnown = false;
		
		// If key value was part of DFS return true.
		if (vertex == x)
			return true;
		
		for (int i = 0; i < numVert + 1; i++)
			if (adjMatrix[vertex][i] && !visited[i])
				// If DFS returned true at any point continue returning true to pop call stack.
				// lastKnown will capture that last true returned, such that this method can return
				// a useful boolean.
				if (lastKnown = xDependyDFS(x, i, visited))
					return true;
				
		if (lastKnown)
			return true;
		
		return false;
	}
	
	// Takes x and y input and returns boolean corresponding to if x can occur before y in a valid
	// topological sort.
	public boolean hasExactTopoSort(int x, int y)
	{
		boolean [] visited = new boolean[numVert + 1];
		
		// Self dependency check which means there is a cycle in the graph.
		for (int i = 1; i <= numVert; i++)
			if (adjMatrix[i][i] == true)
				return false;
		
		// If x and y are equal they are the same vertex. If they are the same vertex
		// there is no topological sort that allows x before or after y.
		if (x == y)
			return false;
		
		// Exit early if x is a direct 'prerequisite' to y.
		else if (adjMatrix[x][y] == true)
			return true;
		
		// If the DFS returned true x occured in a DFS starting at y, therefore y is a 
		// 'prerequisite' of x. X cannot come before Y.
		else if (xDependyDFS(x, y, visited))
			return false;
		
		// Default: DFS did not see that x came after y therefore x can come before y.
		return true;
	}
}