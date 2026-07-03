package LeetCode.Arrays;

import java.util.*;

public class LeetCode_3620_NetworkRecoveryPath {
    public static void main(String[] args) {

        int[][] edges = {
                {0,1,5},
                {0,2,4},
                {1,3,6},
                {2,3,7}
        };

        boolean[] online = {true, true, true, true};

        long k = 11;

        System.out.println(findMaxPathScore(edges, online, k));
    }

    /*
        Binary Search + Topological Sort + Dynamic Programming

        Observation: We need to maximize the minimum edge weight used in the path while ensuring:

            1. Total path cost <= k
            2. Intermediate nodes are online

        Steps:

        1. Build the DAG.
        2. Generate its Topological Order.
        3. Binary Search on the minimum edge score.
        4. For every candidate score, check whether a valid path exists using DP over the DAG.
     */

    static int findMaxPathScore(int[][] edges, boolean[] online, long k) {

        int n = online.length;

        // Adjacency List representation of the graph.
        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Stores indegree of every node.
        int[] indegree = new int[n];

        // Largest edge weight.
        int maxCost = 0;

        // Build graph.
        for (int[] e : edges) {

            int u = e[0];
            int v = e[1];
            int cost = e[2];

            graph[u].add(new int[]{v, cost});

            indegree[v]++;

            maxCost = Math.max(maxCost, cost);
        }

//            Topological Sort : Since the graph is a DAG, Dynamic Programming can be performed in topological order.
        int[] topo = new int[n];

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {

            if (indegree[i] == 0)
                queue.offer(i);
        }

        int idx = 0;

        while (!queue.isEmpty()) {

            int u = queue.poll();

            topo[idx++] = u;

            for (int[] edge : graph[u]) {

                int v = edge[0];

                indegree[v]--;

                if (indegree[v] == 0)
                    queue.offer(v);
            }
        }

//            Binary Search on answer : We search for the largest minimum edge that still allows a valid path.

        int left = 0;
        int right = maxCost;

        int ans = -1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (canReach(mid, graph, topo, online, k, n)) {

                ans = mid;

                // Try to improve the answer.
                left = mid + 1;
            }

            else {

                right = mid - 1;
            }
        }

        return ans;
    }

    /*
        Checks whether a path exists satisfying:

            1. Every edge >= minEdge
            2. Total path cost <= k
            3. Intermediate nodes remain online
     */

    static boolean canReach(int minEdge,
                            List<int[]>[] graph,
                            int[] topo,
                            boolean[] online,
                            long k,
                            int n) {

        long INF = Long.MAX_VALUE / 4;

        // Shortest distance to every node.
        long[] dist = new long[n];

        Arrays.fill(dist, INF);

        dist[0] = 0;

//            Dynamic Programming on DAG. Relax edges following Topological Order.
        for (int u : topo) {

            // Node is unreachable.
            if (dist[u] == INF)
                continue;

            // Intermediate node must be online.
            if (u != 0 && u != n - 1 && !online[u])
                continue;

            for (int[] edge : graph[u]) {

                int v = edge[0];
                int cost = edge[1];

                // Ignore edges below required score.
                if (cost < minEdge)
                    continue;

                // Intermediate destination must be online.
                if (v != n - 1 && !online[v])
                    continue;

                // Relax edge.
                dist[v] = Math.min(dist[v], dist[u] + cost);
            }
        }

        // Destination must be reachable within budget.
        return dist[n - 1] <= k;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = number of nodes
m = number of edges
W = maximum edge weight

---------------------------------------------------------

Time Complexity: O((n + m) log W)

Reason:

1. Building graph: O(m)

2. Topological Sort: O(n + m)

3. Binary Search: O(log W)

4. Each binary search iteration performs one DP traversal over the DAG: O(n + m)

Overall: O((n + m) log W)

---------------------------------------------------------

Space Complexity: O(n + m)

Reason:

Adjacency List: O(m)
Topological Order: O(n)
Distance Array: O(n)
Indegree Array: O(n)
Queue: O(n)
Overall: O(n + m)

---------------------------------------------------------

Key Observation:

The answer itself is monotonic. If a path exists with minimum edge score X, then a path also exists for every score smaller than X.

This makes Binary Search applicable.

For each candidate score, we simply verify its feasibility using Dynamic Programming over the DAG in Topological Order.

---------------------------------------------------------
*/