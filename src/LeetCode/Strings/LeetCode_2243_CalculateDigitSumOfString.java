package LeetCode.Strings;

public class LeetCode_2243_CalculateDigitSumOfString {

    public static void main(String[] args) {
        System.out.println(digitSumIterative("11111222223", 3));
        System.out.println(digitSumRecursive("11111222223", 3));
    }

    /* Approach 1: Iterative round-by-round simulation
       While s.length() > k, split s into consecutive chunks of size k (the
       last chunk may be shorter). Replace each chunk with the sum of its
       digits (as a decimal string) and concatenate the results. Stop once
       length <= k.
       Example: s = "11111222223", k = 3
                round 1 -> chunks "111","112","222","23" -> "3","4","6","5" -> "3465"
                round 2 -> chunks "346","5"                     -> "13","5"      -> "135"
                round 3 -> length 3 <= k, return "135".
    */
    static String digitSumIterative(String s, int k) {
        while (s.length() > k) {
            StringBuilder next = new StringBuilder();
            int n = s.length();

            for (int i = 0; i < n; i += k) {
                int sum = 0;
                int end = Math.min(i + k, n);
                for (int j = i; j < end; j++) {
                    sum += s.charAt(j) - '0';
                }
                next.append(sum);
            }

            s = next.toString();
        }
        return s;
    }

    /* Approach 2: Recursive simulation
       Same logic expressed as a single recursive step: if s.length() <= k
       return s; otherwise build the next round string from chunks of size k
       and recurse on it. The recursion depth is at most O(log n / log k)
       because each round at least shrinks the length roughly by a factor of k
       (or terminates).
       Example: s = "11111222223", k = 3 -> "3465" -> "135" -> "135" (stop).
    */
    static String digitSumRecursive(String s, int k) {
        if (s.length() <= k) return s;

        StringBuilder next = new StringBuilder();
        int n = s.length();

        for (int i = 0; i < n; i += k) {
            int sum = 0;
            int end = Math.min(i + k, n);
            for (int j = i; j < end; j++) {
                sum += s.charAt(j) - '0';
            }
            next.append(sum);
        }

        return digitSumRecursive(next.toString(), k);
    }
}

/* Complexity Analysis
   Let n = s.length() and L = number of rounds until length <= k.

   Approach 1: Iterative round-by-round simulation
     Time : O(n * L)  - each round scans the current string once; the string
            shrinks quickly (roughly factor k per round), so in practice this
            is near O(n) with small L.
     Space: O(n)       - one StringBuilder for the next-round string.
     Key Observation: each round is a straight linear pass over chunks of
     size k; the process terminates because the length never grows.

   Approach 2: Recursive simulation
     Time : O(n * L)  - identical per-round work, one stack frame per round.
     Space: O(n + L)  - recursion stack of depth L plus per-round StringBuilder.
     Key Observation: same algorithm expressed top-down; prefer Approach 1 to
     avoid recursion overhead, since the state is just the current string.
*/

