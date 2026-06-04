package LeetCode.Numbers;

public class LeetCode_3945_DigitFrequencyScore {
    public static void main(String[] args) {
        System.out.println(digitFrequencyScore(122));
        System.out.println(digitFrequencyScore2(101));
    }

/*   My approach :

        Each occurrence of a digit contributes its value once.

        The expression: Σ(d × freq(d)) is equivalent to summing every digit in the number.

        Therefore, we simply traverse the digits and add them.
*/


//    brute force :
    static int digitFrequencyScore(int n){
        String num = String.valueOf(n);
        int ans = 0;
        for(int i = 0 ; i < num.length() ; i++)
            ans += num.charAt(i) - '0';

/*
since we have .charAt(i) it stores and return the value in ASCII
so we subtract char '0' to get the actual numerical value of the integer
this way we can add it directly to our answer and if not then we have the sum of their ASCII values


        charAt(i) returns a digit character such as '5'.

        Digits '0' to '9' are stored consecutively in ASCII/Unicode.

        Subtracting '0' converts the digit character into
        its actual integer value.

        Example:
        '7' - '0'
        = 55 - 48
        = 7

*/

        return ans;
    }

//    optimized solution :  reduces run time to 1ms from 2ms :

    static int digitFrequencyScore2(int n){
        int ans = 0;
        while(n > 0){
            ans += n % 10;
            n /= 10;
        }
        return ans;
    }
}


/*
Time Complexity: O(d)
Space Complexity: O(1)

where d is the number of digits.
*/
