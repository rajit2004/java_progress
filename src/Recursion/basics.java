package Recursion;

public class basics {
    public static void main(String[] args) {

//        in recursion, we call the function inside another function .
//        msg();
      /*
        msg2();
        msg3();
        msg4();
        */

//        so instead of calling various the msg fn again n again why don't we just

//        solution ???

//        call one fn inside of another fn
//        and now we just call single msg fn which will call all the remaining fns.
        msg();

    }
//    ex:

    static void msg(){
        System.out.println("hello world!");
        msg1();
    }
    static void msg1(){
        System.out.println("hello world!");
        msg2();
    }
    static void msg2(){
        System.out.println("hello world!");
        msg3();
    }
    static void msg3(){
        System.out.println("hello world!");
        msg4();
    }
    static void msg4(){
        System.out.println("hello world!");
    }

}
