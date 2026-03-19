package methods;

public class ArgumentMethod {
    public static void main(String[] args) {
        int x = sum(35 , 54);
        int y = prod(12 , 31);
        String z = greet("Max Verstappen");
        System.out.println(x);
        System.out.println(y);
        System.out.println(z);
    }
    static int sum(int x , int y){
        return x+y;
    }
    static int prod(int a , int b){
        return a*b;
    }
    static String greet(String name){
        return "Hello, " + name + "!";
    }
}
