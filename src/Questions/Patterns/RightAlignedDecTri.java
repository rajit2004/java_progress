package Questions.Patterns;

/*
        *****
         ****
          ***
           **
            *
*/

public class RightAlignedDecTri {
    public static void main(String[] args) {
        pattern(5);
    }

    static void pattern(int n) {
        for (int i = n; i != 0 ; i--) {

//            for alignment / spaces :
            int q = 0;
            while (q < n - i) {         // why n-i => first row gets no space .
                System.out.print(" ");
                q++;
            }
//            for decreasing tri:
            int j = i;
            while(j > 0){
                System.out.print("*");
                j--;
            }
            System.out.println();
        }
    }
}



