package Arrays;
import java.util.ArrayList;
import java.util.Scanner;
public class multiD_arraylist {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

//        creation
        ArrayList<ArrayList<Integer>> list = new ArrayList<>(3);

//        initialization
        for (int i = 0; i < 3; i++)
            list.add(new ArrayList<>());

//        adding elements at each index using traversal
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("Enter the "+ i + " " + j +" element : ");
                list.get(i).add(in.nextInt());
            }
        }

//        printing the list
        System.out.println(list);
    }
}
