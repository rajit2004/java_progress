package Methods;

public class shadowing {
    static int x = 90;
    public static void main(String[] args) {
        System.out.println(x);


//        methods.shadowing
        int x = 45;
        System.out.println(x); // overrides the higher value of x from the above declaration and uses local declaration

        func();
    }
    static void func(){
        System.out.println(x);
    }
}
