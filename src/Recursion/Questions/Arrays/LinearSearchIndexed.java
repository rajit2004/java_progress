package Recursion.Questions.Arrays;

public class LinearSearchIndexed {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9,4,4};
        int target = 4;
        System.out.println(find(arr , target));

    }
    static int helper(int[] arr , int target , int index){

//        base condition :
        if(arr[index] == target)
            return index;               // target found

        if(index == arr.length-1)
            return -1;                  // target not found

//        recursively call :
        return helper(arr , target  ,index+1);
    }
    static int find(int[] arr , int target){
        if(arr == null || arr.length == 0)
            return -1;
        return helper(arr , target , 0);
    }
}
