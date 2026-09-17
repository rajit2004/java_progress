package LeetCode.Greedy;

public class LeetCode_11_ContainerWithMostWater {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): [1,8,6,2,5,4,8,3,7] -> max area = 49 (indices 1 and 8)
        System.out.println("Sample 1 -> Brute: " + maxAreaBrute(new int[]{1,8,6,2,5,4,8,3,7})
                + " | Optimal: " + maxArea(new int[]{1,8,6,2,5,4,8,3,7}) + " (expected: 49)");

        // Sample 2 (LeetCode): [1,1] -> 1
        System.out.println("Sample 2 -> Brute: " + maxAreaBrute(new int[]{1,1})
                + " | Optimal: " + maxArea(new int[]{1,1}) + " (expected: 1)");

        // Sample 3 (LeetCode): [1,2,1] -> 2
        System.out.println("Sample 3 -> Brute: " + maxAreaBrute(new int[]{1,2,1})
                + " | Optimal: " + maxArea(new int[]{1,2,1}) + " (expected: 2)");

        // Edge case: single element -> cannot form a container -> 0
        System.out.println("Edge (single) -> Brute: " + maxAreaBrute(new int[]{5})
                + " | Optimal: " + maxArea(new int[]{5}) + " (expected: 0)");

        // Edge case: all equal heights -> widest pair wins
        System.out.println("Edge (all equal) -> Brute: " + maxAreaBrute(new int[]{3,3,3,3})
                + " | Optimal: " + maxArea(new int[]{3,3,3,3}) + " (expected: 9)");

        // Edge case: strictly increasing -> best pair is either end or near end
        System.out.println("Edge (increasing) -> Brute: " + maxAreaBrute(new int[]{1,2,3,4,5})
                + " | Optimal: " + maxArea(new int[]{1,2,3,4,5}) + " (expected: 6)");
    }


    /*
        Approach 1: Brute Force (all pairs)

        Try every pair of indices (i, j) with i < j and compute the area:
            area = min(height[i], height[j]) * (j - i)

        Track the maximum.

        Time: O(n^2)
        Space: O(1)
    */
    static int maxAreaBrute(int[] height) {
        int n = height.length;
        int maxArea = 0;

        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                int currentArea = Math.min(height[i], height[j]) * (j - i);
                maxArea = Math.max(maxArea, currentArea);
            }
        }

        return maxArea;
    }

    /*
        Approach 2: Two Pointers (optimal, greedy)

        Start with the widest possible container: left = 0, right = n-1.
        Area is limited by the SHORTER of the two walls, since water overflows above the shorter side.

        Greedy insight:
            When we move an endpoint inward, the width decreases by 1.
            To possibly get a larger area, the new wall must be taller than the current shorter wall.
            So we always move the pointer on the SHORTER side inward — moving the taller side would only shrink width without any chance of increasing the limiting height.

        Continue while left < right, tracking the maximum area seen.

        Time: O(n)
        Space: O(1)
    */
    static int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            int currentArea = Math.min(height[left], height[right]) * (right - left);
            maxArea = Math.max(maxArea, currentArea);

            // Move the pointer on the shorter side inward
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Brute Force (all pairs)

Time Complexity: O(n^2)

- Two nested loops over all index pairs.

Space Complexity: O(1)

- Only a few int variables.


Approach 2: Two Pointers (optimal, greedy)

Time Complexity: O(n)

- Each iteration moves one pointer inward; total n-1 iterations.

Space Complexity: O(1)

- Only a few int variables.

Key Observation:
The area is capped by the shorter wall. Moving the taller pointer inward can never improve the area (width shrinks, limiting height unchanged or worse), so we always move the shorter pointer — guaranteeing we don't skip the optimum.

---------------------------------------------------------
*/