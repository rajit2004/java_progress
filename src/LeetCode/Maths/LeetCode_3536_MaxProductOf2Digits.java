package LeetCode.Maths;

public class LeetCode_3536_MaxProductOf2Digits {
    public static void main(String[] args) {

        int n = 52391;

        System.out.println(maxProduct(n));
    }

    /*
        Greedy Approach : Traverse every digit of the number.

        Maintain:

        1. The largest digit seen so far.
        2. The second largest digit seen so far.

        Finally, return the product of these two digits.
     */
    static int maxProduct(int n) {

        // Stores the largest digit.
        int largest = -1;

        // Stores the second largest digit.
        int secondLargest = -1;

        // Process every digit.
        while (n > 0) {

            int digit = n % 10;

            // Update the largest and second largest digits.
            if (digit >= largest) {

                secondLargest = largest;
                largest = digit;
            }

            else if (digit > secondLargest) {

                secondLargest = digit;
            }

            // Remove the last digit.
            n /= 10;
        }

        // Return the maximum possible product.
        return largest * secondLargest;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: d = number of digits in n

---------------------------------------------------------

Time Complexity: O(d)

Reason: Each digit is processed exactly once.

Overall: O(d)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only two integer variables are maintained to store the largest and second largest digits.

Overall: O(1)

---------------------------------------------------------

Key Observation:

There is no need to sort the digits. By maintaining the two largest digits while traversing the number once, the answer can be computed in a single pass.

---------------------------------------------------------
*/