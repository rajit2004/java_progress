package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.List;

public class LeetCode_2492MinScoreOfPathBtwCities {
    public static void main(String[] args) {

        int n = 4;

        int[][] roads = {
                {1, 2, 9},
                {2, 3, 6},
                {2, 4, 5},
                {1, 4, 7}
        };

        System.out.println(minScore(n, roads));
    }

    // Stores the minimum road distance seen while traversing.
    static int answer = Integer.MAX_VALUE;

    /*
        Depth First Search (DFS) Approach :

        Observation:

        Cities 1 and n are guaranteed to be connected.

        Since roads can be revisited, every city in the
        connected component containing city 1 is reachable.

        Therefore, the answer is simply the minimum edge
        present anywhere in this connected component.

        Perform DFS starting from city 1 and keep updating
        the smallest road distance encountered.
     */

    static int minScore(int n, int[][] roads) {

        // Adjacency List representation of the graph.
        List<int[]>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Build the graph.
        for (int[] road : roads) {

            int city1 = road[0];
            int city2 = road[1];
            int distance = road[2];

            graph[city1].add(new int[]{city2, distance});
            graph[city2].add(new int[]{city1, distance});
        }

        // Keeps track of visited cities.
        boolean[] visited = new boolean[n + 1];

        // Traverse the connected component containing city 1.
        dfs(1, graph, visited);

        return answer;
    }

    /*
        DFS Traversal

        Visit every reachable city and continuously
        update the minimum road distance encountered.
     */
    static void dfs(int node,
                    List<int[]>[] graph,
                    boolean[] visited) {

        visited[node] = true;

        for (int[] edge : graph[node]) {

            int nextCity = edge[0];
            int distance = edge[1];

            // Update the minimum road distance.
            answer = Math.min(answer, distance);

            // Visit unvisited neighbouring cities.
            if (!visited[nextCity]) {
                dfs(nextCity, graph, visited);
            }
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = number of cities
m = number of roads

---------------------------------------------------------

Time Complexity: O(n + m)

Reason:

1. Building the adjacency list:
   O(m)

2. DFS visits every city once:
   O(n)

3. Every road is explored once:
   O(m)

Overall:

O(n + m)

---------------------------------------------------------

Space Complexity: O(n + m)

Reason:

Adjacency List:
O(m)

Visited Array:
O(n)

Recursive DFS Stack:
O(n) (worst case)

Overall:

O(n + m)

---------------------------------------------------------

Key Observation:

Since roads can be traversed multiple times,
the path is not restricted to being simple.

Therefore, the minimum score between city 1
and city n is simply the smallest road distance
present anywhere in the connected component
containing city 1.

A single DFS is sufficient to find this value.

---------------------------------------------------------
*/