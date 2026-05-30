package Recursion;

public class factorial {
    public static void main(String[] args) {
//        System.out.println(factRec(-2));
        System.out.println(factRec(6));
        System.out.println(factRec(0));
    }
    static int fact(int n){

        if(n<0)
            throw new IllegalArgumentException("Number must be positive !");

       int prod = 1;
       while(n > 0) {
           prod *= n;
           n--;
       }
       return prod;
    }

//    factorial using recursion :
    static int factRec(int n){

        if(n<0)
            throw new IllegalArgumentException("Number must be positive !");

        if(n == 0)
            return 1;
        return n * factRec(n-1);
    }
}


// recursive fn => n! = n * (n-1)!