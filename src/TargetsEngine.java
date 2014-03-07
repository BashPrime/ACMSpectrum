import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TargetsEngine
{
	static ArrayList<LinkedList<String>> targets;
	public static void main(String [] args) throws IOException
	{
		String s;
		targets = new ArrayList<LinkedList<String>>();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		while ((s = stdin.readLine()) != null)
		{
			if (s.length() > 80)
				System.exit(1);

			String [] sArr = s.split(" ");

			switch (sArr.length)
			{
				// Two arguments
				case 2:
				{
					if (sArr[1].length() > 32)
						System.exit(1);
	
					switch (sArr[0])
					{
					case "add":
					{
						// add target
						// adds the target to database
						add(sArr[1]);
						break;
					}
	
					case "connections":
					{
						// connections target
						// Prints the number of hops to direct and indirect connections from the target, beginning with a hop count of 0
						connections(sArr[1]);
						break;
					}
	
					default:
					{
						System.exit(1);
						break;
					}
					}
					//end switch					
					break;
				}
	
				// Three arguments
				case 3:
				{
					if (sArr[1].length() > 32 || sArr[2].length() > 32)
						System.exit(1);
	
					switch(sArr[0])
					{
						case "add":
						{
							// add target1 target2
							// creates a bidirectional connection between the two targets
							add(sArr[1], sArr[2]);
							break;
						}
		
						case "associated":
						{
							// associated target1 target2
							// prints info about the existence of a connection between the two targets:
							// if yes, shows the hopcount of target2 with respect to target1
							// otherwise print no
							associated(sArr[1], sArr[2]);
							break;
						}
		
						default:
						{
							System.exit(1);
							break;
						}
					}
					// end switch
					break;
				}
	
				default:
				{
					System.exit(1);
					break;
				}
			}
			//end switch
		}
		//end while
		System.exit(0);
	}
	//end main


	/* STATIC METHODS */
	public static void add(String target)
	{
		if (!targets.isEmpty())
		{		
			// if target does not exist in database
			if (getTargetIndex(target) == -1)
			{
				targets.add(new LinkedList<String>());
				targets.get(targets.size() - 1).add(target);
			}
		}

		else
		{
			targets.add(new LinkedList<String>());
			targets.get(targets.size() - 1).add(target);
		}
	}
	// end add(1 param)

	public static void add(String target1, String target2)
	{
		//this add operation only happens if target1 and target2 aren't the same strings
		if (!(target1.equals(target2)))
		{
			add(target1);
			add(target2);
			int t1index = getTargetIndex(target1);

			// if no connection between target1 and target2 exists, then the vice versa connection also does not exist. It must be added!
			if (targets.get(t1index).indexOf(target2) == -1)
			{
				int t2index = getTargetIndex(target2);
				targets.get(t1index).add(target2);
				targets.get(t2index).add(target1);
			}
		}

		else
			add(target1);
	}
	// end add(2 params)

	public static void connections(String target)
	{
		int tIndex;

		// If the target exists
		if ((tIndex = getTargetIndex(target)) != -1)
		{
			if (targets.get(tIndex).size() > 1)
				hopSearch(tIndex);

			else
				System.out.println("no connections");
		}

		else
			System.out.println("target does not exist");	
	}
	// end connections()

	public static void associated(String target1, String target2)
	{
		int targetIndex1 = getTargetIndex(target1), targetIndex2 = getTargetIndex(target2);
		if (targetIndex1 != -1 && targetIndex2 != -1)
		{
			int hopNum = searchForConnections(targetIndex1, targetIndex2);

			if (hopNum != -1)
				System.out.println("yes: " + hopNum);

			else
				System.out.println("no");
		}

		else
			System.out.println("target does not exist");
	}
	// end associated()

	public static int getTargetIndex(String target)
	{
		for (int i = 0; i < targets.size(); i++)
		{
			if (target.matches(targets.get(i).getFirst()))
				return i;
		}

		return -1;
	}

	public static void hopSearch(int root)
	{
		Queue<Integer> queue = new LinkedList<Integer>();
		Boolean [] visited = new Boolean[targets.size()];
		int [] hops = new int[targets.size()];
		int maxHopVal = -1;

		//Initialize visited array to false (unvisited) and hop count to some arbitrary value signifying no hop count exists
		for (int i = 0; i < visited.length; i++)
		{
			visited[i] = false;
			hops[i] = -1;
		}

		queue.add(root);
		visited[root] = true;

		while (queue.peek() != null)
		{
			int currentNode = queue.remove();

			for(String adjNode : targets.get(currentNode))
			{
				int adjNodeI = getTargetIndex(adjNode);
				if (!visited[adjNodeI])
				{
					visited[adjNodeI] = true;
					hops[adjNodeI] = hops[currentNode] + 1;

					if (maxHopVal < hops[adjNodeI])
						maxHopVal = hops[adjNodeI];

					queue.add(adjNodeI);
				}
			}
		}

		// Get hop results
		int [ ] numOfHops = new int[maxHopVal + 1];

		for (int i = 0; i < numOfHops.length; i++)
			numOfHops[i] = 0;

		for (int i = 0; i < hops.length; i++)
		{
			if (hops[i] != -1)
				numOfHops[hops[i]]++;
		}

		for (int i = 0; i < numOfHops.length; i++)
			System.out.println(i + ": " + numOfHops[i]);
	}
	// end hopSearch()

	/* HOPSEARCH for a root and target directory */
	public static int searchForConnections(int target1, int target2)
	{
		// Insert pathfinding algorithm here
		Queue<Integer> queue = new LinkedList<Integer>();
		Boolean [] visited = new Boolean[targets.size()];
		int [] hops = new int[targets.size()];

		//Initialize visited array to false (unvisited) and hop count to some arbitrary value signifying no hop count exists
		for (int i = 0; i < visited.length; i++)
		{
			visited[i] = false;
			hops[i] = -1;
		}

		queue.add(target1);
		visited[target1] = true;
		while (queue.peek() != null)
		{
			int currentNode = queue.remove();

			if (target2 == currentNode)
			{
				return hops[currentNode];
			}

			for(String adjNode : targets.get(currentNode))
			{
				int adjNodeI = getTargetIndex(adjNode);
				if (!visited[adjNodeI])
				{
					visited[adjNodeI] = true;
					hops[adjNodeI] = hops[currentNode] + 1;
					queue.add(adjNodeI);
				}
			}
		}
		return -1;
	}
	//end searchForConnections()
}