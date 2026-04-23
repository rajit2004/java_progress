package LeetCode;

public class LeetCode_153_MinInRotatedSortedArray {
    public static void main(String[] args) {
        int[] arr = {3,4,5,0,1,2};
        System.out.println(minEle(arr));
    }
    static int minEle(int[] arr){
        int min = arr[0];
        for (int element : arr) {
            if (element < min)
                min = element;
        }
        return min;
    }
}
