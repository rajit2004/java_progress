package Numbers;

public class armstrongNUM {
    public static void main(String[] args) {
        check(153);

    }
    static void check(int a){
        int sum = 0;
        int num = a ;
        while (a>0){
            int rem = a%10;
            int cube = rem*rem*rem;
            sum += cube;
            a = a/10;
        }
        if (sum == num)
            System.out.println("Is a Armstrong num ");
        else
            System.out.println("Not a armstrong num");

    }
}
