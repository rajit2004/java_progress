package Sorting.UsingRecursion;

public class ALT_SortArray {
    public static void main(String[] args) {
        int[] arr = {1,2,4,8,12};
        System.out.println(sorted(arr, 0));
    }
    static boolean sorted(int[] arr , int index){

//        base condition : when we have reached the last index -> the array was sorted
        if(index == arr.length - 1)
            return true;

//        recursive call : first we check whether the iTh ele is smaller than the i+1 ele or not then we recursively call the fn to check the array
//                              for i+1 index and so on, and we use 'AND' to get the result
        return arr[index] < arr[index+1] && sorted(arr , index + 1);
    }

}
