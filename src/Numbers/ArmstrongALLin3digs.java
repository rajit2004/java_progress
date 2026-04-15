package Numbers;

public class ArmstrongALLin3digs {
    public static void main(String[] args) {
        for (int i = 100; i <1000 ; i++) {
            if(check(i))
                System.out.print(i + " ");
        }
    }
    static boolean check(int a){
        int sum = 0;
        int num = a;
        while(a>0){
            int rem = a%10;
            a /=10;
            int cube = rem*rem*rem;
            sum += cube;
        }
        if (sum == num)
            return true;
        return false;
    }
}
