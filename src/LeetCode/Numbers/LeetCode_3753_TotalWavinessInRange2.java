package LeetCode.Numbers;

/*
You are given two integers num1 and num2 representing an inclusive range [num1, num2].

The waviness of a number is defined as the total count of its peaks and valleys:

A digit is a peak if it is strictly greater than both of its immediate neighbors.
A digit is a valley if it is strictly less than both of its immediate neighbors.
The first and last digits of a number cannot be peaks or valleys.
Any number with fewer than 3 digits has a waviness of 0.
Return the total sum of waviness for all numbers in the range [num1, num
*/

// approach for its part one is correct, but we are getting a TLE.

/*
class Solution {

    public long helper(long num){
        int count = 0;

        String s = String.valueOf(num);

        for(int i = 1; i < s.length()-1; i++){
            int curr = s.charAt(i);
            int prev = s.charAt(i -1);
            int next = s.charAt(i + 1);

            if(curr > prev && curr > next || (curr < prev && curr < next))
                count++;
        }

        return count;
    }

    public long totalWaviness(long num1, long num2) {
        // check each element from num1 to num2 and return the count.

        long start = Math.max(100, num1);        // waviness for num < 100 is 0 so no need to check for nums < 100
        long res = 0;

        for(long i = start; i <= num2; i++)
            res += helper(i);


        return res;
    }
}
*/

// will have to get rid of the part that increases the run time.

public class LeetCode_3753_TotalWavinessInRange2 {
    public static void main(String[] args) {


//        can't solve with current knowledge

//        needs digital dynamic programming knowledge
    }
}
