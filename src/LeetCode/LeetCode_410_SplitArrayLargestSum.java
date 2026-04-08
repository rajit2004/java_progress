package LeetCode;

/*
Given an integer array nums and an integer k, split nums into k non-empty subarrays
such that the largest sum of any subarray is minimized.

Return the minimized largest sum of the split.

A subarray is a contiguous part of the array.
*/


public class LeetCode_410_SplitArrayLargestSum {
    public static void main(String[] args) {
        int[] arr = {1,1,1,1,1,3,2};
        int m = 5;           // tells us how many subarrays we have to make from the array
        System.out.println(sumSubArrays(arr,m));
    }
    static int max(int[] arr){
        int max = arr[0];
        for(int ele : arr){
            max = Math.max(max, ele);
        }
        return max;
    }

    static int sum(int[]arr){
        int ans = 0;
        for(int ele : arr){
            ans+=ele;
        }
        return ans;
    }

    static int sumSubArrays(int[] arr , int m ){
        int start = max(arr);             // case => m = size of array => each ele in one subarray , ans = max ele in array
        int end = sum(arr);

        while(start<end){
            int mid = start + (end-start)/2;

//            calculate that how many pieces we can divide the array

            int ans = 0;
            int pieces = 1;
            for(int ele : arr){
                if(ans+ele > mid){          // no more addition is possible as the ans exceeds the mid
                    ans = ele;
                    pieces++;               // form a new sub array hence count++
                }
                else{
                    ans+=ele;
                }
            }
            if(pieces>m)
                start = mid+1;
            else
                end = mid;

        }
        return end;
    }

//    everything in a single fn no extra helper fn.
    static int splitArray(int[] nums, int m) {

        int start = 0, end = 0;
        for (int num : nums) {
            start = Math.max(start, num);
            end += num;
        }

        while (start < end) {
            int mid = start + (end - start) / 2;

            int sum = 0;
            int pieces = 1;

            for (int num : nums) {
                if (sum + num > mid) {
                    sum = num;
                    pieces++;
                    if (pieces > m) break;
                } else {
                    sum += num;
                }
            }

            if (pieces > m) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }

        return start;
    }
}