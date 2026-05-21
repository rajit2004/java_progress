package Strings;

public class PalindromeUsing2PointersEfficient {
    public static void main(String[] args) {
        String check = "racecar";
        int i = 0;
        int j = check.length() - 1;

        while(j > i) {

            if(check.charAt(i) == check.charAt(j)) {
                i++;
                j--;
            }
            else {
                System.out.println("invalid");
                return;
            }
        }

        System.out.println("valid");
    }
}


// time complexity o(1)