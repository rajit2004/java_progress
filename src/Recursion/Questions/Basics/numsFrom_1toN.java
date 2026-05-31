package Recursion.Questions.Basics;

public class numsFrom_1toN {
    public static void main(String[] args) {
        print(10);
    }
    static void print(int n){
        if(n<1)
            return;
        print(n - 1);
        System.out.println(n);
    }
}


/*
 here first we recursively call and load all the fn call in to the stack memory and once the base condition is reached the recursive call will
 stop, and now we start getting the values of the fn calls.

 so in here we loaded the fn call in stack as : (LIFO)
    main
    f10
    f9
    f8
    f7
    f6
    f5
    f4
    f3
    f2
    f1
    f0 -> base condition reaches . so now we return

    return in sequence :
    f1      -> 1
    f2      -> 2
    f3      -> 3
    f4      -> 4
    f5      -> 5
    f6      -> 6
    f7      -> 7
    f8      -> 8
    f9      -> 9
    f10     -> 10

END
*/
