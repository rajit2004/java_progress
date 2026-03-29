package searching;

public class boolSearch {
    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7,8,9,10};
        int target = 5;

        System.out.println(find(array , target));
    }
    static boolean find(int[] arr , int target){
        if(arr.length == 0 )
            return false;              // if array is empty
        for (int element : arr) {
            if (element == target)
                return true;
        }
        return false;          // not found case
    }
}
