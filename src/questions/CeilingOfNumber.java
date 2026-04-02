package questions;

public class CeilingOfNumber {
    public static void main(String[] args) {
        int[] nums = {2,3,5,9,14,16,18};
        int target = 5;
        System.out.println(ceiling(nums,target));
    }
    static int ceiling(int[] arr , int target){
        int st = 0;
        int end = arr.length-1;
        while(st<=end){
            int mid = st + (end-st)/2;
            if(target==arr[mid]){
                return arr[mid];
            }
            if(target<arr[mid]){
                end = mid-1;
            }
            else {
                st = mid + 1;
            }
        }
        return arr[st];
    }
}
