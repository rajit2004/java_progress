package LeetCode;

/*
Intuition : Given nums from 0 to n
1. find sum of all nums from 0 to n
2. subtract each element present in array from the sum
3. remaining num = missing element
*/

public class LeetCode_268_MissingNum_0ms_answer {
    public static void main(String[] args) {
        int[] arr = {1,2};
        System.out.println(missingNumber(arr));
    }
    static int missingNumber(int[] nums) {
        int n=nums.length;
        int sum=(n*(n+1))/2;

        for (int num : nums) {
            sum -= num;
        }

        return sum;
    }
}
