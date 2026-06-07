package LeetCode.Arrays;

public class LeetCode_3636_EarliestFinishTime_II {
    public static void main(String[] args) {
        int[] landStartTime = {2,8};
        int[] landDuration = {4,1};
        int[] waterStartTime = {6};
        int[] waterDuration = {3};

        System.out.println(earliestFinishTime(landStartTime , landDuration , waterStartTime , waterDuration));
    }

    static int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {

        // Case 1:
        // Land ride first, then Water ride.
        int ans1 = solve(landStartTime, landDuration,
                waterStartTime, waterDuration);

        // Case 2:
        // Water ride first, then Land ride.
        int ans2 = solve(waterStartTime, waterDuration,
                landStartTime, landDuration);

        // Return the better of the two possible orders.
        return Math.min(ans1, ans2);
    }

    static int solve(int[] firstStart, int[] firstDuration,
                     int[] secondStart, int[] secondDuration) {

        // Find the earliest possible finishing time among all rides of the first category.

        /*
             Key Observation:
             If one ride finishes earlier than another,
             it can never be worse because:

             max(smallerFinish, startTime) <= max(largerFinish, startTime)

             Therefore we only need the minimum finish time.
         */

        int earliestFinish = Integer.MAX_VALUE;

        for (int i = 0; i < firstStart.length; i++)
            earliestFinish = Math.min( earliestFinish, firstStart[i] + firstDuration[i]);


        // Try every ride from the second category.

        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < secondStart.length; i++) {

            // If the second ride is already open = > start immediately after finishing the first ride.

            // Otherwise, wait until the second ride opens.

            int startSecond = Math.max(earliestFinish,secondStart[i]);

            // Time when both rides are completed.
            int finishSecond = startSecond + secondDuration[i];

            // Keep track of the earliest overall finish time.
            answer = Math.min(answer, finishSecond);
        }

        return answer;
    }
}
