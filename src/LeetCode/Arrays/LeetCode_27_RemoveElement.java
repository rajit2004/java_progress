package LeetCode.Arrays;

public class LeetCode_27_RemoveElement {
    public static void main(String[] args) {
        int[] arr = {0,1,2,2,3,0,4,2};
        int val = 2;
        System.out.println(removeElement(arr,val));

    }
    static int removeElement(int[] nums, int val){
        int k = 0;

        for(int i = 0 ; i < nums.length ; i++) {
            if (nums[i] != val) {
                nums[k] = nums[i];
                k++;
            }
        }
        return k;
    }
}
