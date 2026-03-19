package methods;

public class swap {
    public static void main(String[] args) {
        int a = 10;
        int b = 20 ;
        swapnum(a,b);
        System.out.println(a + " " + b);
    }
    static void swapnum(int num1 , int num2){
        int temp = num1;
        num1 = num2;
        num2 = temp;
    }
}


// no swapping coz we are having the print in main and in main the values remains unchanged . the value is changed only inside the method.