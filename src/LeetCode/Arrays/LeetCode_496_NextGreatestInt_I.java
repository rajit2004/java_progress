package LeetCode.Arrays;

public class LeetCode_496_NextGreatestInt_I {
    public static void main(String[] args) {

        int[] nums1 = {4, 1, 2};
        int[] nums2 = {1, 3, 4, 2};

        int[] answer = nextGreaterElement(nums1, nums2);

        for (int num : answer) {
            System.out.print(num + " ");
        }
    }

    /*
        Brute Force Approach :

        For every element in nums1:
            1. Find its position in nums2.
            2. Start searching from the next position.
            3. Find the first element greater than it.
            4. If no greater element exists, return -1.

        This directly follows the definition of the next greater element.
     */
    static int[] nextGreaterElement(int[] nums1, int[] nums2) {

        int n = nums1.length;

        // Stores the next greater element for each element of nums1.
        int[] answer = new int[n];

        // Process every element from nums1.
        for (int i = 0; i < n; i++) {

            // Find the position of nums1[i] in nums2.
            int index = linearSearch(nums2, nums1[i]);

            if (index != -1) {

                // Search for the next greater element.
                answer[i] = nextGreater(nums2, index + 1, nums1[i]);

            } else {
                // Element does not exist in nums2.
                answer[i] = -1;
            }
        }

        return answer;
    }

//        Finds the index of the target element using linear search.
    static int linearSearch(int[] arr, int target) {

        for (int i = 0; i < arr.length; i++) {

            if (arr[i] == target) {
                return i;
            }
        }

        return -1;
    }

//        Searches for the first element greater than target starting from the given index.
    static int nextGreater(int[] arr, int start, int target) {

        for (int i = start; i < arr.length; i++) {

            if (arr[i] > target) {
                return arr[i];
            }
        }

        // No greater element exists.
        return -1;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = nums1.length
m = nums2.length

---------------------------------------------------------

Time Complexity: O(n × m)

Reason: For every element in nums1:
    1. Linear search finds its position in nums2: O(m)
    2. Another linear search finds its next greater element: O(m)

Therefore: O(n × (m + m)) = O(n × m)

---------------------------------------------------------

Space Complexity: O(n)

Reason: The answer array contains n elements.

Apart from the output array, only constant extra space is used.

Overall: O(n)

---------------------------------------------------------

Key Observation: For each element in nums1, the problem can be solved directly by first finding its position in nums2 and then scanning to the right for the first greater element.

This is a straightforward brute-force solution and can later be optimized using a Monotonic Stack.

---------------------------------------------------------
*/