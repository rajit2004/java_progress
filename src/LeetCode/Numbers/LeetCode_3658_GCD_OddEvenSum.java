package LeetCode.Numbers;

public class LeetCode_3658_GCD_OddEvenSum {
    public static void main(String[] args) {

        int n = 5;

        System.out.println(gcdOfOddEvenSums(n));
    }

    /*
        Math Observation :

        Sum of the first n odd numbers: 1 + 3 + 5 + ... = n²
        Sum of the first n even numbers: 2 + 4 + 6 + ... = n(n + 1)

        Therefore, GCD(n², n(n + 1)) = n × GCD(n, n + 1)

        Since consecutive numbers are always coprime: GCD(n, n + 1) = 1
        Hence, GCD(n², n(n + 1)) = n
    */
    static int gcdOfOddEvenSums(int n) {

        // Directly return the mathematical result.
        return n;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(1)

Reason: The answer is obtained directly using the derived mathematical formula.

---------------------------------------------------------

Space Complexity: O(1)

Reason: No extra variables or data structures are used.

---------------------------------------------------------

Key Observation:

The required GCD is: GCD(n², n(n + 1))

Factoring out n gives: n × GCD(n, n + 1)

Since consecutive integers are always coprime, GCD(n, n + 1) = 1

Therefore, the answer is simply: n

---------------------------------------------------------
*/