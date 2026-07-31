package LeetCode.Strings;

import java.util.Arrays;

public class LeetCode_3016_MinNumOfPushesToTypeWord_II {
    public static void main(String[] args) {

        String word = "aabbccddeeffgghhiiii";

        System.out.println(minimumPushes(word));
    }

    /*
        Greedy + Frequency Counting Approach :
            Count the frequency of every character.
            Sort the frequencies in descending order.
            Assign the most frequent characters to keys requiring the fewest pushes.
            Every group of 8 characters requires one additional key press.
            This minimizes the total number of pushes.
     */
    static int minimumPushes(String word) {

        // Stores the frequency of every character.
        int[] freq = new int[26];

        // Count character frequencies.
        for (char ch : word.toCharArray()) {

            freq[ch - 'a']++;
        }

        // Sort frequencies in ascending order.
        Arrays.sort(freq);

        // Stores the minimum number of pushes.
        int pushes = 0;

        // Position of the current character after sorting.
        int index = 0;

//            Traverse from the highest frequency to the lowest frequency.
        for (int i = 25; i >= 0; i--) {

            // No more characters are present.
            if (freq[i] == 0) {
                break;
            }

//                Every 8 characters require one additional key press = > Cost = (index / 8) + 1
            pushes += freq[i] * ((index / 8) + 1);

            index++;
        }

        return pushes;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let : n = length of the word

---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. Count character frequencies: O(n)

2. Sort the 26 frequencies: O(26 log 26), which is constant.

3. Traverse the frequency array: O(26)

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

A fixed-size frequency array of size 26 is maintained.

Overall: O(1)

---------------------------------------------------------

Key Observation:

To minimize the total number of key presses, characters with higher frequencies should be assigned to positions requiring fewer presses.
Sorting the frequencies and assigning costs in groups of eight produces the optimal answer.

---------------------------------------------------------
*/