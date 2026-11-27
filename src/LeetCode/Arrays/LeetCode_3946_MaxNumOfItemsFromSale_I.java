package LeetCode.Arrays;

import java.util.*;

public class LeetCode_3946_MaxNumOfItemsFromSale_I {

    public static void main(String[] args) {
        int[][] items1 = {{2, 5}, {3, 10}};
        int budget1 = 15;
        System.out.println(maximumSaleItemsRecursive(items1, budget1) + " " + maximumSaleItemsIterative(items1, budget1));

        int[][] items2 = {{1, 2}, {2, 3}};
        int budget2 = 10;
        System.out.println(maximumSaleItemsRecursive(items2, budget2) + " " + maximumSaleItemsIterative(items2, budget2));

        int[][] items3 = {{1, 3}, {5, 7}, {3, 4}};
        int budget3 = 20;
        System.out.println(maximumSaleItemsRecursive(items3, budget3) + " " + maximumSaleItemsIterative(items3, budget3));
    }

    /* Approach 1: Memoized recursive 0/1 knapsack + cheapest-item leftover
       Each item has a "value" = how many items in the array are divisible by
       its factor (the bought item itself plus every freebie). For every
       budget j from 0 to `budget`, compute the best knapsack value with
       budget j, then use the remaining (budget - j) to buy the cheapest item
       repeatedly, adding one item per cheapest purchase. Answer is the max
       over all j.
       Example: items = [[1,2],[2,3]], budget = 10
                cnt = [2, 1], mn = 2
                j = 2  -> knapsack 2 + (10-2)/2 = 2 + 4 = 6 (best)
    */
    static int maximumSaleItemsRecursive(int[][] items, int budget) {
        int n = items.length;
        int[] cnt = new int[n];
        int mn = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int factor = items[i][0];
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (items[j][0] % factor == 0) c++;
            }
            cnt[i] = c;
            mn = Math.min(mn, items[i][1]);
        }

        int[][] memo = new int[n + 1][budget + 1];
        for (int[] row : memo) Arrays.fill(row, -1);

        int ans = 0;
        for (int j = 0; j <= budget; j++) {
            int viaKnapsack = solve(items, cnt, 0, j, memo);
            ans = Math.max(ans, viaKnapsack + (budget - j) / mn);
        }
        return ans;
    }

    // Max value from items i..n-1 with capacity w.
    static int solve(int[][] items, int[] cnt, int i, int w, int[][] memo) {
        if (i == items.length) return 0;
        if (memo[i][w] != -1) return memo[i][w];

        int skip = solve(items, cnt, i + 1, w, memo);
        int take = 0;
        if (w >= items[i][1]) {
            take = cnt[i] + solve(items, cnt, i + 1, w - items[i][1], memo);
        }

        return memo[i][w] = Math.max(skip, take);
    }

    /* Approach 2: Iterative 0/1 knapsack + cheapest-item leftover (optimal)
       Precompute cnt[i] = how many items have a factor divisible by items[i]
       (so buying item i yields cnt[i] items via the sale), and track the
       global minimum price mn. Run a 1D 0/1 knapsack: f[j] = best value with
       budget at most j. Then for each j, combine f[j] with the number of
       cheapest-item purchases that fit in the remaining (budget - j) slots.
       Example: items = [[1,2],[2,3]], budget = 10
                f[2] = 2, f[3] = 2, f[5] = 3
                ans = max over j of f[j] + (10-j)/2 -> 6 at j = 2.
    */
    static int maximumSaleItemsIterative(int[][] items, int budget) {
        int[] f = new int[budget + 1];
        int mn = Integer.MAX_VALUE;

        for (int[] item : items) {
            int factor = item[0];
            int price  = item[1];
            mn = Math.min(mn, price);

            int c = 0;
            for (int[] other : items) {
                if (other[0] % factor == 0) c++;
            }

            for (int j = budget; j >= price; j--) {
                f[j] = Math.max(f[j], f[j - price] + c);
            }
        }

        int ans = 0;
        for (int j = 0; j <= budget; j++) {
            ans = Math.max(ans, f[j] + (budget - j) / mn);
        }
        return ans;
    }
}

/* Complexity Analysis
   Let n = items.length, B = budget.

   Approach 1: Memoized recursive 0/1 knapsack + cheapest-item leftover
     Time : O(n^2 + n * B)  - O(n^2) to build cnt, then for each of the
            B+1 values of j the memoized knapsack fills O(n * j) states,
            sharing the memo table across all j.
     Space: O(n * B)        - memo table of size (n+1) x (B+1).
     Key Observation: each item's "value" is the number of items in the
            array whose factor is divisible by its own factor, so buying one
            item in the sale yields that many items; the rest of the budget
            can only buy the single cheapest item one at a time.

   Approach 2: Iterative 0/1 knapsack + cheapest-item leftover (recommended)
     Time : O(n^2 + n * B)  - O(n^2) to build cnt, then n knapsack items each
            updating O(B) budget slots.
     Space: O(B)            - single 1D dp array plus a scalar min price.
     Key Observation: standard 0/1 knapsack over "buy item i for value cnt[i]
            at cost price[i]" gives f[j] = best sale value with budget at
            most j; the leftover trick max over j of
            f[j] + (budget - j) / mn captures the option of filling the rest
            of the budget with the single cheapest item.
*/

