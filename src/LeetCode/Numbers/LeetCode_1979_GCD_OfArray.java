package LeetCode.Numbers;

public class LeetCode_1979_GCD_OfArray {
    public static void main(String[] args) {

        int[] nums = {2, 5, 6, 9, 10};

        System.out.println(findGCD(nums));
    }

    /*
        Observation : The GCD of the entire array is equal to the GCD of its smallest and largest elements.

        Therefore,

        Step 1: Find the minimum and maximum values.
        Step 2: Compute their GCD using the Euclidean Algorithm.
    */
    static int findGCD(int[] nums) {

        // Assume the first element is both the minimum and maximum.
        int min = nums[0];
        int max = nums[0];

        // Find the smallest and largest elements.
        for (int num : nums) {

            if (num < min) {
                min = num;
            }

            if (num > max) {
                max = num;
            }
        }

        // Return the GCD of the minimum and maximum values.
        return gcd(min, max);
    }

//        Euclidean Algorithm : Repeatedly replace the larger number with the remainder until the remainder becomes 0.
    static int gcd(int a, int b) {

        while (b != 0) {

            int remainder = a % b;

            a = b;
            b = remainder;
        }

        return a;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. Traverse the array once to find the minimum and maximum values: O(n)

2. Compute the GCD using the Euclidean Algorithm: O(log(min, max))

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a few integer variables are used. No extra data structures are required.

---------------------------------------------------------

Key Observation:

The GCD of all array elements is equal to the GCD of the smallest and largest elements.

Therefore, there is no need to compute the GCD of every element in the array.

---------------------------------------------------------
*/