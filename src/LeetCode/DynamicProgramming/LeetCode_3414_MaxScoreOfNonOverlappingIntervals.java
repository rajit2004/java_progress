package LeetCode.DynamicProgramming;

import java.util.*;

public class LeetCode_3414_MaxScoreOfNonOverlappingIntervals {
    public static void main(String[] args) {

        // Sample 1: pick intervals [1,5,5] (idx 2) and [6,9,3] (idx 3) -> weight 8
        System.out.println("Sample 1 -> " + Arrays.toString(maximumWeight(Arrays.asList(
                Arrays.asList(1, 3, 2),
                Arrays.asList(4, 5, 2),
                Arrays.asList(1, 5, 5),
                Arrays.asList(6, 9, 3)
        ))) + " (expected: [2, 3])");

        // Sample 2: all three non-overlapping -> take all, weight 3+4+5 = 12
        System.out.println("Sample 2 -> " + Arrays.toString(maximumWeight(Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(3, 4, 4),
                Arrays.asList(5, 6, 5)
        ))) + " (expected: [0, 1, 2])");

        // Tie case: both intervals overlap, equal weight -> lexicographically smaller index list wins
        System.out.println("Tie case -> " + Arrays.toString(maximumWeight(Arrays.asList(
                Arrays.asList(1, 3, 5),
                Arrays.asList(2, 4, 5)
        ))) + " (expected: [0])");

        // More than 4 non-overlapping intervals -> can only pick 4
        System.out.println("Cap at 4 -> " + Arrays.toString(maximumWeight(Arrays.asList(
                Arrays.asList(1, 2, 10),
                Arrays.asList(3, 4, 10),
                Arrays.asList(5, 6, 10),
                Arrays.asList(7, 8, 10),
                Arrays.asList(9, 10, 10)
        ))) + " (expected: [0, 1, 2, 3])");

        // Edge case: single interval
        System.out.println("Edge (single) -> " + Arrays.toString(maximumWeight(Arrays.asList(
                Arrays.asList(1, 2, 5)
        ))) + " (expected: [0])");
    }


    /*
        Approach: Sort by right endpoint + DP with tie-breaking

        We must select at most 4 non-overlapping intervals to maximize total weight, and among ties return the lexicographically smallest list of original indices.

        Key ideas:
        1. Sort intervals by their right endpoint. This lets us use binary search to find the last interval j whose end < current start (classic weighted-interval DP).

        2. DP state:
              dp[i][j]  = max total weight using the first i intervals (in sorted order) and selecting at most j intervals.
              indices[i][j] = the corresponding list of original indices (sorted).

        3. Transition for interval i (0-indexed in sorted arr) with weight w and predecessor k = last index whose end < arr[i].start:
              skip case : dp[i][j]
              take case : dp[k][j-1] + w

           - If skip weight > take weight  -> take skip.
           - If take weight > skip weight  -> take take.
           - If weights are equal          -> compare index lists lexicographically and keep the smaller one.

        4. Answer = indices[n][4].
    */
    static int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        // arr[i] = { start, end, weight, originalIndex }
        int[][] arr = new int[n][4];
        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        // Sort by right endpoint so that "non-overlapping" reduces to end < nextStart.
        Arrays.sort(arr, (a, b) -> Integer.compare(a[1], b[1]));

        // dp[i][j] = best total weight using first i intervals with at most j picked
        long[][] dp = new long[n + 1][5];

        // indices[i][j] = list of original indices producing dp[i][j]
        List<Integer>[][] indices = new List[n + 1][5];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < 5; j++) {
                indices[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < n; i++) {
            int l = arr[i][0],
                    weight = arr[i][2],
                    idx = arr[i][3];

            // k = number of intervals (among the first i, sorted) whose end < l
            int k = binarySearch(arr, i, l);

            for (int j = 1; j < 5; j++) {
                long s1 = dp[i][j];               // skip interval i
                long s2 = dp[k][j - 1] + weight;  // take interval i

                // Clear winner: skip is strictly better
                if (s1 > s2) {
                    dp[i + 1][j] = dp[i][j];
                    indices[i + 1][j] = new ArrayList<>(indices[i][j]);
                    continue;
                }

                // Build the "take" index list: predecessor list + current original index
                List<Integer> newIndex = new ArrayList<>(indices[k][j - 1]);
                newIndex.add(idx);
                Collections.sort(newIndex);

                // Tie in weight: keep the lexicographically smaller index list
                if (s1 == s2 && compareLists(indices[i][j], newIndex) < 0) {
                    newIndex = new ArrayList<>(indices[i][j]);
                }

                dp[i + 1][j] = s2;
                indices[i + 1][j] = newIndex;
            }
        }

        // Convert best index list (among first n intervals, at most 4 picked) to int[]
        List<Integer> result = indices[n][4];
        int[] ans = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            ans[i] = result.get(i);
        }
        return ans;
    }

    /*
        Binary search: within arr[0 .. end-1], find the first index whose right endpoint (arr[mid][1]) is >= target (current start).
        That index equals the count of intervals whose end < target.
    */
    static int binarySearch(int[][] arr, int end, int target) {
        int left = 0, right = end;
        while (left < right) {
            int mid = (left + right) / 2;
            if (arr[mid][1] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    /*
        Lexicographic comparison of two index lists.
        Returns < 0 if a < b, 0 if equal, > 0 if a > b.
    */
    static int compareLists(List<Integer> a, List<Integer> b) {
        int minLen = Math.min(a.size(), b.size());
        for (int i = 0; i < minLen; i++) {
            if (!a.get(i).equals(b.get(i))) {
                return Integer.compare(a.get(i), b.get(i));
            }
        }
        return Integer.compare(a.size(), b.size());
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Sort by right endpoint + DP with tie-breaking

Time Complexity: O(n log n + n * 4 * K)

- Sorting: O(n log n)
- Outer loop over n intervals; inner loop over j = 1..4.
- Building newIndex involves copying up to 4 elements and sorting <= 5 elements: O(1) in practice.
- Binary search per interval: O(log n).
- Overall: O(n log n).

Where K is the size of the index list (<= 4), so effectively O(n log n).

Space Complexity: O(n * 5)

- dp and indices tables: O(n * 5).
- Each indices[i][j] holds up to 4 Integers.

Key Observation: Sorting by end lets "non-overlapping" become "end < nextStart", which is exactly what binarySearch finds. Lexicographic tie-breaking is handled incrementally: at each state we keep the smallest index list among equal-weight options.

---------------------------------------------------------
*/