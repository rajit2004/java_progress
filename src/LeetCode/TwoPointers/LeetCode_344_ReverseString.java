package LeetCode.TwoPointers;

import java.util.Arrays;

public class LeetCode_344_ReverseString {
    public static void main(String[] args) {
        String s = "hello";
        char[] c = s.toCharArray();
        System.out.println(Arrays.toString(reverseString(c)));
    }
    static char[] reverseString(char[] s){
        int left = 0;
        int right = s.length - 1;

        while (left < right) {

            // swap characters
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;

            // move pointers
            left++;
            right--;
        }
        return s;
    }
}
