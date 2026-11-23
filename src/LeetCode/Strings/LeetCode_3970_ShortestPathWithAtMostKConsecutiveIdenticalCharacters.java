package LeetCode.Strings;

import java.util.*;

public class LeetCode_3970_ShortestPathWithAtMostKConsecutiveIdenticalCharacters {

    public static void main(String[] args) {
        int n = 4;
        int[][] edges = {{0, 1, 1}, {1, 3, 2}, {0, 2, 1}, {2, 3, 1}};
        String labels = "aaba";
        int k = 2;
        System.out.println(shortestPathBrute(n, edges, labels, k));
        System.out.println(shortestPathDijkstra(n, edges, labels, k));
    }

    /* Approach 1: Brute force DFS/backtracking
       Explore every simple path from 0 to n-1, tracking the current run of
       identical labels along the path. Prune as soon as the run exceeds k.
       Return the shortest such path length. Exponential in the worst case,
       but useful as an oracle for small inputs.
       Example: n = 4, edges as above, labels = "aaba", k = 2
                paths: 0->1->3 (labels a,a,b, run 2) cost 3
                       0->2->3 (labels a,b,a, runs 1,1) cost 2 -> best 2.
    */
    static int shortestPathBrute(int n, int[][] edges, String labels, int k) {
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) g[e[0]].add(new int[]{e[1], e[2], 0});

        boolean[] visited = new boolean[n];
        visited[0] = true;
        int[] best = {Integer.MAX_VALUE};
        dfs(0, n - 1, labels, k, 1, 0, g, visited, best);

        return best[0] == Integer.MAX_VALUE ? -1 : best[0];
    }

    // lastRun = current trailing run of identical labels ending at node x.
    static void dfs(int x, int target, String labels, int k, int lastRun, int dist,
                    List<int[]>[] g, boolean[] visited, int[] best) {
        if (dist >= best[0]) return;

        if (x == target) {
            best[0] = dist;
            return;
        }

        for (int[] e : g[x]) {
            int y = e[0], w = e[1];
            if (visited[y]) continue;

            int newRun = labels.charAt(x) == labels.charAt(y) ? lastRun + 1 : 1;
            if (newRun > k) continue;

            visited[y] = true;
            dfs(y, target, labels, k, newRun, dist + w, g, visited, best);
            visited[y] = false;
        }
    }

    /* Approach 2: Dijkstra on state graph (node, trailing run length)
       Treat (node, cnt) as a node in a state graph, where cnt is the current
       trailing run of identical labels (1..k). Edge (x -> y, w) is allowed
       only if cnt' = (labels[x]==labels[y] ? cnt+1 : 1) <= k. Use a standard
       Dijkstra with dist[node][cnt]. The first time we pop the target node
       n-1 from the priority queue, that distance is the answer.
       Example: n = 4, edges as above, labels = "aaba", k = 2
                best = 2 (path 0->2->3).
    */
    static int shortestPathDijkstra(int n, int[][] edges, String labels, int k) {
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) g[e[0]].add(new int[]{e[1], e[2]});

        // dist[node][run] = cheapest distance to reach `node` with trailing run of `run`.
        long[][] dist = new long[n][k + 1];
        for (long[] row : dist) Arrays.fill(row, Long.MAX_VALUE);

        // {distance, node, trailing run}
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));

        dist[0][1] = 0;
        pq.offer(new long[]{0, 0, 1});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0];
            int x = (int) cur[1];
            int run = (int) cur[2];

            if (d > dist[x][run]) continue;
            if (x == n - 1) return (int) d;

            for (int[] e : g[x]) {
                int y = e[0], w = e[1];
                int newRun = labels.charAt(x) == labels.charAt(y) ? run + 1 : 1;
                if (newRun > k) continue;

                long nd = d + w;
                if (nd < dist[y][newRun]) {
                    dist[y][newRun] = nd;
                    pq.offer(new long[]{nd, y, newRun});
                }
            }
        }

        return -1;
    }
}

/* Complexity Analysis
   Let n = #nodes, m = #edges, k = max allowed consecutive identical labels.

   Approach 1: Brute force DFS/backtracking
     Time : O(n! * something) worst case - every simple path from 0 to n-1
            is explored; pruning on run > k and current-best helps in practice
            but does not change the worst case.
     Space: O(n + k) - visited array + recursion depth (with run counter).
     Key Observation: enumerate simple paths, tracking the trailing run of the
     same label; only paths whose run stays <= k are valid; keep the minimum
     total weight.

   Approach 2: Dijkstra on state graph (recommended)
     Time : O(n * k log(n * k) + m * k) - the state graph has n * (k+1) nodes
            and each edge fans out to at most (k+1) states.
     Space: O(n * k) - dist matrix + priority queue.
     Key Observation: expanding the state space with the trailing run makes
     the "at most k identical in a row" constraint local to a single edge, so
     Dijkstra applies directly; popping the target node gives the answer.
*/

