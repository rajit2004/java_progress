package conditionals;

import java.util.Scanner;
public class word_description {
    public static void main(String[] args) {
//        we take input from a user and giver description about it
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the word : ");
        String word = input.next();
        switch (word) {
            case "apple" -> System.out.println("apple a day keeps doctors away ! ");
            case "orange" -> System.out.println("sour n sweet !");
            case "mango" -> System.out.println("king of fruits ! ");
            default -> System.out.println("not a fruits");
        }
    }
}
