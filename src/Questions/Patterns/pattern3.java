package Questions.Patterns;

/*
 *****
 ****
 ***
 **
 *
*/

public class pattern3 {
    public static void main(String[] args) {
        for(int i = 5 ; i!= 0 ; i--){
            int j = i;
            while(j != 0){
                System.out.print("*");
                j--;
            }
            System.out.println();
        }
    }
}
