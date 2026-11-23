package LeetCode.SlidingWindow;

import java.util.*;

public class LeetCode_2747_CountZeroRequestServers {

    public static void main(String[] args) {
        int n = 4;
        int[][] logs = {{1, 3}, {2, 6}, {3, 7}, {4, 10}};
        int x = 5;
        int[] queries = {10, 11};

        System.out.println(Arrays.toString(countServersBrute(n, logs, x, queries)));
        System.out.println(Arrays.toString(countServersOptimal(n, logs, x, queries)));
    }

    /* Approach 1: Brute force -- rescan logs for every query
       For each query q, a server is "active" if it has at least one log entry
       with time in [q - x, q]. Collect the distinct active server IDs in a
       HashSet, then answer for that query is n - active.size().
       Example: n = 4, logs = [[1,3],[2,6],[3,7],[4,10]], x = 5,
                q = 10 -> times in [5,10]: servers {2,3,4} -> 4 - 3 = 1
                q = 11 -> times in [6,11]: servers {2,3,4} -> 4 - 3 = 1.
    */
    static int[] countServersBrute(int n, int[][] logs, int x, int[] queries) {
        int m = queries.length;
        int[] ans = new int[m];

        for (int i = 0; i < m; i++) {
            int q = queries[i];
            Set<Integer> active = new HashSet<>();

            for (int[] log : logs) {
                int id = log[0], time = log[1];
                if (time >= q - x && time <= q) {
                    active.add(id);
                }
            }

            ans[i] = n - active.size();
        }

        return ans;
    }

    /* Approach 2: Offline queries + sliding window over logs sorted by time (optimal)
       Sort logs by time. Sort query indices by query value so queries are
       processed in non-decreasing order. Maintain a window [left, right) over
       logs whose times lie in [q - x, q]:
         - Move right forward while logs[right].time <= q, adding each server
           to cnt and incrementing `active` when its count goes 0 -> 1.
         - Move left forward while logs[left].time < q - x, decrementing cnt
           and decreasing `active` when its count goes 1 -> 0.
       Then ans[queryIndex] = n - active (servers with zero logs in the window).
       Example: same input as Approach 1 -> [1, 1].
    */
    static int[] countServersOptimal(int n, int[][] logs, int x, int[] queries) {
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        int m = queries.length;
        Integer[] ids = new Integer[m];
        for (int i = 0; i < m; i++) ids[i] = i;
        Arrays.sort(ids, (i, j) -> Integer.compare(queries[i], queries[j]));

        int[] ans = new int[m];
        int[] cnt = new int[n + 1];   // cnt[id] = log entries for server id in current window
        int left = 0, right = 0, active = 0;

        for (int i : ids) {
            int q = queries[i];

            // Extend the right end: include every log with time <= q.
            while (right < logs.length && logs[right][1] <= q) {
                int id = logs[right++][0];
                if (cnt[id]++ == 0) active++;
            }

            // Shrink the left end: drop logs with time < q - x.
            while (left < right && logs[left][1] < q - x) {
                int id = logs[left++][0];
                if (--cnt[id] == 0) active--;
            }

            ans[i] = n - active;
        }

        return ans;
    }
}

/* Complexity Analysis
   Let L = logs.length, m = queries.length, n = number of servers.

   Approach 1: Brute force
     Time : O(m * L)  - each query scans every log once.
     Space: O(n)      - HashSet of active server IDs per query.
     Key Observation: a server is active for query q iff it has a log entry
     with time in [q-x, q]; counting distinct IDs in that range gives
     n - active directly.

   Approach 2: Offline queries + sliding window (recommended)
     Time : O(L log L + m log m + L + m)  - one sort on logs, one on query
            indices, then a single left/right sweep over logs across all
            queries.
     Space: O(n + m)                     - cnt array, id array, answer array.
     Key Observation: sorting queries by value lets the window over logs only
     move forward (amortised O(L) total); cnt[] + `active` maintain the number
     of distinct active servers as the window slides.
*/

