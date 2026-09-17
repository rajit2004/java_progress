package LeetCode.Arrays;

import java.util.*;

public class LeetCode_835_ImageOverlap {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): slide img2 by (1,-1) -> 3 overlapping 1s
        int[][] img1a = {{1,1,0},{0,1,0},{0,1,0}};
        int[][] img2a = {{0,0,0},{0,1,1},{0,0,1}};
        System.out.println("Sample 1 -> " + largestOverlap(img1a, img2a) + " (expected: 3)");

        // Sample 2 (LeetCode): identical images -> all 1s overlap
        int[][] img1b = {{1}};
        int[][] img2b = {{1}};
        System.out.println("Sample 2 (1x1 both 1) -> " + largestOverlap(img1b, img2b) + " (expected: 1)");

        // Edge: all zeros -> no 1s -> overlap is 0
        int[][] img1c = {{0,0},{0,0}};
        int[][] img2c = {{0,0},{0,0}};
        System.out.println("Edge (all zeros) -> " + largestOverlap(img1c, img2c) + " (expected: 0)");

        // Edge: 1x1 with one zero -> no overlap
        int[][] img1d = {{1}};
        int[][] img2d = {{0}};
        System.out.println("Edge (1x1, img2 = 0) -> " + largestOverlap(img1d, img2d) + " (expected: 0)");

        // Case: identical 2x2 with two 1s -> overlap is 2
        int[][] img1e = {{1,0},{0,1}};
        int[][] img2e = {{1,0},{0,1}};
        System.out.println("Identical 2x2 -> " + largestOverlap(img1e, img2e) + " (expected: 2)");
    }


    /*
        Approach: Offset counting (a.k.a. shift histogram)

        Key insight:
        Two 1-cells (i1, j1) in img1 and (i2, j2) in img2 overlap perfectly if we shift img1 by the vector (dx, dy) = (i2 - i1, j2 - j1).
        So each pair of 1-cells from the two images "votes" for one shift.

        Therefore, the best possible overlap equals the maximum number of pairs (a from img1, b from img2) that share the same shift vector.

        Implementation:
        1. Collect coordinates of all 1s in img1 into list A, and in img2 into list B.
        2. For each (a, b) pair, compute shift (dx, dy).
        3. dx, dy range in [-n+1, n-1]; offset by +n to make them non-negative.
        4. cnt[dx][dy] counts how many pairs produced that shift.
        5. Answer = maximum value in cnt.

        Example (n = 3):
        Each pair of 1-cells from the two images votes for a shift.
        The shift with the most votes is the best placement.
    */
    static int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;

        // Collect every coordinate that holds a 1 in each image
        List<int[]> A = new ArrayList<>();
        List<int[]> B = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (img1[i][j] == 1) A.add(new int[]{i, j});
                if (img2[i][j] == 1) B.add(new int[]{i, j});
            }
        }

        // cnt[dx][dy] = number of pairs sharing shift (dx - n, dy - n)
        // Size 2n because shift components lie in [-(n-1), n-1].
        int[][] cnt = new int[2 * n][2 * n];
        int best = 0;

        for (int[] a : A) {
            for (int[] b : B) {
                int dx = b[0] - a[0] + n;   // shift + offset to keep index >= 0
                int dy = b[1] - a[1] + n;
                best = Math.max(best, ++cnt[dx][dy]);
            }
        }

        return best;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Offset counting (shift histogram)

Let n = img1.length and m1, m2 = number of 1-cells in img1 and img2.

Time Complexity: O(n^2 + m1 * m2)

- O(n^2) to collect 1-cell coordinates from both images.
- O(m1 * m2) to iterate over all pairs and accumulate shift counts.
- In the worst case m1 = m2 = n^2, giving O(n^4).

Space Complexity: O(n^2)

- cnt is a 2n x 2n grid -> O(n^2).
- A and B hold up to n^2 coordinates each -> O(n^2).

---------------------------------------------------------
*/