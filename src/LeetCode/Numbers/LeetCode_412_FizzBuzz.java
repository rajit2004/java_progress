package LeetCode.Numbers;

/*
Given an integer n, return a string array answer (1-indexed) where:

answer[i] == "FizzBuzz" if i is divisible by 3 and 5.
answer[i] == "Fizz" if i is divisible by 3.
answer[i] == "Buzz" if i is divisible by 5.
answer[i] == i (as a string) if none of the above conditions are true.
*/

import java.util.ArrayList;
import java.util.List;

public class LeetCode_412_FizzBuzz {
    public static void main(String[] args) {
        int n = 15;
        System.out.println(fizzBuzz(n));
    }
    static List<String> fizzBuzz(int n){
        List<String> result = new ArrayList<>();

        for (int i = 1; i <= n; i++) {

            // Divisible by both 3 and 5
            if (i % 3 == 0 && i % 5 == 0)
                result.add("FizzBuzz");

            // Divisible by 3
            else if (i % 3 == 0)
                result.add("Fizz");

            // Divisible by 5
            else if (i % 5 == 0)
                result.add("Buzz");

            // Not divisible by 3 or 5
            else
                result.add(String.valueOf(i));
        }
        return result;
    }
}
