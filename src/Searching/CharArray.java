package Searching;

import java.util.Arrays;

public class CharArray {
    public static void main(String[] args) {
        String name = "Ranesh Rajit";
        char[] x = name.toCharArray();
        System.out.println(Arrays.toString(x));

        System.out.println(Search(x));
    }
    static boolean Search(char[] arr){
        char target = 'a';
        for(char ch : arr){
            if(ch == target)
                return true;
        }
        return false;
    }
}
