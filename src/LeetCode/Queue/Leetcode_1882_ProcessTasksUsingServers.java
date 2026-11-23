package LeetCode.Queue;

import java.util.*;

public class LeetCode_1882_ProcessTasksUsingServers {

    public static void main(String[] args) {
        int[] servers = {3, 3, 2};
        int[] tasks = {1, 2, 3, 2, 1, 2};
        System.out.println(Arrays.toString(assignTasksBrute(servers, tasks)));
        System.out.println(Arrays.toString(assignTasksHeaps(servers, tasks)));
    }

    /* Approach 1: Brute force simulation
       Keep freeAt[j] = the time at which server j becomes free. For each task:
         - advance time to at least i (tasks arrive one per second);
         - among servers with freeAt[j] <= time, pick the one with the smallest
           weight, tie-broken by the smallest index;
         - if none are free, jump time to the smallest freeAt[j], then pick again.
       Assign the task and set freeAt[idx] = time + tasks[i].
       Example: servers = [3,3,2], tasks = [1,2,3,2,1,2]
                -> output [2,2,0,2,1,2].
    */
    static int[] assignTasksBrute(int[] servers, int[] tasks) {
        int n = servers.length, m = tasks.length;
        long[] freeAt = new long[n];
        int[] ans = new int[m];
        long time = 0;

        for (int i = 0; i < m; i++) {
            if (time < i) time = i;

            int chosen = bestAvailable(servers, freeAt, time);

            if (chosen == -1) {
                long minFree = Long.MAX_VALUE;
                for (int j = 0; j < n; j++) minFree = Math.min(minFree, freeAt[j]);
                time = minFree;
                chosen = bestAvailable(servers, freeAt, time);
            }

            ans[i] = chosen;
            freeAt[chosen] = time + tasks[i];
        }

        return ans;
    }

    // Best free server at `time`: smallest weight, then smallest index; -1 if none.
    static int bestAvailable(int[] servers, long[] freeAt, long time) {
        int chosen = -1;
        for (int j = 0; j < freeAt.length; j++) {
            if (freeAt[j] <= time
                    && (chosen == -1
                    || servers[j] < servers[chosen]
                    || (servers[j] == servers[chosen] && j < chosen))) {
                chosen = j;
            }
        }
        return chosen;
    }

    /* Approach 2: Two priority queues (optimal)
       idle = min-heap of (weight, index) for servers ready to work,
       busy = min-heap of (finishTime, index) for servers currently working.
       For each task i:
         - advance ts to at least i (one task arrives per second);
         - release every busy server whose finishTime <= ts into idle;
         - if idle is empty, jump ts to the earliest finishTime and release;
         - pop the best idle server, record it, and push (ts + tasks[i], idx)
           onto busy.
       Example: same input -> [2,2,0,2,1,2].
    */
    static int[] assignTasksHeaps(int[] servers, int[] tasks) {
        int n = servers.length, m = tasks.length;

        PriorityQueue<int[]> idle = new PriorityQueue<>((a, b) ->
                a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
        PriorityQueue<long[]> busy = new PriorityQueue<>((a, b) ->
                a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));

        for (int i = 0; i < n; i++) idle.offer(new int[]{servers[i], i});

        long ts = 0;
        int[] ans = new int[m];

        for (int i = 0; i < m; i++) {
            if (ts < i) ts = i;

            while (!busy.isEmpty() && busy.peek()[0] <= ts) {
                int idx = (int) busy.poll()[1];
                idle.offer(new int[]{servers[idx], idx});
            }

            if (idle.isEmpty()) {
                ts = busy.peek()[0];
                while (!busy.isEmpty() && busy.peek()[0] <= ts) {
                    int idx = (int) busy.poll()[1];
                    idle.offer(new int[]{servers[idx], idx});
                }
            }

            int idx = idle.poll()[1];
            ans[i] = idx;
            busy.offer(new long[]{ts + tasks[i], idx});
        }

        return ans;
    }
}

/* Complexity Analysis
   Let n = servers.length and m = tasks.length.

   Approach 1: Brute force simulation
     Time : O(m * n)  - each task scans all n servers up to twice (once for
            available, once for the jump case).
     Space: O(n)      - freeAt array; answer array O(m).
     Key Observation: at each second choose the available server with the
     smallest (weight, index); if none is available, jump time to the minimum
     freeAt value — no other time can produce a different choice.

   Approach 2: Two priority queues (recommended)
     Time : O((n + m) log n)  - n initial inserts into idle, then each task
            does O(1) amortised polls/offers on each heap; total busy releases
            over the whole run are O(m).
     Space: O(n + m)          - idle/busy heaps plus answer array.
     Key Observation: idle naturally yields the min-(weight, index) server,
     busy naturally yields the earliest finish time — so each assignment is a
     pair of heap peeks, no scanning.
*/

