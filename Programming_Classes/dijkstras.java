public class dijkstras
{
	//Finds the index of the min in the array, while only considering values greater than -1.
	//Vertices not satisfying this condition, are marked as Visited, hence we skip them.
	//Returns -1 if minimum is Infinty
	public static int findMinDex(double[] vertices)
	{
		double min = Double.POSITIVE_INFINITY;
		int minDex = -1;
		for(int i = 0; i < vertices.length; i++)
		{
			if(vertices[i] > -1 && vertices[i] < min)
			{
				min = vertices[i];
				minDex = i;
			}
		}
		return minDex;
	}

	public static void main(String[] args)
	{
		//Change how many Vertices in the graph you want
		int numberOfVertices = 5;

		//Create the array of vertices.
		//Vertex 0, get's a path value of 0 (because it's already at itself!!)
		//Every other Vertex recieves an initial path value of Infinity (We dont know how far it is, but we're pessimistic!!)
		double[] vertices = new double[numberOfVertices];
		for(int i = 1; i < vertices.length; i++) vertices[i] = Double.POSITIVE_INFINITY;

		//Create the matrix, representing all the vertices and possible neighbors.
		//A value of 0 means that there is no edge between these vertices. 
		//The mapping is [row][col], which means that at that spot in the matrix,
		//there is an edge with a cost of that value, from row to col.
		//EXAMPLE: graph[3][1] = 5. Means there's an edge cost of 5 from vertex 3 TO 1.
		double[][] graph = new double[numberOfVertices][numberOfVertices];

		/*Use the space here (create more or less), to populate the graph yourself*/
		graph[0][1] = 10;
		graph[0][3] = 30;
		graph[0][4] = 100;
		graph[1][2] = 50;
		graph[2][4] = 10;
		graph[3][2] = 20;
		graph[3][4] = 60;
		
		//Uncomment below for an example that doesn't work (Start and End Vertex arent connected), and change numberOfVertices to 3
		/*graph[0][1] = 3; 
		graph[1][0] = 1;
		graph[2][0] = 2;*/
		/*---------------------------END POPULATING GRAPH---------------------------*/


		/*Begin Dijkstr'as algorithm. Following the steps below:
		 * Find the vertex with the mininum path value (call it currentVertex)
		 * Stop the algorithm if:
		 *   - The minimum path value belongs to the destination vertex, (yay we did it!)
		 *   - The minimum path value is Infinity (Better luck next time!)
		 * Otherwise, visit all the neighbors of currentVertex that have NOT been Visited yet and do the following:
		 *   - Set this neighbors path value as the minimum between:
		 *     -- It's current path value
		 *     -- The sum of the currentVertex's path value and the edge cost between them.
		 */
		int minDexVertex = findMinDex(vertices);
		double minVertexPath = minDexVertex > -1 ? vertices[minDexVertex] : Double.POSITIVE_INFINITY;
		while(minDexVertex != numberOfVertices - 1 && minVertexPath < Double.POSITIVE_INFINITY)
		{
			//Update all the neighbors path values (Iterating the row corresponding to the minDex value)
			int row = minDexVertex;
			for(int col = 0; col < graph[row].length; col++)
			{
				//Skip if 0
				if(graph[row][col] == 0) continue;

				//Otherwise, set this neighbors new path value accordingly. 
				vertices[col] = Math.min(vertices[col], vertices[row] + graph[row][col]);
			}

			//Zero's out all column values for this vertex, to update and mark this Neighbor as Visited for every Vertex.
			for(int i = 0; i < graph.length; i++) graph[i][minDexVertex] = 0;

			//Mark this Vertex as visited in vertices by making it -1 (Never coming back here!)
			vertices[minDexVertex] = -1;

			//Get new mins
			minDexVertex = findMinDex(vertices);
			minVertexPath = minDexVertex > -1 ? vertices[minDexVertex] : Double.POSITIVE_INFINITY;
		}

		//Print the results
		System.out.println("The minimum cost path is: " + minVertexPath);
	}
}
