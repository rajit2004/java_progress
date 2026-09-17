package LeetCode.Arrays;

public class LeetCode_3483_Unique3DigitEvenNum {
    public static void main(String[] args) {

        // Sample 1: [1,2,3,4] -> {124, 132, 134, 142, 214, 234, 312, 314, 324, 342, 412, 432} = 12
        System.out.println("Sample 1 -> " + totalNumbers(new int[]{1, 2, 3, 4}) + " (expected: 12)");

        // Sample 2: [0,2,2] -> only {202} is valid (leading digit != 0, even last digit) = 1
        System.out.println("Sample 2 -> " + totalNumbers(new int[]{0, 2, 2}) + " (expected: 1)");

        // Sample 3: [6,6,6] -> {666} = 1
        System.out.println("Sample 3 -> " + totalNumbers(new int[]{6, 6, 6}) + " (expected: 1)");

        // Sample 4: [2,2,8,8,2] -> valid evens with leading digit != 0 = 3 (222, 228, 282, 288, 822, 828, 882, 888 filtered)
        System.out.println("Sample 4 -> " + totalNumbers(new int[]{2, 2, 8, 8, 2}) + " (expected: 3)");

        // Edge case: all zeros -> no valid 3-digit number
        System.out.println("Edge (all zeros) -> " + totalNumbers(new int[]{0, 0, 0}) + " (expected: 0)");

        // Edge case: single element array -> cannot form 3 digits
        System.out.println("Edge (size 1) -> " + totalNumbers(new int[]{2}) + " (expected: 0)");
    }


    /*
        Approach: Brute force over digit indices + HashSet dedupe

        We need to count all distinct 3-digit even numbers that can be formed by picking three digits from the array at distinct indices.

        Rules for a valid number abc:
            - a != 0            (must be a 3-digit number, no leading zero)
            - c % 2 == 0        (must be even)
            - indices i, j, k are pairwise distinct

        We brute force all (i, j, k) index triples with i as hundreds, j as tens, k as ones. To avoid counting the same number twice (which happens when digits contain duplicates), we use a boolean[] of size 1000 as a "seen" set keyed by the number itself.

        Example:
        digits = [1,2,3,4]
        Valid triples produce 12 distinct even 3-digit numbers.

        Example:
        digits = [0,2,2]
        Only {202} is valid (leading digit cannot be 0).
    */
    static int totalNumbers(int[] digits) {
        int n = digits.length;
        boolean[] vis = new boolean[1000];  // vis[x] = true if number x already counted
        int ans = 0;

        for (int i = 0; i < n; ++i) {
            if (digits[i] == 0) {
                continue;               // hundreds digit cannot be 0
            }
            for (int j = 0; j < n; ++j) {
                if (j == i) {
                    continue;           // tens index must differ from hundreds index
                }
                for (int k = 0; k < n; ++k) {
                    if (k == i || k == j || digits[k] % 2 != 0) {
                        continue;       // ones index must differ, and ones digit must be even
                    }
                    int x = digits[i] * 100 + digits[j] * 10 + digits[k];
                    if (!vis[x]) {
                        vis[x] = true;  // mark as counted to handle duplicate digits
                        ++ans;
                    }
                }
            }
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Brute force over digit indices + HashSet dedupe

Time Complexity: O(n^3)

- Three nested loops over n indices each.
- Since n <= 1000 is not typical here (LeetCode constraint: n = digits.length <= 100), this is acceptable.

Space Complexity: O(1)

- boolean[] vis has fixed size 1000, independent of input size.

Key Observation: Using a boolean[1000] as the seen-set avoids the O(n^3) result set blowup and correctly dedupes numbers formed from duplicate digits like [2, 2, 8, 8, 2].
---------------------------------------------------------
*/