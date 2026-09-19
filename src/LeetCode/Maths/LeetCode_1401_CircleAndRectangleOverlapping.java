package LeetCode.Maths;

public class LeetCode_1401_CircleAndRectangleOverlapping {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): circle inside rectangle -> true
        System.out.println("Sample 1 (inside) -> CaseByCase: "
                + checkOverlapCaseByCase(1, 0, 0, -1, -1, 1, 1)
                + " | ClosestPoint: " + checkOverlap(1, 0, 0, -1, -1, 1, 1)
                + " (expected: true)");

        // Sample 2 (LeetCode): circle far away -> false
        System.out.println("Sample 2 (far) -> CaseByCase: "
                + checkOverlapCaseByCase(1, 1, 1, 1, -3, 2, -1)
                + " | ClosestPoint: " + checkOverlap(1, 1, 1, 1, -3, 2, -1)
                + " (expected: false)");

        // Sample 3 (LeetCode): circle intersects an edge -> true
        System.out.println("Sample 3 (edge) -> CaseByCase: "
                + checkOverlapCaseByCase(1, 0, 0, -1, 0, 0, 1)
                + " | ClosestPoint: " + checkOverlap(1, 0, 0, -1, 0, 0, 1)
                + " (expected: true)");

        // Edge case: circle touches a corner exactly (distance == radius)
        System.out.println("Edge (corner touch) -> CaseByCase: "
                + checkOverlapCaseByCase(5, 0, 0, 3, 4, 10, 10)
                + " | ClosestPoint: " + checkOverlap(5, 0, 0, 3, 4, 10, 10)
                + " (expected: true)");

        // Edge case: circle just outside a corner (distance > radius)
        System.out.println("Edge (just outside corner) -> CaseByCase: "
                + checkOverlapCaseByCase(4, 0, 0, 3, 4, 10, 10)
                + " | ClosestPoint: " + checkOverlap(4, 0, 0, 3, 4, 10, 10)
                + " (expected: false)");

        // Edge case: zero radius circle inside rectangle -> true
        System.out.println("Edge (zero radius inside) -> CaseByCase: "
                + checkOverlapCaseByCase(0, 0, 0, -1, -1, 1, 1)
                + " | ClosestPoint: " + checkOverlap(0, 0, 0, -1, -1, 1, 1)
                + " (expected: true)");

        // Edge case: huge coordinates to exercise long arithmetic
        System.out.println("Edge (large coords) -> CaseByCase: "
                + checkOverlapCaseByCase(100000, 0, 0, -1000000, -1000000, 1000000, 1000000)
                + " | ClosestPoint: " + checkOverlap(100000, 0, 0, -1000000, -1000000, 1000000, 1000000)
                + " (expected: true)");
    }


    /*
        Approach 1: Exhaustive case analysis

        Enumerate every spatial relationship between the circle's center and
        the rectangle:
            1. Center inside the rectangle.
            2. Center directly above / below (aligned in x, y outside).
            3. Center directly left / right (aligned in y, x outside).
            4. Center outside a corner region — check the distance from center
               to each of the 4 corners.

        If any of these conditions hold, the circle overlaps the rectangle.

        This approach is verbose but very explicit about the geometry.
        It's easy to get wrong (missing cases, using <= vs < incorrectly).

        Time:  O(1)
        Space: O(1)
    */
    static boolean checkOverlapCaseByCase(
            int radius, int xCenter, int yCenter,
            int x1, int y1, int x2, int y2) {

        // Center is inside the rectangle
        if (x1 <= xCenter && xCenter <= x2 && y1 <= yCenter && yCenter <= y2) {
            return true;
        }

        // Center is directly above the rectangle (x aligned, y above top edge)
        if (x1 <= xCenter && xCenter <= x2 && y2 <= yCenter && yCenter <= y2 + radius) {
            return true;
        }

        // Center is directly below the rectangle (x aligned, y below bottom edge)
        if (x1 <= xCenter && xCenter <= x2 && y1 - radius <= yCenter && yCenter <= y1) {
            return true;
        }

        // Center is directly to the left of the rectangle (y aligned, x left of left edge)
        if (x1 - radius <= xCenter && xCenter <= x1 && y1 <= yCenter && yCenter <= y2) {
            return true;
        }

        // Center is directly to the right of the rectangle (y aligned, x right of right edge)
        if (x2 <= xCenter && xCenter <= x2 + radius && y1 <= yCenter && yCenter <= y2) {
            return true;
        }

        // Center is outside a corner region — check squared distance to each corner
        if (distanceSq(xCenter, yCenter, x1, y2) <= (long) radius * radius) return true; // upper-left
        if (distanceSq(xCenter, yCenter, x1, y1) <= (long) radius * radius) return true; // lower-left
        if (distanceSq(xCenter, yCenter, x2, y2) <= (long) radius * radius) return true; // upper-right
        if (distanceSq(xCenter, yCenter, x2, y1) <= (long) radius * radius) return true; // lower-right

        return false;
    }

    /*
        Squared Euclidean distance between (ux, uy) and (vx, vy).

        Uses long arithmetic to avoid overflow when coordinates are large
        (LeetCode allows coordinates up to +/- 10^9, and squares reach 10^18).
    */
    static long distanceSq(int ux, int uy, int vx, int vy) {
        long dx = (long) ux - vx;
        long dy = (long) uy - vy;
        return dx * dx + dy * dy;
    }


    /*
        Approach 2: Closest point on rectangle + distance check (clean)

        Key insight:
        The circle overlaps the rectangle iff the distance from the circle's
        center to the CLOSEST point on the rectangle is <= radius.

        The closest point on an axis-aligned rectangle to a point (cx, cy) is
        obtained by clamping cx and cy to the rectangle's coordinate ranges:
            closestX = clamp(cx, x1, x2)
            closestY = clamp(cy, y1, y2)

        Then compare squared distance against radius^2.

        This single formula subsumes all the cases in Approach 1:
            - center inside:       closest = center, distance = 0
            - center beside an edge: closest is on the edge, distance = perp gap
            - center outside a corner: closest is the corner

        Time:  O(1)
        Space: O(1)
    */
    static boolean checkOverlap(
            int radius, int xCenter, int yCenter,
            int x1, int y1, int x2, int y2) {

        // Clamp center coordinates to the rectangle's bounds
        int closestX = Math.max(x1, Math.min(xCenter, x2));
        int closestY = Math.max(y1, Math.min(yCenter, y2));

        // Squared distance from circle center to closest point on rectangle
        long dx = (long) xCenter - closestX;
        long dy = (long) yCenter - closestY;

        return dx * dx + dy * dy <= (long) radius * radius;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Exhaustive case analysis

Time Complexity: O(1)

- A fixed number of coordinate comparisons and 4 distance checks.

Space Complexity: O(1)

- Only a few primitive variables.

Key Observation: The overlap condition decomposes into a handful of
geometric cases (center inside, along an edge's projection, or near a corner).


Approach 2: Closest point on rectangle + distance check

Time Complexity: O(1)

- Two clamp operations, a difference, and one squared-distance comparison.

Space Complexity: O(1)

- Only a few primitive variables.

Key Observation: Overlap ⟺ distance from circle center to the closest
point on the rectangle ≤ radius. Clamping the center coordinates to the
rectangle bounds gives that closest point directly, replacing 10 branches
with a single unified formula. Use long arithmetic to avoid overflow on
large coordinate values.

---------------------------------------------------------
*/