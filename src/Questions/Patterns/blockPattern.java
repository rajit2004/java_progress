package Questions.Patterns;

/*

*****
*****
*****
*****
*****

* */

public class blockPattern {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            int j = 5;
            while(j!=0){
                System.out.print("*");
                j--;
            }
            System.out.println();
        }
    }
}
