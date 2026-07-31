package LeetCode.Strings;

import java.util.Arrays;
import java.util.Collections;

public class LeetCode_3014_MinNumOfPushesToTypeWord_I {
    public static void main(String[] args) {

        String word = "abcde";

        System.out.println(minimumPushes(word));
    }

    /*
        Greedy Approach : Count the frequency of every character. Assign the most frequent characters to keys requiring the fewest pushes.

        Since each push level can accommodate at most 8 characters:

        First 8 characters  -> 1 push
        Next 8 characters   -> 2 pushes
        Last 8 characters   -> 3 pushes
        Remaining characters -> 4 pushes

        This minimizes the total number of pushes.
     */
    static int minimumPushes(String word) {

        // Stores the frequency of every character.
        int[] charCount = new int[26];

        // Count character frequencies.
        for (int i = 0; i < word.length(); i++) {

            charCount[word.charAt(i) - 'a']++;
        }

        // Sort frequencies in descending order.
        charCount = Arrays.stream(charCount)
                .boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(i -> i)
                .toArray();

        // Stores the minimum number of pushes.
        int minPushCount = 0;

        // Assign the highest frequencies to the smallest push count.
        for (int i = 0; i < charCount.length; i++) {

            minPushCount += charCount[i] * (i / 8 + 1);
        }

        return minPushCount;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = length of the word

---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. Count character frequencies: O(n)

2. Sort the 26 frequencies: O(26 log 26), which is constant.

3. Compute the total pushes: O(26)

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

A fixed-size frequency array of size 26 is used.

Overall: O(1)

---------------------------------------------------------

Key Observation:

To minimize the total number of pushes, characters that occur most frequently should be assigned to keys requiring the fewest presses.

Sorting the character frequencies in descending order naturally achieves this optimal assignment.

---------------------------------------------------------
*/