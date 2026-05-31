package Recursion.Questions.Basics;

public class ALT_numsFromN_to_1 {
    public static void main(String[] args) {
        print(10);
    }
    static void print(int n){
        if(n < 1)
            return;
        System.out.println(n);
        print(--n);
    }
}
