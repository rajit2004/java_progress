package Questions.Patterns;

/*
 *
 **
 ***
 ****
 *****
*/

public class pattern2 {
    public static void main(String[] args) {
        for (int i = 1; i < 6; i++) {
            int j = i;
            while(j!=0) {
                System.out.print("*");
                j--;
            }
            System.out.println();
        }
    }
}
//General rule: avoid changing the outer loop variable inside an inner loop unless that behavior is intentional.