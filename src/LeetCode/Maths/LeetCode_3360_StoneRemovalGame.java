package LeetCode.Maths;

public class LeetCode_3360_StoneRemovalGame {
    public static void main(String[] args) {

        int n = 12;

        System.out.println(canAliceWin(n));
    }

    /*
        Simulation + Game Theory Approach :

        Alice starts by removing 10 stones.
        After every successful move, the number of stones that must be removed decreases by 1: 10 -> 9 -> 8 -> ... -> 1

        A player loses when they cannot remove the required number of stones.

        We simulate every move and count how many successful removals are possible.

        If the number of successful moves is odd, Alice makes the final valid move and wins.

        If it is even, Bob makes the final valid move and Alice loses.
     */
    static boolean canAliceWin(int n) {

        // Number of successful removals.
        int count = 0;

        // Alice starts by removing 10 stones.
        int remove = 10;

        // Continue while stones are available.
        while (n > 0) {

            // Check whether the current player can remove the required number.
            if (n >= remove) {
                n -= remove;
                // Next move requires one fewer stone.
                remove--;

                count++;
            } else {
                // Current player cannot make a move.
                break;
            }
        }

        // Odd number of moves means Alice wins.
        return count % 2 != 0;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: k = number of successful removals

---------------------------------------------------------

Time Complexity: O(k)

Reason: The loop runs once for every successful stone removal.

Since the required removal starts at 10 and decreases by 1, k is bounded by a small constant.

Overall: O(k), effectively O(1)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only two integer variables are used:
    1. count
    2. remove

Overall: O(1)

---------------------------------------------------------

Key Observation:

The game is completely determined by the number of successful moves.
Alice wins if the total number of valid moves is odd because Alice makes the last move.

Therefore:

odd moves  -> Alice wins
even moves -> Alice loses

---------------------------------------------------------
*/