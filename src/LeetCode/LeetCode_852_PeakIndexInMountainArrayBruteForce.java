package LeetCode;

//https://leetcode.com/problems/peak-index-in-a-mountain-array/description/

public class LeetCode_852_PeakIndexInMountainArrayBruteForce {
    public static void main(String[] args) {
        int[] array = {1, 3, 7, 12, 18, 25, 21, 16, 10, 5, 2};
        System.out.println(maxEleIndex(array));
    }
    static int maxEleIndex(int[] arr){
        int max = arr[0];
        int index = 0;

        for(int i = 0; i < arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
                index = i;
            }
        }
        return index;
    }
}
