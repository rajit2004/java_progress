package LeetCode.Greedy;

public class LeetCode_2656_MaxSumWithExactKElements {
    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 4, 5};
        int k = 3;

        System.out.println(maximizeSum(nums, k));
    }

    /*
        Greedy + Mathematical Approach : To maximize the sum, always choose the largest element from the array.
        After selecting an element, its value increases by 1 for the next selection.

        Therefore, if the maximum element is max:
                First selection  = max
                Second selection = max + 1
                Third selection  = max + 2
                ...
        The total becomes: max + (max + 1) + ... + (max + k - 1)
        Which can be written as: max * k + k * (k - 1) / 2
     */
    static int maximizeSum(int[] nums, int k) {

        // Find the largest element in the array.
        int max = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max)
                max = nums[i];
        }

//            Sum of k consecutive values starting from max: max*k + 0 + 1 + ... + (k-1)
        return (max * k) + (k * (k - 1) / 2);
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n)

Reason: We traverse the array once to find the maximum element. The final sum is calculated using a constant-time mathematical formula.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only the maximum element and a few integer variables are used. No additional data structures are created.

Overall: O(1)

---------------------------------------------------------

Key Observation:

The optimal strategy is always to choose the current maximum element.
If max is the largest element initially, the selected values become: max, max + 1, max + 2, ..., max + k - 1
Using the arithmetic progression formula: Sum = max*k + k*(k-1)/2 , we can calculate the answer directly.

---------------------------------------------------------
*/