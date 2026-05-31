package Recursion.Questions;

public class Palindrome {
    public static void main(String[] args) {
        String word = "racecar";
        System.out.println(check("qwertytrewq"));
    }

//    iterative approach:
    static boolean checkPal(String word){

        if(word == null || word.isEmpty())
            return true;

        word = word.toLowerCase();

        for(int i = 0 ; i <= word.length() - 1 ; i++){

            char st = word.charAt(i);
            char end = word.charAt(word.length()-1-i);

            if (st != end)
                return false;
        }
        return true;
    }

//    recursive approach :
    static boolean helper(String str , int st , int end){

//        base condition : when we are at middle string so we do not wanna move end behind start
        if(st >= end)
            return true;        // all checks passed

        char first = str.charAt(st);
        char last = str.charAt(end);

        if (first != last)
            return false;

         return helper(str , st + 1 , end - 1 );
    }
    static boolean check(String str){

//        check for null:
        if(str == null || str.isEmpty())
            return true;

//        case-sensitive
        str = str.toLowerCase();

//        call to helper fn
        return helper(str , 0 , str.length()-1);
    }
}
