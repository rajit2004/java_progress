package Searching;

public class searchNretrunEle {
    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7,8,9,10};
        int target = 5;

        System.out.println(find(array , target));
    }
    static int find(int[] arr , int target){
        if(arr.length == 0 )
            return -1;              // if array is empty
        for (int element : arr) {
            if (element == target)
                return target;
        }
        return -1;          // not found case
    }
}
