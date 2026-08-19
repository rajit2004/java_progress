package LeetCode.Greedy;

import java.util.HashMap;
import java.util.Map;

public class LeetCode_1386_CinemaSeatAllocation {
    public static void main(String[] args) {

        int n = 3;
        int[][] reservedSeats = {{1, 2},{1, 3},{1, 8},{2, 6},{3, 1},{3, 10}};

        System.out.println(maxNumberOfFamilies(n, reservedSeats));
    }

    /*
        Bitmask + Greedy Approach : Each row has 10 seats.

        A family of 4 can sit in one of these three possible blocks:
            Left   : seats 2, 3, 4, 5
            Middle : seats 4, 5, 6, 7
            Right  : seats 6, 7, 8, 9

        Only seats 2 to 9 matter because seats 1 and 10 can never be part of a group of 4.
        We represent the occupied seats using a bitmask.

        For every reserved seat from 2 to 9: bit = seat - 2

        This maps:
            seat 2 -> bit 0
            seat 3 -> bit 1
            ...
            seat 9 -> bit 7

        Rows without any relevant reserved seats can always accommodate 2 families.
        Therefore: initially = (n - occupiedRows) * 2
        For rows containing reserved seats, check whether at least one of the three possible family blocks is completely free.
     */
    static int maxNumberOfFamilies(
            int n,
            int[][] reservedSeats
    ) {

        /*
            Bitmasks representing the three possible family seating arrangements.
                left   = seats 2,3,4,5
                middle = seats 4,5,6,7
                right  = seats 6,7,8,9
         */
        int left = 0b11110000;
        int middle = 0b11000011;
        int right = 0b00001111;

//            Stores the occupied-seat bitmask for only the rows that contain relevant reserved seats.
        Map<Integer, Integer> occupied = new HashMap<>();

        // Process every reserved seat.
        for (int[] seat : reservedSeats) {

            int row = seat[0];
            int column = seat[1];

//                Seats 1 and 10 cannot affect any possible group of four.
            if (column >= 2 && column <= 9) {

                // Get the current occupied mask for this row.
                int mask = occupied.getOrDefault(row, 0);

                // Mark the reserved seat as occupied.
                mask |= 1 << (column - 2);

                occupied.put(row, mask);
            }
        }

//            Every row without relevant reservations can fit two families.
        int answer = (n - occupied.size()) * 2;

//            Process only rows containing reserved seats that can affect family placement.
        for (Map.Entry<Integer, Integer> entry : occupied.entrySet()) {

            int mask = entry.getValue();

            /*
                If any one of the three possible blocks is completely free, this row can accommodate at least one family.
                The condition: (mask | block) == block , means every occupied bit is contained within the block mask, which indicates that the required seats are available for the family.
             */
            if ((mask | left) == left|| (mask | middle) == middle|| (mask | right) == right)
                answer++;
        }

        return answer;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = number of rows
r = number of reserved seats

---------------------------------------------------------

Time Complexity: O(r)

Reason:

1. Process every reserved seat once: O(r)
2. Process only rows that contain relevant reserved seats. The number of such rows is at most r.
3. Bitmask checks take O(1).

Therefore: O(r)

---------------------------------------------------------

Space Complexity: O(r)

Reason:

The HashMap stores only rows that contain reserved seats from columns 2 to 9.
In the worst case, every reserved seat belongs to a different row.

Therefore: O(r)

---------------------------------------------------------

Key Observation:

A row can accommodate at most two families.

The only possible groups of four are:
    [2,3,4,5]
    [4,5,6,7]
    [6,7,8,9]

Using an 8-bit mask allows us to represent the occupied seats compactly and check all possible family placements using bitwise operations.
Rows with no relevant reservations always contribute 2 families, so we only need to explicitly process affected rows.

---------------------------------------------------------
*/