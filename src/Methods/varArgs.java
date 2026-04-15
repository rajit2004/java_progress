package Methods;

import java.util.Arrays;

public class varArgs {
    public static void main(String[] args) {
        function("Apple " , "Banana ");
        mix(2 , 6 , "apple" , "banana" , "pear");
    }
    static void function(String ...s){
        System.out.println(Arrays.toString(s));
    }
    static void mix(int a , int b , String ...v){
        System.out.println(a+b);
        System.out.println(Arrays.toString(v));
    }
}
