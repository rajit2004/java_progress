package LeetCode;

//https://leetcode.com/problems/find-peak-element/description/

public class LeetCode_162_FindPeakElementBruteForce {
    public static void main(String[] args) {
        int[] nums = {1,2,3,1};
        System.out.println(findPeakElement(nums));
    }
    static int findPeakElement(int[] arr){
        int max = arr[0];
        int index = 0;
        for(int i = 0 ; i <arr.length; i++){
            if(arr[i]>max){
                max = arr[i];
                index = i;
            }
        }
        return index;
    }
}
