package methods;

public class fnOverloading {
    public static void main(String[] args) {
        System.out.println(sum(2,4));
        System.out.println(sum(2.56,7.81 , 9.33));
        System.out.println(sum(1.2f , 12.454f , 0.234f , 54.3245f , 1129.098712f));
    }
    static int sum(int a , int b ){
        return(a+b);
    }
    static double sum(double a , double b , double c){
        return(a+b+c);
    }
    static float sum(float ...f){
        float total = 0;
        for (int i = 0 ; i< f.length ; i++)
            total += f[i];
        return total;
    }
}
