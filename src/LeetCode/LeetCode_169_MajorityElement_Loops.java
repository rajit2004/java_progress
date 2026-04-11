package LeetCode;

// simple linear search and count

public class LeetCode_169_MajorityElement_Loops {
    public static void main(String[] args) {
        int[] arr = {2,2,1,1,1,2,2};
        System.out.println(majorityElement(arr));
        System.out.println(majorityElement2(arr));

    }
    static int majorityElement(int[] nums) {
        int n = nums.length;

        for(int i = 0; i < n; i++){
            int count = 0;

            for(int j = 0; j < n; j++){
                if(nums[j] == nums[i]){
                    count++;
                }
            }

            if(count > n / 2){
                return nums[i];
            }
        }

        return -1;
    }
    static int majorityElement2(int[] nums) {
        int n = nums.length;
        int i = 0;

        while(i < n){
            int count = 0;
            int j = 0;

            while(j < n){
                if(nums[j] == nums[i]){
                    count++;
                }
                j++;
            }

            if(count > n / 2){
                return nums[i];
            }

            i++;
        }

        return -1;
    }
}

