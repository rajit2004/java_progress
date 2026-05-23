package Questions.Patterns;

/*
    1
    1 2
    1 2 3
    1 2 3 4
    1 2 3 4 5
*/

public class pattern4 {
    public static void main(String[] args) {
        for (int i = 1; i < 6; i++) {
            int j = i;
            for(j = 1 ; j <i+1 ; j++)
                System.out.print(j);
            System.out.println();
        }
    }
}
