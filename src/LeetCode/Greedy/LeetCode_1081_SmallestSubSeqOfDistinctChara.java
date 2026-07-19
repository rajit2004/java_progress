package LeetCode.Greedy;

import java.util.Stack;

public class LeetCode_1081_SmallestSubSeqOfDistinctChara {
    public static void main(String[] args) {

        String s = "cbacdcbc";

        System.out.println(smallestSubsequence(s));
    }

    /*
        Monotonic Stack + Greedy Approach : We want the lexicographically smallest subsequence containing every distinct character exactly once.

        For every character:

        1. Skip it if it is already included.
        2. Remove larger characters from the stack if they appear again later.
        3. Push the current character.

        This guarantees the smallest possible lexicographical order.
     */
    static String smallestSubsequence(String s) {

        // Stores the last occurrence of every character.
        int[] lastIndex = new int[26];

        // Record the last index of each character.
        for (int i = 0; i < s.length(); i++) {

            lastIndex[s.charAt(i) - 'a'] = i;
        }

        /*
            visited[i] == true , means the character is already present in the stack.
         */
        boolean[] visited = new boolean[26];

        // Maintains the current smallest subsequence.
        Stack<Character> stack = new Stack<>();

        // Process every character.
        for (int i = 0; i < s.length(); i++) {

            char current = s.charAt(i);

            // Skip duplicate characters.
            if (visited[current - 'a']) {
                continue;
            }

//      Remove larger characters from the stack if they can still appear later.

            while (!stack.isEmpty()
                    && stack.peek() > current
                    && lastIndex[stack.peek() - 'a'] > i) {

                visited[stack.pop() - 'a'] = false;
            }

            // Include the current character.
            stack.push(current);

            visited[current - 'a'] = true;
        }

        // Build the final answer.
        StringBuilder ans = new StringBuilder();

        for (char ch : stack) {
            ans.append(ch);
        }

        return ans.toString();
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

1. Traverse the string once to find the last occurrence of every character.

2. Traverse the string again.

3. Every character is pushed onto the stack at most once and popped at most once.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason:

1. Stack stores at most n characters.

2. lastIndex and visited arrays are of constant size (26).

Overall: O(n)

---------------------------------------------------------

Key Observation:

A character should only be removed from the stack if:

1. It is lexicographically larger than the current character.

2. It appears again later in the string.

This greedy decision guarantees the lexicographically smallest subsequence containing every distinct character exactly once.

---------------------------------------------------------
*/