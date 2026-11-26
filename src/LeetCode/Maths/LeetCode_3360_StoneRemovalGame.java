package LeetCode.Maths;

public class LeetCode_3360_StoneRemovalGame {

    public static void main(String[] args) {
        System.out.println(canAliceWinSimulation(10) + " " + canAliceWinLoop(10) + " " + canAliceWinFormula(10));
        System.out.println(canAliceWinSimulation(12) + " " + canAliceWinLoop(12) + " " + canAliceWinFormula(12));
        System.out.println(canAliceWinSimulation(19) + " " + canAliceWinLoop(19) + " " + canAliceWinFormula(19));
    }

    /* Approach 1: Simulate with a mutable stone count
       Alice and Bob alternate removing 10, 9, 8, ... stones. The game ends
       when the current player cannot remove the required amount. Alice wins
       exactly when the number of successful removals is odd (she made the
       last move). This version mutates `n` and counts removals.
       Example: n = 12 -> remove 10 (n = 2), next take 9 is too big -> 1 move
                -> odd -> Alice wins.
    */
    static boolean canAliceWinSimulation(int n) {
        int count = 0;
        int remove = 10;

        while (n > 0) {
            if (n >= remove) {
                n -= remove;
                remove--;
                count++;
            } else {
                break;
            }
        }

        return count % 2 == 1;
    }

    /* Approach 2: Compact loop
       Same simulation, but the loop condition is `n >= take` and the counter
       `k` tracks how many removals happened. Alice wins iff k is odd.
       Example: n = 19 -> 10 + 9 = 19 -> k = 2 -> even -> Alice loses.
    */
    static boolean canAliceWinLoop(int n) {
        int take = 10;
        int k = 0;

        while (n >= take) {
            n -= take;
            take--;
            k++;
        }

        return k % 2 == 1;
    }

    /* Approach 3: Closed-form quadratic
       The maximum number of removals k satisfies
           10 + 9 + ... + (10 - k + 1) = k * (21 - k) / 2 <= n
       Solving k^2 - 21k + 2n >= 0 gives
           k = floor((21 - sqrt(441 - 8n)) / 2)
       Alice wins iff k is odd. The Math.max(0, ...) keeps the expression
       valid even when n exceeds the total 55 stones.
       Example: n = 12 -> k = floor((21 - sqrt(345)) / 2) = 1 -> odd -> win.
    */
    static boolean canAliceWinFormula(int n) {
        int k = (int) ((21 - Math.sqrt(Math.max(0, 441 - 8 * n))) / 2);
        return k % 2 == 1;
    }
}

/* Complexity Analysis
   Let k be the number of successful removals (at most 10 under the
   problem's constraints).

   Approach 1: Simulate with a mutable stone count
     Time : O(k)  - one loop iteration per successful removal.
     Space: O(1)  - two integers (`count`, `remove`) plus the local `n`.
     Key Observation: the game is fully deterministic once n is fixed, so a
     direct simulation of alternating removals is enough to count moves.

   Approach 2: Compact loop
     Time : O(k)  - identical loop, just with the take/remaining check
            folded into the while condition.
     Space: O(1)  - two integers (`take`, `k`).
     Key Observation: same simulation, but expressing the termination as
     `n >= take` removes the explicit if/else and keeps the invariant clear.

   Approach 3: Closed-form quadratic (recommended)
     Time : O(1)  - one sqrt and a couple of arithmetic operations.
     Space: O(1)  - no loop, no counters beyond the local k.
     Key Observation: the total stones removed after k moves is the triangular
     sum k * (21 - k) / 2, so k is the largest integer satisfying that sum
     <= n, obtained by solving the quadratic and taking the floor.
*/

