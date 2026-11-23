package LeetCode.Maths;

public class LeetCode_3789_MinCostToAcquireRequiredItems {

    public static void main(String[] args) {
        System.out.println(minimumCostBrute(3, 2, 4, 3, 4));
        System.out.println(minimumCostGreedy(3, 2, 4, 3, 4));
    }

    /* Approach 1: Brute force -- try every count of the combined item
       Let x be how many times we buy the "both" item. Any x from 0 up to
       max(need1, need2) is feasible: leftover need1 and need2 are covered by
       buying item 1 and item 2 individually. Cost for a fixed x is
         x * costBoth
         + max(0, need1 - x) * cost1
         + max(0, need2 - x) * cost2
       Take the minimum over all x.
       Example: cost1=3, cost2=2, costBoth=4, need1=3, need2=4
                x=3 -> 12 + 0 + 1*2 = 14  (best)
                answer 14.
    */
    static long minimumCostBrute(int cost1, int cost2, int costBoth, int need1, int need2) {
        long best = Long.MAX_VALUE;
        int maxX = Math.max(need1, need2);

        for (int x = 0; x <= maxX; x++) {
            long cost = 1L * x * costBoth
                    + 1L * Math.max(0, need1 - x) * cost1
                    + 1L * Math.max(0, need2 - x) * cost2;
            best = Math.min(best, cost);
        }

        return best;
    }

    /* Approach 2: Greedy case analysis (optimal)
       Two cases for the combined item:
         (a) cost1 + cost2 <= costBoth -> the combined item is never worth it;
             buy item 1 and item 2 separately.
         (b) costBoth < cost1 + cost2 -> use the combined item as far as it
             helps. Buy min(need1, need2) copies to satisfy both needs at once;
             then only one requirement is left. For the remaining need of that
             type, take the cheaper of its own item and the combined item.
       Example: cost1=3, cost2=2, costBoth=4, need1=3, need2=4
                3+2=5 > 4 -> branch (b): 3 copies of combined item -> 12,
                need2 = 1 left -> min(2, 4) = 2 -> total 14.
    */
    static long minimumCostGreedy(int cost1, int cost2, int costBoth, int need1, int need2) {
        // Buying separately is cheaper, so the combined item is useless
        if (cost1 + cost2 <= costBoth) {
            return 1L * cost1 * need1 + 1L * cost2 * need2;
        }

        // Buy the combined item until at least one need is satisfied
        int both = Math.min(need1, need2);
        long totalCost = 1L * both * costBoth;

        need1 -= both;
        need2 -= both;

        if (need1 > 0) {
            totalCost += 1L * need1 * Math.min(cost1, costBoth);
        } else if (need2 > 0) {
            totalCost += 1L * need2 * Math.min(cost2, costBoth);
        }

        return totalCost;
    }
}

/* Complexity Analysis
   Let N = max(need1, need2).

   Approach 1: Brute force
     Time : O(N)  - one constant-time cost evaluation per choice of x.
     Space: O(1)  - single running minimum.
     Key Observation: the only decision is how many combined items to buy;
     once x is fixed, the rest is forced (buy the shortfall of each type
     individually).

   Approach 2: Greedy case analysis (recommended)
     Time : O(1)  - a handful of arithmetic operations and one min/max.
     Space: O(1)  - no extra storage.
     Key Observation: the combined item is either dominated by cost1 + cost2
     (never use it), or it should be used as much as possible up to the
     smaller need, after which the leftover side takes whichever of its own
     item and the combined item is cheaper.
*/

