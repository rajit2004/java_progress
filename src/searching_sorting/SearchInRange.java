package searching_sorting;

import java.util.Arrays;
import java.util.Scanner;

public class SearchInRange {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] fruits = {"Apple", "Banana", "Mango", "Orange", "Pineapple", "Grapes",
                "Papaya", "Guava", "Watermelon", "Strawberry", "Blueberry", "Raspberry",
                "Cherry", "Peach", "Pear", "Plum", "Kiwi", "Lemon", "Lime", "Pomegranate",
                "Coconut", "Apricot", "Fig", "Dragon fruit", "Lychee"};

        System.out.println(Arrays.toString(fruits));
        System.out.print("Enter the target word : ");
        String target = in.nextLine();
        System.out.print("Enter the range to search element from (st end) : ");
        int index1 = in.nextInt();
        int index2 = in.nextInt();

        System.out.println(SearchFruit(fruits , target , index1 , index2));

    }
    static String SearchFruit(String[] arr , String target ,int st , int end){

        if (st < 0 || end >= arr.length || st > end)
            return "invalid range!";

        for(int i = st ; i <= end ; i++){
            if (arr[i].equalsIgnoreCase(target))
                return "Found at index : "+ i;
        }
        return "Doesn't exist in the given range!";
    }
}
