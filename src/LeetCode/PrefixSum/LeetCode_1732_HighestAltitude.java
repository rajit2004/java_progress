package LeetCode.PrefixSum;

public class LeetCode_1732_HighestAltitude {
    public static void main(String[] args) {
        int[] gain = {-4,-3,-2,-1,4,3,2};

        System.out.println(largestAltitude(gain));
    }

    /*
        Prefix Sum Approach

        We start at altitude 0. gain[i] represents the change in altitude between point i and point i+1.

        By continuously adding gains, we can track the current altitude at every point. While doing so, keep track of the highest altitude reached.
     */

    static int largestAltitude(int[] gain) {

        // Current altitude while travelling.
        int currentAltitude = 0;

        // Highest altitude reached so far.
        int highestAltitude = 0;

        for (int g : gain) {

            // Update altitude after applying the gain/loss.
            currentAltitude += g;

            // Update the highest altitude reached.
            if (currentAltitude > highestAltitude) {
                highestAltitude = currentAltitude;
            }
        }

        return highestAltitude;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = gain.length

---------------------------------------------------------

Time Complexity: O(n)
Reason: The gain array is traversed exactly once. Each iteration performs constant-time work.

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only two integer variables are used: currentAltitude and highestAltitude

No extra arrays or data structures are created.

---------------------------------------------------------

Key Observation:

Altitude at any point is simply the running sum (prefix sum) of all gains encountered so far.

Instead of storing every altitude in a separate array, we maintain a running altitude and update the maximum altitude during traversal.

This achieves:

Time  -> O(n)
Space -> O(1)

---------------------------------------------------------
*/