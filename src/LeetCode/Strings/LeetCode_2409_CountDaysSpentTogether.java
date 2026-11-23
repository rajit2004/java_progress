package LeetCode.Strings;

public class LeetCode_2409_CountDaysSpentTogether {

    public static void main(String[] args) {
        System.out.println(countDaysTogetherBrute("08-15", "08-18", "08-16", "08-19"));
        System.out.println(countDaysTogetherPrefix("08-15", "08-18", "08-16", "08-19"));

        System.out.println(countDaysTogetherBrute("10-01", "10-31", "11-01", "11-30"));
        System.out.println(countDaysTogetherPrefix("10-01", "10-31", "11-01", "11-30"));
    }

    /* Approach 1: Brute force -- iterate every day of the year
       Convert each "MM-DD" into a day-of-year using a plain month-length
       table. Then scan every day 1..365 and increment the answer if the day
       is inside BOTH [arriveAlice, leaveAlice] and [arriveBob, leaveBob].
       Example: alice 08-15..08-18, bob 08-16..08-19
                common days are 08-16, 08-17, 08-18 -> 3.
    */
    static int countDaysTogetherBrute(String arriveAlice, String leaveAlice,
                                      String arriveBob, String leaveBob) {
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] prefix = new int[13];
        for (int i = 0; i < 12; i++) prefix[i + 1] = prefix[i] + daysInMonth[i];

        int aIn   = dayOfYear(arriveAlice, prefix);
        int aOut  = dayOfYear(leaveAlice,  prefix);
        int bIn   = dayOfYear(arriveBob,   prefix);
        int bOut  = dayOfYear(leaveBob,    prefix);

        int count = 0;
        for (int day = 1; day <= 365; day++) {
            if (day >= aIn && day <= aOut && day >= bIn && day <= bOut) count++;
        }
        return count;
    }

    /* Approach 2: Convert to day-of-year and take the interval intersection
       Same day-of-year conversion as Approach 1, but skip the loop. The
       overlap of [aIn, aOut] and [bIn, bOut] is
         max(0, min(aOut, bOut) - max(aIn, bIn) + 1)
       where the +1 counts both endpoints and the outer max(0, ...) handles
       non-overlapping intervals (negative raw length).
       Example: alice 08-15..08-18 -> [227,230], bob 08-16..08-19 -> [228,231]
                overlap = min(230,231) - max(227,228) + 1 = 230 - 228 + 1 = 3.
    */
    static int countDaysTogetherPrefix(String arriveAlice, String leaveAlice,
                                       String arriveBob, String leaveBob) {
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] prefix = new int[13];
        for (int i = 0; i < 12; i++) prefix[i + 1] = prefix[i] + daysInMonth[i];

        int aIn  = dayOfYear(arriveAlice, prefix);
        int aOut = dayOfYear(leaveAlice,  prefix);
        int bIn  = dayOfYear(arriveBob,   prefix);
        int bOut = dayOfYear(leaveBob,    prefix);

        return Math.max(0, Math.min(aOut, bOut) - Math.max(aIn, bIn) + 1);
    }

    // Converts "MM-DD" into a day-of-year (1..365) using the prefix-sum table.
    static int dayOfYear(String date, int[] prefix) {
        int month = Integer.parseInt(date.substring(0, 2));
        int day   = Integer.parseInt(date.substring(3));
        return prefix[month - 1] + day;
    }
}

/* Complexity Analysis
   Dates are always fixed-width "MM-DD" strings, so length is constant.

   Approach 1: Brute force -- iterate every day of the year
     Time : O(365)   - fixed 365-day scan regardless of inputs.
     Space: O(1)     - month table + a handful of integers.
     Key Observation: a day is shared iff it lies in both inclusive intervals,
     so a single pass over the full year suffices and is trivially correct.

   Approach 2: Convert to day-of-year and take the interval intersection (recommended)
     Time : O(1)     - a few substring/parse operations and one intersection.
     Space: O(1)     - month table + prefix + four integers.
     Key Observation: after mapping each date to its day-of-year, the two
     inclusive intervals intersect in exactly max(0, min(ends) - max(starts) + 1)
     days, which needs no loop at all.
*/

