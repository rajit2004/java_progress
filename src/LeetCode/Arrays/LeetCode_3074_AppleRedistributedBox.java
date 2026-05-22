package LeetCode.Arrays;

import java.util.Arrays;
import java.util.Collections;

public class LeetCode_3074_AppleRedistributedBox {
    public static void main(String[] args) {
        int[] apples = {1,8,3,3,5};
        int[] capacity = {3,9,5,1,9};
        System.out.println(minimumBoxes(apples,capacity));
    }
    static int minimumBoxes(int[] apples, int[] capacity){
        int minbox = 0;

//        sum of apples
        int sum = 0;
        for(int apple : apples)
            sum += apple;

//        now we check if the net sum of apples can fit in ith capacity of box
        int biggerbox = 0;

//        sorting the capacity array in reverse order.
        Integer[] boxed = new Integer[capacity.length];
        for (int i = 0; i < capacity.length; i++)
            boxed[i] = capacity[i];
        Arrays.sort(boxed, Collections.reverseOrder());

        for (int storage : boxed){
            if (sum>biggerbox){
                biggerbox += storage;
                minbox++;
            }
        }
        return minbox;
    }
}
