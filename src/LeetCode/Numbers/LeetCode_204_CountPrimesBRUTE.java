package LeetCode.Numbers;

public class LeetCode_204_CountPrimesBRUTE {
    public static void main(String[] args) {

        int n = 10;

        System.out.println(countPrimes(n));
    }

    /*
        Brute Force Approach : Check every number from: 2 to n - 1

        For each number, determine whether it is prime.
        Count every prime number found.
    */
    static int countPrimes(int n) {

        // Stores the number of prime numbers.
        int count = 0;

        // Check every number less than n.
        for (int i = 2; i < n; i++) {

            if (isPrime(i)) {
                count++;
            }
        }

        return count;
    }

    /*
        Prime Number Check :

        A number is prime if it has no divisor other than 1 and itself.

        It is sufficient to check divisibility only up to √num.
    */
    static boolean isPrime(int num) {

        // Numbers smaller than 2 are not prime.
        if (num < 2) {
            return false;
        }

        // Check every possible divisor.
        for (int i = 2; i * i <= num; i++) {

            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = input number

---------------------------------------------------------

Time Complexity: O(n√n)

Reason:

1. Check every number from: 2 to n - 1

2. Each prime check takes: O(√n)

Overall: O(n√n)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a few integer variables are used. No extra data structures are required.

---------------------------------------------------------

Key Observation:

A number only needs to be checked for divisibility up to its square root.

Although this improves the primality test, checking every number individually still results in O(n√n) time, making this much slower than the Sieve of Eratosthenes.

---------------------------------------------------------
*/