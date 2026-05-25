package LeetCode.Arrays;

/*
You are given an m x n binary matrix mat of 1's (representing soldiers) and 0's (representing civilians).
The soldiers are positioned in front of the civilians. That is, all the 1's will appear to the left of all the 0's in each row.

A row i is weaker than a row j if one of the following is true:

The number of soldiers in row i is less than the number of soldiers in row j.
Both rows have the same number of soldiers and i < j.
Return the indices of the k weakest rows in the matrix ordered from weakest to strongest.
*/

import java.util.Arrays;

public class LeetCode_1337_WeakestRowInMatrix {
    public static void main(String[] args) {

// simple understanding = weakest row is the one with least number of 1s.

        int[][] matrix = {{1,1,0,0,0},
                {1,1,1,1,0},
                {1,0,0,0,0},
                {1,1,0,0,0},
                {1,1,1,1,1}};
        System.out.println(Arrays.toString(kWeakestRows(matrix, 3)));

    }
    static int[] kWeakestRows(int[][] mat, int k) {

//        first we traverse through each row and count the num of 1s in each row and store the count in a count array

        int[][] occurrence = new int[mat.length][2];
        for (int i = 0; i < mat.length; i++) {
            int count = 0;
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j] == 1)
                    count++;
            }
            occurrence[i][0] = count;
            occurrence[i][1] = i;
        }
//            now we find our weakest row by sorting the occurrence array :
            Arrays.sort(occurrence, (a, b) -> {
                if (a[0] == b[0]) {
                    return a[1] - b[1]; // smaller row index first
                }
                return a[0] - b[0]; // fewer soldiers first
            });

// now we only use the num of elements asked to show our answer
            int[] ans = new int[k];
            for (int q = 0; q < k; q++) {
                ans[q] = occurrence[q][1];
            }

        return ans;
    }
}
