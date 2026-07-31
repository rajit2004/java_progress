package LeetCode.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LeetCode_3518_SmallestPalindromicRearrangement_II {
    public static void main(String[] args) {

        String s = "aabb";
        int k = 1;

        System.out.println(smallestPalindrome(s, k));
    }

    // Stores all possible palindromic permutations.
    static List<String> list = new ArrayList<>();

    /*
        Backtracking + Frequency Counting Approach :

        Step 1: Count the frequency of every character.

        Step 2: Build the left half of the palindrome.

        Step 3: Generate all unique permutations of the left half using Backtracking.

        Step 4: Mirror every permutation to construct a complete palindrome.

        Step 5: Sort all palindromes and return the k-th lexicographically smallest one.
     */
    static String smallestPalindrome(String s, int k) {

        // Stores the frequency of every character.
        int[] freq = new int[26];

        // Count character frequencies.
        for (char ch : s.toCharArray())
            freq[ch - 'a']++;

        // Stores the left half of the palindrome.
        StringBuilder half = new StringBuilder();

        // Stores the middle character (if any).
        char middle = 0;

        // Construct the left half.
        for (int i = 0; i < 26; i++) {

            if (freq[i] % 2 == 1)
                middle = (char) ('a' + i);


            for (int j = 0; j < freq[i] / 2; j++)
                half.append((char) ('a' + i));

        }

        // Sort to generate permutations in order.
        char[] arr = half.toString().toCharArray();
        Arrays.sort(arr);

        boolean[] used = new boolean[arr.length];

        // Generate all unique left-half permutations.
        backtrack(arr, used, new StringBuilder(), middle);

        // Sort all generated palindromes.
        Collections.sort(list);

        // Not enough palindromes.
        if (k > list.size())
            return "";


        return list.get(k - 1);
    }

//        Generates all unique permutations of the left half using Backtracking.
    static void backtrack(char[] arr,
                          boolean[] used,
                          StringBuilder current,
                          char middle) {

        // One complete permutation generated.
        if (current.length() == arr.length) {

            String left = current.toString();

            String right = new StringBuilder(left).reverse().toString();

            if (middle == 0)
                list.add(left + right);
            else
                list.add(left + middle + right);

            return;
        }

        // Try every unused character.
        for (int i = 0; i < arr.length; i++) {

            if (used[i]) {
                continue;
            }

//                Skip duplicate permutations. Only use the first unused copy of every repeated character.
            if (i > 0 && arr[i] == arr[i - 1] && !used[i - 1])
                continue;


            used[i] = true;
            current.append(arr[i]);

            backtrack(arr, used, current, middle);

            // Undo the current choice.
            current.deleteCharAt(current.length() - 1);
            used[i] = false;
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:
n = length of the string
m = length of the left half = n / 2

---------------------------------------------------------

Time Complexity: O(m! × m)

Reason:

1. Building the frequency array: O(n)

2. Backtracking generates all unique permutations of the left half.

3. Constructing every palindrome takes O(m).

Overall: O(m! × m)

---------------------------------------------------------

Space Complexity: O(m! × n)

Reason:

1. The recursion stack uses: O(m)

2. All generated palindromes are stored, requiring: O(m! × n)

Overall: O(m! × n)

---------------------------------------------------------

Key Observation:

A palindrome is completely determined by its left half.

Instead of generating every permutation of the entire string, generate only the unique permutations of the left half and mirror them.

This reduces the search space significantly.

---------------------------------------------------------
*/