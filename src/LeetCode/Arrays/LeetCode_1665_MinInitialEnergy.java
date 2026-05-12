package LeetCode.Arrays;

/*
You are given an array tasks where tasks[i] = [actuali, minimumi]:
actual i is the actual amount of energy you spend to finish the ith task.
minimum i is the minimum amount of energy you require to begin the ith task.
For example, if the task is [10, 12] and your current energy is 11, you cannot start this task.
However, if your current energy is 13, you can complete this task, and your energy will be 3 after finishing it.
You can finish the tasks in any order you like.
Return the minimum initial amount of energy you will need to finish all the tasks.
*/

import java.util.Arrays;

public class LeetCode_1665_MinInitialEnergy {
    public static void main(String[] args) {
        int[][] tasks = {{1,2},{2,4},{4,8}};
        System.out.println(minimumEffort(tasks));
    }
    static int minimumEffort(int[][] tasks) {

        // Sort by (minimum - actual) in descending order
        Arrays.sort(tasks, (a, b) -> {
            return (b[1] - b[0]) - (a[1] - a[0]);
        });

        int energy = 0;
        int answer = 0;

        for (int[] task : tasks) {

            int actual = task[0];
            int minimum = task[1];

            // If energy is not enough, add more
            if (energy < minimum) {
                int need = minimum - energy;
                answer += need;
                energy += need;
            }

            // Complete task
            energy -= actual;
        }

        return answer;
    }
}
