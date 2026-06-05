package LeetCode.Maths;

/*
You are playing the following Nim Game with your friend:

Initially, there is a heap of stones on the table.
You and your friend will alternate taking turns, and you go first.
On each turn, the person whose turn it is will remove 1 to 3 stones from the heap.
The one who removes the last stone is the winner.

Return true if you can win assuming both players play optimally.
*/

public class LeetCode_292_NimGame {
    public static void main(String[] args) {
        System.out.println(canWinNim(5));
    }

    static boolean canWinNim(int n){

        /*
            Pattern Observation:

            n = 1  -> Win       pick 1 and win
            n = 2  -> Win       pick 2 and win
            n = 3  -> Win       pick 3 and win
            n = 4  -> Lose      NO MATTER WHATEVER YOU PICK OPPONENTS GETS EITHER 1 OR 2 OR 3 TO PICK AND CAN PICK THEM ALL & WIN

            n = 5  -> Win (take 1, leave 4 , whatever opponet picks i can pick 3 and win)
            n = 6  -> Win (take 2, leave 4 , whatever opponet picks i can pick 2 and win)
            n = 7  -> Win (take 3, leave 4 , whatever opponet picks i can pick 1 and win)
            n = 8  -> Lose

            Multiples of 4 are always losing positions.
         */

        /*
            Why?

            If n is a multiple of 4:

            Whatever we take (1, 2, or 3),
            we leave a non-multiple of 4 for the opponent.

            The opponent can then always take enough stones
            to leave a multiple of 4 back to us.

            Eventually we are forced to face 4 stones and lose.
         */

        /*
            If n is NOT a multiple of 4:

            We can always remove 1, 2, or 3 stones
            to make the remaining pile a multiple of 4.

            Example:
            5 -> take 1 -> leave 4
            6 -> take 2 -> leave 4
            7 -> take 3 -> leave 4

            This forces the opponent into a losing position.
         */

        return n % 4 != 0;

/*
        return n % 4 != 0; is same as:

        if(n % 4 != 0)
            return true;

        return false;

*/
    }
}