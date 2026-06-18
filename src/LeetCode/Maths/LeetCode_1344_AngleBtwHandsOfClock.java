package LeetCode.Maths;

public class LeetCode_1344_AngleBtwHandsOfClock {
    public static void main(String[] args) {
        System.out.println(angleClock(12,30));
        System.out.println(angleClock(17,59));
    }

    /*
        Approach: Find the angle made by:
            1. Hour hand
            2. Minute hand

        Then calculate the absolute difference between the two angles. Since two angles are formed on a clock,return the smaller one.
     */

    static double angleClock(int hour, int minutes) {

        /*
            Hour Hand: Every hour contributes: 360 / 12 = 30 degrees

            The hour hand also moves as the minutes increase.

            Every minute contributes: 30 / 60 = 0.5 degrees

            Example: 12:30 => hourAngle = 12 * 30 + 30 * 0.5 = 15 degrees
         */
        double hourAngle = (hour % 12) * 30 + minutes * 0.5;

        /*
            Minute Hand: Every minute contributes: 360 / 60 = 6 degrees

            Example: 30 minutes => minuteAngle = 30 * 6 = 180 degrees
         */
        double minuteAngle = minutes * 6;

        // Absolute angle between both hands.
        double diff = Math.abs(hourAngle - minuteAngle);

        /*
            Two angles exist: diff = 360 - diff

            Return the smaller one.
         */
        return Math.min(diff, 360 - diff);
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(1)

Reason: Only a fixed number of arithmetic operations are performed. No loops or recursion are used.

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a few variables are used. hourAngle and minuteAngle and diff

No extra data structures are created.

---------------------------------------------------------

Key Observation: The hour hand is continuously moving. It does NOT stay fixed at the hour mark.

Therefore: hourAngle = (hour * 30) + (minutes * 0.5)

This extra minute contribution is the most important part of the solution.

---------------------------------------------------------
*/