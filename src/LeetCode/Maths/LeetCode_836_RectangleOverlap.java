package LeetCode.Maths;

public class LeetCode_836_RectangleOverlap {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): rec1 = [0,0,2,2], rec2 = [1,1,3,3] -> overlap area > 0
        System.out.println("Sample 1 -> " + isRectangleOverlap(new int[]{0,0,2,2}, new int[]{1,1,3,3})
                + " (expected: true)");

        // Sample 2 (LeetCode): rec1 = [0,0,1,1], rec2 = [1,0,2,1] -> touch only at edge
        System.out.println("Sample 2 (edge touch) -> " + isRectangleOverlap(new int[]{0,0,1,1}, new int[]{1,0,2,1})
                + " (expected: false)");

        // Sample 3 (LeetCode): rec1 = [0,0,1,1], rec2 = [2,2,3,3] -> far apart
        System.out.println("Sample 3 (far apart) -> " + isRectangleOverlap(new int[]{0,0,1,1}, new int[]{2,2,3,3})
                + " (expected: false)");

        // Edge case: rec1 is a degenerate line (width 0)
        System.out.println("Edge (line rec1) -> " + isRectangleOverlap(new int[]{0,0,0,2}, new int[]{0,0,2,2})
                + " (expected: false)");

        // Edge case: identical rectangles -> full overlap
        System.out.println("Edge (identical) -> " + isRectangleOverlap(new int[]{1,1,3,3}, new int[]{1,1,3,3})
                + " (expected: true)");

        // Edge case: one rectangle fully inside the other
        System.out.println("Edge (nested) -> " + isRectangleOverlap(new int[]{0,0,10,10}, new int[]{4,4,6,6})
                + " (expected: true)");

        // Cross-check both approaches on all above cases
        checkBothAgree(new int[]{0,0,2,2}, new int[]{1,1,3,3});
        checkBothAgree(new int[]{0,0,1,1}, new int[]{1,0,2,1});
        checkBothAgree(new int[]{0,0,1,1}, new int[]{2,2,3,3});
    }


    /*
        Approach 1: Explicit non-overlap check

        Two axis-aligned rectangles overlap with positive area iff none of these four separation conditions hold:
            rec1 is entirely to the LEFT of rec2  -> rec1[2] <= rec2[0]
            rec1 is entirely BELOW rec2           -> rec1[3] <= rec2[1]
            rec1 is entirely to the RIGHT of rec2 -> rec1[0] >= rec2[2]
            rec1 is entirely ABOVE rec2           -> rec1[1] >= rec2[3]

        We also short-circuit degenerate rectangles (width 0 or height 0) because a line segment cannot have positive-area overlap.

        Note: The degenerate check is actually redundant — the four separation conditions already return false for a line — but it makes the intent explicit and short-circuits earlier.
    */
    static boolean isRectangleOverlapSeparation(int[] rec1, int[] rec2) {
        // Check if either rectangle is actually a line (zero width or height)
        if (rec1[0] == rec1[2] || rec1[1] == rec1[3] ||
                rec2[0] == rec2[2] || rec2[1] == rec2[3]) {
            // A line cannot have positive-area overlap
            return false;
        }

        // No separation condition holds -> rectangles overlap with area > 0
        return !(rec1[2] <= rec2[0] ||   // rec1 entirely left of rec2
                rec1[3] <= rec2[1] ||   // rec1 entirely below rec2
                rec1[0] >= rec2[2] ||   // rec1 entirely right of rec2
                rec1[1] >= rec2[3]);    // rec1 entirely above rec2
    }

    /*
        Approach 2: Positive intersection area check (compact)

        Compute the intersection of the two rectangles:
            x-overlap width  = min(rec1[2], rec2[2]) - max(rec1[0], rec2[0])
            y-overlap height = min(rec1[3], rec2[3]) - max(rec1[1], rec2[1])

        The rectangles overlap with positive area iff BOTH the overlap width and overlap height are strictly greater than 0.

        This elegantly handles degenerate lines automatically:
        if rec1 has zero width, rec1[2] == rec1[0], so min(rec1[2], rec2[2]) > max(rec1[0], rec2[0]) becomes an impossible inequality, returning false.
    */
    static boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        return (Math.min(rec1[2], rec2[2]) > Math.max(rec1[0], rec2[0]) && // overlap width > 0
                Math.min(rec1[3], rec2[3]) > Math.max(rec1[1], rec2[1]));  // overlap height > 0
    }


    /*
        Helper used in main to verify both approaches return the same result.
        Useful sanity check when maintaining multiple implementations.
    */
    static void checkBothAgree(int[] rec1, int[] rec2) {
        boolean a = isRectangleOverlapSeparation(rec1, rec2);
        boolean b = isRectangleOverlap(rec1, rec2);
        System.out.println("Agree? " + (a == b) + " (separation=" + a + ", area=" + b + ")");
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Both approaches (Separation check & Positive-area check)

Time Complexity: O(1)

- Constant number of arithmetic / comparison operations regardless of input.

Space Complexity: O(1)

- Only a few primitive variables; no additional data structures.

Key Observation:
Two axis-aligned rectangles overlap with positive area iff the overlap
width AND overlap height are both strictly positive. Equivalently, none
of the four axis-separation conditions holds. The area-based formulation
is shorter and handles degenerate line rectangles automatically.

---------------------------------------------------------
*/