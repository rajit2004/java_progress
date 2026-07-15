package LeetCode.Numbers;

public class LeetCode_204_CountPrime_SieveOfEratosthenes {
    public static void main(String[] args) {

        int n = 10;

        System.out.println(countPrimes(n));
    }

    /*
        Sieve of Eratosthenes Approach : Initially assume every number from: 2 to n - 1 is prime.

        Starting from 2, repeatedly mark all multiples of every prime number as non-prime.

        The remaining numbers marked as prime are the required prime numbers.
    */
    static int countPrimes(int n) {

        // No prime numbers exist below 2.
        if (n <= 2) {
            return 0;
        }

//            isPrime[i] == true , means i is currently considered prime.
        boolean[] isPrime = new boolean[n];

        // Initially assume every number is prime.
        for (int i = 2; i < n; i++) {
            isPrime[i] = true;
        }

        /*
            Perform the Sieve of Eratosthenes.

            It is sufficient to process numbers only up to √n.
         */
        for (int i = 2; i * i < n; i++) {

            if (isPrime[i]) {

                /*
                    Mark every multiple of i as non-prime.

                    Start from i² because all smaller multiples have already been processed.
                 */
                for (int j = i * i; j < n; j += i) {

                    isPrime[j] = false;
                }
            }
        }

        // Count all remaining prime numbers.
        int count = 0;

        for (int i = 2; i < n; i++) {

            if (isPrime[i]) {
                count++;
            }
        }

        return count;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = input number

---------------------------------------------------------

Time Complexity: O(n log log n)

Reason: The Sieve of Eratosthenes efficiently marks multiples of prime numbers.

The overall complexity is: O(n log log n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: A boolean array of size n is used to track whether each number is prime.

Overall: O(n)

---------------------------------------------------------

Key Observation:

Instead of checking every number individually for primality,

the Sieve eliminates all multiples of prime numbers.

Starting from i² avoids redundant work, since smaller multiples have already been marked by previous prime numbers.

This makes the Sieve significantly faster than the brute-force O(n√n) approach.

---------------------------------------------------------
*/