package LeetCode.Strings;

public class LeetCode_3517_SmallestPalindromicRearrangement_I {
    public static void main(String[] args) {

        String s = "aabbcc";

        System.out.println(smallestPalindrome(s));
    }

    /*
        Greedy + Frequency Counting Approach :

        Step 1: Count the frequency of every character.

        Step 2: Build the left half of the palindrome using half of each character's frequency in lexicographical order.

        Step 3: If a character has an odd frequency, place it in the middle.

        Step 4: Reverse the left half to form the right half.

        This produces the lexicographically smallest palindrome.
     */
    static String smallestPalindrome(String s) {

        // Stores the frequency of every character.
        int[] freq = new int[26];

        // Count the frequency of each character.
        for (char ch : s.toCharArray()) {

            freq[ch - 'a']++;
        }

        // Stores the left half of the palindrome.
        StringBuilder left = new StringBuilder();

        // Stores the middle character (if any).
        char middle = 0;

//            Build the left half in lexicographical order.
        for (int i = 0; i < 26; i++) {

            int times = freq[i] / 2;

            while (times-- > 0) {

                left.append((char) ('a' + i));
            }

            // Store the character having odd frequency.
            if (freq[i] % 2 == 1) {

                middle = (char) ('a' + i);
            }
        }

        // Stores the final palindrome.
        StringBuilder answer = new StringBuilder();

        // Append the left half.
        answer.append(left);

        // Append the middle character if present.
        if (middle != 0) {

            answer.append(middle);
        }

        // Append the reversed left half.
        answer.append(new StringBuilder(left).reverse());

        return answer.toString();
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = length of the string

---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. Count character frequencies: O(n)

2. Build the palindrome: O(n)

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason:

1. Frequency array: O(1)

2. StringBuilder stores the resulting palindrome: O(n)

Overall: O(n)

---------------------------------------------------------

Key Observation:

To obtain the lexicographically smallest
palindrome:

1. Place the smallest available characters first in the left half.

2. Place the only odd-frequency character (if any) in the middle.

3. Mirror the left half to construct the right half.

---------------------------------------------------------
*/