package Conditionals;

public class CheckAlphabet {
    public static void main(String[] args) {
        char c = '9';

//        check
        if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
            System.out.println("Is a char");
        else
            System.out.println("Not a char");

    }
}
