package Recursion;

public class Example {
    public static void main(String[] args) {
        call(1);

    }
    static void call(int n) {

//        base condition:
        if (n == 10) {
            return;
        }

//        body  :

//        recursive call :
        System.out.println(n);
        call(n + 1);
    }
}