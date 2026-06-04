package LeetCode.Numbers;

/*
You are given two integers num1 and num2 representing an inclusive range [num1, num2].

The waviness of a number is defined as the total count of its peaks and valleys:

A digit is a peak if it is strictly greater than both of its immediate neighbors.
A digit is a valley if it is strictly less than both of its immediate neighbors.
The first and last digits of a number cannot be peaks or valleys.
Any number with fewer than 3 digits has a waviness of 0.
Return the total sum of waviness for all numbers in the range [num1, num2].
*/

public class LeetCode_3751_TotalWavinessOfNNum {
    public static void main(String[] args) {
        int n = 120;
        int m = 130;
        System.out.println(totalWaviness(n,m));
    }
    static int helper(int num){
        String s = String.valueOf(num);
        int peak = 0;
        int valley = 0;
        for(int i = 1 ; i <= s.length()-2 ; i++) {              // s.length-2 bcoz peak or valley can't be the last or first element
            if (s.charAt(i-1) < s.charAt(i) && s.charAt(i) > s.charAt(i + 1))
                peak++;
            if(s.charAt(i - 1) > s.charAt(i) && s.charAt(i) < s.charAt(i+1))
                valley++;
        }
        return peak + valley;
    }
    static int totalWaviness(int num1, int num2){
//        base condition :
        if(num1 > num2)
            return 0;
        if(num1 == num2)
            return helper(num1);

// recursive call:
        return helper(num1) + totalWaviness(num1+1 , num2);
    }
}
