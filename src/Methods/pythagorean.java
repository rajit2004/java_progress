package Methods;

import java.util.Scanner;
public class pythagorean {
    static void main() {
        System.out.println(pythaTriplet());
    }
    static String pythaTriplet(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter three nums (hypot , base , perp): ");
        int h = in.nextInt();
        int b = in.nextInt();
        int p = in.nextInt();
        int q = h*h;
        int w = b*b;
        int e = p*p;
        if (q == w+e){
            return "is a Pythagorean triplet";
        }
        else
            return "not a Pythagorean triplet";
    }
}
