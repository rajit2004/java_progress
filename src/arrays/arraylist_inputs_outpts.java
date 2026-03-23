package arrays;
import java.util.Scanner;
import java.util.ArrayList;
public class arraylist_inputs_outpts {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList<>(5);

//        input :

        for (int i = 0; i < 5; i++) {
            System.out.print("Enter the element : ");
            list.add(in.nextInt());
        }
        System.out.println("List : " + list);

//        different o/p methods :
        System.out.println(list);
        for(int i = 0 ; i < 5 ; i++){
            System.out.println(list.get(i));
        }
    }
}
