package LeetCode.Greedy;

public class ALT_LeetCode_2656_MaxSumWithExactKElements {
    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 4, 5};
        int k = 3;

        System.out.println(maximizeSum(nums, k));
    }

    /*
        Greedy Approach :

        To maximize the sum, always choose the
        current maximum element.

        After selecting the maximum element,
        its value increases by 1.

        Therefore, the selected values become:

            max, max + 1, max + 2, ...

        We repeat this process exactly k times.
     */
    static int maximizeSum(int[] nums, int k) {

        // Find the maximum element in the array.
        int max = 0;

        for (int num : nums) {
            max = Math.max(max, num);
        }

        int answer = 0;

        /*
            Select the current maximum element k times.

            After every selection, increase the maximum
            value by 1 for the next selection.
         */
        while (k > 0) {

            answer += max;

            // The selected value increases by 1.
            max++;

            k--;
        }

        return answer;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = nums.length
k = number of elements to select

---------------------------------------------------------

Time Complexity: O(n + k)

Reason:

1. Traverse the array to find the maximum:
   O(n)

2. Select exactly k elements:
   O(k)

Overall:

O(n + k)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

Only a few integer variables are used.

No additional data structures are created.

Overall:

O(1)

---------------------------------------------------------

Key Observation:

The maximum possible sum is obtained by
always selecting the current maximum value.

After every selection, that value increases
by 1.

Therefore, the sequence of selected values is:

    max, max + 1, max + 2, ..., max + k - 1

The loop directly simulates this process.

---------------------------------------------------------
*/