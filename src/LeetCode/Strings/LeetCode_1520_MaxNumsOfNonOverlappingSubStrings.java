package LeetCode.Strings;

import java.util.*;

public class LeetCode_1520_MaxNumsOfNonOverlappingSubStrings {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "adefaddaccc" -> ["e","f","c"] (3 substrings)
        System.out.println("Sample 1 -> " + maxNumOfSubstrings("adefaddaccc")
                + " (expected 3 substrings, e.g. [e, f, c])");

        // Sample 2 (LeetCode): "abbaccd" -> 3 substrings, e.g. ["bb","cc","d"]
        System.out.println("Sample 2 -> " + maxNumOfSubstrings("abbaccd")
                + " (expected 3 substrings)");

        // Edge case: all identical characters -> one substring covers all occurrences
        System.out.println("Edge (aaaa) -> " + maxNumOfSubstrings("aaaa")
                + " (expected: [aaaa], size 1)");

        // Edge case: each character appears exactly once -> every char its own substring
        System.out.println("Edge (abcd) -> " + maxNumOfSubstrings("abcd")
                + " (expected: [a, b, c, d], size 4)");

        // Edge case: fully interleaved -> one big substring is forced
        System.out.println("Edge (abab) -> " + maxNumOfSubstrings("abab")
                + " (expected: [abab], size 1)");

        // Edge case: single character
        System.out.println("Edge (a) -> " + maxNumOfSubstrings("a")
                + " (expected: [a], size 1)");
    }


    /*
        Approach: Interval expansion + greedy selection

        Problem restated:
        Split s into as many non-overlapping substrings as possible, such that each chosen substring contains EVERY occurrence of every character it contains.
        Return any maximum-size partition.

        Idea:
        1. For each character, its minimal valid substring must span from its first occurrence to its last occurrence. Call this [left, right].

        2. But that span may include OTHER characters whose own [left, right] extend beyond the current interval.
           So we iteratively expand the interval until it is "closed": every character inside has all its occurrences inside.
           Example: "adefaddaccc"
             a: [0, 7], d: [1, 6], e: [2, 2], f: [3, 3], c: [8, 10]
             d's span [1,6] contains 'a' at indices 0 and 7 -> expand d to [0,7].
             Now every character inside [0,7] (a,d,e,f) is fully contained.

        3. Sort the closed intervals by right endpoint ascending, and for equal right endpoints by left endpoint DESCENDING (shortest first).
           This ordering is critical for the greedy to work.

        4. Greedily scan: keep `end` = end of last chosen interval.
           If a segment's left > end, it doesn't overlap anything chosen so far, so take it and update end = its right.

        Why greedy works:
           Sorting by right endpoint ascending ensures that when we pick a segment, it finishes as early as possible, leaving maximum room for subsequent picks.
           The left-descending tie-break ensures that among segments with the same end, the one that starts later (i.e. shorter, less likely to conflict with previously-chosen segments) is considered first — though in practice the count is the same.

        Time:  O(n * 26) for interval expansion (amortized) + O(26 log 26) sort
        Space: O(26) for segment bookkeeping
    */
    static List<String> maxNumOfSubstrings(String s) {
        Seg[] seg = new Seg[26];
        for (int i = 0; i < 26; ++i) {
            seg[i] = new Seg(-1, -1);
        }

        // Step 1: record leftmost and rightmost occurrence of each character
        for (int i = 0; i < s.length(); ++i) {
            int charIdx = s.charAt(i) - 'a';
            if (seg[charIdx].left == -1) {
                seg[charIdx].left = seg[charIdx].right = i;
            } else {
                seg[charIdx].right = i;
            }
        }

        // Step 2: iteratively expand each character's interval until it is "closed" (i.e. contains every occurrence of every character within it)
        for (int i = 0; i < 26; ++i) {
            if (seg[i].left != -1) {
                for (int j = seg[i].left; j <= seg[i].right; ++j) {
                    int charIdx = s.charAt(j) - 'a';

                    // If this inner character is already fully contained, keep going
                    if (seg[i].left <= seg[charIdx].left &&
                            seg[charIdx].right <= seg[i].right) {
                        continue;
                    }

                    // Otherwise, expand to include the inner character's full span
                    seg[i].left = Math.min(seg[i].left, seg[charIdx].left);
                    seg[i].right = Math.max(seg[i].right, seg[charIdx].right);

                    // Restart the inner scan from the new (possibly earlier) left edge
                    j = seg[i].left;
                }
            }
        }

        // Step 3: sort closed intervals (right asc, left desc)
        Arrays.sort(seg);

        // Step 4: greedy pick of non-overlapping intervals
        List<String> ans = new ArrayList<>();
        int end = -1;
        for (Seg segment : seg) {
            int left = segment.left, right = segment.right;
            if (left == -1) {
                continue;                       // unused character slot
            }
            if (end == -1 || left > end) {      // no overlap with last pick
                end = right;
                ans.add(s.substring(left, right + 1));
            }
        }
        return ans;
    }

    /*
        Segment representing a character's minimal valid substring bounds.

        Comparator:
            - primary:   right ascending  (finish early, leave room)
            - tie-break: left descending  (among equal rights, prefer shorter)
    */
    static class Seg implements Comparable<Seg> {
        int left, right;

        Seg(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Seg rhs) {
            if (right == rhs.right) {
                return rhs.left - left;   // left descending
            }
            return right - rhs.right;     // right ascending
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Interval expansion + greedy selection

Let n = s.length() and Σ = 26 (alphabet size).

Time Complexity: O(n + Σ^2)

- Recording first/last occurrence: O(n).
- Interval expansion: each character's interval only ever grows, and each expansion resets the inner scan.
  In the worst case this is O(Σ * n) but in practice converges quickly; the tight bound commonly cited is O(n * Σ).
- Sorting Σ intervals: O(Σ log Σ) = O(1) since Σ = 26 is constant.
- Greedy scan: O(Σ).

Dominant term: O(n * Σ) worst case; O(n + Σ^2) amortized in typical inputs.

Space Complexity: O(Σ)

- seg[26] array of small Seg objects.
- Output list holds at most 26 substrings (one per distinct character).

Key Observation:
Each character's minimal valid substring is determined by its first and last occurrence, but this span may be forced to grow to contain other characters' full spans , hence the closure/expansion step.
Once intervals are closed, sorting by right endpoint and greedily picking non-overlapping ones yields the maximum count, because finishing early always leaves more room.

---------------------------------------------------------
*/