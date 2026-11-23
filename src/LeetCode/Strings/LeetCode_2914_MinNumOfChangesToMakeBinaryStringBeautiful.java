package LeetCode.Strings;

public class LeetCode_2914_MinNumOfChangesToMakeBinaryStringBeautiful {

    public static void main(String[] args) {
        String s = "1001";
        System.out.println(minChangesGrouping(s));
        System.out.println(minChangesAdjacentPairs(s));
    }

    /* Approach 1: Walk consecutive groups
       A "beautiful" string splits into even-length blocks of equal bits, so
       every run of identical characters must have even length. Walk the string
       keeping the current run's length; when a run ends with odd length, flip
       the incoming character (one change) to extend that run to even length.
       Example: "1001" -> run '1' = 1 (odd, change '0' -> 1 change, now run '1' = 1),
                run '0' = 1 (odd, change '1' -> 2 changes) -> answer 2.
    */
    static int minChangesGrouping(String s) {
        char currentChar = s.charAt(0);

        int consecutiveCount = 0;
        int minChangesRequired = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == currentChar) {
                consecutiveCount++;
                continue;
            }

            // run ended; if its length is even, this is a fresh run
            if (consecutiveCount % 2 == 0) {
                consecutiveCount = 1;
            }
            // if odd, flip the incoming char to make the run even
            else {
                consecutiveCount = 0;
                minChangesRequired++;
            }

            currentChar = s.charAt(i);
        }

        return minChangesRequired;
    }

    /* Approach 2: Count mismatched adjacent pairs (optimal)
       Beautiful strings split into even-length blocks of identical bits, so we
       can fix the partition at (0,1), (2,3), (4,5), ... Each pair (i, i+1) must
       match; if it doesn't, exactly one change fixes it and no change can fix
       two pairs at once. The answer is the number of mismatched pairs.
       Example: "1001" -> (1,0) mismatch, (0,1) mismatch -> answer 2.
                "11000111" -> (1,1) ok, (0,0) ok, (0,1) mismatch, (1,1) ok -> 1.
    */
    static int minChangesAdjacentPairs(String s) {
        int minChangesRequired = 0;

        for (int i = 0; i < s.length(); i += 2) {
            if (s.charAt(i) != s.charAt(i + 1)) {
                minChangesRequired++;
            }
        }

        return minChangesRequired;
    }
}

/* Complexity Analysis
   Let n = s.length() (guaranteed even by the problem).

   Approach 1: Walk consecutive groups
     Time : O(n)  - single left-to-right pass over the string.
     Space: O(1)  - three scalars (currentChar, consecutiveCount, changes).
     Key Observation: each maximal run must have even length; closing an odd
     run costs exactly one change and can be merged with the next run.

   Approach 2: Count mismatched adjacent pairs (recommended)
     Time : O(n)  - visits every pair once, n/2 iterations.
     Space: O(1)  - one counter.
     Key Observation: fixing the partition at even offsets makes the problem
     independent per pair; each mismatched pair (i, i+1) needs exactly one
     change and no single change can resolve two such pairs.
*/

