package arrays;
import java.util.ArrayList;
public class arraylist_basics {
    public static void main(String[] args) {
        ArrayList<Integer> list1 = new ArrayList<>(5);

//      adding elements :
        list1.add(23);
        list1.add(44);
        list1.add(12);
        list1.add(76);
        list1.add(84);
        list1.add(69);
        list1.add(22);
        list1.add(1);
        list1.add(93);
        list1.add(77);

//      printing list :
        System.out.println(list1);

//      finding if an element exists or not ?
        System.out.println("Element exists ? " + list1.contains(55));
        System.out.println("Element exists ? " + list1.contains(23));

//        modifying an existing element:
        System.out.println("Original list : " + list1);
        list1.set(2,90);
        list1.set(5,10);
        list1.set(9,13);
        list1.set(0,70);
        System.out.println("Modified list : " + list1);

//        removing an element :
        list1.remove(1);
        list1.remove(6);
        list1.remove(7);

        System.out.println("List after deletion : " + list1);
    }
}
