package LeetCode.Strings;

/*
You are given a string password.

The strength of the password is calculated based on the following rules:

        1 point for each distinct lowercase letter ('a' to 'z').
        2 points for each distinct uppercase letter ('A' to 'Z').
        3 points for each distinct digit ('0' to '9').
        5 points for each distinct special character from the set "!@#$".
Each character contributes at most once, even if it appears multiple times.

Return an integer denoting the strength of the password.
*/


import java.util.ArrayList;

public class LeetCode_3941_PasswordStrength {
    public static void main(String[] args) {
        String password = "bbB11#";
        System.out.println(passwordStrength(password));
    }
    static int passwordStrength(String password){
        int point = 0;

        ArrayList<Character> seen = new ArrayList<>();

        String symbols = "!@#$";
        for(int i = 0 ; i < password.length() ; i++) {
            if (Character.isLowerCase(password.charAt(i)) && !seen.contains(password.charAt(i))){
                seen.add(password.charAt(i));
                point += 1;
        }
            else if(Character.isUpperCase(password.charAt(i)) && !seen.contains(password.charAt(i))) {
                seen.add(password.charAt(i));
                point += 2;
            }
            else if(Character.isDigit(password.charAt(i)) && !seen.contains(password.charAt(i))){
                seen.add(password.charAt(i));
                point += 3;
            }
            else if(symbols.indexOf(password.charAt(i)) >= 0 && !seen.contains(password.charAt(i))) {
                seen.add(password.charAt(i));
                point += 5;
            }
        }
        return point;
    }
}
