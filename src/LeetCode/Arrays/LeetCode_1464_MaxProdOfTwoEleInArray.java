package LeetCode.Arrays;

public class LeetCode_1464_MaxProdOfTwoEleInArray {
    public static void main(String[] args) {

        int[] nums = {3, 4, 5, 2};

        System.out.println(maxProduct(nums));
    }

    /*
        Greedy Approach : Traverse the array once.

        Maintain:

        1. The largest element.
        2. The second largest element.

        The required answer is: (largest - 1) × (secondLargest - 1)
     */
    static int maxProduct(int[] nums) {

        // Stores the largest element.
        int largest = -1;

        // Stores the second largest element.
        int secondLargest = -1;

        // Process every element.
        for (int num : nums) {

            // Update the largest and second largest elements.
            if (num > largest) {

                secondLargest = largest;
                largest = num;
            }

            else if (num > secondLargest) {

                secondLargest = num;
            }
        }

        // Compute the maximum product.
        return (largest - 1) * (secondLargest - 1);
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n)

Reason: The array is traversed exactly once. Each element is processed in constant time.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only two integer variables are maintained to store the largest and second largest elements.

Overall: O(1)

---------------------------------------------------------

Key Observation:

Sorting the array is unnecessary.
By maintaining the two largest elements during a single traversal, the maximum product can be computed in linear time.

---------------------------------------------------------
*/