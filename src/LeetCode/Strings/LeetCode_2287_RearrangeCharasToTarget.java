package LeetCode.Strings;

public class LeetCode_2287_RearrangeCharasToTarget {
    public static void main(String[] args) {
        String s = "ilovecodingonleetcode";
        String target = "code";

        System.out.println(rearrangeCharacters(s, target));
    }

    /*
        Frequency Counting Approach

        We need to determine how many complete copies of "target" can be formed using the characters available in string s.

        Steps:

        1. Count frequency of each character in s.
        2. Count frequency of each character in target.
        3. For every character required by target, calculate: available / required
        4. The smallest such value determines how many complete copies of target can be formed.
     */

    static int rearrangeCharacters(String s, String target) {

        // Stores frequency of characters in s.
        int[] freqS = new int[26];

        // Stores frequency of characters in target.
        int[] freqTarget = new int[26];

//            Count occurrences of each character available in string s.
        for (char ch : s.toCharArray()) {
            freqS[ch - 'a']++;
        }

//            Count occurrences of each character required by target.
        for (char ch : target.toCharArray()) {
            freqTarget[ch - 'a']++;
        }

//            Initialize with a very large value. We'll keep taking the minimum possible number of target copies.

        int answer = Integer.MAX_VALUE;

        /*
            For every character used in target: possibleCopies = availableFrequency / requiredFrequency ;
            The limiting character determines the final answer.
         */
        for (int i = 0; i < 26; i++) {

            // Only consider characters that actually appear in target.
            if (freqTarget[i] > 0) {

                answer = Math.min(
                        answer,
                        freqS[i] / freqTarget[i]
                );
            }
        }

        return answer;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = length of s and m = length of target

---------------------------------------------------------

Time Complexity: O(n + m)

Reason:

1. Traverse string s: O(n)

2. Traverse target: O(m)

3. Traverse frequency array of size 26: O(26) = O(1)

Overall: O(n + m)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Two frequency arrays of fixed size 26 are used: freqS[26] and freqTarget[26]

Since their size never depends on input: O(1)

---------------------------------------------------------

Key Observation:

To build one copy of target, every required character must be available.

For each character: available / required , gives the number of copies that character can support.

The smallest of these values becomes the maximum number of complete target strings that can be formed.

---------------------------------------------------------
*/