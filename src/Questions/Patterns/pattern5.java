package Questions.Patterns;

/*
 *
 **
 ***
 ****
 *****
 ****
 ***
 **
 *
*/

public class pattern5 {
    public static void main(String[] args) {
        int n = 5;
        pattern(5);
    }
    static void pattern(int num){
        for(int i = 0; i < 2 * num +1 ; i++) {
            int j = i;

//            for the decreasing part :
            if(i > num){
                int p = num - (i - num);
                while(p!=0){
                    System.out.print("*");
                    p--;
                }
                System.out.println();
            }

//            for the increasing part :
           else {
                while(j != 0){
                    System.out.print("*");
                    j--;
                }
                System.out.println();
            }
        }
    }
}
