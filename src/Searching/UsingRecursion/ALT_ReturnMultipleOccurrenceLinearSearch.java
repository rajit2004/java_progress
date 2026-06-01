package Searching.UsingRecursion;

import java.util.ArrayList;

public class ALT_ReturnMultipleOccurrenceLinearSearch {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9,4,4};
        indexes(arr , 4 , 0);
        System.out.println(ans);
    }
    static ArrayList<Integer> ans = new ArrayList<>();

    static void indexes(int[] arr , int target , int index){

//        base condition :
        if(index == arr.length)
            return;

        if (arr[index] == target )
            ans.add(index);

//        recursive call :
        indexes(arr , target , index+1);
    }
}
