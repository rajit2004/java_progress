package Strings;

public class PalindromeUsing2Pointers {
    public static void main(String[] args) {
        String check = "aba";
        char[] arr = check.toCharArray();
        int i = 0;
        int j = arr.length-1;
        while(j>i){
            if(arr[i] == arr[j] ){
                i++;
                j--;
            }
            else{
                System.out.println("invalid");
            return;
            }
        }
        System.out.println("valid");
    }
}


// time complexity o(n)