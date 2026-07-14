package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.List;

public class LeetCode_2685_NumberOfCompleteComponents {
    public static void main(String[] args) {

        int n = 6;

        int[][] edges = {
                {0, 1},
                {0, 2},
                {1, 2},
                {3, 4}
        };

        System.out.println(countCompleteComponents(n, edges));
    }

    // Adjacency List representation of the graph.
    static List<Integer>[] graph;

    // Tracks whether a node has already been visited.
    static boolean[] visited;

    // Stores all nodes belonging to the current connected component.
    static List<Integer> component;

    /*
        DFS + Graph Traversal Approach :

        Traverse every connected component using DFS.

        For each connected component:

            1. Collect all its nodes.
            2. Let its size be k.
            3. Every node must have degree (k - 1)
               for the component to be complete.

        Count every component satisfying this condition.
     */

    static int countCompleteComponents(int n, int[][] edges) {

        graph = new ArrayList[n];
        visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Build the graph.
        for (int[] edge : edges) {

            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        int answer = 0;

        // Traverse every connected component.
        for (int i = 0; i < n; i++) {

            if (!visited[i]) {

                component = new ArrayList<>();

                dfs(i);

                int size = component.size();

                boolean complete = true;

                /*
                    Every node in a complete graph
                    must be connected to every other node.

                    Therefore:

                    degree = size - 1
                 */
                for (int node : component) {

                    if (graph[node].size() != size - 1) {

                        complete = false;
                        break;
                    }
                }

                if (complete) {
                    answer++;
                }
            }
        }

        return answer;
    }

    /*
        DFS Traversal

        Visits every node belonging to the
        current connected component.
     */
    static void dfs(int node) {

        visited[node] = true;

        component.add(node);

        for (int neighbor : graph[node]) {

            if (!visited[neighbor]) {
                dfs(neighbor);
            }
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = number of nodes
m = number of edges

---------------------------------------------------------

Time Complexity: O(n + m)

Reason:

1. Building the adjacency list:
   O(m)

2. DFS visits every node once:
   O(n)

3. Every edge is explored once:
   O(m)

4. Degree check for every node:
   O(n)

Overall:

O(n + m)

---------------------------------------------------------

Space Complexity: O(n + m)

Reason:

Adjacency List:
O(m)

Visited Array:
O(n)

Component List:
O(n)

Recursive DFS Stack:
O(n) (worst case)

Overall:

O(n + m)

---------------------------------------------------------

Key Observation:

A connected component containing k nodes
is complete if every node has exactly:

k - 1

neighbours.

Therefore, after finding a connected component,
checking the degree of each node is sufficient
to determine whether it forms a complete graph.

---------------------------------------------------------
*/