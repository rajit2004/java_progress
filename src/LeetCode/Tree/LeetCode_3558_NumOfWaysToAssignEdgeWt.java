package LeetCode.Tree;

import java.util.ArrayList;
import java.util.List;

public class LeetCode_3558_NumOfWaysToAssignEdgeWt {
    public static void main(String[] args) {
        int[][] edges = {{1,2},{1,3},{3,4},{3,5}};

        System.out.println(assignEdgeWeights(edges));
    }

    // Required modulo for large answers.
    static long MOD = 1_000_000_007L;

    // Stores the maximum depth of the tree.
    static int maxDepth = 0;

    /*
        Approach:

        1. Convert the edge list into an adjacency list.

        2. Perform DFS starting from node 1
           to find the maximum depth of the tree.

        3. For every level after the root,
           there are 2 possible weight choices.

        Therefore: Answer = 2^(maxDepth - 1) . Compute this value under modulo MOD.
     */

    static int assignEdgeWeights(int[][] edges) {

        // Number of nodes in a tree: nodes = edges + 1
        int n = edges.length + 1;

        // Adjacency list representation of the tree.
        List<Integer>[] g = new ArrayList[n + 1];

        for (int i = 0; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        // Build the undirected graph.
        for (int[] e : edges) {
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }

        // Find the maximum depth starting from root node 1.
        dfs(1, -1, 0, g);

        /*
            Number of valid assignments: 2^(maxDepth - 1)

            Multiply by 2 for every level beyond the root.
         */
        long ans = 1;

        for (int i = 1; i < maxDepth; i++) {
            ans = (ans * 2) % MOD;
        }

        return (int) ans;
    }

    /*
        DFS Traversal
        node   -> current node
        parent -> node from which we arrived
        depth  -> current depth
     */

    static void dfs(int node, int parent, int depth, List<Integer>[] g) {

        // Update the deepest level seen so far.
        maxDepth = Math.max(maxDepth, depth);

        // Visit all children.
        for (int next : g[node]) {

            // Avoid revisiting the parent.
            if (next != parent) {
                dfs(next, node, depth + 1, g);
            }
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = number of nodes

---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. Building adjacency list: O(n)

2. DFS traversal: Every node and edge is visited once. O(n)

3. Computing 2^(maxDepth-1): O(maxDepth)

   In the worst case: maxDepth <= n

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason:

1. Adjacency list stores all nodes and edges.
2. DFS recursion stack may reach height n
   in a skewed tree.

Therefore:O(n)

---------------------------------------------------------

Key Observation:

The actual edge weights do not need to be explicitly assigned.

The answer depends only on the maximum depth of the tree.

After finding the deepest level, the total number of valid assignments is: 2^(maxDepth - 1) computed under modulo 1e9 + 7.

---------------------------------------------------------
*/