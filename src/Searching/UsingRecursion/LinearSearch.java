package Searching.UsingRecursion;

public class LinearSearch {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9,4,4};
        int target = 4;
        System.out.println(linSearch(arr , target));
    }

//    iterative approach :
    static boolean search(int[] arr , int target){
        for(int ele : arr){
            if(ele == target)
                return true;
        }
        return false;
    }

//    recursive approach :

//    helper fn finds the index where we find the target
    static boolean helper(int[] arr , int index , int target){

//        base condition :
        if(arr[index] == target)
            return true;            // target found at any index
        if (index == arr.length-1)
            return false;           // reached the end of the array but target not found

        return helper(arr , index +1 , target);
    }
    static boolean linSearch(int[] arr , int target){
        if(arr == null || arr.length == 0)
            return false;
        return helper(arr , 0 , target);
    }
}
