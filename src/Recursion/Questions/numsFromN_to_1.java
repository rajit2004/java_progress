package Recursion.Questions;

public class numsFromN_to_1 {
    public static void main(String[] args) {
        numPrint(10);
    }
    static void numPrint(int n){
        if(n<1)
            return;
        System.out.println(n);
        numPrint(n - 1);
    }
}
