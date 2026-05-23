package Questions.Patterns;
/*
                  *
                 **
                ***
               ****
              *****
 */

public class pattern6 {
    public static void main(String[] args) {
        pattern(5);
    }

    static void pattern(int n) {
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j >= 0) {
                System.out.printf("%s", "*");
                j--;
            }
            System.out.println();
        }
    }
}

/*
static void pattern(int n){
    for (int i = 0; i < n; i++) {
        // "*" repeated (i+1) times, right-aligned into a field of width n
        System.out.printf("%" + n + "s%n", "*".repeat(i + 1));
 */


