package conditionals;

import java.util.Scanner;
public class word_comparison {
    public static void main(String[] args){
//        String a = "apple";
//        String b = "banana";
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a word : ");
        String a = input.next();
        System.out.print("Enter another word : ");
        String b = input.next();
        System.out.println(a.equals(b));
    }
}
