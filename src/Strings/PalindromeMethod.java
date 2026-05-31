package Strings;

public class PalindromeMethod {
    public static void main(String[] args) {

        System.out.println(ispalindrome("RaCeCaR"));
    }
    static boolean ispalindrome(String str){

        if(str == null || str.isEmpty())
            return true;

        str = str.toLowerCase();

        for (int i = 0 ; i <= (str.length()-1)/2 ; i++){
            char start = str.charAt(i);
            char end = str.charAt(str.length()-1-i);

            if(start != end)
                return false;
        }
        return true;
    }
}
