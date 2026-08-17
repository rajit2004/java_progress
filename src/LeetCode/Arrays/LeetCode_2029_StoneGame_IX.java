package LeetCode.Arrays;

public class LeetCode_2029_StoneGame_IX {
    public static void main(String[] args) {

        int[] stones = {2, 1};

        System.out.println(stoneGameIX(stones));
    }

    /*
        Game Theory + Modulo Approach : Only the remainder of each stone when divided by 3 matters.

        Every stone belongs to one of three groups:
                remainder 0
                remainder 1
                remainder 2

        A player loses if their selected stones make the total sum divisible by 3.
        Therefore, we only need the count of stones in each remainder group.

        Let:
            f[0] = count of stones % 3 == 0
            f[1] = count of stones % 3 == 1
            f[2] = count of stones % 3 == 2

        The effect of remainder-0 stones depends only on whether their count is even or odd.
     */
    static boolean stoneGameIX(int[] stones) {

        // Count stones according to their remainder when divided by 3.
        int[] frequency = {0, 0, 0};

        for (int stone : stones)
            frequency[stone % 3]++;


        /*
            Case 1: Number of remainder-0 stones is even.
            These stones can effectively be taken in pairs without changing the winner.
            Alice needs at least one stone from both remainder-1 and remainder-2 groups.
            Otherwise, Bob can force the game.
         */
        if ((frequency[0] & 1) == 0)
            return Math.min(frequency[1],frequency[2]) > 0;


        /*
            Case 2: Number of remainder-0 stones is odd.
            Alice wins only when the difference between the counts of remainder-1 and remainder-2 stones is greater than 2.
            This allows Alice to force Bob into selecting a combination whose sum is divisible by 3.
         */
        return Math.abs(frequency[1] - frequency[2]) > 2;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = stones.length

---------------------------------------------------------

Time Complexity: O(n)

Reason: The array is traversed exactly once to count the stones according to their remainder modulo 3.

All remaining operations take O(1).

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a fixed-size array of three elements is used:
            frequency[0]
            frequency[1]
            frequency[2]

Overall: O(1)

---------------------------------------------------------

Key Observation:

For this game, the actual values of the stones do not matter.
Only their remainder modulo 3 matters.
Every stone can therefore be reduced to: 0, 1, or 2
The parity of the number of remainder-0 stones and the difference between the remainder-1 and remainder-2 counts completely determine whether Alice can force a win.

---------------------------------------------------------
*/